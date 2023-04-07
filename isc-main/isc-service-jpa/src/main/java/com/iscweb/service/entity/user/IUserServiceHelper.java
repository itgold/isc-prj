package com.iscweb.service.entity.user;

import com.iscweb.common.exception.ResourceNotFoundException;
import com.iscweb.persistence.model.jpa.UserJpa;
import org.springframework.cache.annotation.Cacheable;

public interface IUserServiceHelper {

    @Cacheable(cacheNames = "users", key = "#p0")
    UserJpa findUser(String userEmail) throws ResourceNotFoundException;
}
