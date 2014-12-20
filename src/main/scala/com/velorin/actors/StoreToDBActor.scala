package com.velorin.actors

import java.net.URL

import akka.actor.Actor
import com.velorin.model.{Node, NodeList}
import eu.fakod.neo4jscala.{Cypher, EmbeddedGraphDatabaseServiceProvider, Neo4jWrapper}

import scala.sys.ShutdownHookThread

/**
 * Created by amir on 12/12/14.
 */


trait Test_MatrixBase {
  val name: String
  val value: String
}

case class Test_Matrix(name: String, value: String) extends Test_MatrixBase


class StoreToDBActor extends Actor with Neo4jWrapper with EmbeddedGraphDatabaseServiceProvider with Cypher {

 /* ShutdownHookThread {
    shutdown(ds)
  }
*/
 //TODO: should move to the another class
 override def neo4jStoreDir: String = "db/test"



  def receive  = {
    case SaveToDBEvent(url: URL, concepts: List[List[NodeList]]  ) => {

      for(i <- concepts)
        for(j <- i)
        storeToDB(j)

      shutdown(ds)

      println("finished..........................................")
    }
  }

  def storeToDB(concepts: NodeList) = {

    for(i <- concepts.listOfNodes) {
      storePairs(concepts.head, i)

    }
  }
 
  def storePairs(head: Node, node2: Node) = {
    if(!exists(head.name)) create_node(head.name, head.name)
    if(!exists(node2.name)) create_node(node2.name, node2.name)
    createRelation(head, node2)
  }

  def createRelation(node1: Node, node2: Node) = {
    val query = "START n=node(*), m=node(*)  " +
      "\n where has(n.name) and has(m.name) and n.name = "+ "\'"+ node1.name + "\'" +
      "\n and m.name = " + "\'" +  node2.name + "\'" +
      "\n create (n)-[r:R { weight : " + "\'" + node2.weight + "\'" +" }]-(m)"
    withTx {
      implicit neo =>
        query.execute
    }
  }

  def create_node(name: String, value: String) = {
    withTx {
      implicit neo =>
        createNode(Test_Matrix(name, value))
    }
  }

  def exists(name: String): Boolean = {
    val query = "start n=node(*) where has(n.name) AND n.name=" + "\""+ name + "\"" + " return n"
    withTx {
      implicit neo =>
        val typedResult = query.execute.asCC[Test_Matrix]("n")
        if(typedResult.toList.isEmpty) false else true
    }
  }


}
