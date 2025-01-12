package com.duniasteak.service.user.repo;

import com.duniasteak.service.user.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    User findByUsername(String username);
}
