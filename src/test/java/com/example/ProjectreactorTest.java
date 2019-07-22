package com.example;

import com.example.projectreactor.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import reactor.core.publisher.Flux;

@Log4j2
public class ProjectreactorTest {

    private UserRepository userRepository = new UserRepository();

    @Test
    public void test() {
        Flux.just("a", "b", "c")
                .map(name -> new User(null, name))
                .flatMap(userRepository::save)
                .subscribe();// block
        UserRepository.println();
    }


}
