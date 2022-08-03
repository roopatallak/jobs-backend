package com.genspark.jwtsecurity.repository;

import com.genspark.jwtsecurity.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
    //Optional<AuthUser> findByUserName(String userName);
    //@Query("select p from authusers p where p.userName=?1")
    Optional<AuthUser> findByUserName(String username1);


}
