# install on Mac
  1) search & install older version (NO NEED TO DO THIS, specify the version as below)
     brew search sbt
       sbt@0.13 
     brew install sbt@0.13 
  2) install latest version
     brew install sbt
     (/usr/local/Cellar/sbt/1.1.6: 500 files, 52.5MB, built in 4 seconds)
  3) uninstall sbt
     brew uninstall sbt

# specify sbt version to use: it will fetch the sbt version from internet
  edit project/build.properties (if this file is not provided, it will use the default sbt installed at your mac)
    sbt.version=0.13.15 # use an older version of sbt

# specify artifact version
  edit version.sbt
    version := "0.1.6-SNAPSHOT"
  
# SBT (simple build tool) commands
  an interactive build tool
  a) Project-level tasks
     sbt clean: deletes all generated files (the target directory: i.e. rm -rf target/ projects/target)
  b) Configuration-level tasks
     sbt              : load project (under the same folder of build.sbt), fetch dependencies, and enter console
     sbt compile      : compile the main sources (in the src/main/scala directory)
     sbt run          : this runs the project, i.e. the main classes in the main sources (i.e. src/main/scala)
     sbt test         : compile and run all tests detected during test compilation
     sbt test:compile : compile test sources only without running the tests (i.e. src/test/scala/)
     sbt test:run     : test:run runs main classes defined in the test sources (i.e. src/test/scala/), i.e. similar to "sbt run" which runs main classes in the main sources (i.e. src/main/scala/)
       ex. sbt -Darg=value test:run
       ex. sbt debug clean -Darg=value test:run (this provides debug information and clean the target/ before running the test)
     sbt "testOnly [test_name]": run a specific test, ex. sbt "testOnly com.my_package.MyTest"
     sbt package      : creates jar file containing files in src/main/resources and classes compiled from
                        src/main/scala and src/main/java (only project classes will be included in the jar)
     sbt war          : package for Java container sbt war
     sbt dist         : package for standalone Play package (build a standalone project issue)
       this create a zip file: target/universal/<pkg-ver>.zip, which can be deploy to another machine and run the server via the unzipped script: <pkg-ver>/bin/<pkg> 
     sbt run <args>*  : runs main class of project in the same virtual machine as sbt (run an application)
     sbt -jvm-debug <port>:  Turn on JVM debugging, open at the given port (in IDE, select Run -> Attach to Local Process)
       ex. sbt -jvm-debug 5005 run
     sbt assembly     : build standalone jar with all dependencies 
       requires plugin: add a line to file project/plugins.sbt
         addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")
  c) run sbt commands in batch: ex. sbt clean compile test)
     ex.
       sbt "project project_name" clean format compile test package
       sbt "project project_name" run
       sbt "project project_name" "test-only TestFilename"
  d) interactive mode
     sbt                 : enter interactive mode
     projects            : list projects
     project project_name: set current project to project_name
     testOnly MyTest     : run MyTest only
     ex.
       sbt "project project_name" testOnly com.mydomain.MyClassTest
     run                 : run the project

# sbt dependency management flow
  1) update: resolves dependencies according to the settings of build.sbt file
     ex. libraryDependencies and resolvers
  2) update task needs to run before compile can run
  3) run clean and then update
  4) try to delete files in ~/.ivy2/cache related to problematic dependencies
     ex. ~/.ivy2/cache/org.example/demo/1.0/
     (or deleting ~/.ivy2/cache completely)

# build.sbt: project configuration file
    name := """play-java"""   # project name
    version := "1.0-SNAPSHOT" # project version

# install sbt manually
  1) put sbt-launch.jar in ~/bin
  2) create a script to run the jar, by creating ~/bin/sbt with these contents:
     #!/bin/bash
     SBT_OPTS="-Xms512M -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=256M"
     java $SBT_OPTS -jar `dirname $0`/sbt-launch.jar "$@"
  3) make the script executable:
     $ chmod u+x ~/bin/sbt

