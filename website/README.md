# 笔记

[toc]

# 概念

## 什么是面向对象

- 面向对象有三个基本特性: 封装,继承和多态
    - 封装是将事物的属性和行为封装成一个类
    - 继承是将具有相同属性的对象抽象出来并包装成一个父类
    - 多态是多种形态,相同的属性和行为有多种不同表现形式
- 面向对象设计原则
    - 面向扩展开放,面向修改关闭
    - 接口隔离原则
    - 组合/聚合原则
    - 里氏替换原则
    - 最少知识原则(迪米特法则)
    - 单一职责原则
    - 依赖倒置原则

## 什么是面向接口编程

- 接口是为了处理各个对象之间的的协作关系,是关系设计的重要组成部分,主要作用是将`定义`和`实现`分离,从而实现系统`解耦`的目的

## Maven

- `Maven`是一个项目管理工具,提供完整的生命周期框架
- `Maven`使用标准的目录结构默认构建周期,可以自动完成项目构建.
- `Maven`简化和标准化项目的创建过程

- 语义化版本规范
    - `1.0.x`: `bug`修复版本号
    - `1.x.0`: 功能增减版本号
    - `x.0.0`: 项目结构变化
- `Maven plugin`: `tomcat7-maven-plugin`
    - 本地运行: `mvn tomcat7:run`
    - 远程部署: `mvn tomcat7:deploy / mvn tomcat7:redeploy`
    ```
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    	<modelVersion>4.0.0</modelVersion>
    
    	<groupId>com.qpf</groupId>
    	<artifactId>frontend</artifactId>
    	<version>1.0-SNAPSHOT</version>
    	<packaging>war</packaging>
    	
    	<dependencies>
    	</dependencies>
    	
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
    					<path>/frontend</path>
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
    </project>
    ```
## 系统框架

- 三层架构:
    - 视图层(`View`): 应用`MVC`模式
    - 业务层(`Service`)
        - 业务复杂度:
            - 简单业务: 开启一个事务
            - 普通业务: 开启三个事务
            - 复杂业务: 开启七个事务
    - 数据操作层(`Dao`)
- `MVC`模式
    - 视图(`View`)
    - 模型(`Model`):
        - 数据模型
        - 业务模型
    - 控制器(`Controller`)
- 基本`package`结构
    ```
    com.qpf.demo.web
                    .controller
                    .filter
    com.qpf.demo.service
    com.qpf.demo.dao
    com.qpf.demo.entity
    ```

## Bootstrap

- 简介: `Bootstrap`，来自 `Twitter`，是目前很受欢迎的前端框架。`Bootstrap`是基于`HTML`、`CSS`、`JavaScript`的，它简洁灵活，使得`Web`开发更加快捷。它是一个`CSS/HTML`框架。`Bootstrap`提供了优雅的`HTML`和`CSS`规范。`Bootstrap`一经推出后颇受欢迎，一直是`GitHub`上的热门开源项目。
- 基本结构：`Bootstrap`提供了一个带有网格系统、链接样式、背景的基本结构。

- `CSS`: `Bootstrap`自带以下特性，全局的`CSS`设置、定义基本的`HTML`元素样式、可扩展的`class`，以及一个先进的网格系统。

- 组件: `Bootstrap`包含了十几个可重用的组件，用于创建图像、下拉菜单、导航、警告框、弹出框等等。`JavaScript`插件: `Bootstrap`包含了十几个自定义的`jQuery`插件。您可以直接包含所有的插件，也可以逐个包含这些插件。

- 定制：您可以定制`Bootstrap`的组件、`LESS`变量和`jQuery`插件来得到您自己的版本。

- `HTML`模板:
    ```
    <!DOCTYPE html>
    <html>
       <head>
          <title>Bootstrap 模板</title>
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <!-- 引入 Bootstrap -->
          <link href="https://maxcdn.bootstrapcdn.com/bootstrap/css/bootstrap.min.css" rel="stylesheet">
     
          <!-- HTML5 Shiv 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
          <!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
          <!--[if lt IE 9]>
             <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
             <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
          <![endif]-->
       </head>
       <body>
          <h1>Hello, world!</h1>
     
          <!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
          <script src="https://code.jquery.com/jquery.js"></script>
          <!-- 包括所有已编译的插件 -->
          <script src="js/bootstrap.min.js"></script>
       </body>
    </html>
    ```
