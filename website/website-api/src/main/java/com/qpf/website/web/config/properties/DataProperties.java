package com.qpf.website.web.config.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Data
@ToString
@MetaProperties
public class DataProperties {

    @Value("${jdbc.class.driver}")
    private String jdbcClassDriver = "org.sqlite.JDBC";
    @Value("${jdbc.connection.url.linux}")
    private String jdbcConnectionUrlLinux = "jdbc:sqlite:file:/home/shadaileng/develop/Git/repository/spring-annotation/data/data.db";
    @Value("${jdbc.connection.url.win}")
    private String jdbcConnectionUrlWin = "jdbc:sqlite:file:F:/qipf/dev/ideaIU/git-respository/spring-annotation/data/data.db";
    //    private String JDBC_CONNECTION_URL = "jdbc:sqlite::resource:data/data.db";
    @Value("${jdbc.username}")
    private String jdbcUsername = "";
    @Value("${jdbc.password.linux}")
    private String jdbcPassword = "";

    @Value("${jdbc.pool.init}")
    private int jdbcPoolInit = 1;
    @Value("${jdbc.pool.min.idle}")
    private int jdbcPoolMinIdle = 3;
    @Value("${jdbc.pool.max.active}")
    private int JdbcPoolMaxActive = 20;
    @Value("${jdbc.test.sql}")
    private String jdbcTestSql = "SELECT 'x' FROM sqlite_master ";
    @Value("${jdbc.max.wait}")
    private long jdbcMaxWait = 60000;
    @Value("${jdbc.time.close}")
    private long jdbcTimeclose = 300000;
}
