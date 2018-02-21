# MySQL client
  mysql -u[username] -p[password] -h [hostname] -D [database_name]

# basic commands
  SHOW databases;
  USE [dbname];
  DESC [dbname].[tablename];
  SHOW GRANTS;
  ex.
    +---------------------------------------------------------------------------------------------------------------------+
    | Grants for username@10.1.2.3                                                                                        |
    +---------------------------------------------------------------------------------------------------------------------+
    | GRANT USAGE ON *.* TO 'username'@'10.1.2.3'                                                                         |
    | GRANT SELECT, INSERT, UPDATE, DELETE, CREATE TEMPORARY TABLES, EXECUTE ON `mydb_name`.* TO 'username'@'10.1.2.3'    |
    +---------------------------------------------------------------------------------------------------------------------+


# MySQL queries

  SELECT DATE(a.col_time), c.`col_name`, count(*) FROM \
  table1 a JOIN table2 b ON a.id = b.col1_id JOIN table3 c ON b.col2_id = c.id \
  WHERE DATE(a.col_time)>'2015-12-27' GROUP BY DATE(a.col_time), c.`col_name`;

# selects only the distinct values
  SELECT DISTINCT col_name FROM table_name;

# create a backup table
  CREATE TABLE table_name_bak AS (SELECT * FROM table_name);

# MySQL backquotes/backticks
  allow spaces and other special characters in table/column names

# function DATE([expr]): parse a date string
  1) function DATE(expr):
     extracts the date part of the date or datetime expression expr
     ex.
       SELECT DATE('2003-12-31 01:02:03');  ==> '2003-12-31'

  2) sysdate: system's current date
     ex.
       INSERT INTO table_name(COL1, COL2) VALUES('abc', sysdate);
       (COL2 must be of DATE type)

# MySQL LENGTH([Column]): return the length of a column string
  SELECT * FROM table_name WHERE LENGTH(col_name) < 10;

# WHERE col_name IN ('val1', 'val2', ...)
  ex.
  SELECT * FROM table_name WHERE col_name IN ('7105', '8105') ORDER BY job_id DESC;

# UNION: used to combine the result-set of two or more SELECT statements
  ex.
  SELECT column_name(s) FROM table1 UNION SELECT column_name(s) FROM table2;
  (selects only distinct values)

  SELECT column_name(s) FROM table1 UNION ALL SELECT column_name(s) FROM table2;
  (allow duplicate values)

# match regular expression
# I) LIKE
  ex.
  1) SELECT ... FROM ... WHERE col_name LIKE 'prefix%'
  2) SELECT ... FROM ... WHERE col_name LIKE '%suffix'
  3) SELECT ... FROM ... WHERE col_name LIKE '%keyword%'
  4) SELECT ... FROM ... WHERE col_name LIKE '___'
    (find col_name with exactly three characters)
# II) REGEXP or RLIKE
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
  5) SELECT * FROM table_name WHERE (col_name REGEXP '^[a-zA-Z0-9]+$');
  6) SELECT * FROM table_name WHERE (col_name REGEXP 'foo|bar|baz');                      # match words: this is foo story, this is foo short story, this is bar story, etc.
  7) SELECT * FROM table_name WHERE (col_name REGEXP '(foo|bar) (short )?story');         # match phrases: this is foo story, this is foo short story, this is bar story, etc.
  8) SELECT * FROM table_name WHERE (col_name REGEXP '(this is a book)|(this is a pen)'); # match phrases: this is a book, this is a pen 

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

# Oracle View
  1) create view: create a virtual table based on the result set of the SELECT statement
     CREATE VIEW view_name AS SELECT columns FROM tables [WHERE conditions];
     ex.
       CREATE VIEW sup_orders AS
       SELECT suppliers.supplier_id, orders.quantity, orders.price
       FROM suppliers
       INNER JOIN orders
       ON suppliers.supplier_id = orders.supplier_id
       WHERE suppliers.supplier_name = 'Microsoft';

       SELECT * FROM sup_orders;      ==> use the view just like a table
  2) Update VIEW
     CREATE OR REPLACE VIEW view_name AS SELECT columns FROM table WHERE conditions;
  3) Drop VIEW
     DROP VIEW view_name;

