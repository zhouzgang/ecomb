### BaoMiDou 源码阅读笔记

### 可以学习的点
**ThreadLocal的切换数据源工具类**
- 使用栈来实现 A 调 B 调 C 的每次一切换数据源。
- 这样就会很优雅。

**整体设计**
- 其实工具类就是在原有流程上加一层，来做一些特殊的处理，比如路由数据源，包装一下，规范处理流程等。