#!/bin/bash
SERVICE_NAME=

#------------------------------------------------------------------

selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath

function exec() {
    tpid=`cat tpid`
    rm -f tpid
    if [ ${tpid} ]; then
        echo $SERVICE_NAME "Kill '${tpid}' Process!"
        kill -15 $tpid
    else
        echo $SERVICE_NAME "Stop Success!"
    fi
    sleep 5
}

#------------------------------------------------------------------

exec 

rm -rf ./logs/*
echo $SERVICE_NAME "Stop finished!"