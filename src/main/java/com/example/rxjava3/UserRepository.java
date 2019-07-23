package com.example.rxjava3;

import com.example.AbstractUserRepository;
import com.example.User;
import io.reactivex.Single;

public class UserRepository extends AbstractUserRepository {

    public Single<Void> deleteAll() {
        // return Maybe.fromRunnable(this::blockDeleteAll);
        return null;
    }

    public Single<User> save(User user) {
        return Single.fromCallable(() -> this.blockSave(user));
    }

}
