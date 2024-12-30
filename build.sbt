ThisBuild / version := "1.0"
ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
    name := "Skanban",
    libraryDependencies := Seq(
      "io.circe" %% "circe-core" % "0.14.10",
      "io.circe" %% "circe-generic" % "0.14.10",
      "io.circe" %% "circe-parser" % "0.14.10",
      "org.scalafx" % "scalafx_3" % "23.0.1-R34",
      "org.scalatest" %% "scalatest" % "3.2.19" % "test"
    )
  )
