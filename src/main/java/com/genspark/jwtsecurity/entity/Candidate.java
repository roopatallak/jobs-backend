package com.genspark.jwtsecurity.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data

@Entity
public class Candidate {
    @Id
    @GeneratedValue
    private int candidateId;
    private String fname;
    private String lname;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role; //for security implementation
    private String qualification;
    private String skills;
    private String resumeLink;
    private String experience;


    //Candidate status
    private String status;

    //jobId
    private int jobId;

    //@OneToMany
    //List<Application> applications;

}
