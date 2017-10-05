# install on Mac
  1) search & install older version (NO NEED TO DO THIS, specify the version as below)
     brew search sbt
       sbt@0.13 
     brew install sbt@0.13 
  2) install latest version
     brew install sbt

# specify sbt version to use
  edit project/build.properties
    sbt.version=0.13.15 # use an older version of sbt

# specify artifact version
  edit version.sbt
    version := "0.1.6-SNAPSHOT"
  
# SBT (simple build tool) commands
  an interactive build tool
  a) Project-level tasks
     sbt clean: deletes all generated files (the target directory)
  b) Configuration-level tasks
     sbt              : load project (under the same folder of build.sbt), fetch dependencies, and enter console
     sbt compile      : compiles the main sources (in the src/main/scala directory)
     sbt test:compile : compiles test sources (in the src/test/scala/ directory).
     sbt test         : runs all tests detected during test compilation
     sbt "test-only [test_name]": run a specific test, ex. sbt "test-only com.my_package.MyTest"
     sbt package      : creates jar file containing files in src/main/resources and classes compiled from
                        src/main/scala and src/main/java
     sbt war          : package for Java container sbt war
     sbt dist         : package for standalone Play package (build a standalone project issue)
     sbt run <args>*  : runs main class of project in the same virtual machine as sbt (run an application)
     sbt -jvm-debug <port>:  Turn on JVM debugging, open at the given port (in IDE, select Run -> Attach to Local Process)
       ex. sbt -jvm-debug 5005 run
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
  2) sbt publishLocal
     publish your developping library to a local Ivy repository
     ex. /.ivy2/local/[groupid]/[artifact_id]/[version]-SNAPSHOT/poms/[artifact_id].pom
         /.ivy2/local/[groupid]/[artifact_id]/[version]-SNAPSHOT/jars/[artifact_id]-assembly.jar

# libraryDependencies
  1) "test" scope:
     if you want a dependency to show up in the classpath only for the Test configuration and not the Compile configuration, add % "test"
     ex. libraryDependencies += "org.specs2" %% "specs2" % "1.7-SNAPSHOT" % "test"
  2) "provided" scope:
     this excludes the dependency from the assembly artifact
  3) changing():
     this specifies that the dependency can change and that it ivy must download it on each update
     ex. libraryDependencies += "org.specs2" %% "specs2" % "1.7-SNAPSHOT" % "test" changing()
     ex. libraryDependencies += "org.specs2" %% "specs2" % "1.7-SNAPSHOT" % "provided" changing()
     
# add local/remote snapshot repo for development:
  create file "dev.sbt" with the following content:
    resolvers in ThisBuild += "My artifactory Snaps" at "http://artifactory.mydomain.com:4080/artifactory/maven-local-snapshot" # remote snapshot repo
    resolvers in ThisBuild += "Local Maven Repository" at Path.userHome.asFile.toURI.toURL + ".m2/repository"                   # local snapshot repo
  or
    resolvers ++= Seq(
      "My artifactory Snaps" at "http://artifactory.mydomain.com:4080/artifactory/maven-local-snapshot" # remote snapshot repo
      "Local Maven Repository" at "file:///Users/kuanyu/.m2/repository"                                 # local snapshot repo
    )

