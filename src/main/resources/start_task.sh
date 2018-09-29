#!/bin/sh
export LANG=en_US.UTF-8
binPath=$(cd "$(dirname "$0")"; pwd);
libPath="$binPath/lib/";
taskPath=com.cyl.blog.task.
function exportClassPath(){
    jarFileList=`find "$libPath" -name *.jar |awk -F'/' '{print $(NF)}' 2>>/dev/null`
    CLASSPATH=".:$binPath";
    for jarItem in $jarFileList
      do
        CLASSPATH="$CLASSPATH:$libPath$jarItem"
    done
    export CLASSPATH
}
cd $binPath/classes
logDir=/log/blog-task
if [ ! -d "$logDir" ]; then
    mkdir "$logDir"
fi
echo "start $1"
ulimit -n 65535
exportClassPath
nohup /usr/local/cyl/jdk1.8.0_144/bin/java -server -Xmx512m -Xss256k -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 $taskPath$@ >> $logDir/$1.log 2>&1 &
echo $! > $logDir/$1.pid
