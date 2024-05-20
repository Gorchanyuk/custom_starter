package ru.gorchanyuk.loggerhttpspringbootstarter.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.gorchanyuk.loggerhttpspringbootstarter.props.LoggerHttpProperties;
import ru.gorchanyuk.loggerhttpspringbootstarter.service.LoggerService;
import ru.gorchanyuk.loggerhttpspringbootstarter.service.interceptor.AfterCompletionLoggingInterceptor;
import ru.gorchanyuk.loggerhttpspringbootstarter.service.interceptor.PostHandleLoggingInterceptor;
import ru.gorchanyuk.loggerhttpspringbootstarter.service.interceptor.PreHandleLoggingInterceptor;

import java.util.List;

/**
 * Класс отвечающий за автоконфигурацию стартера
 */
@RequiredArgsConstructor
@EnableConfigurationProperties(LoggerHttpProperties.class)
@ConditionalOnProperty(prefix = "logger-http.interceptor", value = "enabled", havingValue = "true", matchIfMissing = true)
public class LoggerHttpAutoConfiguration {

    private final LoggerHttpProperties level;

    @Bean
    public WebMvcConfigurer webConfig(List<HandlerInterceptor> loggingInterceptors) {

        return new WebConfig(loggingInterceptors);
    }

    @Bean
    public LoggerService loggerService() {
        return new LoggerService();
    }

    @Bean
    @ConditionalOnProperty(prefix = "logger-http.interceptor",
            value = "pre-handle-enabled", havingValue = "true")
    public HandlerInterceptor preHandleLoggingInterceptor(LoggerService loggerService) {

        return new PreHandleLoggingInterceptor(loggerService, level);
    }

    @Bean
    @ConditionalOnProperty(prefix = "logger-http.interceptor",
            value = "post-handle-enabled", havingValue = "true")
    public HandlerInterceptor postHandleLoggingInterceptor(LoggerService loggerService) {

        return new PostHandleLoggingInterceptor(loggerService, level);
    }

    @Bean
    @ConditionalOnProperty(prefix = "logger-http.interceptor",
            value = "after-completion-enabled", havingValue = "true", matchIfMissing = true)
    public HandlerInterceptor afterCompletionLoggingInterceptor(LoggerService loggerService) {

        return new AfterCompletionLoggingInterceptor(loggerService, level);
    }
}
