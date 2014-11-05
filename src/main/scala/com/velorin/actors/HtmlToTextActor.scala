package com.velorin.actors

import akka.actor.Actor

class HtmlToTextActor extends Actor {

  def receive =  {

    case test: String =>
      {
        Thread.sleep(2000)
        println("Helloo world")
      }
  }
}
