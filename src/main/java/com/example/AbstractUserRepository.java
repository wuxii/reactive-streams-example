package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wuxii
 */
public abstract class AbstractUserRepository {

    protected final Logger log = LogManager.getLogger(getClass());
    protected final List<Object> store = new CopyOnWriteArrayList<>();
    protected final AtomicLong idGenerate = new AtomicLong(1);

    protected void blockDeleteAll() {
        store.clear();
    }

    protected User blockSave(User user) {
        long id = idGenerate.getAndIncrement();
        String name = user.getName();
        log.info("save new user with id: {}, name: {}", id, name);
        User u = new User(id, name);
        store.add(u);
        return u;
    }

    public void printAll() {
        log.info("show all objects: {}", store);
    }

}
