package com.mills.performances.repositories;

import com.mills.performances.models.AuthUser;
import org.springframework.stereotype.Repository;

/**
 * Created by ryan on 12/04/16.
 */
@Repository
public interface AuthUserCustomRepository {

    AuthUser getUserByEmail(String email);

}
