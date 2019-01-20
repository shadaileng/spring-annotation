# spring-annotation

```



```

# frontend

## 创建无配置Spring Web工程

1. 导入依赖
    ```xml
    <properties>
        <spring.version>4.3.11.RELEASE</spring.version>
    </properties>
    <dependencies>
        <!-- Spring -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <!-- 文件上传 -->
        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.3.3</version>
        </dependency>
        <!-- Json -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.5</version>
        </dependency>
        <!-- Servlet API -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>
        <!-- jsp相关的依赖 -->
        <dependency>
            <groupId>jstl</groupId>
            <artifactId>jstl</artifactId>
            <version>1.1.2</version>
        </dependency>
        <dependency>
            <groupId>taglibs</groupId>
            <artifactId>standard</artifactId>
            <version>1.1.2</version>
        </dependency>
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
            <!-- 热部署插件 -->
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
    ```
2. 编写`IOC`容器类,扫面业务注解
    ```java
    @ComponentScan(value = {"com.qpf"}, excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
    })
    public class RootConfig {
    }
    ```
3. 编写`WebMvc`容器类,扫描`@Controller`注解
    ```java
    @PropertySource("classpath:/application.properties")
    @Configuration
    @ComponentScan(value = {"com.qpf"}, includeFilters = {
            @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
    }, useDefaultFilters = false)
    @EnableWebMvc
    public class WebConfig extends WebMvcConfigurerAdapter {
        private static final String RESOURCES_LOCATION = "/resources/";
        /**
         * 静态资源交给Servlet容器处理
         */
        @Override
        public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
            configurer.enable();
        }
        /**
         * 视图解析器
         */
        @Override
        public void configureViewResolvers(ViewResolverRegistry registry) {
            registry.jsp();
        }
        
        /**
         * 静态资源映射
         */
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/tmp/" + "**").addResourceLocations("file:/home/shadaileng/develop/tmp/");
            registry.addResourceHandler(RESOURCES_LOCATION + "**").addResourceLocations(RESOURCES_LOCATION);
        }
        /**
         * 拦截器
         */
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            InterceptorRegistration registration = registry.addInterceptor(new HelloInterceptor());
            registration.addPathPatterns("/**");
        }
        /**
         * 文件上传解析器
         */
        @Bean
        public CommonsMultipartResolver multipartResolver() {
            return new CommonsMultipartResolver();
        }
        /**
         * 消息类型转换
         */
        @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
            stringHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(new MediaType[]{MediaType.TEXT_PLAIN, MediaType.TEXT_HTML}));
            stringHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
            converters.add(stringHttpMessageConverter);
            // jacksonConverter
            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
            converters.add(mappingJackson2HttpMessageConverter);
        }
        /**
         * 自定义配置类
         */
        @Bean
        public ConfigProperties configProperties() {
            return new ConfigProperties();
        }
    }
    ```
4. 继承`AbstractAnnotationConfigDispatcherServletInitializer`类,加载`Spring`容器配置
    ```java
    public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    
        /**
         * 加载主容器配置类
         * @return 
         */
        @Override
        protected Class<?>[] getRootConfigClasses() {
            return new Class<?>[]{RootConfig.class};
        }
        /**
        * 加载Web容器配置类
        * @return 
        */
        @Override
        protected Class<?>[] getServletConfigClasses() {
            return new Class<?>[]{WebConfig.class};
        }
        /**
        * DispatchServlet映射路径
        * @return 
        */
        @Override
        protected String[] getServletMappings() {
            return new String[]{"/"};
        }
        /**
        * 配置容器启动参数
        * @param registration
        */
        @Override
        protected void customizeRegistration(ServletRegistration.Dynamic registration) {
            registration.setInitParameter("defaultHtmlEscape", "true");
            registration.setInitParameter("spring.profiles.active", "default");
            System.setProperty("app.version", "1.0.0.RELEASE");
        }
        /**
        * 字符编码过滤器
        * @return 
        */
        @Override
        protected Filter[] getServletFilters() {
            CharacterEncodingFilter filter = new CharacterEncodingFilter();
            filter.setEncoding("UTF-8");
            filter.setForceRequestEncoding(true);
            filter.setForceResponseEncoding(true);
            System.out.println("-------------------------------------");
            return new Filter[]{filter};
        }
    
    }
    ```
