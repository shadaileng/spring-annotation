package com.qpf.website.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.qpf.website.web.config.properties.DataProperties;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan({"com.qpf.website.dao"})
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class DataConfig {
    @Autowired
    private DataProperties properties;

    // TODO 配置Druid数据源
    @Bean(destroyMethod = "close", initMethod = "init")
    public DataSource druidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        // 数据源驱动类可不写，Druid默认会自动根据URL识别DriverClass
        dataSource.setDriverClassName(properties.getJdbcClassDriver());
        // 基本属性 url、user、password
        dataSource.setUrl(properties.getJdbcConnectionUrl());
        dataSource.setUsername(properties.getJdbcUsername());
        dataSource.setPassword(properties.getJdbcPassword());

        // 配置初始化大小、最小、最大
        dataSource.setInitialSize(properties.getJdbcPoolInit());
        dataSource.setMinIdle(properties.getJdbcPoolMinIdle());
        dataSource.setMaxActive(properties.getJdbcPoolMaxActive());

        // 配置获取连接等待超时的时间
        dataSource.setMaxWait(properties.getJdbcMaxWait());

        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenConnectErrorMillis(properties.getJdbcTimeclose());

        // 测试
        dataSource.setValidationQuery(properties.getJdbcTestSql());
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);

        return dataSource;
    }

    // TODO 配置MyBatis
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        sqlSessionFactoryBean.setDataSource(druidDataSource());
        sqlSessionFactoryBean.setTypeAliasesPackage("com.qpf.manage.entity");

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setLogImpl("STDOUT_LOGGING");
        // 使全局的映射器启用或禁用缓存
        configuration.setCacheEnabled(false);
        // 全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载
        configuration.setLazyLoadingEnabled(false);
        // 当启用时，有延迟加载属性的对象在被调用时将会完全加载任意属性。否则，每种属性将会按需要加载
        configuration.setAggressiveLazyLoading(true);
        // 是否允许单条 SQL 返回多个数据集 (取决于驱动的兼容性) default:true
        configuration.setMultipleResultSetsEnabled(true);
        // 是否可以使用列的别名 (取决于驱动的兼容性) default:true
        configuration.setUseColumnLabel(true);
        // 允许 JDBC 生成主键。需要驱动器支持。如果设为了 true，这个设置将强制使用被生成的主键，有一些驱动器不兼容不过仍然可以执行。 default:false
        configuration.setUseGeneratedKeys(true);
        // 指定 MyBatis 如何自动映射 数据基表的列 NONE：不映射 PARTIAL：部分 FULL:全部
        configuration.setAutoMappingBehavior(AutoMappingBehavior.PARTIAL);
        // 这是默认的执行类型 （SIMPLE: 简单； REUSE: 执行器可能重复使用prepared statements语句；BATCH: 执行器可以重复执行语句和批量更新）
        configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
        // 使用驼峰命名法转换字段
        configuration.setMapUnderscoreToCamelCase(true);
        // 设置本地缓存范围 session:就会有数据的共享 statement:语句范围 (这样就不会有数据的共享 ) defalut:session
        configuration.setLocalCacheScope(LocalCacheScope.SESSION);
        // 设置 JDBC 类型为空时,某些驱动程序 要指定值, default:OTHER，插入空值时不需要指定类型
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionFactoryBean.setConfiguration(configuration);

        return sqlSessionFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(druidDataSource());
    }

}
