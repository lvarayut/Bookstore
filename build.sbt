name := "BookStore"

version := "1.0-SNAPSHOT"

resolvers ++= Seq(
  "play-vaadin-integration Snapshots" at "http://henrikerola.github.io/repository/snapshots/",
  "Vaadin addons" at "http://maven.vaadin.com/vaadin-addons",
  "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  // Vaadin
  "com.vaadin" % "vaadin-server" % "7.1.12",
  "com.vaadin" % "vaadin-client-compiled" % "7.1.12",
  "com.vaadin" % "vaadin-themes" % "7.1.12",
  "org.vaadin.playintegration" %% "play-vaadin-integration" % "0.1-SNAPSHOT",
  "org.vaadin.addons" % "scaladin" % "3.0.0",
  // Webjar for bootstrap
  "org.webjars" %% "webjars-play" % "2.2.1-2",
  "org.webjars" % "bootstrap" % "3.1.0",
  //Cassandra Plugin
  //"com.github.filosganga" %% "play-cassandra" % "1.0-SNAPSHOT"
  // Jongo, MongoDb Java driver wrapper
  "de.undercouch" % "bson4jackson" % "2.1.0" force(),
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.1.0" force(),
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.1.0" force(),
  "com.fasterxml.jackson.core" % "jackson-core" % "2.1.0" force(),
  "org.mongodb" % "mongo-java-driver" % "2.11.3",
  "org.jongo" % "jongo" % "1.0",
  "uk.co.panaxiom" %% "play-jongo" % "0.6.0-jongo1.0"
)

play.Project.playJavaSettings

playAssetsDirectories <+= baseDirectory / "VAADIN"