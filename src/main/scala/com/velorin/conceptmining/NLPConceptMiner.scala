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



/*

  These are the list of tags you can use:

  CC Coordinating conjunction
  CD Cardinal number
  DT Determiner
  EX Existential there
  FW Foreign word
  IN Preposition or subordinating conjunction
  JJ Adjective
  JJR Adjective, comparative
  JJS Adjective, superlative
  LS List item marker
  MD Modal
  NN Noun, singular or mass
  NNS Noun, plural
  NNP Proper noun, singular
  NNPS Proper noun, plural
  PDT Predeterminer
  POS Possessive ending
  PRP Personal pronoun
  PRP$ Possessive pronoun
  RB Adverb
  RBR Adverb, comparative
  RBS Adverb, superlative
  RP Particle
  SYM Symbol
  TO to
  UH Interjection
  VB Verb, base form
  VBD Verb, past tense
  VBG Verb, gerund or present participle
  VBN Verb, past participle
  VBP Verb, non 3rd person singular present
  VBZ Verb, 3rd person singular present
  WDT Wh­determiner
  WP Wh­pronoun
  WP$ Possessive wh­pronoun
  WRB Whadverb
*/
  def recursiveTypefinder(parse: opennlp.tools.parser.Parse): List[String] = {
    val concepts = parse.getType match {
      //TODO: two words NN, removing 's and dots
      case "NN" | "JJ" /* | "NNS" | "NNP" */  => { List(if(parse.getCoveredText.contains(".") |
        parse.getCoveredText.contains("-") |
        parse.getCoveredText.contains(",") | parse.getCoveredText.contains("’s") | parse.getCoveredText.contains("\"") )
        parse.getCoveredText.replaceAll("(\"|.|’s|'s|,)", "")
        else parse.getCoveredText)  }
      case _ => Nil
    }
    concepts ::: parse.getChildren.flatMap(recursiveTypefinder).toList
  }

  def getListOfSentences(paragraph: String): Array[String] = {
    val sentenceFinderME = new SentenceDetectorME(NLPConceptMiner.sentenceModel)
    sentenceFinderME.sentDetect(paragraph)
  }
}
