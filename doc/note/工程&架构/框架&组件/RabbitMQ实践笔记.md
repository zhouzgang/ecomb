## RabbitMQ 实践笔记

![RabbitMQ架构图][doc/note/images/rabbitmq架构图.png]

### 需求场景
- 异步处理。下单后，邮件或短信通知用户
- 应用解藕。下单时，通知减库存，这里存在数据一致性问题，在实现时要多考虑。
- 流量削峰。秒杀系统，用户请求先进入队列，再做处理。

**异步处理**
> 下单后，通过消息队列，发送通知邮件的消息，「ecomb-app-sms」消费消息
目前订单系统还没有发展到拆解出来，先在「ecomb-web-app」中实现


### RabbitMQ 模式
1. Work queues，多个消费者处理一个生产者的任务，rabbit采用轮询的方式将消息是平均发送给消费者的；
2. Publish/Subscribe
3. topic模式，按照设置的路由信息（routing key）将消息路由到一个或者多个消费端，而消息只能由一个消费者消费一次。一个消费者可以设置多个路由信息，可以同时获取多个消费者发送的消息；
4. fanout模式，与topic模式唯一的区别是同一消息会发送到订阅（binding）的多个消费者；
5. direct模式，一对一模式，实际中比较少用；
6. RPC模式，结合topic和direct模式，发送消息的同时指定要接受的消息。


### 实践
**延时队列**
- 使用 延时队列 + 死信队列 实现。
    - 为了非均等延时，延时时间需要加在每一条消息上。
    - 队列只会刷新队列头的一个消息的时间
    - 存在非均等延时问题，比如 msg1 延时 200s，msg2 延时 10s，msg2 需要等 msg 1 完成才会被消费，也就是 210s 后才会被消费到。
- 使用延时队列插件实现
    - 这里的延时是放在交换机上，到时间时，才会将消息放到队列中。
    - 这种设置过期时间的方式与前一种不一样。
    
**一个队列多个消费者，消费不同的消息**


### 问题处理
1. 如何保证消息消费时的幂等性

2. 触发异常消息处理，以免一直重复消费有问题的消息

3. 生产者丢失数据问题
- 使用事务机制（同步）或 confirm 机制（异步），一般生产使用confirm机制，写成功后异步确认


### docker 搭建 RabbitMQ
**快速搭建**
```shell script
docker pull rabbitmq:3-management
docker run -d -p 5672:5672 -p 15672:15672 --name myrabbitmq IMAGE_ID
# 默认账号密码：guest/guest
```




**参考网址**
- [这也许是最全面透彻的一篇RabbitMQ指南](https://dbaplus.cn/news-141-1464-1.html)
- [延时队列实现](https://www.cnblogs.com/mfrank/p/11260355.html)

[doc/note/images/rabbitmq架构图.png]: ../images/rabbitmq架构图.png