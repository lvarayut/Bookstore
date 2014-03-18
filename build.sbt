name := "BookStore"

version := "1.0-SNAPSHOT"

resolvers ++= Seq(
  "play-vaadin-integration Snapshots" at "http://henrikerola.github.io/repository/snapshots/"
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
  // Webjar for bootstrap
  "org.webjars" %% "webjars-play" % "2.2.1-2",
  "org.webjars" % "bootstrap" % "3.1.0"
)

play.Project.playJavaSettings

playAssetsDirectories <+= baseDirectory / "VAADIN"