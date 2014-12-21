package com.velorin.wordnet

import edu.cmu.lti.ws4j.RelatednessCalculator
import edu.cmu.lti.ws4j.impl._
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
      case "JiangConrath" => {
        val rc: RelatednessCalculator = new JiangConrath(db)
        rc.calcRelatednessOfWords(_ , _)
      }
      case "LeacockChodorow" => {
        val rc: RelatednessCalculator = new LeacockChodorow(db)
        rc.calcRelatednessOfWords(_ , _)
      }
      case "Lesk" => {
        val rc: RelatednessCalculator = new Lesk(db)
        rc.calcRelatednessOfWords(_ , _)
      }
      case "Lin" => {
        val rc: RelatednessCalculator = new Lin(db)
        rc.calcRelatednessOfWords(_ , _)
      }
      case "Resnik" => {
        val rc: RelatednessCalculator = new Resnik(db)
        rc.calcRelatednessOfWords(_ , _)
      }
      case "Path" => {
        val rc: RelatednessCalculator = new Path(db)
        rc.calcRelatednessOfWords(_ , _)
      }
      case _ => null
    }

    function
  }

}
