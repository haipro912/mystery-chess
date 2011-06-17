#!/bin/ksh
javaCommand=java

# Change java command if needed
# javaCommand=/usr/bin/java

curDir=`pwd`
cd `dirname $0`
appDir=`pwd`
cd $curDir
$javaCommand -jar $appDir/MysteryChess.jar