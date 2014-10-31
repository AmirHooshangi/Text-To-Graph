package com.velorin.conceptmining

import opennlp.tools.cmdline.parser.ParserTool
import opennlp.tools.parser.Parse
import opennlp.tools.parser.Parser
import opennlp.tools.parser.ParserFactory
import opennlp.tools.parser.ParserModel
import opennlp.tools.sentdetect.SentenceDetectorME
import opennlp.tools.sentdetect.SentenceModel



class NLPConceptMiner extends ConceptMiner{

  var parserModel : ParserModel = null
  var sentenceModel : ParserModel = null

  def getListOfConcepts(): List[String] = {
    var a = List("")
      a
  }


}