## 整合Mybatis

1. 导入相关依赖
    ```xml
    <dependencies>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.2</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>1.3.2</version>
        </dependency>
        <!-- C3P0 -->
        <dependency>
            <groupId>c3p0</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.1</version>
        </dependency>
        <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>3.21.0.1</version>
        </dependency>
    </dependencies>
    ```
2. 配置`Mybatis`
    ```java
    @Configuration
    // 配置文件位置
    @PropertySource("classpath:/application.properties")
    // mapper接口所在的包
    @MapperScan("com.qpf.dao")
    @EnableTransactionManagement
    public class DataConfig {
        @Value("${jdbc.url}")
        private String jdbcUrl;
        @Value("${jdbc.driver}")
        private String jdbcDriver;
        @Value("${jdbc.user}")
        private String user;
        @Value("${jdbc.password}")
        private String password;
        // 数据源
        @Bean
        public DataSource dataSource() throws Exception {
            ComboPooledDataSource dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(jdbcDriver);
            dataSource.setJdbcUrl(jdbcUrl);
            dataSource.setUser(user);
            dataSource.setPassword(password);
            return dataSource;
        }
        // 数据源管理器
        @Bean
        public DataSourceTransactionManager transactionManager() throws Exception {
            return new DataSourceTransactionManager(dataSource());
        }
        // Sql工厂类
        @Bean
        public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
            SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
            factoryBean.setDataSource(dataSource());
            // 实体类所在的包
            factoryBean.setTypeAliasesPackage("com.qpf.bean");
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            // 自动生成主键
            configuration.setUseGeneratedKeys(true);
            // 驼峰法命名规则
            configuration.setMapUnderscoreToCamelCase(true);
            factoryBean.setConfiguration(configuration);
            return factoryBean;
        }
    }
    ```
3. `mapper`接口
    ```
    @Mapper // 标注接口
    public interface PersonMapper {
        @Select("select * from person where id = #{id}")
        Person selectPersonById(@Param("id") int id);
        @Select("Select * from person")
        List<Person> listPerson();
        @Insert("insert into person(name,gender) values(#{name}, #{gender})")
        @Options(useGeneratedKeys = true, keyProperty = "id")
        int insertPerson(Person person);
        @Update("update person name = #{person.name}, gender = #{gender} where id = #{id}")
        int updatePersonById(Person person);
        @Delete("delete person where id = #{id}")
        int deletePersonById(@Param("id") int id);
    }
    ```
4. 配置文件
    ```
    # application.properties
    jdbc.url=jdbc:sqlite::resource:data/data.db
    #jdbc.url=jdbc:sqlite:F:/qipf/tmp/data.db
    jdbc.driver=org.sqlite.JDBC
    jdbc.user=
    jdbc.password=
    ```
5. 初始数据和实体类
    ```sql
    Create table if not exists person(
        id INTEGER primary key AUTOINCREMENT,
        name varchar(200) not null,
        gender tinyint(1) not null
    );

    insert into person(name, gender) values('shadaleng', 1);
    insert into person(name, gender) values('cy', 0);
    insert into person(name, gender) values('cxl', 0);

    select * from person;
    -----------------------------------------------------
    public class Person {
        private Integer id;
        private String name;
        private String gender;
        // constructors,getter,settor和toString
    }
    ```
6. 测试类
    ```java
    @ContextConfiguration(classes={DataConfig.class})
    @RunWith(SpringJUnit4ClassRunner.class)
    public class BasicTest {
        @Autowired
        private PersonMapper personMapper;
        @Test
        public void testPersonMapper() {
            Person person = personMapper.selectPersonById(1);
            System.out.println(person);
            System.out.println(personMapper.listPerson());
        }
        @Test
        public void testSavePerson() {
            int insert = personMapper.insertPerson(new Person(null, "qpf", "1"));
            System.out.println(insert);
        }
    }
    ```
## 整合Thymeleaf

1. 导入`Thymeleaf`相关依赖

    ```xml
    <dependencies>
        <dependency>
            <groupId>org.thymeleaf</groupId>
            <artifactId>thymeleaf-spring4</artifactId>
            <version>3.0.9.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity4</artifactId>
            <version>3.0.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-java8time</artifactId>
            <version>3.0.0.RELEASE</version>
        </dependency>
    </dependencies>
    ```

