package com.velorin

import akka.actor._
import akka.routing.FromConfig
import com.velorin.actors._
import com.velorin.actors.URLEvent
import java.net.URL

object Acmgs extends App {

  val system = ActorSystem("ConceptMapSystem")
  val storeToDBActor: ActorRef = system.actorOf(FromConfig.props(Props[StoreToDBActor]), "store_db")
  val relationFinderActor: ActorRef = system.actorOf(Props(new RelationFinderActor(storeToDBActor)), "relation_finder")
  val conceptMinerActor: ActorRef = system.actorOf(Props(new ConceptMinerActor(relationFinderActor)), "concept_miner")
  val html2textActor: ActorRef = system.actorOf(Props(new HtmlToTextActor(conceptMinerActor)), "html2text")
 // html2textActor ! new URLEvent(new URL("file:///Users/amir/Desktop/Computer.html"), "a", List())
   html2textActor ! new URLEvent(new URL("https://support.apple.com/en-us/HT202225"), "a", List())

}
