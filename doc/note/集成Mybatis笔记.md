## 集成 Mybatis 笔记
> 再一次阅读 Mybatis 源码，整理阅读和实践使用笔记
> 阅读源码的时候在阅读什么？

### 问题
- 什么时候启动配置 Mybatis 的，Even
- springBoot 配置装载过程
- 配置方式，配置文件&自定义编码配置
- 使用 「user.mapper」这种方式命名导致 「.」为文件夹名的字符，
    - 从而在「mybatis.mapper-locations: classpath*:/*/mapper/*.xml」中读取不到 xml 文件
    - 解决方式，手动在文件夹类建立文件目录

### 记录
- 一段代码的意图是什么？用来解决什么类型的问题的
- 验证设计思路