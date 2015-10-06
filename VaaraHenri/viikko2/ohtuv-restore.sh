#!/bin/bash

#echo "ohtuv restore for file $2 at time $1 called"

# 1. find ohtuv repo folder, if not found, print error message

counter=0
slashes=$(java -classpath ~/Documents/Courses/ohtu15/ohtuv/ slashcounter $(pwd))
currentdir=$(pwd)
found=0

while [[ $counter -lt $slashes ]]; do
	#echo $currentdir
	let counter=counter+1
	
	d=$(ls -halF $currentdir | grep -F '.ohtuv/')
	if [[ "$d" != "" ]]; then
		let found=1
		break
	fi

	currentdir=$(java -classpath ~/Documents/Courses/ohtu15/ohtuv/ taildirectoryparser $currentdir)

done

if [[ $found == 0 ]]; then
	echo "[FAILED TO RESTORE]: no suitable ohtuv repository found"
else
#	echo "found repo in $currentdir"
	
	#simpler timestamp filter here plz.
	a=$(ls "$currentdir/.ohtuv" | grep -F "$1.$2")
	if [[ "$a" != "" ]]; then #file found
		cp "$currentdir/.ohtuv/$1.$2" "$(pwd)/$2"
		echo "success"
	else echo "[FAIL : BACKUP FILE NOT FOUND] was looking in: $currentir/.ohtuv"
	fi
fi


# 2. find file to match timestamp, if not found, print error message

# 3. ohtuv-save current file, replace current file with repo one.
