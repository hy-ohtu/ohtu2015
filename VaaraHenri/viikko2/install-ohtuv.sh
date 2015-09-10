#!/bin/bash

echo "# ohtuv aliases" >> ~/.bash_aliases
echo "alias 'ohtuv-init'='~/Documents/Courses/ohtu15/ohtuv/ohtuv-init.sh'" >> ~/.bash_aliases
echo "alias 'ohtuv-save'='~/Documents/Courses/ohtu15/ohtuv/ohtuv-save.sh'" >> ~/.bash_aliases
echo "alias 'ohtuv-restore'='~/Documents/Courses/ohtu15/ohtuv/ohtuv-restore.sh'" >> ~/bash_aliases

javac slashcounter.java
javac taildirectoryparser.java

exec bash
