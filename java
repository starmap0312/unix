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

