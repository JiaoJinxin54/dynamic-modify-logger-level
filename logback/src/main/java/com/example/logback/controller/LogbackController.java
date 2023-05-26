package com.example.logback.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "A")
@RestController
public class LogbackController {

    private static final Logger LOGGER = LoggerFactory.getLogger("B");

    @GetMapping("/{loggerName}/{level}")
    public Map<String, String> modifyLoggerLevel(@PathVariable String loggerName, @PathVariable String level) {
        ch.qos.logback.classic.Logger logger = getLogger(loggerName);
        if (logger != null) {
            logger.setLevel(Level.toLevel(level.toUpperCase()));
        }
        return getLoggers();
    }

    @GetMapping("/get")
    public Map<String, String> getLoggers() {
        Map<String, String> map = new HashMap<>();
        ch.qos.logback.classic.Logger a = getLogger("A");
        if (a != null) {
            map.put(a.getName(), String.valueOf(a.getLevel()));
        }
        ch.qos.logback.classic.Logger b = getLogger("B");
        if (b != null) {
            map.put(b.getName(), String.valueOf(b.getLevel()));
        }
        return map;
    }

    @GetMapping("/print")
    public void printLog() {
        new Thread(() -> printLog(log)).start();
        new Thread(() -> printLog(LOGGER)).start();
    }

    private void printLog(Logger logger) {
        logger.error("error");
        logger.warn("warn");
        logger.info("info");
        logger.debug("debug");
        logger.trace("trace");
    }

    private ch.qos.logback.classic.Logger getLogger(String loggerName) {
        if (LoggerFactory.getILoggerFactory() instanceof LoggerContext loggerContext) {
            return loggerContext.getLogger(loggerName);
        }
        return null;
    }
}
