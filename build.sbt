lazy val root = (project in file("."))
  .settings(
    name := "Kanbafy",
    version := "1.0.0",
    scalaVersion := "3.3.5",
    libraryDependencies ++= Seq(
      "org.scalafx" % "scalafx_3" % "23.0.1-R34",
      "org.scalatest" %% "scalatest" % "3.2.19" % "test",
      "io.circe" %% "circe-core" % "0.14.10",
      "io.circe" %% "circe-generic" % "0.14.10",
      "io.circe" %% "circe-parser" % "0.14.10"
    )
  )
