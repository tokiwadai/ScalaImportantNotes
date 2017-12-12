import sbt._

object Dependencies {
  // Versions
  lazy val akkaVersion = "2.5.4"
  lazy val akkaHttpVersion = "10.0.10"
  lazy val twitVersion = "4.+"

  // Libraries
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaCluster = "com.typesafe.akka" %% "akka-cluster" % akkaVersion
  val specs2core = "org.specs2" %% "specs2-core" % "2.5"

  val akkaStreamDep = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  val akkaHttpDep = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
  // https://mvnrepository.com/artifact/io.spray/spray-json_2.11
  val ioSprayJsonDep = "io.spray" %% "spray-json" % "1.3.3"
  // https://mvnrepository.com/artifact/com.typesafe.akka/akka-http-spray-json_2.11
  val akkaHttpSprayJson = "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion

  val ioSwagger = "io.swagger" % "swagger-jaxrs" % "1.5.16"
  val swaggerAkka = "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.11.0"
  val httpCors = "ch.megard" %% "akka-http-cors" % "0.2.1"
  val twitter = "org.twitter4j" % "twitter4j-stream" % twitVersion

  val scalatest = "org.scalatest" %% "scalatest" % "3.0.4"
  val scalacheck = "org.scalacheck" %% "scalacheck" % "1.13.5"
  val scalameter = "com.storm-enroute" %% "scalameter" % "0.8.2"
  val junit = "junit" % "junit" % "4.10"
  val scalatic = "org.scalactic" %% "scalactic" % "3.0.4"
  // https://mvnrepository.com/artifact/com.typesafe.akka/akka-testkit_2.11
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion

  // Dependencies
  val backendDeps =
  Seq(akkaActor,
    specs2core % Test, scalatest, scalacheck % Test, scalameter % Test, junit % Test, scalatic % Test)
  val akkaDeps = Seq(akkaActor, akkaTestkit, akkaStreamDep, twitter)
  val akkaHttpDeps = Seq(akkaHttpSprayJson, akkaStreamDep, akkaHttpDep)
  val akkaSwaggerDeps = akkaHttpDeps :+ ioSwagger :+ swaggerAkka :+ httpCors
}