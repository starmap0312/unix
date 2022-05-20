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

# daemon thread
1) a daemon thread is a thread that does not prevent the JVM from exiting when the program finishes, i.e. the thread is still running when program finishes
   ex. a daemon thread of the garbage collection
2) you can use the setDaemon(boolean) method to change the Thread daemon properties before the thread starts
   ex.
public class SimpleThread { 
    public static void main(String[] args) { 
        Thread thread = new Thread(new Runnable() {
            public void run() { 
                while(true) { 
                    System.out.print("a created normal thread."); 
                } 
            }        
        }); 
        thread.start(); 
        # the main program is about to finish, but the created normal thread is still running
        #   the program cannot exit unless you abruptly call System.exit();
        # if you want to exit the main program even though the created thread is not finished yet, you need to set the created thread as a Daemon thread
    }
}
  ex.
public class SimpleThread {
     public static void main(String[] args){  
         Thread thread = new Thread(new Runnable() {
             public void run() {                        
                 while(true) { 
                     System.out.print("a created normal thread.");
                 } 
             }                                          
         });
         # note: if you want to make a user thread as Daemon, it must not be started otherwise it will throw IllegalThreadStateException
         thread.setDaemon(true);  # setting the thread to be a daemon thread before starting it
         thread.start();          # starting the thread
         # the main program will finish normally here, as all non-daemon threads finish
     }  
}

# switch between different java versions using jenv
ref: https://github.com/jenv/jenv
1) brew install jenv
2) jenv add $(/usr/libexec/java_home) # add current java_home, ex. jdk1.14, to jenv
   jenv add /usr/local/Cellar/openjdk/17.0.1 # add openjdk-17
   #jenv add /Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/ # add jdk1.8 jenv
3) jenv shell 1.8 # this manually sets the shell environment to use jdk1.8
   jenv local 1.8 # this creates a file .java-version in the directory for jenv to remember which jdk version it should use for this directory

# jcmd: JVM diagnostic utility (heap memory status): ref: https://www.baeldung.com/java-heap-size-cli
1) jps -l: find the process id of a particular Java application
  ex.
    73170 org.jetbrains.idea.maven.server.RemoteMavenServer36
    4309  quarkus.jar
    12070 sun.tools.jps.Jps
2) jcmd <pid> GC.heap_info: check GC heap info
 ex. jcmd 4309 GC.heap_info
     4309:
       garbage-first heap   total 206848K, used 43061K
       region size 1024K, 43 young (44032K), 3 survivors (3072K)
       Metaspace       used 12983K, capacity 13724K, committed 13824K, reserved 1060864K
       class space    used 1599K, capacity 1740K, committed 1792K, reserved 1048576K

3) jcmd <pid> GC.class_histogram: check GC class/created objects
 ex. jcmd 1 GC.class_histogram | head -50
4) jcmd <pid> VM.native_memory summary                                                                
 ex. jcmd 1 VM.native_memory
     # note: you can java enable native memory tracking by running the java application with parameter "-XX:NativeMemoryTracking=summary"

# java heap dumps analysis: ref https://www.baeldung.com/java-heap-dump-capture
1) add java options:
   java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=<local-dirpath>
2) use intellij to analyze memory snapshots # ref: https://www.jetbrains.com/help/idea/analyze-hprof-memory-snapshots.html
   View | Tool Windows | Profiler -> open snapshot: hprof file # note: you can find the heap dump file at the <local-dirpath> specified in the previous step

# Java is Pass-by-Value
ref: https://www.javadude.com/articles/passbyvalue.htm
ref: https://www.cprogramming.com/tutorial/references.html
1) pointer: 
   a pointer is a variable that indirectly tracks the location of some piece of data
   the value of a pointer is often the memory address of the data, and you are able to manipulate that address in C++
   the value uniquely identifies some object on the heap
     Java object reference works just like a C++ pointer
     Java does not allow pointer arithmetic like C++ does, and so there is no * and -> syntax
   ex.
     Dog d;             // Java
     d.setName("Fifi");

     Dog *d;            // C++ equivalent
     d->setName("Fifi");

2) reference (alias):
   a reference is an alias to another object
   any manipulation done to the reference object directly changes the original object
   (there is a special null reference which refers to no object)
   ex.
     void swap(SomeType& arg1, Sometype& arg2) {
       SomeType temp = arg1;
       arg1 = arg2;
       arg2 = temp;
     }
     SomeType var1 = ...; // value "A"
     SomeType var2 = ...; // value "B"
     swap(var1, var2); // swaps their values!
     // now var1 has value "B" and var2 has value "A"
  note: there is no reference (alias) in Java, an object reference in Java works just like C++ pointers
        Java is pass-by-value, and there is no such thing as pass-by-reference in java

  In a Java method call, you are not passing an object; you are passing a pointer to the object (i.e. object reference)
  In a Java method call, pointers (object references) are passed by value, and primitives are also passed by value
  ex.
    public void foo(Dog someDog) {  // someDog is a new pointer to the same Dog object at address 42
      someDog.setName("Max");       // the Dog object is set with a different name
      someDog = new Dog("Fifi");    // someDog is changed to point to another Dog object newly created
      someDog.setName("Rowlf");     // the new Dog object is set with a different name
    }

    Dog myDog = new Dog("Rover");   // myDog is a pointer to a Dog object at address 42 (heap memory)
    foo(myDog);                     // myDog always point to the Dog object at address 42 (it's never changed to another object, safer programming)
  ex.
    Integer x = integerWrapper.getNum(); // In Java method return, pointer to an Integer is passed by value, so x is a new pointer to the Integer object of integerWrapper 
    x = x + 1;                           // x is changed to point to another Integer object newly created, x + 1
                                         // the Integer pointer inside integerWrapper is never changed to another object, safer programming

