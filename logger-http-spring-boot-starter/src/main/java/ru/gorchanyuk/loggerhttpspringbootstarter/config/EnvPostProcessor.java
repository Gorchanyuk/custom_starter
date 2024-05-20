package ru.gorchanyuk.loggerhttpspringbootstarter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * Класс предназначенный для загрузки дефолтных настроек стартера из default.properties
 */
@Slf4j
public class EnvPostProcessor implements EnvironmentPostProcessor {
    private final PropertySourceLoader propertySourceLoader;

    public EnvPostProcessor() {
        propertySourceLoader = new PropertiesPropertySourceLoader();
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        var resource = new ClassPathResource("default.properties"); // определяем default.properties как локальный ресурс
        PropertySource<?> propertySource = null;
        try {
            propertySource = propertySourceLoader.load("logger-http-spring-boot-starter", resource).get(0);
            // прочитанные настройки проставляются в настройки окружения Spring'а
            environment.getPropertySources().addLast(propertySource);
        } catch (IOException e) {
            log.warn("Не удалось загрузить файл .properties для logger-http-spring-boot-starter");
        }
    }
}