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

# Oracle
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
