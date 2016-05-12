# WHERE col_name IN ('val1', 'val2', ...)
  ex.
  SELECT * FROM table_name WHERE col_name IN ('7105', '8105') ORDER BY job_id DESC;

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
