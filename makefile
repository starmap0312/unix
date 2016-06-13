# Makefile variable assignment
  1) Lazy Set: value is expanded when the variable is used, not when it is declared
     VARIABLE = value
  2) Immediate Set: value is expanded when the variable is declared
     VARIABLE := value
  3) Set If Absent: set variable only when it is undefined (do nothing if it already has a value)
     VARIABLE ?= value
  4) Append: append the value to the existing value (simply set the variable if it is undefined)
     VARIABLE += value
