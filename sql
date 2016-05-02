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
