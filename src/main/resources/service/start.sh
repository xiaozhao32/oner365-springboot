#!/bin/bash
SERVICE_NAME=
VERSION=
RESOURCE_NAME=$SERVICE_NAME-$VERSION.jar
ACTIVE=

selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath

tpid=`ps -ef|grep $RESOURCE_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
    echo $RESOURCE_NAME 'Stop Process...'
    kill -15 $tpid
fi

sleep 5

tpid=`ps -ef|grep $RESOURCE_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
    echo $RESOURCE_NAME 'Kill Process!'
    kill -9 $tpid
else
    echo $RESOURCE_NAME 'Stop Success!'
fi
 
tpid=`ps -ef|grep $RESOURCE_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
    echo $RESOURCE_NAME 'is running.'
else
    echo $RESOURCE_NAME 'is NOT running.'
fi
 
rm -f tpid
nohup java -jar -Dspring.profiles.active=$ACTIVE ./$RESOURCE_NAME > ../logs/$RESOURCE_NAME.log 2>&1 &

echo $! > tpid

echo $RESOURCE_NAME 'Start Success'!
echo $!
