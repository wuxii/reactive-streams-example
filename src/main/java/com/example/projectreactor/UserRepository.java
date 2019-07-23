package com.example.projectreactor;

import com.example.AbstractUserRepository;
import com.example.User;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

public class UserRepository extends AbstractUserRepository {

    public Mono<Void> deleteAll() {
        return Mono.fromRunnable(this::blockDeleteAll);
    }

    public Mono<User> save(User user) {
        return Mono.fromCallable(() -> this.blockSave(user));
    }

    public Mono<Void> doError(String something) {
        return Mono.fromRunnable(() -> {
            throw new RuntimeException(something + " wrong.");
        });
    }

    public Mono<User> longCostQuery(Long id) {
        return Mono.delay(Duration.ofSeconds(5))
                .flatMap(delay -> {
                    Optional<User> userOpt = store.stream()
                            .filter(e -> e instanceof User)
                            .map(e -> (User) e)
                            .filter(e -> e.getId().equals(id))
                            .findFirst();
                    // return Mono.just(userOpt.orElse(new User(-1l, "not found")));
                    return userOpt.isPresent() ? Mono.just(userOpt.get()) : Mono.empty();
                });
    }

}