2. 配置视图解析器
    ```java
    @Configuration
    @ComponentScan(value = {"com.qpf"}, includeFilters = {
            @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
    }, useDefaultFilters = false)
    @EnableWebMvc
    public class WebConfig extends WebMvcConfigurerAdapter {
        private static final String VIEWS = "/WEB-INF/views/";
        private static final String CHARACTER_ENCODING = "UTF-8";
        private boolean THTMELEAF_CACHEABLE = false;
        @Bean
        public ITemplateResolver templateResolver() {
            SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
            resolver.setPrefix(VIEWS);
            resolver.setSuffix(".html");
            resolver.setTemplateMode(TemplateMode.HTML);
            resolver.setCharacterEncoding(CHARACTER_ENCODING);
            resolver.setCacheable(THTMELEAF_CACHEABLE);
            return resolver;
        }
        @Bean
        public SpringTemplateEngine templateEngine() {
            SpringTemplateEngine templateEngine = new SpringTemplateEngine();
            templateEngine.setTemplateResolver(templateResolver());
            templateEngine.addDialect(new SpringSecurityDialect());
            templateEngine.addDialect(new Java8TimeDialect());
            return templateEngine;
        }
        @Bean
        public ViewResolver viewResolver() {
            ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
            thymeleafViewResolver.setTemplateEngine(templateEngine());
            thymeleafViewResolver.setCharacterEncoding(CHARACTER_ENCODING);
            return thymeleafViewResolver;
        }
     }
    ```
3. `Controller`映射`HTML`文件
    ```java
    @Controller
    public class HelloController {
        @GetMapping("/success")
        public String success(Map<String, Object> map) {
            map.put("Hello", "Hello World");
            return "success";
        }
    
    }
    ```
4. `/WEB-INF/views`文件夹下新建`success.html`文件
    ```html
    <!DOCTYPE html>
    <html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Success</title>
    </head>
    <body>
        <h1>Success</h1>
        <h2>thymeleaf</h2>
        <h2 th:utext="${Hello}"></h2>
    </body>
    </html>
    ```
5. 启动应用,浏览器访问`http://localhost:8080/frontend/success`

## 配置logback

- `logback`主要模块:`logback-access`, `logback-core`, `logback-clasic`
    - `Logger`类位于`logback-classic`模块中,而`Appender`和`Layout`位于`logback-core`中,`Logger`会依赖于`Appender`和`Layout`的协助, 日志信息才能被正常打印出来
- `logback`加载配置文件的顺序
    - `JVM`启动参数`-Dlogback.configurationFile=/path/to/xxx.xml`
    - `classpath:logback.groovy`
    - `classpath:logback-test.xml`
    - `classpath:logback.xml`
    - `JDK1.6x`以上版本:`com.qos.logback.classic.spi.Configurator`接口的实现类
        - `META-INF\services\ch.qos.logback.classic.spi.Configurator`文件指定配置类
    - `ch.qos.logback.classic.BasicConfigurator`接口的实现类
- 配置文件主要标签
    - `root`: 根日志对象
    - `logger`: 日志对象
    - `appender`: 日志输出目标,每个`logger`可以绑定多个`appender`

- `logger`分层命名规则: 为了控制哪些日志可以输出,哪些日志不能输出,每个`logger`都有一个`name`属性,以包名作为属性值
    - `logger`之间根据包名存在父子关系,如`com.qpf`是`com.qpf.service`的父级
    - 存在一个`root logger`是所有`logger`的祖先
        `Logger root = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);`
- 日志打印级别
    - `TRACE` < `DEBUG` < `INFO` < `WARN` < `ERROR`
    - 如果当前`logger`未指定打印级别,会继承父级`logger`的打印级别,直到`root logger`,所以`root logger`必须指定打印级别
- `logback`的内部运行流程如下所示
    - 获得过滤器链条
    - 检查日志级别以决定是否继续打印
    - 创建一个`LoggingEvent`对象
    - 调用`Appenders`
    - 进行日志信息格式化
    - 发送`LoggingEvent`到对应的目的地
