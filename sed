# sed command: (sed often handles rows, whereas awk handles the fields of a row)

  ex.
  sed -n '3,5p' filename: print 3rd to 5th rows 
      -n: no echoed to STDOUT
      ex. nl filename | sed -n '3,5p'
          head -n 10 filename ==> print first 10 rows
          tail -n 10 filename ==> print last 10 rows
  sed '3,5d' filename: delete 3rd to 5th rows
      ex. nl filename | sed '3,5d'
  sed 's/word1/word2/g' filename: replace word1 with word2
      ex. nl filename | sed 's/word1/word2/g'
          sed -e 's/#.*//' filename ==> remove comments
          sed -e 's/.$//' filename ==> convert DOS newlines ('\r\n') with Unix format

