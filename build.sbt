name := """play-bio"""
organization := "compugain"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test
libraryDependencies ++= Seq(  
  "org.webjars" % "bootstrap" % "3.3.7"
)


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "compugain.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "compugain.binders._"
