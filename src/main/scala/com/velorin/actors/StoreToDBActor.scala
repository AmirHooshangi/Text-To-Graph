package com.velorin.actors

import java.net.URL

import akka.actor.Actor
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
    case SaveToDBEvent(url: URL, concepts: List[List[String]] ) => {

      for(i <- concepts)
        storeToDB(i)

      shutdown(ds)
    }
  }

  def storeToDB(concepts: List[String]) = {

    for(i <- concepts) {
      storePairs(concepts.head, i)

    }
  }

  def storePairs(first:String, second: String) = {
    if(!exists(first)) create_node(first, first)
    if(!exists(second)) create_node(second, second)
    createRelation(first, second)
  }

  def createRelation(first:String, second: String) = {
    val query = "START n=node(*), m=node(*)  " +
      "\n where has(n.name) and has(m.name) and n.name = "+ "\'"+ first + "\'" +
      "\n and m.name = " + "\'" +  second + "\'" +
      "\n create (n)-[:R]->(m)"
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
