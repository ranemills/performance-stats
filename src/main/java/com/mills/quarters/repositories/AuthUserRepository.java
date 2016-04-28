package com.mills.quarters.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ryan on 12/04/16.
 */
@Repository
public interface AuthUserRepository extends MongoRepository<User, Long> {

    public List<User> findByUsername(String email);

}
