package ru.gorchanyuk.loggerhttpspringbootstarter.service.interceptor;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import ru.gorchanyuk.loggerhttpspringbootstarter.props.LoggerHttpProperties;
import ru.gorchanyuk.loggerhttpspringbootstarter.service.LoggerService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование работы класса AfterCompletionLoggingInterceptor")
public class AfterCompletionLoggingInterceptorTest {

    @Mock
    private LoggerService loggerService;

    @Mock
    private LoggerHttpProperties properties;

    @InjectMocks
    private AfterCompletionLoggingInterceptor interceptor;

    MockHttpServletRequest request;
    MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        request.setRequestURI("/testPath");
        request.setMethod(HttpMethod.GET.name());

        response = new MockHttpServletResponse();
    }

    @SneakyThrows
    @Test
    @DisplayName("Тестирование отправки сообщения при возникновении ошибки")
    void testAfterCompletionWithException() {

        response.setStatus(500);
        String logLevel = "error";
        Object handler = mock(Object.class);
        Exception exception = new RuntimeException("Test exception");
        when(properties.getAfterCompletionLevel()).thenReturn(logLevel);

        interceptor.afterCompletion(request, response, handler, exception);

        verify(loggerService, times(1)).sendLog(any(Logger.class), eq(logLevel), any(String.class));
    }

    @SneakyThrows
    @Test
    @DisplayName("Тестирование отсутствия отправки сообщения если не было ошибок")
    void testAfterCompletionNoException() {

        response.setStatus(200);
        Object handler = mock(Object.class);
        interceptor.afterCompletion(request, response, handler, null);

        verify(loggerService, never()).sendLog(any(Logger.class), any(String.class), any(String.class));
    }
}
