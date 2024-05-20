package ru.gorchanyuk.loggerhttpspringbootstarter.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Отвкчает за настройку конфигурации Spring MVC
 */
@Slf4j
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final List<HandlerInterceptor> loggingInterceptors;

    /**
     * Добавление списка пользовательских интерцепторов в конфигурацию Spring MVC
     * @param registry список Interceptor'ов
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        if (ObjectUtils.isEmpty(loggingInterceptors)){
            return;
        }
        loggingInterceptors.forEach(registry::addInterceptor);
        log.info("Добавление Interceptor'ов для логирования http запросов");
    }
}