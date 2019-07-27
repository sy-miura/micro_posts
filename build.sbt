import com.typesafe.config.{ Config, ConfigFactory }
import scala.collection.JavaConverters._

name := """micro-posts"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
lazy val envConfig = settingKey[Config]("env-config")

envConfig := {
  val env = sys.props.getOrElse("env", "dev")
  ConfigFactory.parseFile(file("env") / (env + ".conf"))
}

scalaVersion := "2.12.0"

TwirlKeys.templateImports ++= Seq("forms._")


libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test

libraryDependencies ++= Seq(
  "org.scalikejdbc"        %% "scalikejdbc"                  % "3.3.5",
  "org.scalikejdbc"        %% "scalikejdbc-config"           % "3.3.5",
  "org.scalikejdbc"        %% "scalikejdbc-test"             % "3.3.5" % Test,
  "org.scalikejdbc"        %% "scalikejdbc-jsr310"              % "2.5.2",
  "org.scalikejdbc"        %% "scalikejdbc-syntax-support-macro" % "3.3.5",
  "org.scalikejdbc"        %% "scalikejdbc-play-dbapi-adapter" % "2.7.1-scalikejdbc-3.3",
  "org.skinny-framework"   %% "skinny-orm"                   % "3.0.0",
  "org.scalikejdbc"        %% "scalikejdbc-play-initializer" % "2.7.1-scalikejdbc-3.3",
  "ch.qos.logback"         %  "logback-classic"              % "1.2.3",
  "mysql"                  %  "mysql-connector-java"         % "6.0.6",
  "com.adrianhurt"         %% "play-bootstrap"                  % "1.2-P26-B3",
  "com.github.t3hnar" %% "scala-bcrypt" % "3.1",
  "org.flywaydb"           %% "flyway-play"                  % "4.0.0"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

flywayLocations := envConfig.value.getStringList("flywayLocations").asScala
flywayDriver := envConfig.value.getString("jdbcDriver")
flywayUrl := envConfig.value.getString("jdbcUrl")
flywayUser := envConfig.value.getString("jdbcUserName")
flywayPassword := envConfig.value.getString("jdbcPassword")