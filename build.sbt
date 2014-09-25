name := "lift-mandrill"

version := "0.1.0"

organization := "com.hacklanta"

scalaVersion := "2.10.4"

libraryDependencies ++= {
  val liftVersion = "2.6-RC1"
  Seq(
    "net.liftweb"             %% "lift-common"        % liftVersion,
    "net.liftweb"             %% "lift-util"          % liftVersion,
    "net.liftweb"             %% "lift-json"          % liftVersion,
    "net.databinder.dispatch" %% "dispatch-core"      % "0.11.0",
    "net.databinder.dispatch" %% "dispatch-lift-json" % "0.11.0",
    "org.scalatest"           %% "scalatest"          % "2.0" % "test"
  )
}

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) 
    Some("snapshots" at nexus + "content/repositories/snapshots") 
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

credentials += Credentials(Path.userHome / ".sonatype")

pomExtra :=
<url>https://github.com/hacklanta/lift-mandrill</url>
<licenses>
  <license>
    <name>Apache 2</name>
    <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    <distribution>repo</distribution>
  </license>
</licenses>
<scm>
  <url>https://github.com/hacklanta/lift-mandrill.git</url>
  <connection>https://github.com/hacklanta/lift-mandrill.git</connection>
</scm>
<developers>
  <developer>
    <id>farmdawgnation</id>
    <name>Matt Farmer</name>
    <email>matt@frmr.me</email>
  </developer>
</developers>