# sbt publish
  1) sbt publish
  2) sbt publishLocal (publish to local repo and test the local library)
     first, publish your developping library to a local Ivy repository: it will publish to
     ex. ~/.ivy2/local/[groupid]/[artifact_id]/[version]-SNAPSHOT/poms/[artifact_id].pom
         ~/.ivy2/local/[groupid]/[artifact_id]/[version]-SNAPSHOT/jars/[artifact_id]-assembly.jar
     next, specify the local repository in build.sbt:
       resolvers ++= Seq("Local Maven Repository" at "file:///Users/username/.ivy2/repository")
       # add maven local repo: resolvers ++= Seq("Local Maven Repository" at "file:///Users/username/.m2/repository")

# libraryDependencies
  1) "test" scope:
     if you want a dependency to show up in the classpath only for the Test configuration and not the Compile configuration, add % "test"
     ex. libraryDependencies += "org.specs2" %% "specs2" % "1.7-SNAPSHOT" % "test"
  2) "provided" scope:
     this excludes the dependency from the assembly artifact
  3) changing():
     this specifies that the dependency can change and that it ivy must download it on each update
     i.e. marks this dependency as "changing".  Ivy will always check if the metadata has changed and then if the artifact has changed, redownload it
          sbt configures all -SNAPSHOT dependencies to be changing
     ex. libraryDependencies += "org.specs2" %% "specs2" % "1.7.20" % "provided" changing()

# Forking tests: specifies that all tests will be executed in a single external JVM
fork in Test := true
     
# add local/remote snapshot repo for development:
  create file "dev.sbt" with the following content:
    resolvers in ThisBuild += "My artifactory Snaps" at "http://artifactory.mydomain.com:4080/artifactory/maven-local-snapshot" # remote snapshot repo
    resolvers in ThisBuild += "Local Maven Repository" at Path.userHome.asFile.toURI.toURL + ".m2/repository"                   # local snapshot repo
  or
    resolvers ++= Seq(
      "My artifactory Snaps" at "http://artifactory.mydomain.com:4080/artifactory/maven-local-snapshot" # remote snapshot repo
      "Local Maven Repository" at "file:///Users/kuanyu/.m2/repository"                                 # local snapshot repo
    )

