package com.qpf.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@PropertySource("classpath:/application.properties")
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
	@Bean
	public DataSource dataSource() throws Exception {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(jdbcDriver);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUser(user);
		dataSource.setPassword(password);
//		dataSource.setJdbcUrl("jdbc:sqlite:F:/qipf/tmp/data.db");
//		dataSource.setJdbcUrl("jdbc:sqlite::resource:data/data.db");
		return dataSource;
	}
	@Bean
	public DataSourceTransactionManager transactionManager() throws Exception {
		return new DataSourceTransactionManager(dataSource());
	}
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
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
