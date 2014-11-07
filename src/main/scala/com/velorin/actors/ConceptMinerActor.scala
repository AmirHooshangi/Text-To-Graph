package com.velorin.actors

import akka.actor.Actor
import akka.actor.ActorRef
import java.net.URL
import com.velorin.conceptmining.ConceptMiner
import com.velorin.conceptmining.NLPConceptMiner

class ConceptMinerActor extends Actor{

 def receive =  {
    case URLEvent(url: URL, text: String) => {
      val nlpConceptMiner: NLPConceptMiner = new NLPConceptMiner
      nlpConceptMiner.getListOfConcepts(text)
      }
  }

}
