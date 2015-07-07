name := "ticket-system"

scalaVersion in ThisBuild := "2.10.4"

scalacOptions ++= Seq("-feature")

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.webjars" %% "webjars-play" % "2.2.0",
  "org.webjars" % "bootstrap" % "3.1.1",
  "org.webjars" % "html5shiv" % "3.7.0",
  "org.webjars" % "respond" % "1.4.2",
  "com.typesafe.play" %% "play-slick" % "0.6.0.1",
  "com.typesafe.slick" %% "slick-extensions" % "2.0.0",
  "mysql" % "mysql-connector-java" % "5.1.31",
  "org.xerial" % "sqlite-jdbc" % "3.7.2",
  "postgresql" % "postgresql" % "9.1-901.jdbc4"
)

play.Project.playScalaSettings

