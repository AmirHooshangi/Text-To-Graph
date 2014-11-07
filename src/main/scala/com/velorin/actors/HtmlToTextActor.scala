package com.velorin.actors

import akka.actor.Actor
import akka.actor.ActorRef
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.ParseContext
import org.apache.tika.parser.html.HtmlParser
import org.apache.tika.sax.BodyContentHandler
import org.xml.sax.ContentHandler


class HtmlToTextActor(actorRef: ActorRef) extends Actor {

  def receive =  {
    case URLEvent(url: URL, text: String ) => {
      println("message recieved i repeat ! message recieved")
      actorRef ! new URLEvent(url, extractText(url))
      }
  }

  def extractText(url: URL) : String = {
    val input:  InputStream =  url.openStream()
    val handler: ContentHandler = new BodyContentHandler()
    val metadata: Metadata  = new Metadata()
    new HtmlParser().parse(input, handler, metadata, new ParseContext())
    handler.toString()
//    println(plainText)
  }
}
