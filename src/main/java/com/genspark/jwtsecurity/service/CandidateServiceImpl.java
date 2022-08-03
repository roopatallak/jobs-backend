package com.genspark.jwtsecurity.service;

import com.genspark.jwtsecurity.entity.Candidate;
import com.genspark.jwtsecurity.entity.Job;
import com.genspark.jwtsecurity.repository.CandidateRepository;
import com.genspark.jwtsecurity.repository.JobRepository;
import com.genspark.jwtsecurity.util.JobStatusObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j

@Service
public class CandidateServiceImpl implements CandidateService{
    @Autowired
    private CandidateRepository repo;

    @Autowired
    private JobRepository jobRepo;

    @Override
    public List<Candidate> getAllCandidates() {

        //return repo.findAll();
        return repo.findAll(Sort.by(Sort.Direction.ASC, "lname"));
    }

    @Override
    public Candidate saveCandidate(Candidate candidate) {
        return repo.save(candidate);
    }

    @Override
    public Candidate getCandidateById(int id) {
        Optional<Candidate> optCandidate = repo.findById(id);
        Candidate candidate;
        if (optCandidate.isPresent()) {
            candidate = optCandidate.get();
        }
        else {
            throw new RuntimeException("Candidate not found for ID : " + id);
        }
        return candidate;
    }

    //remove the candidate from job candidate list
    //save the job
    @Override
    public void deleteCandidateById(int id) {

        Candidate candidate = getCandidateById(id);
        if (candidate == null) {
            return;
        }
        int jobId = candidate.getJobId();
        Job job = jobRepo.getById(jobId);
        job.removeCandidate(candidate);
        jobRepo.save(job);
    }

    @Override
    public List<Candidate> findBySkills(String skills) {
        return repo.findBySkills(skills);
    }

    public Candidate updateCandidateStatus(int candidateId, String status) {
        Candidate candidate = getCandidateById(candidateId);
        candidate.setStatus(status);
        return repo.save(candidate);
    }
    public List<JobStatusObject> getStatusResults() {
        String[][] myList = repo.getStatusResults();
        ArrayList<JobStatusObject> theList = new ArrayList<JobStatusObject>();
        JobStatusObject obj;

        for (String[] item: myList
        ) {
            obj = new JobStatusObject(item[0], item[1], item[2],item[3],item[4]);
            log.info("Found object : "+ item[0]);
            theList.add(obj);
        }
        return theList;
    }


}
