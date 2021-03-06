## Redis常用组件封装及实践
> 模拟问题出现的情况，并设计实现解决方案

** todo 列表 **
[] 接入 Redis，封装使用组件（放到 step-1-2 去做）
[] 分布式锁组件（放到 step-1-2 去做）
[] 使用 redisson

### 话题整理
- Redis 模式搭建
    - 单点
    - 集群
    - 哨兵
- 集群与哨兵模式的区别
- 缓存与数据库中的 数据一致性
- 缓存击穿与雪崩
- RedLock，对比分布式锁不同实现的优缺点
- Redis 事件原理，线程模型

### 文档
- [redisson](https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95)
- [redis](http://redis.cn/topics/sentinel.html)