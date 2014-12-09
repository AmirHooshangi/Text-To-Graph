package com.velorin.actors

import akka.actor.Actor
import akka.actor.ActorRef
import java.net.URL
import com.velorin.conceptmining.ConceptMiner
import com.velorin.conceptmining.NLPConceptMiner
import com.velorin.wordnet.WsFactory

class RelationFinderActor extends Actor{

 def receive =  {
    case URLEvent(url: URL, text: String, concepts: List[String]) => {

      val listOfLists = for { i <- 0 to concepts.size - 1

                              relatedList = relatedConcepts(concepts(i), concepts.slice( i+1, concepts.size))

      } yield  relatedList

      listOfLists.filter(_ != null).foreach(println)
    }
  }

  def relatedConcepts(arg1: String, arg2: List[String]) : List[String] = {

    val list = for { i <- arg2; if areRelated(arg1,i)} yield i
    if(list.isEmpty) null else list
  }

  def areRelated(concept1: String, concept2: String): Boolean = {

    val function =  WsFactory.calculationFunction("WuPalmer")
    println("similiarity bet " + concept1 + " and " + concept2 + " = " + function(concept1,concept2))
    if(function(concept1,concept2) > 0.4 ) true else false
  }
}
