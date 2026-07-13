#!/bin/bash
SERVICE_NAME=
VERSION=
RESOURCE_NAME=$SERVICE_NAME-$VERSION.jar
ACTIVE=

#------------------------------------------------------------------

selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath

if [ ! -d logs ];then
    mkdir logs
fi

if [ -f tpid ];then
    tpid=`cat tpid`
    if kill -0 "$tpid" &> /dev/null; then 
        echo $SERVICE_NAME "Stop '$tpid' Process!"
        kill -15 $tpid
        sleep 5
    fi
    if kill -0 "$tpid" &> /dev/null; then 
        echo $SERVICE_NAME "Kill '$tpid' Process!"
        kill -9 $tpid
        sleep 5
    fi
    rm -f tpid
fi

# 调优后
#nohup $JAVA_HOME/bin/java -jar \
#  -XX:InitialRAMPercentage=70.0 \
#  -XX:MaxRAMPercentage=80.0 \
#  -XX:MaxMetaspaceSize=512m \
#  -XX:MaxDirectMemorySize=1g \
#  -XX:+UseZGC \
#  -XX:ZCollectionInterval=300 \
#  -XX:+HeapDumpOnOutOfMemoryError \
#  -XX:HeapDumpPath=./logs/heapdump-%p.hprof \
#  -XX:NativeMemoryTracking=summary \
#  -Xlog:gc*:file=./logs/gc-%t.log:time,uptime,level,tags:filecount=5,filesize=100M \
#  -XX:+DisableExplicitGC \
#  -XX:+ParallelRefProcEnabled \
#  -Djdk.virtualThreadScheduler.parallelism=8 \
#  -Dspring.profiles.active=$ACTIVE ./$RESOURCE_NAME > logs/$RESOURCE_NAME.log 2>&1 &

nohup java -jar -Dspring.profiles.active=$ACTIVE ./$RESOURCE_NAME > logs/$RESOURCE_NAME.log 2>&1 &

echo $! > tpid

#------------------------------------------------------------------

echo $RESOURCE_NAME 'Start Success'!
echo $!
