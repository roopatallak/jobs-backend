package com.genspark.jwtsecurity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genspark.jwtsecurity.entity.AuthUser;
import com.genspark.jwtsecurity.entity.JWTRequest;
import com.genspark.jwtsecurity.entity.JWTResponse;
import com.genspark.jwtsecurity.service.AuthUserDetailService;
import com.genspark.jwtsecurity.util.JWTUtility;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    AuthUserDetailService service;

    @Autowired
    private JWTUtility jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String welcome() {
        return "Welcome here";
    }

//    @GetMapping("/user")
//    public String welcomeUser() {
//        log.info("In welcome User of controller");
//        return "User Page";
//    }
//
//    @GetMapping("/user/addResume")
//    public String addResume() {
//        return "Add resume here";
//    }
//
//    @GetMapping("/admin")
//    public String welcomeAdmin() {
//        log.info("In admin of controller");
//        return "Admin Page";
//    }
//
//    @GetMapping("/admin/candidates")
//    public String showCandidates() {
//        return "List of candidates";
//    }
//
//    @GetMapping("/register")
//    public String registerUser() {
//        return "registerForm";
//    }

    @PostMapping("/saveUser")
    public String saveUser(@RequestParam("user") String jsonUser) throws Exception {
        log.info("In Save User of Controller");
        ObjectMapper mapper = new ObjectMapper();
        AuthUser user = mapper.readValue(jsonUser, AuthUser.class);
        log.info("User resolved: "+ user.getUserName() + user.getPassword());

        service.addUser(user);
        log.info("After service add user");
        return("Saved");
    }

    @PostMapping("/authenticate")
    public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest) throws Exception {

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials", e);
        }

        final UserDetails userDetails
                = service.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtil.generateToken(userDetails);

        return new JWTResponse(token);
    }

    @RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
        // From the HttpRequest get the claims
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        String token = jwtUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        return ResponseEntity.ok(new JWTResponse(token));
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }

//    @PostMapping("/authenticate")
//    public JWTResponse autheticate(@RequestBody  JWTRequest jwtRequest) throws Exception {
//        log.info("In authenticate of controller");
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            jwtRequest.getUsername(),
//                            jwtRequest.getPassword()
//                    )
//            );
//        }
//        catch (BadCredentialsException badCredentialsException) {
//            throw new Exception("Invalid Credentials", badCredentialsException);
//        }
//
//        UserDetails userDetails = service.loadUserByUsername(jwtRequest.getUsername());
//        final String token = jwtUtil.generateToken(userDetails);
//
//
//        return new JWTResponse(token);
//    }

}
