lazy val circeVersion = "0.8.0"
lazy val enumeroVersion = "1.2.1"

lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.0.10"
lazy val akkaHttpCirce = "de.heikoseeberger" %% "akka-http-circe" % "1.18.0"
lazy val circeCore = "io.circe" %% "circe-core" % circeVersion
lazy val circeGeneric = "io.circe" %% "circe-generic" % circeVersion
lazy val wiroHttpServer = "io.buildo" %% "wiro-http-server" % "0.5.2"
lazy val scalatest = "org.scalatest" %% "scalatest" % "3.0.4" % "test"
lazy val typesafeConfig = "com.typesafe" % "config" % "1.3.2"
lazy val enumero = "io.buildo" %% "enumero" % enumeroVersion
lazy val enumeroCirce = "io.buildo" %% "enumero-circe-support" % enumeroVersion
lazy val root = project.in(file("."))
  .settings(
    name := "cryptonite",
    scalaVersion := "2.12.4",
    resolvers += Resolver.bintrayRepo("buildo", "maven"),
    libraryDependencies ++= Seq(
      akkaHttp,
      akkaHttpCirce,
      circeCore,
      circeGeneric,
      wiroHttpServer,
      scalatest,
      typesafeConfig,
      enumero,
      enumeroCirce
    ),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
  )
