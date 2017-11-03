# commands
1) run Pig script locally
   pig -x local -p INPUT_PATH=/path/to/local/input_folder -p OUTPUT_PATH=/path/to/local/output_folder

# pig scripting
1) %DEFAULT [PARAM] [value]: define parameters in Pig script:
   ex.
     %DEFAULT INPUT_PATH '/path/to/hdfs/'

# use external jar when running Pig script
1) register the jar file in the Pig script:
   REGISTER ./mylib.jar;
2) then any implementation of org.apache.pig.EvalFunc<T> can be used as a function in the Pig script
   ex.
   // our Java code of the jar file
   public class Concatenate extends EvalFunc<String> {  // defines a function that takes a tuple and returns the lowercase of the first field of the tuple
     @Override
     public String exec(Tuple tuple) throws IOException {
       String field1 = (String) tuple.get(0);
       String field2 = (String) tuple.get(1);
       return field1 + field2;
     }
   }
   // the Pig script that uses the Java function
   REGISTER ./mylib.jar;
   lines = LOAD '$INPUT_PATH' USING PigStorage('\t') as (field1:chararray, field2:chararray);
   FOREACH lines GENERATE com.yahoo.nuwa.pig.udf.Concatenate(field1, field2);

# exceptions when running Pig script
1) in pig logs:
   Error before Pig is launched
   ----------------------------
   ERROR 2998: Unhandled internal error. org/apache/tez/dag/api/TezException
   java.lang.NoClassDefFoundError: org/apache/tez/dag/api/TezException

   this is due to the initial setting of the environment is wrong. make sure the environment is set correctly for running pig

