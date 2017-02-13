# SBT (simple build tool) commands
  an interactive build tool
  a) Project-level tasks
     sbt clean: deletes all generated files (the target directory)
  b) Configuration-level tasks
     sbt              : load project (under the same folder of build.sbt), fetch dependencies, and enter console
     sbt compile      : compiles the main sources (in the src/main/scala directory)
     sbt test:compile : compiles test sources (in the src/test/scala/ directory).
     sbt test         : runs all tests detected during test compilation
     sbt package      : creates jar file containing files in src/main/resources and classes compiled from
                        src/main/scala and src/main/java
     sbt war          : package for Java container sbt war
     sbt dist         : package for standalone Play package (build a standalone project issue)
     sbt run <args>*  : runs main class of project in the same virtual machine as sbt (run an application)
  c) run sbt commands in batch: ex. sbt clean compile test)
     ex.
       sbt "project project_name" clean format compile test package
       sbt "project project_name" run
   

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
