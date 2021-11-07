### 启动 Tomcat 源码服务记录

1. GitHub 下载源码
2. idea 打开项目
3. 添加 pom.xml 文件
```java
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>org.apache.tomcat</groupId>
    <artifactId>Tomcat9.0</artifactId>
    <name>Tomcat9.0</name>
    <version>9.0</version>
    <build>
        <finalName>Tomcat9.0</finalName>
        <sourceDirectory>java</sourceDirectory>
        <resources>
            <resource>
                <directory>java</directory>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>2.3</version>
                <configuration>
                    <encoding>UTF-8</encoding>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>ant</groupId>
            <artifactId>ant</artifactId>
            <version>1.7.0</version>
        </dependency>
        <dependency>
            <groupId>wsdl4j</groupId>
            <artifactId>wsdl4j</artifactId>
            <version>1.6.3</version>
        </dependency>
        <dependency>
            <groupId>javax.xml</groupId>
            <artifactId>jaxrpc</artifactId>
            <version>1.1</version>
        </dependency>
        <dependency>
            <groupId>org.eclipse.jdt.core.compiler</groupId>
            <artifactId>ecj</artifactId>
            <version>4.6.1</version>
        </dependency>
        <dependency>
            <groupId>biz.aQute.bnd</groupId>
            <artifactId>biz.aQute.bndlib</artifactId>
            <version>5.2.0</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
```

4. 添加启动参数
```shell
# 注意修改成自己的路径
-Dcatalina.home=/Users/brian.zhou/Documents/workspace/code/source/tomcat
-Dcatalina.base=/Users/brian.zhou/Documents/workspace/code/source/tomcat
-Djava.endorsed.dirs=~/Doucuments/workspace/code/source/tomcat/endorsed
-Djava.io.tmpdir=/Users/brian.zhou/Documents/workspace/code/source/tomcat/temp
-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager
-Djava.util.logging.config.file=/Users/brian.zhou/Documents/workspace/code/source/tomcat/conf/logging.properties
-Djava.protocol.handler.pkgs=org.apache.catalina.webresources
-Djdk.tls.ephemeralDHKeySize=2048
-Duser.country=EN #   解决启动日志乱码问题
```

5. Bootstrap 类启动