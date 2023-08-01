#!/bin/bash
SERVICE_NAME=

#------------------------------------------------------------------

selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath

function exec() {
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
}

#------------------------------------------------------------------

exec 

echo $SERVICE_NAME "Stop finished!"
