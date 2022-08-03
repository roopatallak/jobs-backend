package com.genspark.jwtsecurity.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int jobId;
    private String title;
    private String description;
    private String skills;
    private String experience;


    @OneToMany(targetEntity = Candidate.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidate> candidateList = new ArrayList<>();

    public void addCandidate(Candidate c) {
        candidateList.add(c);
    }

    public void removeCandidate (Candidate c) {
        candidateList.remove(c);
    }

}
