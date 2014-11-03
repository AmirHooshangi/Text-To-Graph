package com.velorin.conceptmining

import scala.collection.mutable.Set

trait ConceptMiner {

  def getListOfConcepts(text: String): Set[String]
}
