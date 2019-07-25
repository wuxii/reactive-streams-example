package com.example;

import com.example.tools.Server;
import com.example.tools.Utils;
import lombok.extern.log4j.Log4j2;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuxii
 */
@Log4j2
public class WebfluxWebClientTest {

    static WebClient client;

    @BeforeClass
    public static void setUp() {
        client = WebClient.builder()
                .baseUrl("http://localhost:" + Server.PORT)
                .build();
    }

    @Test
    public void test() {
        Instant start = Instant.now();
        client.get()
                .uri("/greeting")
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(e -> log.info("Got {}", e))
                .block();// blocked
        Utils.logTime(start);
    }

    @Test
    public void testMultiplyRequest() {
        Instant start = Instant.now();
        List<Mono<String>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(client.get()
                    .uri("/greeting")
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnNext(e -> log.info("Got {}", e)));
        }
        Mono.when(list).block();
        Utils.logTime(start);
    }

    @Test
    public void testCollector() {
        Flux.range(1, 5)
                .flatMap(i -> client.get().uri("/greeting").retrieve().bodyToMono(String.class))
                .doOnNext(e -> log.info("Got {}", e))
                .collect(Collectors.toList())
                .doOnNext(e -> log.info("Got all range result: {}", e))
                .block();
    }


}
