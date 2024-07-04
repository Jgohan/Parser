package com.netcracker.parser.repositories;

import com.netcracker.parser.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String string);

    Boolean existsByUsername(String username);
}
