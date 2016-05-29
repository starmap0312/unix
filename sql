# MySQL client
  mysql -u[username] -p[password] -h [hostname] -D [database_name]

# MySQL queries

  SELECT DATE(a.col_time), c.`col_name`, count(*) FROM \
  table1 a JOIN table2 b ON a.id = b.col1_id JOIN table3 c ON b.col2_id = c.id \
  WHERE DATE(a.col_time)>'2015-12-27' GROUP BY DATE(a.col_time), c.`col_name`;

# MySQL backquotes/backticks
  allow spaces and other special characters in table/column names

# MySQL dates:
  1) function DATE(expr):
     extracts the date part of the date or datetime expression expr
     ex.
       SELECT DATE('2003-12-31 01:02:03');  ==> '2003-12-31'

  2) sysdate: system's current date
     ex.
       INSERT INTO table_name(COL1, COL2) VALUES('abc', sysdate);
       (COL2 must be of DATE type)

# WHERE col_name IN ('val1', 'val2', ...)
  ex.
  SELECT * FROM table_name WHERE col_name IN ('7105', '8105') ORDER BY job_id DESC;

# UNION: used to combine the result-set of two or more SELECT statements
  ex.
  SELECT column_name(s) FROM table1 UNION SELECT column_name(s) FROM table2;
  (selects only distinct values)

  SELECT column_name(s) FROM table1 UNION ALL SELECT column_name(s) FROM table2;
  (allow duplicate values)

# LIKE
  ex.
  1) SELECT ... FROM ... WHERE col_name LIKE 'prefix%'
  2) SELECT ... FROM ... WHERE col_name LIKE '%suffix'
  3) SELECT ... FROM ... WHERE col_name LIKE '%keyword%'
  4) SELECT ... FROM ... WHERE col_name LIKE '___'
    (find col_name with exactly three characters)

# REGEXP
  .: match one character
  [abc]: match a, or b, or c
  [0-9]*: match any number of digits
  .*: match anything
  ex.
  1) SELECT ... FROM ... WHERE col_name REGEXP '^prefix'
  2) SELECT ... FROM ... WHERE col_name REGEXP 'suffix$'
  3) SELECT ... FROM ... WHERE col_name REGEXP 'keyword'
  4) SELECT ... FROM ... WHERE col_name REGEXP '^...$'
     SELECT ... FROM ... WHERE col_name REGEXP '^.{3}$'
    (find col_name with exactly three characters)

# TRUNC function
  TRUNC(n1): returns n1 truncated to integer number
  ex.
    SELECT TRUNC(15.79) "Truncate" FROM dual;
      Truncate
    ----------
            15
 
  TRUNC(n1, n2): returns n1 truncated to n2 decimal places
  ex.
    SELECT TRUNC(15.79,1) "Truncate" FROM dual;
      Truncate
    ----------
          15.7

    SELECT TRUNC(15.79,-1) "Truncate" FROM dual;
      Truncate
    ----------
            10

    dual: a dummy table with a single record used for selecting
    ex. SELECT sysdate FROM dual;

    SELECT TRUNC(col_name / 60) new_col_name

# CASE expressions
  simple expression:
    CASE expr WHEN comparision_expr THEN return_expr ELSE else_expr END
  searched expression:
    CASE WHEN condition THEN return_expr ELSE else_expr END

  let you use IF THEN ELSE logic in SQL statements without having to invoke procedures
  ex.
    SELECT username, CASE col_name WHEN 100 THEN 'Low' WHEN 5000 THEN 'High' ELSE 'Medium' END
    FROM table_name;

    USERNAME CASECR
    ---------------
    Loren    Medium
    Gueney   High

    SELECT AVG(CASE WHEN salary > 2000 THEN salary ELSE 2000 END) "Average Salary"
    FROM table_name;

    Average Salary
    --------------
    6461.68224

