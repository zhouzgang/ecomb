## SpringBoot 源码阅读笔记
> 结合项目构建阅读 SpringBoot 源码，去理解源码中的设计和解决的问题

### 问题
- JVM 栈帧结构是什么样的，初始化时获取了当前栈帧
- Spring 的 listener 是如何实现的
    - 流程节点上调用具体的方法
- Spring的SPI机制（spring.factories机制）


### 记录
- 从 ApplicationContext 的继承接口来看，设计一个这样的系统是如何处理的？
- 功能的声明与边界的划分是如何定义的
- 对事物的抽象


### ** 问题 ** SpringBoot 启动流程
> SpringBoot 实现了什么功能，如何实现的？

![启动流程](images/springboot启动流程图.jpg)
 
### ** 设计 ** 接口加解密封装