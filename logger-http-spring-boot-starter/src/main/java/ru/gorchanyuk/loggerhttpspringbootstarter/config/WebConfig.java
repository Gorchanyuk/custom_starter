package ru.gorchanyuk.loggerhttpspringbootstarter.config;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final List<HandlerInterceptor> loggingInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        loggingInterceptors.forEach(registry::addInterceptor);
    }
}