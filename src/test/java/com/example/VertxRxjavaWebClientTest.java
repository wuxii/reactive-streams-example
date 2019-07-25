package com.example;

import com.example.tools.Server;
import com.example.tools.Utils;
import io.reactivex.Flowable;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.client.WebClient;
import lombok.extern.log4j.Log4j2;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Instant;

/**
 * @author wuxii
 */
@Log4j2
public class VertxRxjavaWebClientTest {

    static Vertx vertx;
    static WebClient webClient;

    @BeforeClass
    public static void setUp() {
        vertx = Vertx.vertx();
        webClient = WebClient.create(vertx);
    }

    @Test
    public void test() {
        webClient.get(Server.PORT, "localhost", "/greeting")
                .rxSend()
                .doOnSuccess(e -> log.info("Got {}", e.bodyAsString()))
                .blockingGet();
    }

    @Test
    public void testMultiplyRequest() {
        Instant start = Instant.now();
        Flowable.range(1, 5)
                .flatMap(i -> webClient
                        .get(Server.PORT, "localhost", "/greeting")
                        .rxSend()
                        .doOnSuccess(e -> log.info("Got {}", e.bodyAsString()))
                        .map(HttpResponse::bodyAsString)
                        .toFlowable())
                .toList()
                .doOnSuccess(e -> log.info("Got all range result: {}", e))
                .blockingGet();
        Utils.logTime(start);
    }

}
