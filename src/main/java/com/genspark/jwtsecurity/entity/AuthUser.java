package com.genspark.jwtsecurity.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="AuthUsers")
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userid;

    @Column(name="userName")
    private String userName;
    @Column(name="password")
    private String password;
    @Column(name="active")
    private boolean active;
    @Column(name="roles")
    private String roles;


}