# 框架

## Spring

- `Spring`的主要作用是代码解耦,降低代码间的耦合度.
- 根据功能的不同，可以将一个系统中的代码分为 主业务逻辑 与 系统级业务逻辑 两类,解耦的方式有两种: `IoC`和`AOP`
    - `Ioc`: 创建和维护对象的交给`IoC`容器,业务之间相互调用自动`注入`
    - `AOP`: 主业务逻辑之间不需要混杂系统服务,而是由`Spring`容器统一`织入`,使系统服务得到最大复用

- `Spring`系统架构`Spring`由`20`多个模块组成,它们可以归纳为以下`7`种类型
    - 数据访问/集成`(Data Access/Integration`): `JDBC`, `ORM`, `OXM`, `JMS`, `Transactions`
    - `Web`: `WebSocket`, `Servlet`, `Web`, `Portlet`
    - 面向切面编程(`AOP`,` Aspects`)
    - 应用服务器设备管理(`Instrumentation`)
    - 消息发送(`Messaging`)
    - 核心容器(`Core Container`): `Bean`, `Core`, `Context`, `SpEL`
    - 测试(`Test`)

## Junit

- `JUnit`是用于编写和运行可重复的自动化测试的开源测试框架，这样可以保证我们的代码按预期工作
- `JUnit`可广泛用于工业和作为支架(从命令行)或`IDE`(如` IDEA`)内单独的`Java`程序


## Log4J

- `Log4j`的全称为`Log for java`，即专门用于`Java`语言的日志记录工具。

- 为了方便对于日志信息的输出显示，对日志内容进行了分级管理。日志级别由高到低，共分`6`个级别：
    - `fatal`(致命的)
    - `error`
    - `warn`
    - `info`
    - `debug`
    - `trace`(堆栈)

- `Log4j`的日志输出控制文件，主要由三个部分构成：
    - 输出位置：控制日志信息将要输出的位置，是控制台还是文件等。(`log4j.appender.appenderName` = `输出位置`)
        - `org.apache.log4j.ConsoleAppender`: 日志输出到控制台
        - `org.apache.log4j.FileAppender`: 日志输出到文件
        - `org.apache.log4j.RollingFileAppender`: 当日志文件大小到达指定尺寸的时候将产生一个新的日志文件
        - `org.apache.log4j.DailyRollingFileAppender`: 每天产生一个日志文件
    - 输出格式：控制日志信息的显示格式，即以怎样的字符串形式显示。设置输出布局,然后设置输出格式
        - 输出布局(`log4j.appender.console/file.layout`)
            - `org.apache.log4j.HTMLLayout`: 网页布局，以`HTML`表格形式布局
            - `org.apache.log4j.SimpleLayout`: 简单布局，包含日志信息的级别和信息字符串
            - `org.apache.log4j.PatternLayout`: 匹配器布局，可以灵活地指定布局模式。其主要是通过设置`PatternLayout`的`ConversionPattern`属性值来控制具体输出格式的 。
        - 输出格式(`log4j.appender.console/file.layout.ConversionPattern`)
            - `%m`: 输出代码中指定的消息
            - `%p`: 输出优先级，即`DEBUG`，`INFO`，`WARN`，`ERROR`，`FATAL`
            - `%r`: 输出自应用启动到输出该`log`信息耗费的毫秒数
            - `%c`: 输出所属的类目，通常就是所在类的全名
            - `%t`: 输出产生该日志事件的线程名
            - `%n`: 输出一个回车换行符，`Windows `平台为`/r/n`，`Unix`平台为`/n`
            - `%d`: 输出日志时间点的日期或时间，默认格式为`ISO8601`，也可以在其后指定格式，比如: `%d{yyy MMM dd HH:mm:ss , SSS}`，输出类似: `2002年10月18日 22:10:28,921`
            - `%l`: 输出日志事件的发生位置，包括类目名、发生的线程，以及在代码中的行数。举例: `Testlog4.main(TestLog4.java: 10 )`
    - 输出级别：控制日志信息的显示内容，即显示哪些级别的日志信息。
