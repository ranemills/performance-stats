package com.mills.quarters.repositories;

import com.mills.quarters.models.AuthUser;
import org.springframework.stereotype.Repository;

/**
 * Created by ryan on 12/04/16.
 */
@Repository
public interface AuthUserCustomRepository {

    AuthUser getUserByEmail(String email);

}
