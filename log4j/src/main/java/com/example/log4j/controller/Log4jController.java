package com.example.log4j.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "A")
@RestController
public class Log4jController {

    private static final Logger LOGGER = LoggerFactory.getLogger("B");

    @GetMapping("/{loggerName}/{level}")
    public Map<String, String> modifyLoggerLevel(@PathVariable String loggerName, @PathVariable String level) {
        org.apache.log4j.Logger logger = LogManager.getLogger(loggerName);
        if (logger != null) {
            logger.setLevel(Level.toLevel(level.toUpperCase()));
        }
        return getLoggers();
    }

    @GetMapping("/get")
    public Map<String, String> getLoggers() {
        Map<String, String> map = new HashMap<>();
        org.apache.log4j.Logger a = LogManager.getLogger("A");
        map.put(a.getName(), String.valueOf(a.getLevel()));
        org.apache.log4j.Logger b = LogManager.getLogger("B");
        map.put(b.getName(), String.valueOf(b.getLevel()));
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
}
