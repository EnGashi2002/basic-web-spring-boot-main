package com.edon.basic.web.repository;

import com.edon.basic.web.model.UserEntitySession;
import org.springframework.data.repository.CrudRepository;

public interface UserEntitySessionRepository extends CrudRepository<UserEntitySession, Long> {

    UserEntitySession findBySessionHashCode(String sessionHashCode);

}
