ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "3.3.4"

// Dependency versions
val scalaFXVersion = "23.0.1-R34"
val scalaTestVersion = "3.2.19"
val circeVersion = "0.14.10"

lazy val root = (project in file("."))
  .settings(
    name := "Skanban",
    libraryDependencies ++= Seq(
      "org.scalafx" % "scalafx_3" % scalaFXVersion,
      "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion
    )
  )
