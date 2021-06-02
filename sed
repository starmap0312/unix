# sed command
  sed often handles rows, whereas awk handles the fields of a row
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

# replace all the keywords of a file row by row 
  sed 's/word1/word2/g' filename
  option:
    g: global, replace all occurrences (without g, it will only replace the first occurrence)

# replace only the first occurrence of the keyword in a file
  sed '0,/word1/s/word1/word2/'

# replace one line in filename
  sed -i 'Ns/.*/replaced_content/' filename
    -i, --in-place: edit files in place
    N: target line number
  sed 'Ns/.*/replaced_content/' filename > filename2
    save the changed text in another file
  sed -i -E "s/$pattern/replaced_string" filename
    -E: enable shell variable

# print the 10th line of a file
  sed -n '10p' filename
  awk 'NR == 10' filename
  head -n 10 filename | tail -n 1
