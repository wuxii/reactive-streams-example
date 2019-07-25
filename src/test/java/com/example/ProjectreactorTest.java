package com.example;

import com.example.projectreactor.UserRepository;
import com.example.tools.Utils;
import lombok.extern.log4j.Log4j2;
import org.junit.BeforeClass;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Log4j2
public class ProjectreactorTest {

    private static UserRepository userRepository = new UserRepository();

    @BeforeClass
    public static void setUp() {
        Flux.just("foo", "bar", "baz")
                .map(name -> new User(null, name))
                .flatMap(userRepository::save)
                .subscribe();// block
    }

    @Test
    public void test() {
        Flux.just("a", "b", "c")
                .map(name -> new User(null, name))
                .flatMap(userRepository::save)
                .doOnComplete(userRepository::printAll)
                .subscribe();// block
    }

    @Test
    public void testErrorHandle() {
        Mono.just("greeting")
                .flatMap(userRepository::doError)
                .doOnError(e -> log.error("something wrong happen", e))
                .subscribe();
    }

    @Test
    public void testLongCostQuery() {
        Instant start = Instant.now();
        Flux.range(1, 2000)
                .flatMap(e -> userRepository.longCostQuery((long) e))
                .doOnNext(e -> log.info("got user {}", e))
                .blockLast();// block
        Utils.logTime(start);
    }

}
