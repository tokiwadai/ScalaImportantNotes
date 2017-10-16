import Dependencies._

lazy val commonSettings = Seq(
  organization := "com.hope",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.8"
)

lazy val root = (project in file("."))
  .settings(
    commonSettings
  )


lazy val core = (project in file("core"))
  .settings(
    commonSettings,
    libraryDependencies ++= backendDeps
  )

lazy val akka = (project in file("akka"))
  .settings(
    commonSettings,
    libraryDependencies ++= backendDeps,
    libraryDependencies ++= akkaDeps
  )

lazy val akkaHttp = (project in file("akka-http"))
  .settings(
    commonSettings,
    libraryDependencies ++= backendDeps,
    libraryDependencies ++= akkaDeps,
    libraryDependencies ++= akkaHttpDeps
  )

lazy val akkaHttpSwagger = (project in file("akka-http-swagger"))
  .settings(
    commonSettings,
    libraryDependencies ++= backendDeps,
    libraryDependencies ++= akkaDeps,
    libraryDependencies ++= akkaHttpDeps,
    libraryDependencies ++= akkaSwaggerDeps
  )