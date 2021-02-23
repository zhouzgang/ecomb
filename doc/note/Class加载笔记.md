### Class 加载笔记

- 加载类的过程

在前面介绍类加载器的代理模式的时候，提到过类加载器会首先代理给其它类加载器来尝试加载某个类。这就意味着真正完成类的加载工作的类加载器和启动这个加载过程的类加载器，有可能不是同一个。
真正完成类的加载工作是通过调用 defineClass 来实现的；而启动类的加载过程是通过调用 loadClass 来实现的。前者称为一个类的定义加载器（defining loader），后者称为初始加载器（initiating loader）。
在 Java 虚拟机判断两个类是否相同的时候，使用的是类的定义加载器。也就是说，哪个类加载器启动类的加载过程并不重要，重要的是最终定义这个类的加载器。两种类加载器的关联之处在于：一个类的定义加载器是它引用的其它类的初始加载器。
如类 com.example.Outer 引用了类 com.example.Inner ，则由类 com.example.Outer 的定义加载器负责启动类 com.example.Inner 的加载过程。

方法 loadClass() 抛出的是 java.lang.ClassNotFoundException 异常；
方法 defineClass() 抛出的是 java.lang.NoClassDefFoundError 异常。

