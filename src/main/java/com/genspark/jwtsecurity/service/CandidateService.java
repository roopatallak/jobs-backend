package com.genspark.jwtsecurity.service;

import com.genspark.jwtsecurity.entity.Candidate;
import com.genspark.jwtsecurity.entity.Candidate;
import com.genspark.jwtsecurity.util.JobStatusObject;

import java.util.List;

public interface CandidateService {
    List<Candidate> getAllCandidates();
    Candidate saveCandidate(Candidate candidate);
    Candidate getCandidateById(int id);
    void deleteCandidateById(int id);
    List<Candidate> findBySkills(String skills);
    Candidate updateCandidateStatus(int candidateId, String status);
    //    List<JobStatusObject> getJobStatusCounts();
    List<JobStatusObject> getStatusResults();

}
