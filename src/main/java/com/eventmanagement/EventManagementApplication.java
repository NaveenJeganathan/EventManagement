package com.eventmanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class EventManagementApplication {

    private static final Logger logger = LoggerFactory.getLogger(EventManagementApplication.class);

    public static void main(String[] args) {
        logger.info("Starting EventManagementApplication...");
        SpringApplication.run(EventManagementApplication.class, args);
        logger.info("EventManagementApplication started successfully.");
    }
}
