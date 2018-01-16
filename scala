# commands
  1) compile and run
     scala HelloWorld.scala
  2) compile:
     scalac HelloWorld.scala
     this will produces the following two files:
       HelloWorld.class
       HelloWorld$.class
  3) run:
     scala -classpath . HelloWorld
     scala -cp . HelloWorld
  4) verify class file with javap (Java class file disassembler) :
     javap -v HelloWorld.class
       -v: verbose: prints stack size, number of locals, and args for methods
     javap HelloWorld$.class

# nested class in Scala
ex1. 1-level nested class
  // Foo.scala: a singleton object with nested class 
  object Foo {
    class Bar
  }
  // to construct Bar instance in Java:
  new Foo.Bar()

  // $ scalac Foo.scala produces Foo.class, Foo$.class, and Foo$Bar.class
  //   for every Scala singleton object, the compiler creates a Java class with a dollar sign at the end
  //   i.e. Foo$.class
  // $ javap -v Foo.class 
       SourceFile: "Foo.scala"
       InnerClasses:
         public static #11= #10 of #2; //Bar=class Foo$Bar of class Foo
         // Foo$Bar is an enclosing class of Foo: that is why we can use new Foo.Bar() to construct instance in Java

ex2. 2-level nested classes
  object Foo {
    object Bar {
      class Baz
    }
  }
  // note: you cannnot construct Baz instance in Java: new Foo.Bar.Baz()
  // therefore, it is not recommended to have 2-level nested classes

# scala REPL (Read-Eval-Print Loop)
1) the Scala REPL is a tool (scala) for evaluating expressions in Scala
2) the scala command will execute a source script by wrapping it in a template and then compiling and executing
   the resulting program
3) in interactive mode, the REPL reads expressions at the prompt, wraps them in an executable template, and then
   compiles and executes the result
4) previous results are automatically imported into the scope of the current expression as required
5) Ammonite: an alternative REPL 
   Ammonite project provides a richer shell environment

