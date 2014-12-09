package com.velorin.actors

import com.velorin.UnitSpec
import com.velorin.wordnet
import com.velorin.wordnet.WsFactory

class RelationFinderActorTest extends UnitSpec {

  val concepts = List("Computer", "Chat", "Mouse", "Keyboard", "ghasem", "-100")

  val listOfLists = for { i <- 0 to concepts.size - 1

  relatedList = relatedConcepts(concepts(i), concepts.slice( i+1, concepts.size))

  } yield  relatedList

  listOfLists.filter(_ != null).foreach(println)

  def relatedConcepts(arg1: String, arg2: List[String]) : List[String] = {

  val list = for { i <- arg2; if areRelated(arg1,i)} yield i
  if(list.isEmpty) null else list
}


def areRelated(concept1: String, concept2: String): Boolean = {

  val function =  WsFactory.calculationFunction("WuPalmer")
  println("similiarity bet " + concept1 + " and " + concept2 + " = " + function(concept1,concept2))
  if(function(concept1,concept2) > 0 ) true else false
  }

}
