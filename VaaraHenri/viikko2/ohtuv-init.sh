#!/bin/bash

#java taildirectoryparser $(pwd)

#sc=$(java ~/Documents/Courses/ohtu15/ohtuv/slashcounter)

#scdir=`~/Documents/Courses/ohtu15/ohtuv/`

#echo $scdir

counter=0
#slashes=$(java -classpath $scdir slashcounter $(pwd))
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

#echo $currentdir

if [[ $found == 0 ]]; then
	mkdir .ohtuv
else echo "[FAILED TO INITIALIZE]: ohtuv already initialized, base directory: $currentdir"
fi



#d=$(ls -halF | grep -F '.ohtuv/')
#if [[ -z "$d" ]];
#then  echo null
#else echo found
#fi


