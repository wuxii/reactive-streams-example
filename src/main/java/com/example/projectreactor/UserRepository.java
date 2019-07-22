package com.example.projectreactor;

import com.example.User;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Log4j2
public class UserRepository {

    private static final AtomicLong idGenerate = new AtomicLong();
    private static final List<Object> STORE = new CopyOnWriteArrayList<>();

    public Mono<Void> deleteAll() {
        return Mono.fromRunnable(STORE::clear);
    }

    public Mono<User> save(User user) {
        return Mono.fromCallable(() -> {
            long id = idGenerate.getAndIncrement();
            String name = user.getName();
            log.info("save new user with id: {}, name: {}", id, name);
            User u = new User(id, name);
            STORE.add(u);
            return u;
        });
    }

    public static void println() {
        log.info("show all user: {}", STORE);
    }

}
