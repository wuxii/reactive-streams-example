package com.example;

import lombok.extern.log4j.Log4j2;

import java.time.Duration;
import java.time.Instant;

/**
 * @author wuxii
 */
@Log4j2
public class Utils {

    public static void logTime(Instant start) {
        log.info("Total: " + Duration.between(start, Instant.now()).toMillis() + " millis");
    }

}
