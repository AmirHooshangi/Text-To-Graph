import sbt._
import sbt.Keys._

object AcmgsBuild extends Build {

  lazy val acmgs = Project(
    id = "acmg-s",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "ACMG-S",
      organization := "com.velorin",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.11.2",
      resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
      libraryDependencies ++= Seq(
        "com.typesafe.akka" % "akka-actor_2.10" % "2.3.6",
        "org.apache.opennlp" % "opennlp-tools" % "1.5.3",
        "edu.cmu.lti" % "ws4j" % "1.0.1"
      )
    )
  )
}
