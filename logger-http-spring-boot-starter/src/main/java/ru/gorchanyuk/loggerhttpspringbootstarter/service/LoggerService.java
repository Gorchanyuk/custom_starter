package ru.gorchanyuk.loggerhttpspringbootstarter.service;

import org.slf4j.Logger;

public class LoggerService {
    /**
     * Логирует сообщения согласно заданному уровню логирования
     *
     * @param logger  логер который будет отправлять сообщение
     * @param level   уровень логирования
     * @param message сообщение лога
     */
    public void sendLog(Logger logger, String level, String message) {

        switch (level) {
            case "trace" -> logger.trace(message);
            case "debug" -> logger.debug(message);
            case "warn" -> logger.warn(message);
            case "error" -> logger.error(message);
            default -> logger.info(message);
        }
    }
}
