#!/bin/bash

#find .ohtuv directory:
currentfile="$(pwd)/$1"
currentdir=$(pwd)
slashes=$(java -classpath ~/Documents/Courses/ohtu15/ohtuv/ slashcounter $(pwd))
counter=0
found=0

while [[ $counter -lt $slashes ]]; do
	let counter=counter+1
	
	d=$(ls -halF $currentdir | grep -F '.ohtuv/')
	if [[ "$d" != "" ]]; then
		let found=1
		break
	fi

	currentdir=$(java -classpath ~/Documents/Courses/ohtu15/ohtuv/ taildirectoryparser $currentdir)
done

if [[ $found == 0 ]]; then
	echo "ohtuv repository not found"
else
#	echo "ohtuv repository is $currentdir/.ohtuv/"
	cp $currentfile "$currentdir/.ohtuv/$(date +%d.%m.%y.%H:%M).$1"
fi



#currentfile="$(pwd)/$1"

#echo "current file is $currentfile"
