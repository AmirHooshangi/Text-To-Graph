import sbt._
import sbt.Keys._

object AcmgsBuild extends Build {

  lazy val acmgs = Project(
    id = "acmg-s",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "ACMG-S",
      organization := "com.veloring",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.9.2",
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
      libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.3.6"
    )
  )
}
