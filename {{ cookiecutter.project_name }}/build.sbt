name := "{{ cookiecutter.project_name }}"

version := "0.1"

scalaVersion := "2.12.14"

val versions = new {
  val spark = "3.1.1"
  val aws = "1.11.563"
}

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % versions.spark % Provided,
  "org.apache.spark" %% "spark-sql" % versions.spark % Provided,
  "org.apache.spark" %% "spark-hive" % versions.spark % Provided,
  "com.amazonaws" % "aws-java-sdk-ssm" % versions.aws % Provided,
  "org.reflections" % "reflections" % "0.9.12",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "com.github.pathikrit" %% "better-files" % "3.9.1",
  "com.github.scopt" %% "scopt" % "3.7.1",

  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

Compile / run := Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run)).evaluated

Test / fork := true
Test / parallelExecution := false

javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled")

assembly / assemblyMergeStrategy := {
  case "META-INF/services/org.apache.spark.sql.sources.DataSourceRegister" => MergeStrategy.concat
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

assemblyPackageScala / assembleArtifact := false