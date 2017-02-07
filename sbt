# SBT (simple build tool)
  1) interactive build tool
  2) commands
     a) Project-level tasks
        sbt clean: deletes all generated files (the target directory)
     b) Configuration-level tasks
        sbt compile      : compiles the main sources (in the src/main/scala directory)
        sbt test:compile : compiles test sources (in the src/test/scala/ directory).
        sbt test         : runs all tests detected during test compilation
     ex. sbt clean test
  3) Dependency Management Flow
     a) update resolves dependencies according to the settings in a build file, such as libraryDependencies and resolvers
     b) before compile can run, the update task needs to run
     c) run clean and then update
     d) before deleting ~/.ivy2/cache, try to delete files in ~/.ivy2/cache related to problematic dependencies
        ex. ~/.ivy2/cache/org.example/demo/1.0/
