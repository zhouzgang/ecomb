## RabbitMQ 实践笔记

### 需求场景
- 异步处理。下单后，邮件或短信通知用户
- 应用解藕。下单时，通知减库存，这里存在数据一致性问题，在实现时要多考虑。
- 流量削峰。秒杀系统，用户请求先进入队列，再做处理。

**异步处理**
> 下单后，通过消息队列，发送通知邮件的消息，「ecomb-app-sms」消费消息
目前订单系统还没有发展到拆解出来，先在「ecomb-web-app」中实现




### docker 搭建 RabbitMQ
**快速搭建**
```shell script
docker pull rabbitmq:3-management
docker run -d -p 5672:5672 -p 15672:15672 --name myrabbitmq IMAGE_ID
# 默认账号密码：guest/guest
```




**参考网址**
- (这也许是最全面透彻的一篇RabbitMQ指南)[https://dbaplus.cn/news-141-1464-1.html]