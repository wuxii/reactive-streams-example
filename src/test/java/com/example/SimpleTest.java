package com.example;

import com.example.tools.Utils;
import io.reactivex.Flowable;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;

/**
 * @author wuxii
 */
@Log4j2
public class SimpleTest {

    @Test
    public void testFlux() {
        Flux.range(1, 10)
                .subscribe(System.out::println);
    }

    @Test
    public void testFlowable() {
        Flowable.range(1, 10)
                .subscribe(System.out::println);
    }

    @Test
    public void testCollector() {
        Flux.just(3, 5, 1, 67, 23, 7, 32)
                .map(e -> e * 3)
                .collect(Collectors.toList())
                .doOnNext(e -> log.info("collector result: {}", e))
                .block();
    }

    @Test
    public void testSequence() {
        Flux.range(1, 1000)
                .flatMap(Utils::increment)
                .doOnNext(e -> log.info("Got result: {}", e))
                .blockLast();
    }

}