# ORDER BY 1
  ORDER the select results based on the number of columns defined in the SELECT clause
  ex.
    SELECT col_name1, col_name2 FROM table_name ORDER BY 1

    the results will be ordered by col_name1 

# fact table vs. dimension table
  fact table works with dimension tables
  differences
    dimension table:
      stores data about the ways in which the data in the fact table can be analyzed
      1) could have a parent table
      2) not aggregated
      3) not supposed to be updated in place
    fact table: 
      holds the data to be analyzed (quantitative information and is often denormalized)
      1) always has a dimension table (or more) as a parent
      2) may be aggregated
      3) could be updated in place in some cases
      consists of two types of columns:
        the foreign keys column allows joins with dimension tables
        the measures columns contain the data that is being analyzed
  examples
    a company sells products to customers

    fact table: every sale is a fact that happens

    TimeID	ProductID	CustomerID	Unit
    4		17		2		1
    8		21		3		2
    8		4		1		1

    dimension table (info about customers)

    CustomerID	Name	Gender	Income
    1		Brian	M	2
    2		Fred	M	3
    3		Sally	F	1

    customerID column in fact table is the foreign key that joins with the dimension table

# Oracle tables
  dba_tables: describes all relational tables in the database
  all_tables: describes the relational tables accessible to the current user
  user_tables: describes the relational tables owned by the current user

  1) list all tables in a database:
     SELECT tablespace_name, table_name FROM dba_tables;
  2) list all tables accessible to the current user
     SELECT tablespace_name, table_name FROM all_tables;
  3) list all tables owned by the current user
     SELECT tablespace_name, table_name FROM user_tables;
  4) find table owner
     SELECT * FROM dba_tables WHERE table_name='XXX';
  5) desc table_name;

# V$ Views and GV$ Views
  V$ Views: dynamic performance views 
  GV$ Views: querying a GV$ view retrieves the V$ view information from all qualified instances
             GV$ view contains an extra column named INST_ID (datatype: NUMBER)

# gv$session: lists session information for each current session
  v$session: used on standalone database
  gv$session: used mostly on RAC environments, showing all sessions on each node
    g: global, RAC: Real Application Clusters

  columns:
    INST_ID: the instance number from which the associated V$ view information was obtained
    SID: Session identifier
    SERIAL#: session serial number. used to uniquely identify a session's objects
    SQL_ID: SQL identifier of the SQL statement that is currently being executed
    USERNAME: Oracle username
    MACHINE: Operating system machine name
    LAST_CALL_ET: the elapsed time in seconds for ACTIVE sessions
    STATUS: Status of the session
      ACTIVE: Session currently executing SQL
      KILLED: Session marked to be killed
    PROGRAM: Operating system program name

  example:
    SELECT inst_id, sid, serial#, sql_id, username, machine, TRUNC (last_call_et / 60) time
    FROM gv$session
    WHERE status = 'ACTIVE' AND program = 'JDBC Thin Client'

    INST_ID SID  SERIAL# SQL_ID        USERNAME MACHINE                  TIME
    3       132  213     01c4fdtv73xrp PPADMIN  app-server-003.yahoo.com 0
    1       1350 1279    837bunkkupksj PPADMIN  app-server-001.yahoo.com 0
    4       1476 39      9gvt21txwwb0u PPADMIN  app-server-001.yahoo.com 0

# gv$px_process: contains information about the sessions running parallel execution
  columns:
    INST_ID: the instance number from which the associated V$ view information was obtained
    STATUS: The state of the PX server (IN USE|AVAILABLE), PX: parallel execution

# PARALLEL_MAX_SERVERS
  specifies maximum number of parallel execution processes and parallel recovery processes for
    an instance

  parameter type: integer (0 - 3599)
  default value: derived from the values of CPU_COUNT, PARALLEL_THREADS_PER_CPU, and
                 PGA_AGGREGATE_TARGET

  1) too low: some queries may not have a parallel execution process available to them during
              query processing
  2) too high: memory resource shortages may occur during peak periods, which can degrade performance.
