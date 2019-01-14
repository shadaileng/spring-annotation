package com.qpf.init;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.classic.layout.TTLLLayout;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.FilterReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

public class LogbackConfigurator extends ContextAwareBase implements Configurator {
    private String rootPath = "F:";
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
        layout.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} %line ==> %msg%n");

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

        rollingFileAppender.setFile(rootPath + "/logs/webapps/" + name + ".log");

        rollingFileAppender.setEncoder(encoder);

        LevelFilter filter = new LevelFilter();
        filter.setContext(loggerContext);
        filter.setLevel(level);
        filter.setOnMatch(FilterReply.ACCEPT);
        filter.setOnMismatch(FilterReply.DENY);
        rollingFileAppender.addFilter(filter);

        TimeBasedRollingPolicy policy = new TimeBasedRollingPolicy();
        policy.setContext(loggerContext);
        policy.setFileNamePattern(rootPath + "/logs/webapps/" + name + "/" + name + ".%d{yyyy-MM-dd}.log.gz");
        policy.setMaxHistory(30);
        rollingFileAppender.setRollingPolicy(policy);

        return rollingFileAppender;
    }
}
