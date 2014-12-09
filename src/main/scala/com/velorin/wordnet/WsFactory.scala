package com.velorin.wordnet

import edu.cmu.lti.ws4j.RelatednessCalculator
import edu.cmu.lti.ws4j.impl.{WuPalmer, HirstStOnge}
import edu.cmu.lti.lexical_db.NictWordNet
import edu.cmu.lti.lexical_db.ILexicalDatabase;


/**
 * Created by amir on 12/9/14.
 */
object WsFactory {

  def calculationFunction(methodName: String): ((String, String) => Double)  = {
    val db: ILexicalDatabase = new NictWordNet

    val function = methodName  match {
      case "HirstStOnge" => {

        val rc: RelatednessCalculator = new HirstStOnge(db)
        rc.calcRelatednessOfWords(_ , _)
      }
      case "WuPalmer" => {
        val rc: RelatednessCalculator = new WuPalmer(db)
        rc.calcRelatednessOfWords(_ , _)
      }
      case _ => null
    }

    function
  }

}
