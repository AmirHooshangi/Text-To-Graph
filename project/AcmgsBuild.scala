import sbt._
import sbt.Keys._

object AcmgsBuild extends Build {

  lazy val acmgs = Project(
    id = "acmg-s",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "ACMG-S",
      fork in run := true,
      organization := "com.velorin",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.4",
      resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
      resolvers +=  "sonatype-snapshots" at  "https://oss.sonatype.org/content/repositories/snapshots/",
      resolvers += "neo4j-releases" at "http://m2.neo4j.org/releases/",
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
      libraryDependencies ++= Seq(
        "com.typesafe.akka" %% "akka-actor" % "2.3.6",
        "org.scalatest" %% "scalatest" % "2.2.1" % "test",
        "org.apache.opennlp" % "opennlp-tools" % "1.5.3",
        "edu.cmu.lti" % "ws4j" % "1.0.1",
        "edu.cmu.lti" % "jawjaw" % "1.0.2",
        "org.neo4j" % "neo4j" % "2.1.3",
        "eu.fakod" %% "neo4j-scala" % "0.3.1-SNAPSHOT",
        "org.apache.tika" % "tika-core" % "1.6",
        "org.apache.tika" % "tika-parsers" % "1.6"
      )
    )
  )
}
