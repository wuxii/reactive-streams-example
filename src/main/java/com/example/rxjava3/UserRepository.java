package com.example.rxjava3;

import com.example.AbstractUserRepository;
import com.example.User;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class UserRepository extends AbstractUserRepository {

    public Flowable<Void> deleteAll() {
        return Maybe.<Void>fromRunnable(this::blockDeleteAll).toFlowable();
    }

    public Single<User> save(User user) {
        return Single.fromCallable(() -> this.blockSave(user));
    }

}
