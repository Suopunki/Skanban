lazy val root = (project in file("."))
  .settings(
    name := "Skanban",
    version := "1.0.0",
    scalaVersion := "3.3.5",
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.14.10",
      "io.circe" %% "circe-generic" % "0.14.10",
      "io.circe" %% "circe-parser" % "0.14.10",
      "org.scalafx" % "scalafx_3" % "23.0.1-R34",
      "org.scalameta" %% "munit" % "1.1.0" % Test,
    )
  )
