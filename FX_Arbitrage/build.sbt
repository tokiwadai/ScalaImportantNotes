import Dependencies._

lazy val commonSettings = Seq(
  organization := "com.fx_arb",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.8"
)

lazy val root = (project in file("."))
  .settings(
    commonSettings
  )


lazy val fxArb = (project in file("FX_Arbitrage"))
  .settings(
    commonSettings,
    libraryDependencies ++= backendDeps,
    libraryDependencies ++= akkaDeps :+ liftjson
)
