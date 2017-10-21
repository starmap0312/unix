# commands
1) run Pig script locally
   pig -x local -p INPUT_PATH=/path/to/local/input_folder -p OUTPUT_PATH=/path/to/local/output_folder

# exceptions of running Pig script
1) in pig logs:
   Error before Pig is launched
   ----------------------------
   ERROR 2998: Unhandled internal error. org/apache/tez/dag/api/TezException
   java.lang.NoClassDefFoundError: org/apache/tez/dag/api/TezException

   this is due to the initial setting of the environment is wrong. make sure the environment is set correctly for running pig

