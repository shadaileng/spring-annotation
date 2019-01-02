package com.qpf.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@PropertySource("classpath:application.properties")
@MapperScan("com.qpf.dao")
public class DataConfig {
	@Bean
	public DataSource  dataSource() throws Exception {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass("org.sqlite.JDBC");
		dataSource.setJdbcUrl("jdbc:sqlite:F:/qipf/tmp/data.db");
//		dataSource.setJdbcUrl("jdbc:sqlite::resource:data/data.db");
		dataSource.setUser("");
		dataSource.setPassword("");
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
		return factoryBean;
	}
}