- `Log4j`是日志实现,真正打印日志的是`Slf4j`接口
    - 导入`slf4j-log4j`依赖
    ```xml
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
        <version>1.7.25</version>
    </dependency>
    ```
    - `log4j.properties`
        
        ```properties
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
        - `log4j.rootLogger`: 根日志，配置了日志级别为`INFO`，预定义了名称为`console`、`file`两种附加器 
        - `log4j.appender.console`: `console`附加器，日志输出位置在控制台
        - `log4j.appender.console.layout`: `console`附加器，采用匹配器布局模式
        - `log4j.appender.console.layout.ConversionPattern`: `console`附加器，日志输出格式为：日期 日志级别 [类名] - 消息换行符 
        - `log4j.appender.file`: `file`附加器，每天产生一个日志文件
        - `log4j.appender.file.File`: `file`附加器，日志文件输出位置`logs/log.log`
        - `log4j.appender.file.layout`: `file`附加器，采用匹配器布局模式
        - `log4j.appender.A3.MaxFileSize`: 日志文件最大值
        - `log4j.appender.A3.MaxBackupIndex`: 最多纪录文件数
        - `log4j.appender.file.layout.ConversionPattern`: `file`附加器，日志输出格式为：日期 日志级别 [类名] - 消息换行符
    - 使用`Logger`
        ```java
        private static final Logger logger = LoggerFactory.getLogger(Main.class);
        logger.trace("trace级别日志");
        logger.info("info级别日志");
        logger.warn("warn级别日志");
        logger.debug("debug级别日志");
        logger.error("error级别日志");
        logger.fatal("fatal级别日志");
        logger.trace("日志占位符: {}, {}", "1", "2");
       ```

## SpringMVC

- `SpringMVC`是`Spring`对展示层的`MVC`架构的一种实现.
- `SpringMVC`提供`MVC`架构和用于灵活开发与松散耦合的`Web`程序组件
    - 模型(`Model`): 封装应用数据
    - 视图(`View`): 渲染模型数据
    - 控制器(`Controller`): 处理用户请求并构建数据模型,然后将其传递给视图进行渲染
- `DispatcherServlet`是`SpringMVC`的核心组件类,负责处理所以请求和响应,处理流程如下
    - 接收到`HTTP`请求后,`DispatcherServlet`通过`Handler Mapping`查找并调用对应的`Controller`
    - `Controller`接受请求并根据请求方法和路径映射,调用相应服务方法,服务方法根据业务逻辑设置模型数据,将视图名称返回`DispatcherServlet`
    - `DispatcherServlet`从`ViewResolver`中获取请求视图
    - 视图渲染完成,`DispatcherServlet`将视图响应返回浏览器

## Mybatis

`MyBatis`是一个优秀的基于`Java`的持久层框架，它内部封装了`JDBC`, 使开发者只需关注`SQL`语句本身，而不用再花费精力去处理诸如注册驱动、创建`Connection`、配置`Statement`等繁杂过程

`Mybatis`通过`xml`或注解的方式配置`Statement`,并根据`Java`对象和`Statement`中`SQL`的参数映射生成最终的`SQL`语句,最后由`Mybatis`框架执行`SQL`并将结果映射成`Java`对象返回.

`Mybatis`是半自动的`ORM`框架,着重于`POJO`类与`SQL`语句之间的映射关系.

## Spring 整合 Druid数据源

`Druid`是阿里巴巴开源平台上的一个项目, 整个项目由数据库连接池、插件框架和`SQL`解析器组成. 该项目主要是为了扩展`JDBC`的一些限制, 可以让程序员实现一些特殊的需求, 比如向密钥服务请求凭证、统计`SQL`信息、`SQL`性能收集、`SQL`注入检查、`SQL`翻译等, 程序员可以通过定制来实现自己需要的功能.


### POM

在`pom.xml`文件中添加`com.alibaba:druid`和`mysql:mysql-connector-java`依赖
```
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.6</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.46</version>
</dependency>
```

### 数据源配置

数据源配置文件`jdbc.properties`
```
# JDBC
# MySQL 8.x: com.mysql.cj.jdbc.Driver
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.connectionURL=jdbc:mysql://192.168.75.134:3306/myshop?useUnicode=true&characterEncoding=utf-8&useSSL=false
jdbc.username=root
jdbc.password=123456

