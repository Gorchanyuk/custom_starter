package ru.gorchanyuk.loggerhttpspringbootstarter.service.interceptor;

import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.ModelAndView;
import ru.gorchanyuk.loggerhttpspringbootstarter.props.LoggerHttpProperties;
import ru.gorchanyuk.loggerhttpspringbootstarter.service.LoggerService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование работы класса PostHandleLoggingInterceptor")
public class PostHandleLoggingInterceptorTest {

    @Mock
    private LoggerService loggerService;

    @Mock
    private LoggerHttpProperties properties;

    @InjectMocks
    private PostHandleLoggingInterceptor interceptor;

    MockHttpServletRequest request;
    MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        request.setRequestURI("/testPath");
        request.setMethod(HttpMethod.GET.name());

        response = new MockHttpServletResponse();
        response.setStatus(200);
        response.setContentType("text/plain");
    }

    @Test
    @DisplayName("Тестирование работы метода postHandle")
    void testPostHandle() {
        String logLevel = "info";
        Object handler = mock(Object.class);
        ModelAndView modelAndView = mock(ModelAndView.class);
        request.setAttribute("startTime", System.currentTimeMillis());
        when(properties.getPostHandleLevel()).thenReturn(logLevel);

        interceptor.postHandle(request, response, handler, modelAndView);

        verify(loggerService).sendLog(any(Logger.class), eq(logLevel), any(String.class));
        verify(properties, times(1)).isLoggingWithHeaders();
    }

    @Test
    @DisplayName("Проверка добавления времени начала обработки запроса в request")
    void testPreHandle() {
        Object handler = mock(Object.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        boolean result = interceptor.preHandle(mockRequest, response, handler);

        assertTrue(result);
        verify(mockRequest).setAttribute(eq("startTime"), anyLong());
    }

    @Test
    @DisplayName("Тестирование получения заголовков ответа")
    void testGetHeaders() {

        String headers = interceptor.getHeaders(response);

        assertNotNull(headers);
        assertTrue(headers.contains("Content-Type: text/plain"));
    }
}
