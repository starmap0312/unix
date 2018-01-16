# Java Remote Method Invocation (Java RMI)
  1) Java API that performs remote method invocation
  2) the object-oriented equivalent of remote procedure calls (RPC)
  3) support for direct transfer of serialized Java classes and distributed garbage collection

# Java Database Connectivity (JDBC)
  1) an application programming interface (API) for Java, which defines how a client may
     access a database
  2) provides methods to query and update data in a database, and is oriented towards relational
     databases

# Java Package
  Java namespace management: a grouping of related types (classes, interfaces, etc.)
    used to prevent naming conflicts, to control access, to make locating and usage of classes easier

# javac: compile source code & java: execute main class
  javac -cp ./lib1.jar:./lib2.jar source.java
  java -cp .:./lib1.jar:./lib2.jar source.java

# .jar vs. .war files
  .jar file: contains libraries, resources and accessories files like property files
  .war file: contains the web application that can be deployed on any servlet/jsp container
             ex. jsp, html, javascript, etc. necessary for the development of web applications

# JavaFX (FX: functions)
  1) a set of graphics and media packages that enables developers to design, create, test, debug, and
     deploy rich client applications that operate consistently across diverse platforms
  2) JavaFX applications can use Java API libraries to access native system capabilities and connect to
     server-based middleware applications

# Apache Akka
  1) a convenient framework for doing reactive and distributed application on the JVM
  2) it is based on reactive manifesto
     a) Responsive    : the system responds in a timely manner
     b) Resilient     : the system stays responsive in the face of failure
                        achieved by replication, containment, isolation and delegation
     c) Elastic       : the system stays responsive under varying workload 
                        need the ability to shard or replicate components and distribute inputs among them
     d) Message Driven: the system rely on asynchronous message-passing to establish a boundary between
                        components that ensures loose coupling, isolation and location transparency 
  3) characteristics
     a) Event-driven with message passing (i.e loosely coupled)
     b) Resilient through the use of supervision strategies, death watch and hierarchies
     c) Scalable and responsive
        it saves resources with your actors sharing threads in a non-blocking way
        it does not require locking mechanisms blocking the current thread and context switching
  4) it uses the Actor model to hide all the thread-related code
     this gives you simple interfaces to implement a scalable and fault-tolerant system

# java decompile
  1) convert .class to bytecode
     ex. javap -c com/jayway/jsonpath/spi/json/JsonSmartJsonProvider.class
     public class com.jayway.jsonpath.spi.json.JsonSmartJsonProvider extends com.jayway.jsonpath.spi.json.AbstractJsonProvider {

     public com.jayway.jsonpath.spi.json.JsonSmartJsonProvider();
     Code:
       0: aload_0
       1: iconst_m1
       2: getstatic     #18                 // Field net/minidev/shade/json/JSONValue.defaultReader:Lnet/minidev/shade/json/writer/JsonReader;
       5: getfield      #23                 // Field net/minidev/shade/json/writer/JsonReader.DEFAULT_ORDERED:Lnet/minidev/shade/json/writer/JsonReaderI;
       8: invokespecial #26                 // Method "<init>":(ILnet/minidev/shade/json/writer/JsonReaderI;)V
      11: return
     ...
  2) covert .class to .java
     need to use a third-party java decompiler tool

# java tests
  1) org.testng vs. org.junit
     both Testng and Junit are Testing framework used for Unit Testing
     TestNG is similar to JUnit, with few more functionalities added to make it more powerful than JUnit

# java ClassLoader
  1) How Java compilation works?
     a) the Java compiler does not just compile the classes you ask
        it also compiles other classes needed by the classes you ask it to compile
     b) CompilingClassLoader (CCL) compiles your Java code 
        i.e. it will compile each class in our application, one by one, that needs to be compiled
        it reports on what application classes it is compiling as it compiles them
     c) CCL invokes a single invocation of the compiler only once 
          i.e. CCL calls the compiler on the main class in your program, and that will be all it does
        the reason is that:
          after the compiler compiles the first class, if the compiler finds a class doesn't exist or is out of date
          with respect to its source, that class will be compiled
     d) some exceptions that classes don't get compiled on the first pass:
        ex. if you load a class by name, using the Class.forName method, the compiler won't know that this class is needed
        in this case, the CCL will run the Java compiler again to compile this class
  2) java code runs on a Java virtual machine (JVM), so it is not a single executable file
     instead, it is composed of individual class files (bytecode), each of which corresponds to a single Java class
     the class files are not loaded into memory all at once, but rather are loaded on demand (when needed by the program)
  3) ClassLoader is responsible for finding and loading class files (bytecode) at run time
     you can customize the JVM and redefine how class files are brought into the system
     the ClassLoader is written in Java language, so you can create your own ClassLoader without understanding JVM details
     i.e. you can customize how to load class files (bytecode) from any source, or even to generate them on the fly
  4) why write a ClassLoader?
     the default ClassLoader only knows how to load class files from the local filesystem
     some popular use cases:
       a) browsers use a custom ClassLoader to load executable content from a Web site
       b) automatically verify a digital signature before executing untrusted code
       c) transparently decrypt code with a user-supplied password
       d) create dynamically built classes customized to the user's specific needs
     anything that can generate Java bytecode can be integrated into your application
  5) appletviewer contains a ClassLoader that, instead of looking in the local filesystem for classes, accesses a Web site on
     a remote server, loads the raw bytecode files via HTTP, and turns them into classes inside the JVM
  6) other ClassLoader ideas:
     used for Security, Encryption, Archiving, Self-extracting programs, Dynamic generation, etc.

# command line tool
1) jps: Java Virtual Machine Process Status Tool
   jps [options] [hostid]
     the tool is limited to reporting information on JVMs for which it has the access permissions
     options:
       -m: output the arguments passed to the main method. The output may be null for embedded JVMs
     ex. jps -m

