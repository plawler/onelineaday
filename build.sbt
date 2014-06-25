name := "onelineaday"

version := "1.0-SNAPSHOT"

resolvers += Resolver.url("sbt-plugin-releases", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.webjars" %% "webjars-play" % "2.2.1",
  "org.webjars" % "bootstrap" % "3.0.1",
  "org.webjars" % "jquery" % "1.10.2",
  "org.webjars" % "bootstrap-glyphicons" % "bdd2cbfba0",
  "org.webjars" % "font-awesome" % "4.0.3",
  "com.github.nscala-time" %% "nscala-time" % "0.6.0",
  "org.mockito" % "mockito-all" % "1.9.5",
  "securesocial" %% "securesocial" % "2.1.2",
  "postgresql" % "postgresql" % "9.1-901.jdbc4"
)

play.Project.playScalaSettings
