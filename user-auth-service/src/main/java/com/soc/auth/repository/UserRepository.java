package com.soc.auth.repository;

import com.soc.auth.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    // Custom method to find a user by their username
    Optional<User> findByUsername(String username);
}