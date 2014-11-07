package com.velorin.conceptmining

import opennlp.tools.cmdline.parser.ParserTool
import opennlp.tools.parser.Parse
import opennlp.tools.parser.Parser
import opennlp.tools.parser.ParserFactory
import opennlp.tools.parser.ParserModel
import opennlp.tools.sentdetect.SentenceDetectorME
import opennlp.tools.sentdetect.SentenceModel
import scala.collection.mutable.Set

class NLPConceptMiner extends ConceptMiner {

  val concepts = Set[String]()

  def getListOfConcepts(text: String): Set[String] = {
    val parser = ParserFactory.create(NLPConceptMiner.parserModel)
    val listOfSentences = getListOfSentences(text)

    for (tokenizedSentence <- listOfSentences) {
      var parses: Array[Parse] = ParserTool.parseLine(tokenizedSentence, parser, 1)
      recursiveTypefinder(parses(0))
    }
    concepts
  }

  def recursiveTypefinder(parse: opennlp.tools.parser.Parse): Unit = {
    /*
     * TODO: CHECKING ONE OR TWO WORDS NP's
     */

    if (parse.getType().equals("NN") || parse.getType().equals("JJ")
      || parse.getType().equals("NNS")
      || parse.getType().equals("NNP")) {
      println(parse.getCoveredText)
      concepts.add(parse.getCoveredText)
    }

    for (child <- parse.getChildren) {
      recursiveTypefinder(child)
    }
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
