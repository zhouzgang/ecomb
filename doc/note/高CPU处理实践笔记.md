##  高 CPU 处理实践笔记

top 发现问题 找到PID。
ps aux | grep PID 查看具体 程序。
ps -mp &PID -o THREAD,tid,tim；
定位到问题线程id。
printf "%x\n" tid; 转换 tid 为 16进制格式。
jstack PID | grep tid -A60 ,打印堆栈信息 ，定位问题代码。

// --mac--
ps -M pid提供了线程列表，但未显示每个线程的TID
sudo dtruss -ap pid 现实线程id