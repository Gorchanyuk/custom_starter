package ru.gorchanyuk.loggerhttpspringbootstarter.service.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.gorchanyuk.loggerhttpspringbootstarter.props.LoggerHttpProperties;
import ru.gorchanyuk.loggerhttpspringbootstarter.service.LoggerService;

import java.util.Enumeration;

/**
 * Реализация интерфейса HandlerInterceptor отвечающая за лргирование
 * всех входящих http запросов
 */
@Slf4j
@RequiredArgsConstructor
public class PreHandleLoggingInterceptor implements HandlerInterceptor {

    private final LoggerService loggerservice;
    private final LoggerHttpProperties properties;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {

        String headers = String.format("Заголовки запроса: \n%s", getHeaders(request));
        String message = String.format("Получен запрос %s: %s. %s",
                request.getMethod(),
                request.getRequestURI(),
                properties.isLoggingWithHeaders() ? headers : "");

        loggerservice.sendLog(log, properties.getPreHandleLevel(), message);

        return true; // Продолжить выполнение запроса
    }

    /**
     * Формирует сьроку с заголовками входящего http запроса
     *
     * @return заголовки запроса
     */
    String getHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headers = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headers.append(name).append(": ")
                    .append(request.getHeader(name)).append("\n");
        }
        return headers.toString();
    }
}
