package com.reactive.webFluxNonBlock.repository;

import com.reactive.webFluxNonBlock.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<UserDetails> findByUsername(String username);
}