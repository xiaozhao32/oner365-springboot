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
        sleep 15
    fi
    if kill -0 "$tpid" &> /dev/null; then 
        echo $SERVICE_NAME "Kill '$tpid' Process!"
        kill -9 $tpid
        sleep 15
    fi
    rm -r tpid
fi
 
#4核8G配置jvm(根据服务器做调整，翻倍即可)
#nohup java  -Xms4g -Xmx4g -Xmn2g -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m -XX:MaxDirectMemorySize=1g -XX:+PrintGCDetails  -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -Xloggc:../logs/gc.log -jar -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../logs/dump.hprof -Dspring.profiles.active=$ACTIVE ./$RESOURCE_NAME > ../logs/$RESOURCE_NAME.log 2>&1 &
#默认启动jvm不调优

console=$1
if [ "$console" == "-console" ]; then
	java -jar -Dspring.profiles.active=$ACTIVE ./$RESOURCE_NAME
else 
	nohup java -jar -Dspring.profiles.active=$ACTIVE ./$RESOURCE_NAME > logs/$RESOURCE_NAME.log 2>&1 &
fi

echo $! > tpid

#------------------------------------------------------------------

echo $RESOURCE_NAME 'Start Success'!
echo $!
