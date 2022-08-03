package com.genspark.jwtsecurity.service;

import com.genspark.jwtsecurity.entity.AuthUser;
import com.genspark.jwtsecurity.repository.AuthUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
public class AuthUserDetailService implements UserDetailsService {
    @Autowired
    AuthUserRepository repo;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("In loadUserByUsername : {}", userName);
        Optional<AuthUser> user = repo.findByUserName(userName);
        if (user == null) log.info("User is null");
        user.orElseThrow(()-> new UsernameNotFoundException("User not found : "+ userName));
        AuthUser user1 = user.get();
        log.info("Found User by user name : " + user);
        return new AuthUserDetails(user1);

    }

    public AuthUser addUser(AuthUser user) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = user.getPassword();
        log.info("Orig password: " + pass);
        String encodedPass = encoder.encode(pass);
        log.info("Encoded password : " + encodedPass);
        user.setPassword(encodedPass);
        user.setRoles("ROLE_USER");
        return repo.save(user);
    }

}

