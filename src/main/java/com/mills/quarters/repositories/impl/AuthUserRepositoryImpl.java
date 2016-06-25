package com.mills.quarters.repositories.impl;

import com.mills.quarters.models.AuthUser;
import com.mills.quarters.repositories.AuthUserCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ryan on 25/06/16.
 */
@Repository
public class AuthUserRepositoryImpl implements AuthUserCustomRepository {

    @Autowired
    private MongoTemplate _mongoTemplate;

    public AuthUser getUserByEmail(String email)
        throws UsernameNotFoundException
    {
        List<AuthUser> matchingUsers = _mongoTemplate.find(new Query().addCriteria(new Criteria().and("username").is(email)), AuthUser.class);
        if (matchingUsers.size() > 1 || matchingUsers.size() == 0) {
            throw new UsernameNotFoundException("supplied email incorrect");
        }
        return matchingUsers.get(0);
    }

}
