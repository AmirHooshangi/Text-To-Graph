package com.velorin.conceptmining

import opennlp.tools.cmdline.parser.ParserTool
import opennlp.tools.parser.Parse
import opennlp.tools.parser.Parser
import opennlp.tools.parser.ParserFactory
import opennlp.tools.parser.ParserModel
import opennlp.tools.sentdetect.SentenceDetectorME
import opennlp.tools.sentdetect.SentenceModel
//import scala.collection.mutable.Set

class NLPConceptMiner extends ConceptMiner {

  val concepts = Set[String]()

  def getListOfConcepts(text: String): Set[String] = {
    val parser = ParserFactory.create(NLPConceptMiner.parserModel)
    val listOfSentences = getListOfSentences(text)
    val parses: Array[Parse] = listOfSentences.flatMap(x => ParserTool.parseLine(x, parser, 1))
    val concepts = parses.flatMap(recursiveTypefinder)
    println("Size of List is = " + concepts.toSet.size)
    concepts.toSet
//    for (tokenizedSentence <- listOfSentences) {
//      var parses: Array[Parse] = ParserTool.parseLine(tokenizedSentence, parser, 1)
//      concepts ::: recursiveTypefinder(parses(0))
//    }

  }

  def recursiveTypefinder1(parse: opennlp.tools.parser.Parse): Unit = {
    /*
     * TODO: CHECKING ONE OR TWO WORDS NP's
     */

    if (parse.getType().equals("NN") || parse.getType().equals("JJ")
      || parse.getType().equals("NNS")
      || parse.getType().equals("NNP")) {
      println(parse.getCoveredText)
//      concepts.add(parse.getCoveredText)
    }

    for (child <- parse.getChildren) {
      recursiveTypefinder1(child)
    }
  }

  def recursiveTypefinder(parse: opennlp.tools.parser.Parse): List[String] = {
    val concepts = parse.getType match {
      case "NN" | "JJ" | "NNS" | "NNP"  => {   List(parse.getCoveredText) }
      case _ => Nil
    }
    concepts ::: parse.getChildren.flatMap(recursiveTypefinder).toList
  }

  def getListOfSentences(paragraph: String): Array[String] = {
    val sentenceFinderME = new SentenceDetectorME(NLPConceptMiner.sentenceModel)
    sentenceFinderME.sentDetect(paragraph)
  }
}

object NLPConceptMiner {

  val parserModel = new ParserModel(getClass.getResource("/en-parser-chunking.bin"));
  val sentenceModel = new SentenceModel(getClass.getResource("/en-sent.bin"));
}
