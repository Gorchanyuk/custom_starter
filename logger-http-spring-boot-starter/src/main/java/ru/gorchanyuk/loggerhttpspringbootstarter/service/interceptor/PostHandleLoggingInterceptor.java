package ru.gorchanyuk.loggerhttpspringbootstarter.service.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.gorchanyuk.loggerhttpspringbootstarter.props.LoggerHttpProperties;
import ru.gorchanyuk.loggerhttpspringbootstarter.service.LoggerService;

/**
 * Реализация интерфейса HandlerInterceptor отвечающая за лргирование
 * всех исходящих http запросов
 */
@Slf4j
@RequiredArgsConstructor
public class PostHandleLoggingInterceptor implements HandlerInterceptor {

    private final LoggerService loggerservice;
    private final LoggerHttpProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true; // Продолжить выполнение запроса
    }

    @Override
    public void postHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) {

        String headers = String.format("Заголовки ответа: \n%s", getHeaders(response));
        String message = String.format("Ответ на запрос %s: %s сформирован за %s мс. Статус ответа: %s, тип ответа: %s. %s",
                request.getMethod(),
                request.getRequestURI(),
                getExecutionTime(request),
                response.getStatus(),
                response.getContentType(),
                properties.isLoggingWithHeaders() ? headers : "");

        loggerservice.sendLog(log, properties.getPostHandleLevel(), message);
    }

    /**
     * Формирует строку содержущую информацию о заголовках исхлдящего http запроса
     *
     * @return строку с заголовками
     */
    String getHeaders(HttpServletResponse response) {

        StringBuilder headers = new StringBuilder();
        response.getHeaderNames()
                .forEach(header -> headers.append(header)
                        .append(": ")
                        .append(response.getHeader(header))
                        .append("\n"));
        return headers.toString();
    }

    /**
     * Высчитывет время обработки запроса
     *
     * @return времф обработки запроса
     */
    private long getExecutionTime(HttpServletRequest request) {

        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
