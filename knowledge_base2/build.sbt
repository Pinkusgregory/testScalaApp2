name := "knowledge_base2"
 
version := "1.0" 
      
lazy val `knowledge_base2` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc ,
  ehcache ,
  ws ,
  specs2 % Test ,
  guice,
  "org.squeryl" %% "squeryl" % "0.9.5-7",
  "org.postgresql" % "postgresql" % "9.3-1102-jdbc4",
  evolutions
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      