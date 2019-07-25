package com.example.tools;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuxii
 */
@Log4j2
public class Utils {

    private static final AtomicInteger times = new AtomicInteger(1);

    private static Random random = new Random();

    public static void logTime(Instant start) {
        log.info("Total: " + Duration.between(start, Instant.now()).toMillis() + " millis");
    }

    public static Mono<Increment> increment(int index) {
        return Mono.fromCallable(() -> new Increment(index, times.getAndIncrement()))
                .delayElement(Duration.ofSeconds(random.nextInt(5) + 1));
    }

    @ToString
    @AllArgsConstructor
    public static class Increment {
        public final int p0;
        public final int p1;
    }

}
