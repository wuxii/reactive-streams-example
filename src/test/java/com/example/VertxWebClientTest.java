package com.example;

import com.example.tools.Server;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import lombok.extern.log4j.Log4j2;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author wuxii
 */
@Log4j2
public class VertxWebClientTest {

    static WebClient webClient;
    static Vertx vertx;

    @BeforeClass
    public static void setUp() {
        vertx = Vertx.vertx();
        webClient = WebClient.create(vertx);
    }

    @Test
    public void test() throws InterruptedException {
        webClient.get(Server.PORT, "localhost", "/greeting")
                .send(ar -> log.info("Got {}", ar.result().bodyAsString()));
        Thread.sleep(5000);
    }

}
