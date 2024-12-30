ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "Kanbafy"
  )

libraryDependencies ++= Seq(
  // GUI
  "org.scalafx" % "scalafx_3" % "20.0.0-R31",
  // Testing
  "org.scalatest" %% "scalatest" % "3.2.18" % "test",
  // JSON
  "io.circe" %% "circe-core" % "0.14.7",
  "io.circe" %% "circe-generic" % "0.14.7",
  "io.circe" %% "circe-parser" % "0.14.7"
)
