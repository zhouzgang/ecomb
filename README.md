## ecomb
> 模拟实践高并发分布式系统中常见的问题
> 构建模版项目，整理开发流程中的各种规范

### 模块划分
> 接口，模块，服务等这些是一种容器边界的定义，容器之间需要相互调用。理想的情况是边界清晰，便于管理，不出现循环调用等情况。
    1. 参考 Spring 的依赖注入和控制反转，应该向调用方注入服务，由实现方控制如何构建服务。
    2. 定义【接口】优于实现，应该明确地将服务定义出来。
    3. 服务可以看作有边界的接口集合。
> 每个模块可以独立创建 git 仓库

**业务层 ecomb-web**
- ecomb-web-app 封装 app 接口之类

**服务定义层 ecomb-provider-api**
- ecomb-provider-api-product
- ecomb-provider-api-order
- ecomb-provider-api-user

**数据模型层 ecomb-provider**
- ecomb-provider-product 商品模块
- ecomb-provider-order 订单模块
- ecomb-provider-user 用户模块

**公共组件模块 ecomb-common**
- ecomb-common-mq 消息队列模块
- ecomb-common-sms 短信消息模块


## 设计规则
### 端口使用规则
- 端口段：80[80~]
- app: 8080
- admin: 8081

### 异常码使用规则
- 错误码A-BB-CCC
- A  : 错误级别编号： 1-系统级  2-内部业务  3-外部业务
- BB : 模块编号: 数据模块 与 业务模块使用同一位置编码，分别使用不同段
    - 数据模块编号[00~49]：00-scene 01-admin 02-area 03-user 04-service 05-trip 06-sync 07-customer 08-locator
    - 业务模块编号[50~99]：50-common 51-admin 52-coupon 53-mobile 54-trip 55-indefinite
- CCC: 具体错误编号: 错误编码根据功能块分段，具体分段划分备注在 XXXErrorCode 的错误枚举类中，ex：
  - 优惠: 252[]
    - 公共：00 ~ 49
    - 商场：50 ~ 99
    - 店铺：100 ~ 149
    - 排号：150 ~ 199
    - 用户中心：200 ~ 249

## 配置
> 项目配置表


## 开发规范
### web 层
- 请求参数统一使用 XXXRequest，并继承BaseRequest，封装公共参数
- 请求返回体使用 XXXResponse
- 请求尽量使用 POST 请求，方便统一管理公共参数
- XXXController 职责，区分子模块边界，定义对外接口，文档以及校验参数
- IWebXXXService 职责，处理业务逻辑，封装接口返回结果

### provider-api
- 入参对象格式：XxxParam
- 入参实体：Entity
    - 实体命名根据表名，去除前缀「tbl」，实体字段名完全同实体名，并转驼峰
- 入参尽量使用基础类型，已确保明确接口参数
- 出参对象：XxxDTO
- 出参：Entity
- 出参：基础字段

### provider 层
- 接口命名 IXXXMapper
    - 参数 @Param 中，集合使用 list，set，map，query 参数使用 param。
    
### 数据库 层
- 表命名 tbl_module_XXX

### 统一处理方案
- 操作类接口不返回 true 或 false。最少原则，成功了什么都不反回，失败了肯定是某种异常。
- 接口没查询到结果，返回 null 还是 抛异常？统一返回 null，没有结果应该不是一种异常。
- 表记录关联应该使用主键索引，还是非主键索引。比如 product 表的 id 与 product_id。

### git commit message 使用规范
> 参考(阮一峰Commit message 和 Change log 编写指南)[http://www.ruanyifeng.com/blog/2016/01/commit_message_change_log.html]
- message格式：\<type>(\<scope>): \<subject> 其中，Header 是必需的，Body 和 Footer 可以省略。
- （1）type用于说明 commit 的类别，只允许使用下面7个标识。
  - feature：新功能（feature）
  - tech：技术点实践
  - fix：修补bug
  - docs：文档（documentation）
  - style： 格式（不影响代码运行的变动）
  - refactor：重构（即不是新增功能，也不是修改bug的代码变动）
  - test：增加测试
  - chore：构建过程或辅助工具的变动
  如果type为feat和fix，则该 commit 将肯定出现在 Change log 之中。其他情况（docs、chore、style、refactor、test）由你决定，要不要放入 Change log，建议是不要。
- scope 用于说明 commit 影响的范围，比如数据层、控制层、视图层等等，视项目不同而不同。
- subject 是 commit 目的的简短描述，不超过50个字符。
  - 以动词开头，使用第一人称现在时，比如change，而不是changed或changes
  - 第一个字母小写
  - 结尾不加句号（.）

## 规划
> 每一步发布一个 release 分支 

** Step-1 **
- 构建项目基础结构
- 实现基本的用户，商品，订单业务代码
- 容器化部署
- 开箱即用，编写使用脚本

** Step-2 **
- 多数据源，主从分离，分库分表
- redis 集群
- 搭建并集成配置中心
- 搭建并集成消息队列
- JVM 调优，并发调优，数据库调优（模拟订单操作订单功能生产数据）

** Step-3 **
- 注册中心
- 接口限流与熔断
- 服务监控
- 日志收集

** Step-4 **
- K8s 管理集群
- 常见问题模拟实现

## 记录
** 方案记录 **
- 外层项目做简单接口压测
- 订单&商品做分库分表实践
- 读写分离实践
- 订单&商品：下单/减库存。实践分布式事务
- 模拟实现限流功能
- 消息队列实践
- 商品独立成系统后，下单事务中订单记录和扣减库存存在分布式事务需要解决方案

** todo 列表 **
[] 接入 Redis，封装使用组件（放到 step-1-2 去做）
[] 分布式锁组件（放到 step-1-2 去做）

** 需求记录 **
- 模拟秒杀场景需求

## 使用
> 环境说明：

** 帮助 **
- make help

** 环境搭建并初始化 **
- make init

** 项目构建并部署 **
- make build
- make deploy
