# Manage 后台管理

## 开发环境

- `jdk1.8`
- `maven-3.2.2`

## 启动应用

```shell
$ mvn tomcat7:run
```

## 版本步骤

### 基础版本 1.0.0
- 使用`spring-web`,`conroller`使用`servlet`
- 实现`ApplicationContextAware`接口获取容器以及容器组件

1. 导入依赖
    ```xml
    <dependencies>
        <!-- Spring Begin -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <!-- Spring End -->
        <!-- Junit Begin -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <!-- Junit End -->
        <!-- Servlet Begin -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>${servlet.version}</version>
            <scope>provided</scope>
        </dependency>
        <!-- Servlet End -->
        <!-- Slf4j Begin -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        <!-- Slf4j End -->
        <!-- Commons Begin -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.8.1</version>
        </dependency>
        <!-- Commons End -->
    </dependencies>
    ```
2. 配置`maven-tomcat7-plugin`
    ```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>2.4</version>
                <configuration>
                    <failOnMissingWebXml>false</failOnMissingWebXml>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.0</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF8</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>2.2</version>
                <configuration>
                    <!-- 本地运行端口 -->
                    <port>8080</port>
                    <!-- 项目名称 -->
                    <path>/manage</path>
                    <!-- 热部署tomcat用户名称 -->
                    <username>tomcat</username>
                    <!-- 热部署tomcat用户密码 -->
                    <password>tomcat</password>
                    <!-- 热部署tomcat路径 -->
                    <url>http://localhost:8080/manager/text</url>
                    <!-- 编码格式 -->
                    <uriEncoding>UTF-8</uriEncoding>
                </configuration>
            </plugin>
        </plugins>
    </build>
    ```
3. 添加`log4j`配置文件
    ```properties
    # log4j.properties
    log4j.rootLogger=INFO, console, file
    
    log4j.appender.console=org.apache.log4j.ConsoleAppender
    log4j.appender.console.layout=org.apache.log4j.PatternLayout
    log4j.appender.console.layout.ConversionPattern=%d %p [%c] - %m%n
    
    log4j.appender.file=org.apache.log4j.DailyRollingFileAppender
    log4j.appender.file.File=logs/log.log
    log4j.appender.file.layout=org.apache.log4j.PatternLayout
    log4j.appender.A3.MaxFileSize=1024KB
    log4j.appender.A3.MaxBackupIndex=10
    log4j.appender.file.layout.ConversionPattern=%d %p [%c] - %m%n
    ```
4. 构建包目录
    ```java
    com.qpf.manage.dao;
    com.qpf.manage.service;
    com.qpf.manage.entity;
    com.qpf.manage.web.controller;
    ```
5. 注解版`Spring`
    - 容器配置类`RootConfig`指定扫描的包以及添加组件
    - 实现`WebApplicationInitializerImpl`类,指定容器配置类