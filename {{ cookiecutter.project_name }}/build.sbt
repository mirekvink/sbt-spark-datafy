name := "{{ cookiecutter.project_name }}"

version := "0.1"

scalaVersion := "2.12.13"

val versions = new {
  val spark = "3.1.1",
  val aws = "1.11.563"
}

resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % versions.spark % Provided,
  "org.apache.spark" %% "spark-sql" % versions.spark % Provided,
  "com.amazonaws" % "aws-java-sdk-ssm" % versions.aws % Provided,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "com.github.pathikri" %% "better-files" % "3.6.0",
  "com.github.scopt" %% "scopt" % "3.7.1",

  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

run in Compile := Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run)).evaluated

fork in Test := true
javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled")

parallelExecution in Test := false

assemblyMergeStrategy in assembly := {
  case "META-INF/services/org.apache.spark.sql.sources.DataSourceRegister" => MergeStrategy.concat
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}