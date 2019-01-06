# spring-annotation

```



```

# frontend

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
## 参考

- [Spring MVC Quickstart Maven Archetype](https://github.com/kolorobot/spring-mvc-quickstart-archetype.git)
- [sqlite3使用入门](https://yuanzhifei89.iteye.com/blog/1123870)
