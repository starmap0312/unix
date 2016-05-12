# awk command: (sed often handles rows, whereas awk handles the fields of a row)
  $0: current read string
  $1: 1st column of $0
  $2: 2nd column of $0
  built-in variables:
  NF: number of fields of $0
  NR: number of records (lines) read so far
  FILENAME: the input filename

  ex.
  awk '{print $1 "\t" $3}' filename   ==> print column 1 and column 3
  tail -n 3 filename | awk '{print $1 "\t" $3}' ==> print column 1 and column 3 of last 3 lines
  awk -F ':' '$3==$4' /etc/passwd
  echo $HOME | awk -F= '{print $NF}'  ==> print the last column of each row
    HOME=/home/kuanyu ($NF == $1, print $NF == /home/kuanyu)

# example: print the 3rd column of a tab separated file
  awk -F "\t" '{print $3}' filename (default delimiter: "\t", " ", "\n")
  cut -f3 filename (default delimiter: "\t")
  cut -d " " -f3 filename (cut 3rd column using delimiter: " ")
  cut -d : -f 1 /etc/passwd (cut -d":" -f1 /etc/passwd)

# gawk: pattern scanning and processing language (GNU awk)
  gawk '/pattern/ {action}' filename
  ex. content in filename
      3 1 2 4
      1 2 3 4
      1 d 3 4
      5 5 5 5
    gawk '{ sum += $2 }; { print sum }' filename
    (adding the 2nd column of each line to variable sum and printing the result)
      will print out:
      1
      3
      3
      8
    gawk '{ sum += $2 }; END { print sum }' filename
    (adding the 2nd column of each line to variable sum and printing the result at the end)
      will print out:
      8
