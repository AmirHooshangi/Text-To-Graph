package com.velorin.actors

import akka.actor.Actor
import akka.actor.ActorRef
import java.net.URL
import com.velorin.conceptmining.ConceptMiner
import com.velorin.conceptmining.NLPConceptMiner

class ConceptMinerActor(actorRef: ActorRef) extends Actor{

 def receive =  {
    case URLEvent(url: URL, text: String, setOfConcepts: List[String]) => {
      NLPConceptMiner.getListOfConcepts(text).foreach(println)
      actorRef ! URLEvent(url, "a", NLPConceptMiner.getListOfConcepts(text).toList)
      }
  }
}