- `logback.xml`
    ```
    <?xml version="1.0" encoding="UTF-8"?>
    <!-- scan: 是否扫描最新配置文件 -->
    <!-- scanPeriod: 扫描配置文件间隔时间,默认6000 milliseconds -->
    <!-- debug: 是否打印logback的日志 -->
    <configuration scan="true" scanPeriod="60 seconds" debug="false">
        <!-- 打印日志的等级: TRACE < DEBUG < INFO < WARN < ERROR -->
        <property name="log.level" value="debug"/>
        <!-- 日志保留最大历史数量 -->
        <property name="log.maxHistory" value="30"/>
        <!-- 日志保存路径 -->
        <property name="log.filePath" value="${catalina.base}/logs/webapps"/>
        <!-- 日志打印格式 -->
        <property name="log.pattern" value="%d{yyyy-MM-dd HH:mm:ss:SSS} [%thread] %-5level %logger{50}-%msg%n"/>
        <appender name="consoleApperder" class="ch.qos.logback.core.ConsoleAppender">
            <encoder>
                <pattern>${log.pattern}</pattern>
            </encoder>
        </appender>
        <!-- DEBUG -->
        <appender name="debugApperder" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <!-- 文件路径 -->
            <file>${log.filePath}/debug.log</file>
            <!-- 文件名称 -->
            <rollingPolicy clasic="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>${log.filePath}/debug/debug.%d{yyyy-MM-dd}.log.gz</fileNamePattern>
                <maxHistory>${log.maxHistory}</maxHistory>
            </rollingPolicy>
            <encoder>
                <pattern>${log.pattern}</pattern>
            </encoder>
            <filter class="ch.qos.logback.classic.filter.LevelFilter">
                <level>DEBUG</level>
                <onMatch>ACCEPT</onMatch>
                <onMismatch>DENY</onMismatch>
            </filter>
        </appender>
            <!-- INFO -->
        <appender name="infoApperder" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <!-- 文件路径 -->
            <file>${log.filePath}/info.log</file>
            <!-- 日志备份文件名称 -->
            <rollingPolicy clasic="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>${log.filePath}/info/info.%d{yyyy-MM-dd}.log.gz</fileNamePattern>
                <MaxHistory>${log.maxHistory}</MaxHistory>
            </rollingPolicy>
            <encoder>
                <pattern>${log.pattern}</pattern>
            </encoder>
            <filter class="ch.qos.logback.classic.filter.LevelFilter">
                <level>INFO</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
            </filter>
        </appender>
        <!-- ERROR -->
        <appender name="errorApperder" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <!-- 文件路径 -->
            <file>${log.filePath}/error.log</file>
            <!-- 日志备份文件名称 -->
            <rollingPolicy clasic="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>${log.filePath}/error/error.%d{yyyy-MM-dd}.log.gz</fileNamePattern>
                <maxHistory>${log.maxHistory}</maxHistory>
            </rollingPolicy>
            <encoder>
                <pattern>${log.pattern}</pattern>
            </encoder>
            <filter class="ch.qos.logback.classic.filter.LevelFilter">
                <level>ERROR</level>
                <onMatch>ACCEPT</onMatch>
                <onMismatch>DENY</onMismatch>
            </filter>
        </appender>
        <!-- 指定log级别,以及包,additivity: 是否继承root标签 -->
        <logger name="com.qpf" level="${log.level}" additivity="true">
        <appender-ref ref="debugApperder"/>
        <appender-ref ref="infoApperder"/>
        <appender-ref ref="errorApperder"/>
        </logger>
        <!-- 基础的日志打印 -->
        <root level="info">
        <appender-ref ref="consoleApperder"/>
        </root>
    </configuration>
    ```
    - `ch.qos.logback.classic.spi.Configurator`实现类
        ```java
        public class LogbackConfigurator extends ContextAwareBase implements Configurator {
            @Override
            public void configure(LoggerContext loggerContext) {
                addInfo("Setting up Logback configuration begin.");
        
                ConsoleAppender<ILoggingEvent> ca = new ConsoleAppender<ILoggingEvent>();
                ca.setContext(loggerContext);
                ca.setName("console");
                LayoutWrappingEncoder<ILoggingEvent> encoder = new LayoutWrappingEncoder<ILoggingEvent>();
                encoder.setContext(loggerContext);
                
                // same as
                // TTLLLayout layout = new TTLLLayout();
                PatternLayout layout = new PatternLayout();
                layout.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        
                layout.setContext(loggerContext);
                layout.start();
                encoder.setLayout(layout);
        
                ca.setEncoder(encoder);
                ca.start();
        
                Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
                rootLogger.addAppender(ca);
        
                rootLogger.addAppender(generateAppender(loggerContext, "debug", Level.DEBUG, encoder));
                rootLogger.addAppender(generateAppender(loggerContext, "info", Level.INFO, encoder));
                rootLogger.addAppender(generateAppender(loggerContext, "error", Level.ERROR, encoder));
        
                addInfo("Setting up Logback configuration end.");
            }
        
            private OutputStreamAppender<ILoggingEvent> generateAppender(LoggerContext loggerContext, String name, Level level, LayoutWrappingEncoder<ILoggingEvent> encoder) {
                RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<>();
                rollingFileAppender.setContext(loggerContext);
        
                rollingFileAppender.setName(name);
        
                rollingFileAppender.setFile("${catalina.base}/logs/webapps/" + name + ".log");
        
                rollingFileAppender.setEncoder(encoder);
        
                LevelFilter filter = new LevelFilter();
                filter.setContext(loggerContext);
                filter.setLevel(level);
                filter.setOnMatch(FilterReply.ACCEPT);
                filter.setOnMismatch(FilterReply.DENY);
                rollingFileAppender.addFilter(filter);
        
                TimeBasedRollingPolicy policy = new TimeBasedRollingPolicy();
                policy.setContext(loggerContext);
                policy.setFileNamePattern("${catalina.base}/logs/webapps/" + name + "/" + name + ".%d{yyyy-MM-dd}.log.gz");
                policy.setMaxHistory(30);
                rollingFileAppender.setRollingPolicy(policy);
        
                return rollingFileAppender;
            }
        }
        ```
