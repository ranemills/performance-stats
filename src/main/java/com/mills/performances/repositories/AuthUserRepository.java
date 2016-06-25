package com.mills.performances.repositories;

import com.mills.performances.models.AuthUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ryan on 12/04/16.
 */
@Repository
public interface AuthUserRepository extends MongoRepository<AuthUser, Long>, AuthUserCustomRepository {

    List<AuthUser> findByUsername(String email);

}