# list dependency tree
sbt "inspect tree clean": list dependency tree of a sbt project
ex.
[info]       +-*:projectInfo = ModuleInfo(feeding-spark,feeding-spark,None,None,List(),com.yahoo.nuwa,None,None,List())
[info]         +-*:description = feeding-spark
[info]         | +-*:name = feeding-spark
[info]         | 
[info]         +-*/*:developers = List()
[info]         +-*/*:homepage = None
[info]         +-*/*:licenses = List()

# shading support
sbt-assembly can shade classes from your projects or from the library dependencies
ex.
assemblyShadeRules in assembly := Seq(
  ShadeRule.rename("org.apache.commons.io.**" -> "shadeio.@1").inAll
)
ex.
if you take on the dependencies as managed dependencies, you can shade them both in the library itself and inside your project
suppose that I have a project which takes dependency on com.typesafe.config
then I can shade it inside it's own library, meaning inside the code of com.typesafe.config, and also in the my consuming project
assemblyShadeRules in assembly ++= Seq(
  ShadeRule.rename("com.typesafe.config.**" -> "my_conf.@1")
    .inLibrary("com.typesafe" % "config" % "1.3.0")
    .inProject
)
1) the above basically means:
   take anything that beings with com.typesafe.config and shade it to my_conf
2) inLibrary("com.typesafe" % "config" % "1.3.0") means:
   change the package names inside com.typesafe.config
3) inProject means:
   change all references to com.typesafe.config inside my code
4) or you can use inAll, which applies to both inLibrary & inProject

# add plugins to projects
1) release & snapshot repo support plugins:
resolvers += Resolver.typesafeRepo("releases")
resolvers += Resolver.sbtPluginRepo("snapshots")

2) other plugins: ex. show dependency graph
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")
addSbtPlugin("com.lightbend.sbt" % "sbt-java-formatter" % "0.2.0")

3) equivalent to maven's release plugin used by java platform
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.4")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")

4) dependency resolver:
a Scala library to fetch dependencies from Maven / Ivy repositories
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0")

5) documentation:
addSbtPlugin("com.lightbend.paradox" % "sbt-paradox" % "0.3.+")

# show dependency tree (i.e. mvn dependency:tree)
1) add plugin to project/plugins.sbt
   addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")
2) add sbt setting in build.sbt (can be skipped)
   net.virtualvoid.sbt.graph.Plugin.graphSettings
3) run sbt commond
   sbt dependencyTree: shows an ASCII tree representation of the project's dependencies
   sbt dependencyGraph: shows an ASCII graph of the project's dependencies on the sbt console (only supported on sbt 0.13)
   sbt dependencyBrowseGraph: show the dependency graph on browser

# sbt plugins
in project/plugin.sbt
1) sbt-scalariform:
   an sbt plugin adding support for source code formatting using Scalariform
   in project/plugin.sbt, add:
     addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.1")
   in build.sbt, add:
     import scalariform.formatter.preferences._              # imports
     scalariformPreferences := scalariformPreferences.value. # set preferences
       setPreference(AlignSingleLineCaseStatements, true).
       setPreference(DoubleIndentConstructorArguments, true).
       setPreference(DanglingCloseParenthesis, Preserve)
   then sources will automatically formatted on compile and test:compile by default
2) sbt-java-formatter:
   an sbt plugin for formating Java code
   in project/plugin.sbt, add:
     addSbtPlugin("com.lightbend.sbt" % "sbt-java-formatter" % JavaFormatterVersion)
   prepare a formatting-java.xml using Eclipse or find a setting online
   in build.sbt, add:
     javaFormattingSettingsFilename := "my-little-formatting-settings.xml"
3) sbt-assembly:
   a sbt plugin originally ported inspired by Maven's assembly plugin
     the goal is to create a fat JAR of the project with all of its dependencies
   in project/plugin.sbt, add:
     addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")
4) sbt-release:
   this sbt plugin provides a customizable release process that you can add to your project
   equivalent to maven's release plugin used by java platform
   in project/plugin.sbt, add:
     addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.4")
   in version.sbt, add:
     version := "1.2.3" or
     version in ThisBuild := "1.5-SNAPSHOT"

# issues
1) sbt test: [info] No tests were executed
   unlike Scala testing frameworks like ScalaTest (which can also run JUnit test cases),
     both JUnit and this adapter are pure Java, so you can run JUnit tests with any Scala version supported by sbt
     without having to build a binary-compatible test framework first
   solution:
     junit-interface: an implementation of sbt's test interface for JUnit 4. This allows you to run JUnit tests from sbt.
     add the following dependency to build.sbt:
       libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
     set default options in build.sbt file:
       testOptions += Tests.Argument(TestFrameworks.JUnit, "-a")
       # this shows stack traces and exception class name for AssertionErrors (thrown by all assert* methods in JUnit)
2) Waiting for lock on /Users/username/.ivy2/.sbt.ivy.lock to be available...
   when resolving dependancies and there are conflicts due to launching two Play consoles
   solution:
     rm /Users/username/.ivy2/.sbt.ivy.lock

# sbt-native-packager plugin
  SBT native packager lets you build application packages in native formats
  1) it offers different archetypes for common configurations, ex. simple Java apps or server applications
  2) it attempts to make building packages for different operating systems easier
     ex. play framework can rely on the plugin to deploy to different operating systems
  3) you need to add the plugin: edit plugins.sbt
       addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "x.y.z")
     you need to enable the archetype: edit build.sbt
       enablePlugins(JavaAppPackaging)
  4) create a package
     universal:packageBin: this generates a universal zip file
     rpm:packageBin      : this generates an rpm
     docker:publishLocal : this builds a Docker image using the local Docker server
  5) the autoplugin hierarchy
     SbtNativePackager - Universal - Linux - RPM / Debian
                                   - Docker
  6) Universal Plugin:
     output Zip format: sbt universal:packageBin
     output Tar format: sbt universal:packageZipTarball

# override property values in application.conf
  ex. in application.conf, we have:

  akka {
    cluster {
      seed-nodes = ["akka.tcp://TestApp@host1:2552"]
    }
  }

  sbt run -D akka.cluster.seed-nodes=["akka.tcp://TestApp@host1:2552", "akka.tcp://TestApp@host2:2552"]
  
  (similarly, in Java, we can pass parameters to main function as such)

  java [-options] -jar jarfile [args...]

  ex. java -Darg1=true -jar myApplication.jar

