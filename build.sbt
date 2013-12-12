name := "onelineaday"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.webjars" %% "webjars-play" % "2.2.0",
  "org.webjars" % "bootstrap" % "3.0.1",
  "org.webjars" % "jquery" % "1.10.2",
  "com.github.nscala-time" %% "nscala-time" % "0.6.0",
  "org.mockito" % "mockito-all" % "1.9.5"
)

play.Project.playScalaSettings