# JDBC Pool
jdbc.pool.init=1
jdbc.pool.minIdle=3
jdbc.pool.maxActive=20

# JDBC Test
jdbc.testSql=SELECT 'x' FROM DUAL
```

### Spring整合数据源组件

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 加载配置属性文件 -->
    <context:property-placeholder ignore-unresolvable="true" location="classpath:jdbc.properties"/>

    <!-- 数据源配置, 使用 Druid 数据库连接池 -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <!-- 数据源驱动类可不写，Druid默认会自动根据URL识别DriverClass -->
        <property name="driverClassName" value="${jdbc.driverClass}"/>

        <!-- 基本属性 url、user、password -->
        <property name="url" value="${jdbc.connectionURL}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>

        <!-- 配置初始化大小、最小、最大 -->
        <property name="initialSize" value="${jdbc.pool.init}"/>
        <property name="minIdle" value="${jdbc.pool.minIdle}"/>
        <property name="maxActive" value="${jdbc.pool.maxActive}"/>

        <!-- 配置获取连接等待超时的时间 -->
        <property name="maxWait" value="60000"/>

        <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
        <property name="timeBetweenEvictionRunsMillis" value="60000"/>

        <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
        <property name="minEvictableIdleTimeMillis" value="300000"/>

        <property name="validationQuery" value="${jdbc.testSql}"/>
        <property name="testWhileIdle" value="true"/>
        <property name="testOnBorrow" value="false"/>
        <property name="testOnReturn" value="false"/>

        <!-- 配置监控统计拦截的filters -->
        <property name="filters" value="stat"/>
    </bean>
</beans>
```

### 配置Druid监控中心

`web.xml`文件中增加`Druid`提供的`Servlet`

```
<servlet>
    <servlet-name>DruidStatView</servlet-name>
    <servlet-class>com.alibaba.druid.support.http.StatViewServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>DruidStatView</servlet-name>
    <url-pattern>/druid/*</url-pattern>
</servlet-mapping>
```

浏览器打开`http://localhost:8080/druid/index.html`

## Spring 整合 Mybatis

### POM

在`pom.xml`文件中增加`Mybatis`的依赖: `org.mybatis:mybatis`、`org.mybatis:mybatis-spring`、`org.springframework:spring-jdbc`

```
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.2.8</version>
</dependency>
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>1.3.1</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>4.3.17.RELEASE</version>
</dependency>
```

### 创建 Mybatis 配置文件

创建`Mybatis`配置文件`mybatis-config.xml`

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 全局参数 -->
    <settings>
        <!-- 打印 SQL 语句 -->
        <setting name="logImpl" value="STDOUT_LOGGING" />
    
        <!-- 使全局的映射器启用或禁用缓存。 -->
        <setting name="cacheEnabled" value="false"/>

        <!-- 全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载。 -->
        <setting name="lazyLoadingEnabled" value="true"/>

        <!-- 当启用时，有延迟加载属性的对象在被调用时将会完全加载任意属性。否则，每种属性将会按需要加载。 -->
        <setting name="aggressiveLazyLoading" value="true"/>

        <!-- 是否允许单条 SQL 返回多个数据集 (取决于驱动的兼容性) default:true -->
        <setting name="multipleResultSetsEnabled" value="true"/>

        <!-- 是否可以使用列的别名 (取决于驱动的兼容性) default:true -->
        <setting name="useColumnLabel" value="true"/>

        <!-- 允许 JDBC 生成主键。需要驱动器支持。如果设为了 true，这个设置将强制使用被生成的主键，有一些驱动器不兼容不过仍然可以执行。 default:false  -->
        <setting name="useGeneratedKeys" value="false"/>

        <!-- 指定 MyBatis 如何自动映射 数据基表的列 NONE：不映射 PARTIAL：部分 FULL:全部  -->
        <setting name="autoMappingBehavior" value="PARTIAL"/>

        <!-- 这是默认的执行类型 （SIMPLE: 简单； REUSE: 执行器可能重复使用prepared statements语句；BATCH: 执行器可以重复执行语句和批量更新） -->
        <setting name="defaultExecutorType" value="SIMPLE"/>

        <!-- 使用驼峰命名法转换字段。 -->
        <setting name="mapUnderscoreToCamelCase" value="true"/>

        <!-- 设置本地缓存范围 session:就会有数据的共享 statement:语句范围 (这样就不会有数据的共享 ) defalut:session -->
        <setting name="localCacheScope" value="SESSION"/>

        <!-- 设置 JDBC 类型为空时,某些驱动程序 要指定值, default:OTHER，插入空值时不需要指定类型 -->
        <setting name="jdbcTypeForNull" value="NULL"/>
    </settings>
