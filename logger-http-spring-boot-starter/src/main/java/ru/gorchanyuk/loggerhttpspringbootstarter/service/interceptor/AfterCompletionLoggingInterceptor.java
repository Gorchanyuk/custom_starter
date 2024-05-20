package ru.gorchanyuk.loggerhttpspringbootstarter.service.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.gorchanyuk.loggerhttpspringbootstarter.props.LoggerHttpProperties;
import ru.gorchanyuk.loggerhttpspringbootstarter.service.LoggerService;

/**
 * Реализация интерфейса HandlerInterceptor отвечающая за лргирование
 * http запросов во время выполнения которых было выброшено исключение
 */
@Slf4j
@RequiredArgsConstructor
public class AfterCompletionLoggingInterceptor implements HandlerInterceptor {

    private final LoggerService loggerservice;
    private final LoggerHttpProperties properties;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        if (ObjectUtils.isEmpty(ex)) {
            return;
        }
        String message = String.format("При запросе %s: %s произошел выброс исключения %s",
                request.getMethod(),
                request.getRequestURI(),
                ex);

        loggerservice.sendLog(log, properties.getAfterCompletionLevel(), message);
    }
}
