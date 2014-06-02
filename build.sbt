name := "BookStore"

version := "1.0-SNAPSHOT"

resolvers ++= Seq(
  //"play-vaadin-integration Snapshots" at "http://henrikerola.github.io/repository/snapshots/",
  //"Vaadin addons" at "http://maven.vaadin.com/vaadin-addons",
  // Cassandra
  //"Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  // SecureSocial
  Resolver.sonatypeRepo("releases")
  // Play-authenticate plugin
//  Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns),
//  Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns),
//  Resolver.url("play-authenticate (release)", url("http://joscha.github.com/play-authenticate/repo/releases/"))(Resolver.ivyStylePatterns),
//  Resolver.url("play-authenticate (snapshot)", url("http://joscha.github.com/play-authenticate/repo/snapshots/"))(Resolver.ivyStylePatterns)
)

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  // Vaadin
  //"com.vaadin" % "vaadin-server" % "7.1.12",
  //"com.vaadin" % "vaadin-client-compiled" % "7.1.12",
  //"com.vaadin" % "vaadin-themes" % "7.1.12",
  //"org.vaadin.playintegration" %% "play-vaadin-integration" % "0.1-SNAPSHOT",
  //"org.vaadin.addons" % "scaladin" % "3.0.0",
  //Webjar for bootstrap
  "org.webjars" %% "webjars-play" % "2.2.1-2",
  "org.webjars" % "bootstrap" % "3.1.1",
  //Cassandra Plugin
  //"com.github.filosganga" %% "play-cassandra" % "1.0-SNAPSHOT",
  // Jongo, MongoDb Java driver wrapper
  "de.undercouch" % "bson4jackson" % "2.1.0" force(),
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.1.0" force(),
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.1.0" force(),
  "com.fasterxml.jackson.core" % "jackson-core" % "2.1.0" force(),
  "org.mongodb" % "mongo-java-driver" % "2.11.3",
  "org.jongo" % "jongo" % "1.0",
  "uk.co.panaxiom" %% "play-jongo" % "0.6.0-jongo1.0",
  // SecureSocial
  "ws.securesocial" %% "securesocial" % "2.1.3",
  // Play-authenticate plugin
  //"com.feth" %% "play-authenticate" % "0.5.2-SNAPSHOT"
  // AngularJS
  "org.webjars" % "angularjs" % "1.3.0-beta.8"
)

play.Project.playJavaSettings

playAssetsDirectories <+= baseDirectory / "VAADIN"
