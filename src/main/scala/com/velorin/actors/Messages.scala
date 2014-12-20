package com.velorin.actors

import java.net.URL

import com.velorin.model.NodeList

case class URLEvent(url: URL, text: String, setOfConcepts: List[String] )

case class SaveToDBEvent(url: URL, concepts: List[List[NodeList]] )

