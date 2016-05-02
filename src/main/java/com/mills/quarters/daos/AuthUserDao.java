package com.mills.quarters.daos;

import com.mills.quarters.models.AuthUser;
import com.mills.quarters.repositories.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ryan on 28/04/16.
 */
@Service
public class AuthUserDao {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    AuthUserRepository authUserRepository;

    public AuthUser getUserByEmail(String email)
        throws UsernameNotFoundException
    {
        System.out.println("GOing to Mongo");
        System.out.println(email);
        List<AuthUser> matchingUsers = mongoTemplate.find(new Query().addCriteria(new Criteria().and("username").is(email)), AuthUser.class);
//        List<User> matchingUsers = authUserRepository.findByUsername(email);
        System.out.println(matchingUsers);
        System.out.println("Been to Mongo");
        if (matchingUsers.size() > 1 || matchingUsers.size() == 0) {
            System.out.println("Too many");
            throw new UsernameNotFoundException("supplied email incorrect");
        }
        System.out.println("Returning nicely");
        return matchingUsers.get(0);
    }

}