# Oracle indexes
  1) an optional structure, associated with a table or table cluster that speed data access
     if a heap-organized table has no indexes, then database must perform a full table scan to find a value
     i.e. search every row in every table block for the given value
  2) by creating an index on one or more columns of a table, you gain the ability in some cases to
     retrieve a small set of randomly distributed rows from the table
  3) indexes are one of many means of reducing disk I/O
  4) when to create an index
     a) the indexed columns are queried frequently and return a small percentage of the total number of
        rows in the table
     b) a unique key constraint will be placed on the table and you want to manually specify the index and
        all index options
     c) a referential integrity constraint exists on the indexed column or columns
        the index is a means to avoid a full table lock that would otherwise be required if you update
        the parent table primary key, merge into the parent table, or delete from the parent table
  5) create an index
     ex. creates an index on the customer_id column of table orders
         CREATE INDEX ord_customer_ix ON orders (customer_id);
  6) create composite indexes, i.e. an index on multiple columns
     order of the columns used in the definition is important: the most commonly accessed columns go first
     ex. if application frequently queries the last_name, job_id, and salary columns in the employees table 
         CREATE INDEX employees_ix ON employees (last_name, job_id, salary);
         (queries that access all three columns, or only last_name, or only last_name and job_id will 
          use the index)

# Oracle partitions
  1) decompose large tables and indexes into smaller and more manageable pieces called partitions
  2) do not need to modify SQL queries and DML statements to access partitioned tables
  3) however, after partitions are defined, DDL statements can access and manipulate individuals partitions
     rather than entire tables or indexes
  4) partitioning is entirely transparent to applications
  5) partitioning key: each row in a partitioned table is unambiguously assigned to a single partition
     the partitioning key is comprised of one or more columns that determine the partition
  6) partitioning methods
     a) range partitioning: ex. [Jan.-Feb.], [Mar.-Apr.], [May-June], [July-Aug.]
     ex.
       CREATE TABLE sales_range 
       (salesman_id   NUMBER(5), 
        salesman_name VARCHAR2(30), 
        sales_amount  NUMBER(10), 
        sales_date    DATE
       )
       PARTITION BY RANGE(sales_date) 
       (PARTITION sales_jan2000 VALUES LESS THAN(TO_DATE('02/01/2000','MM/DD/YYYY')),
        PARTITION sales_feb2000 VALUES LESS THAN(TO_DATE('03/01/2000','MM/DD/YYYY')),
        PARTITION sales_mar2000 VALUES LESS THAN(TO_DATE('04/01/2000','MM/DD/YYYY')),
        PARTITION sales_apr2000 VALUES LESS THAN(TO_DATE('05/01/2000','MM/DD/YYYY'))
       );

       // retriving rows from a table partition
       SELECT * FROM sales_range;
       SELECT * FROM sales_range PARTITION(sales_feb2000);

       // adding a partition
       ALTER TABLE sales_range ADD PARTITION sales_may2000 VALUES LESS THAN (TO_DATE('06/01/2000','MM/DD/YYYY'));

       // dropping a partition
       ALTER TABLE sales_range DROP PARTITION sales_may2000; 

     b) list partitioning:  ex. [New York, Virginia, Florida], [California, Oregon, Hawaii], [Illinois, Texas]
       ex.
       CREATE TABLE sales_list
       (salesman_id  NUMBER(5), 
        salesman_name VARCHAR2(30),
        sales_state   VARCHAR2(20),
        sales_amount  NUMBER(10), 
        sales_date    DATE)
       PARTITION BY LIST(sales_state)
       (PARTITION sales_west VALUES('California', 'Hawaii'),
        PARTITION sales_east VALUES ('New York', 'Virginia', 'Florida'),
        PARTITION sales_central VALUES('Texas', 'Illinois'),
        PARTITION sales_other VALUES(DEFAULT)
       );

       (10, 'Jones', 'Hawaii', 100, '05-JAN-2000') maps to partition sales_west
     c) hash partitioning:  ex. [h1], [h2], [h3], [h4]
     d) composite partitioning: ex. range-hash or range-list
  7) when to partition a table
     a) tables greater than 2GB should always be considered for partitioning
     b) tables containing historical data, in which new data is added into the newest partition
        ex. a historical table where only the current month's data is updatable and the other 11 months
            are read only
  8) advantages
     a) reducing downtime for scheduled maintenance
        i.e. allows maintenance operations to be carried out on selected partitions while other partitions are
        available to users
     b) reducing downtime due to data failure
        i.e. failure of a particular partition will no way affect other partitions
     c) partition independence allows for concurrent use of the various partitions for various purposes
  9) differences with storing in many Tablespaces
     a) reduces the possibility of data corruption in multiple partitions
     b) back up and recovery of each partition can be done independently

