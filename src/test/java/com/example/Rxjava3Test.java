package com.example;

import com.example.rxjava3.UserRepository;
import io.reactivex.Flowable;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

/**
 * @author wuxii
 */
@Log4j2
public class Rxjava3Test {

    private UserRepository userRepository = new UserRepository();

    @Test
    public void testDeleteAll() {
        userRepository.deleteAll()
                .doOnComplete(() -> log.info("delete all"))
                .subscribe();
    }

    @Test
    public void testErrorHandle() {
        try {
            error0(true)
                    .doOnError(e -> log.error("print error0"))
                    .doOnNext(e -> log.info("Got {}", e))
                    .blockingLast();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            error1(true)
                    .doOnError(e -> log.error("print error1"))
                    .doOnNext(e -> log.info("Got {}", e))
                    .blockingLast();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Flowable<String> error0(boolean flag) {
        if (flag) {
            throw new RuntimeException("error0");
        }
        return Flowable.just("Hello");
    }

    public static Flowable<String> error1(boolean flag) {
        return Flowable.fromCallable(() -> {
            if (flag) {
                throw new RuntimeException("error1");
            }
            return "Hello";
        });
    }


}
