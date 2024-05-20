package ru.gorchanyuk.loggerhttpspringbootstarter.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

@DisplayName("Тестирование класса LoggerService")
public class LoggerServiceTest {

    @ParameterizedTest
    @DisplayName("Тестирование выбора уровня логирования")
    @ValueSource(strings = {"trace", "debug", "info", "warn", "error", "some text"})
    void testSendLog(String logLevel) {

        LoggerService service = new LoggerService();
        Logger logger = mock(Logger.class);
        String message = "Сообщение для логирования";

        service.sendLog(logger, logLevel, message);

        switch (logLevel) {
            case "trace" -> verify(logger, times(1)).trace(message);
            case "debug" -> verify(logger, times(1)).debug(message);
            case "warn" -> verify(logger, times(1)).warn(message);
            case "error" -> verify(logger, times(1)).error(message);
            default -> verify(logger, times(1)).info(message);
        }
    }
}