</configuration>
```

### Spring 集成 Mybatis

创建`Spring`整合`Mybatis`的配置文件`spring-context-mybatis.xml`

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!-- 配置 SqlSession -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!-- 用于配置对应实体类所在的包，多个 package 之间可以用 ',' 号分割 -->
        <property name="typeAliasesPackage" value="com.funtl.my.shop.domain"/>
        <!-- 用于配置对象关系映射配置文件所在目录 -->
        <property name="mapperLocations" value="classpath:/mapper/**/*.xml"/>
        <property name="configLocation" value="classpath:/mybatis-config.xml"></property>
    </bean>

    <!-- 扫描 Mapper -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.funtl.my.shop.web.admin.dao" />
    </bean>
</beans>
```

# 参考

## 媒体查询:
- 媒体类型: `Screen`、`All`、`Print`为最常见的三种媒体类型
- 媒体特性: 媒体查询中的大部分接受`min/max`前缀，用来表达其逻辑关系，表示应用大于等于或小于等于某个值的情况
    - `width`: `Length`渲染界面的宽度
    - `height: `Length`渲染界面的高度
    - `color` 整数，表示色彩的字节数
    - `color-index` 整数， 色彩表中的色彩数
    -  `device-aspct-ratio` 整数/整数，宽高比例
    -  `device-height`: `Length`设备屏幕的输出高度
    -  `device-width`: `Length`设备屏幕的输出宽度
    -  `grid`(不支持`min/max`前缀): 整数，是否基于栅格的设备
    -  `monochrome`: 整数，单色帧缓冲器中每像素字节数
    -  `resolution`: 分辨率(`dpi/dpcm`)分辨率
    -  `scan`(不支持`min/max`前缀): `Progressive interlaced`，`TV`媒体类型的扫描方式
    -  `orientation`(不支持`min/max`前缀): `Portrait/landscape` 横屏或竖屏
- 格式
    ```css
    /*****************************************
    @media 媒体类型 and (媒体特性) {样式}
    ******************************************/
    /* max-width: 媒体类型小于等于指定值,样式生效 */
    @media screen and (max-width: 480px) {
        .abc {
            display: none;
        }
    }
    ```
## 编程思想

- `TDD`(测试驱动编程): 先写测试用例,再编码
- `DDD`(领域驱动设计): 

## 资源
- [AdminLTE](https://github.com/almasaeed2010/AdminLTE.git)
- [metronic-default](http://metronic.net.cn/dist/default/index.html)/[metronic](http://metronic.net.cn/)
- [admin-lte_cdn](https://cdnjs.com/libraries/admin-lte)
- [LUG@USTC Google Fonts 加速服务](https://lug.ustc.edu.cn/wiki/lug/services/googlefonts)
- [常用前端公共库 CDN 服务](https://css.loli.net/)
- [funtl-github](https://github.com/funtl)
- [funtl](http://www.funtl.com)
- [JSON API](https://www.sojson.com/api/qqmusic.html)