# Oracle Tablespaces & Datafiles
  1) Oracle stores data logically in tablespaces and physically in datafiles associated with the
     corresponding tablespace
  2) differences between databases, tablespaces, and datafiles
     a) database consists of one or more logical storage units called tablespaces, which collectively
        store all of the database's data
     b) each tablespace in a database consists of one or more files called datafiles, which are
        physical structures that conform to the operating system
     c) database's data is collectively stored in the datafiles that constitute each tablespace of the
        database

# mysql: MySQL DB client
  1) mysql -u root:
     login localhost as root
  2) mysql -u <user> -p <password> -e "SELECT * FROM tablename"

# sqlplus: Oracle DB client
  1) display query result more clearly
     set lines 128: the length of the line
     set wrap off: truncates the line if its is longer then 128

  2) execute a sql script
     sqlplus -s username/passwd@service_name @filename.sql
     -s: suppresses all information and prompt messages, ex. command prompt, command echoing, and banner 

  3) spool output to a file
     ex. 
       spool stdout.txt
       <sql commands>
       spool off;

# INNER JOIN vs. OUTER JOIN

  ex.
    ai   bi
    -    -
    1    
    2    
    3    3
    4    4
         5
         6

  1) a INNER JOIN b: (intersection of a and b)

  ex. SELECT * FROM a INNER JOIN b ON a.ai = b.bi;

    ai   bi
    -    -
    3    3
    4    4

  2) a LEFT OUTER JOIN b:

  ex. SELECT * FROM a LEFT OUTER JOIN b ON a.ai = b.bi;

    ai   bi
    -    -
    1    null
    2    null
    3    3
    4    4
  
  3) a RIGHT OUTER JOIN b

  ex. SELECT * FROM a RIGHT OUTER JOIN b ON a.ai = b.bi;

    ai   bi
    -    -
    3    3
    4    4
   null  5
   null  6

  4) a FULL OUTER JOIN b: (union of a and b)

  ex. SELECT * FROM a FULL OUTER JOIN b ON a.ai = b.bi;

    ai   bi
    -    -
    1    null
    2    null
    3    3
    4    4
   null  5
   null  6

# find duplicate values in table
  ex. table Person

    Id   Email
    -    -
    1    a@b.com
    2    c@d.com
    3    a@b.com

  SELECT Email FROM Person GROUP BY Email HAVING COUNT(*) > 1

    Id   Email
    -    -
    1    a@b.com

  (HAVING must reference only columns in the GROUP BY clause or columns used in aggregate functions)


# select MAX number of a column
  Oracle:
    SELECT * FROM (SELECT * FROM table_name ORDER BY column DESC) WHERE rownum = 1;

  MySQL:
    SELECT id, MAX(column_name) FROM table_name GROUP BY id;

# GROUP BY
  SELECT column_name(s), aggregate_function(column_name)
  FROM table_name
  WHERE column_name operator value
  GROUP BY column_name1, column_name2...;

  scala groupBy analogy, i.e. group a map of (key, value) tuples by the keys
  ex. { (1, 100), (2, 100), (1, 200), (2, 300) }
        if group by ._1, it produces a set of (key, [grouped values]) as below
      { (1, [100, 200]), (2, [100, 300]) }

  in sql, we then apply an aggregate_function, ex. SUM, AVG, MAX, etc., to the grouped values
    this creates a new group column, ex. SUM(column), AVG(column), MAX(column), etc.

  ex. sql table: orders

    id   price  customer 
    -    -      -
    1    100    John
    2    100    Joe
    3    100    Wang
    4    100    Joe
    5    200    Wang

  SELECT customer, SUM(price) FROM orders GROUP BY customer;
    customer   SUM(price)
    John       100
    Joe        200
    Wang       300
