#!/usr/bin/env bash

#
# 发布脚本
# @author chenj
# @date 2016-08-11 17:24:31
# @description Copyright (c) 2016, yitopapp.com All Rights Reserved.
#

workdir=`pwd`
dirname $0|grep "^/" >/dev/null
if [ $? -eq 0 ];then
   workdir=`dirname $0`
else
    dirname $0|grep "^\." >/dev/null
    retval=$?
    if [ $retval -eq 0 ];then
        workdir=`dirname $0|sed "s#^.#$workdir#"`
    else
        workdir=`dirname $0|sed "s#^#$workdir/#"`
    fi
fi

cd $workdir


# config log dir
if [ "$logdir" = "" ]
then
    logdir=$workdir/logs/spring/
fi

if [ ! -d "$logdir" ]; then
	mkdir -p "$logdir"
fi

if [ -f "$workdir/config-env.sh" ]; then
    source $workdir/config-env.sh
fi

if [ "$SERVICE_NAME" = "" ]
then
    SERVICE_NAME="`echo $workdir | grep -o -E "[^/]+$"`"
fi

# JVM_OPTS=""
# DEBUG_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5000"

JVM_OPTS="-server -Xms512m -Xmx2048m -Xloggc:$logdir/$SERVICE_NAME-gc.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGC -XX:+HeapDumpOnOutOfMemoryError -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC"
DEBUG_OPTS="-Dlog.dir=$logdir"
USER_OPTS="$USER_OPTS -Dlog.dir=$logdir/.. -Ddubbo.shutdown.hook=true"

LIB_DIR="$workdir/lib"
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`
SERVICE_NAME="`echo $workdir | grep -o -E "[^/]+$"`"
#SERVICE_NAME="apache-camel-spring"

echo $LIB_JARS

SEND_URIS=http4://192.168.141.204:8081/camel/post,http4://192.168.141.204:8082/camel/post
export SEND_URIS

nohup java $JVM_OPTS $DEBUG_OPTS $USER_OPTS -cp $LIB_JARS demo.chenj.FailoverXmlCamel >> $logdir/$SERVICE_NAME-catalina.out 2>&1 &
echo $! > $logdir/$SERVICE_NAME-pid.txt