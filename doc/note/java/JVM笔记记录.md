## JVM 笔记记录

### 零散记录
- 堆内内存由 JVM 管理，堆外内存（也就是栈内存）由操作系统管理。其中 Unsafe 类可以操作堆外内存。
（这里的理解很重要，结合 JVM 内存模型来看。同样可以结合 CPU-[寄存器-高速缓存-缓存]-主存 来看）


### JVM 参数

### JVM 调优
- 高分配速率
分配速率过高就会严重影响程序的性能，在JVM中可能会导致巨大的GC开销。
正常系统：分配速率较低~ 回收速率-> 健康
内存泄漏：分配速率持续大于回收速率-> OOM
性能劣化：分配速率较高~ 回收速率-> 压健康
- 过早提升





### 阅读资料
- [美团unsafe类]()