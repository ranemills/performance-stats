package com.mills.performances.repositories.impl;

import com.mills.performances.models.AuthUser;
import com.mills.performances.repositories.AuthUserCustomRepository;
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

    @Override
    public AuthUser getUserByEmail(String email)
        throws UsernameNotFoundException
    {
        List<AuthUser> matchingUsers = _mongoTemplate.find(new Query().addCriteria(new Criteria().and("username").is(email)), AuthUser.class);
        if (matchingUsers.size() > 1 || matchingUsers.size() == 0) {
            throw new UsernameNotFoundException("supplied email incorrect");
        }
        return matchingUsers.get(0);
    }

    /**
     * Override the standard implementation of findAll, given that we have no interest in restricting to just this customer.
     * @return
     */
    public List<AuthUser> findAll() {
        return _mongoTemplate.findAll(AuthUser.class);
    }

}
