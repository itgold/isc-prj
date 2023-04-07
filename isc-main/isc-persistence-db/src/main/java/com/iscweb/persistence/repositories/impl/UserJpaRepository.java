package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.SchoolDistrictJpa;
import com.iscweb.persistence.model.jpa.UserJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Repository for {@link UserJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface UserJpaRepository extends IPersistenceRepository<UserJpa>, UserJpaRepositoryCustom {

    UserJpa findByGuid(String guid);

    /**
     * Fetches the user that matches the email (case-insensitive).
     *
     * @param email an email address
     * @return the user matching the address
     */
    UserJpa findByEmailIgnoreCase(String email);

    Set<UserJpa> findByIdIn(Set<Long> userIds);

    @Query("SELECT u FROM UserJpa u WHERE u_deactivated = false AND u_last_login < ?1 ")
    List<UserJpa> findActiveUsers(Date date);

    Set<UserJpa> findBySchoolDistrict(SchoolDistrictJpa schoolDistrict);

}
