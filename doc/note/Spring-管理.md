### Spring 使用笔记

**Spring管理**
- 对象管理
- 切面管理
- 数据管理
- 事务管理
- 配置管理
- 环境管理
- 生命周期管理
- 事件管理
- 消息管理
- 状态管理
- 重试管理
- 容器管理
- 任务管理

其实Spring做的事情就是帮忙，或者以系统，管理员的更高一个维度的方式来管理这些事情。简单地来看，它就是一个管理系统，只是管理的东西不是常见的实物，而是一些抽象的东西。





### 有意思的问题
- 「datasource connection实例是线程安全的吗？」和 「controller 是线程安全的吗？」有同样的味道


### 事务管理
**五个方面**
- 4 个隔离级别ACID，对应有，脏读，丢失修改，不可重复读，幻读的数据问题。
- 7 个传播行为，3 个支持当前的，3 个不支持当前的，1 个 Spring 特有的。
- 回滚规则
- 事务超时
- 是否只读

**更多详细内容**
- [Spring事务管理详解](https://juejin.cn/post/6844903608224333838)
- [一个带有详细注释的源码解释](https://www.cnblogs.com/leeSmall/p/10306672.html)

**TransactionSynchronizationManager**
- 作用
我们在事务执行前后可能需要做一些额外的操作这个时候我们就需要用到TransactionSynchronizationManager去注入一个TransactionSynchronization事务同步器，然后重写TransactionSynchronization或者其子类的beforeCommit()或者afterCommit()方法，写入我们需要执行的业务。