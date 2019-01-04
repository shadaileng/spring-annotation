# spring-annotation

```



```

# frontend

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
        public String success() {
            return "success";
        }
    
    }
    ```
4. `/WEB-INF/views`文件夹下新建`success.html`文件
    ```html
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Success</title>
    </head>
    <body>
        <h1>Success</h1>
        <h2>thymeleaf</h2>
    </body>
    </html>
    ```
5. 启动应用,浏览器访问`http://localhost/frontend/success`
## 参考

- [Spring MVC Quickstart Maven Archetype](https://github.com/kolorobot/spring-mvc-quickstart-archetype.git)