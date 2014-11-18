package com.velorin.conceptmining

import opennlp.tools.cmdline.parser.ParserTool
import opennlp.tools.parser.Parse
import opennlp.tools.parser.Parser
import opennlp.tools.parser.ParserFactory
import opennlp.tools.parser.ParserModel
import opennlp.tools.sentdetect.SentenceDetectorME
import opennlp.tools.sentdetect.SentenceModel

object NLPConceptMiner extends ConceptMiner {

  val parserModel = new ParserModel(getClass.getResource("/en-parser-chunking.bin"));
  val sentenceModel = new SentenceModel(getClass.getResource("/en-sent.bin"));

  def getListOfConcepts(text: String): Set[String] = {
    val parser = ParserFactory.create(NLPConceptMiner.parserModel)
    val listOfSentences = getListOfSentences(text)
    val parses: Array[Parse] = listOfSentences.flatMap(x => ParserTool.parseLine(x, parser, 1))
    val concepts = parses.flatMap(recursiveTypefinder)
    concepts.toSet
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
