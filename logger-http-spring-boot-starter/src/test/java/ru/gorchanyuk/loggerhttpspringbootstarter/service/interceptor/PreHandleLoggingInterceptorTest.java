package ru.gorchanyuk.loggerhttpspringbootstarter.service.interceptor;

import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование работы класса PreHandleLoggingInterceptor")
class PreHandleLoggingInterceptorTest {

    @Mock
    private LoggerService loggerService;

    @Mock
    private LoggerHttpProperties properties;

    @InjectMocks
    private PreHandleLoggingInterceptor interceptor;

    MockHttpServletRequest request;
    MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        request.setRequestURI("/testPath");
        request.setMethod(HttpMethod.GET.name());
        request.addHeader("Content-Type", "application/json");

        response = new MockHttpServletResponse();
    }

    @Test
    @DisplayName("Тестирование что метод preHandle возвращает true")
    void testPreHandle() {

        Object handler = mock(Object.class);
        String logLevel = "info";
        when(properties.getPreHandleLevel()).thenReturn(logLevel);
        when(properties.isLoggingWithHeaders()).thenReturn(true);

        boolean result = interceptor.preHandle(request, response, handler);

        assertTrue(result);
        verify(properties, times(1)).isLoggingWithHeaders();
        verify(loggerService).sendLog(any(Logger.class), eq(logLevel), any(String.class));
    }

    @Test
    @DisplayName("Тестирование  получения заголовков запрса")
    void testGetHeaders() {

        String headers = interceptor.getHeaders(request);

        assertNotNull(headers);
        Assertions.assertTrue(headers.contains("Content-Type: application/json"));
    }
}