## 使用Redis缓存

`Redis`是一种`key-value`型数据库

## 定时备份

- 使用数据库的备份命令生成数据备份`sql`文件
    ```
    $ mysqldump -uroot -proot ssm > /root/backup/sql/ssm-`date +%Y%m%d%H%M%S`.sql
    ```
- 备份图片文件
    ```
    $ tar -zcvf /root/backup/image/img-`data +%Y%m%d%H%M%S`.tar.gz ~/work/image/upload/
    ```
- 将备份命令放入`Shell`文件中
    ```
    # /root/backup/backup.sh
    ---------------------------
    #!/bin/sh
    mysqldump -uroot -proot ssm > /root/backup/sql/ssm-`date +%Y%m%d%H%M%S`.sql
    tar -zcvf /root/backup/image/img-`data +%Y%m%d%H%M%S`.tar.gz ~/work/image/upload/
    ```
- 使用`crontab`执行定时任务
    - 查看定时任务
        ```
        $ crontab -l
        ```
    - 设置定时任务
        ```
        $ crontab -e
        -------------------------
        # 执行定时任务,每天00::00执行一次
        0 0 * * * sh /root/backup/backup.sh
        ```
     - 查看`crontab`日志
        ```
        $ tail -f /var/log/cron
        ```
## 参考

- [Spring MVC Quickstart Maven Archetype](https://github.com/kolorobot/spring-mvc-quickstart-archetype.git)
- [sqlite3使用入门](https://yuanzhifei89.iteye.com/blog/1123870)
- [logback 配置详解](https://www.jianshu.com/p/1ded57f6c4e3)
- [Bootstrap File Input](http://plugins.krajee.com/file-input)
- [基于Metronic的Bootstrap开发框架经验总结（5）--Bootstrap文件上传插件File Input的使用](http://www.cnblogs.com/wuhuacong/p/4774396.html)
- [SpringCache与redis集成，优雅的缓存解决方案](https://www.cnblogs.com/chenkeyu/p/8028781.html)
- [Spring 后置处理器 PropertyPlaceholderConfigurer 类（引用外部文件）](https://www.cnblogs.com/libra0920/p/6169338.html)
