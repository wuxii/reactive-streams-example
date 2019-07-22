package com.example.rxjava3;

import com.example.User;
import io.reactivex.Single;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Log4j2
public class UserRepository {

    private final List<User> STORE = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerate = new AtomicLong();

    public Single<Void> deleteAll() {
        return null;
    }

    public Single<User> save(User user) {
        return Single.fromCallable(() -> {
            long id = idGenerate.getAndIncrement();
            String name = user.getName();
            log.info("save new user with id: {}, name: {}", id, name);
            User u = new User(id, name);
            STORE.add(u);
            return u;
        });
    }

}
