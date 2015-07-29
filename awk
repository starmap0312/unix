# awk command: (sed often handles rows, whereas awk handles the fields of a row)
  $0: current read string
  $1: 1st column of $0
  $2: 2nd column of $0
  built-in variables:
  NF: number of fields of $0
  NR: number of records (lines) read so far
  FILENAME: the input filename

  ex.
  awk '{print $1 "\t" $3}' filename ==> print column 1 and column 3
  tail -n 3 filename | awk '{print $1 "\t" $3}' ==> print column 1 and column 3 of last 3 lines
  awk -F ':' '$3==$4' /etc/passwd
