package ru.gorchanyuk.loggerhttpspringbootstarter.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "logger-http.interceptor")
public class LoggerHttpProperties {

    /**
     * Позволяет отключить работу стартера.
     */
    private boolean enabled;

    /**
     * Позволяет включить логирование входящих http запросов.
     */
    private boolean preHandleEnabled;

    /**
     * Позволяет включить логирование исходящих http запросов.
     */
    private boolean postHandleEnabled;

    /**
     * Позволяет включить логирование http запросов
     * во время выполнения которых было выброшено исключение.
     */
    private boolean afterCompletionEnabled;

    /**
     * Задает уровень логирования для всех поступивших http запросов
     */
    private String preHandleLevel = "info";

    /**
     * Задает уровень логирования для всех исходящих http запросов
     */
    private String postHandleLevel = "info";

    /**
     * Задает уровень логирования для  http запросов завершившихся ошибкой
     */
    private String afterCompletionLevel = "error";

    /**
     * Определяет нужно ли выводить заголовки запросов
     */
    private boolean loggingWithHeaders;
}
