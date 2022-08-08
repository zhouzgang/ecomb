##  高 CPU 使用率处理实践笔记

```shell script
# top 发现问题 找到PID。
ps aux | grep PID #查看具体 程序。
ps -mp &PID -o THREAD,tid,tim;
# 定位到问题线程id。
printf "%x\n" tid #转换 tid 为 16进制格式。
jstack PID | grep tid -A60 #打印堆栈信息 ，定位问题代码。

# --mac 环境--
ps -M pid # 提供了线程列表，但未显示每个线程的TID
sudo dtruss -ap pid #现实线程id
```

- 找出导致高 CPU 的线程脚本
```shell script
#!/bin/sh
ts=$(date +"%s")
jvmPid=$1
defaultLines=100
defaultTop=20

threadStackLines=${2:-$defaultLines}
topThreads=${3:-$defaultTop}

jvmCapture=$(top -b -n1 | grep java )
threadsTopCapture=$(top -b -n1 -H | grep java )
jstackOutput=$(echo "$(jstack $jvmPid )" )
topOutput=$(echo "$(echo "$threadsTopCapture" | head -n $topThreads | perl -pe 's/\e\[?.*?[\@-~] ?//g' | awk '{gsub(/^ +/,"");print}' | awk '{gsub(/ +|[+-]/," ");print}' | cut -d " " -f 1,9 )\n ")

echo "*************************************************************************************************************"

uptime

echo "Analyzing top $topThreads threads"

echo "*************************************************************************************************************"

printf %s "$topOutput" | while IFS= read  line

do
   pid=$(echo $line | cut -d " " -f 1)
   hexapid=$(printf "%x" $pid)
   cpu=$(echo $line | cut -d " " -f 2)
   echo -n $cpu"% [$pid] " 
   echo "$jstackOutput" | grep "tid.*0x$hexapid " -A $threadStackLines | sed -n -e '/0x'$hexapid'/,/tid/ p' | head -n -1
   echo "\n"
done

echo "\n" 
```