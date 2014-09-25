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
