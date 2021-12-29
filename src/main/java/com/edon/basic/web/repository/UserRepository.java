package com.edon.basic.web.repository;

import com.edon.basic.web.model.User;
import com.edon.basic.web.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    List<User> findByEmailContaining(String search);
}
