package com.genspark.jwtsecurity.service;

import com.genspark.jwtsecurity.entity.Job;
import com.genspark.jwtsecurity.entity.Job;
import com.genspark.jwtsecurity.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository repo;

    @Override
    public List<Job> getAllJobs() {
        return repo.findAll();
    }

    @Override
    public Job saveJob(Job job) {
        return repo.save(job);
    }

    @Override
    public Job getJobById(int id) {
        Optional<Job> optJob = repo.findById(id);
        Job job;
        if (optJob.isPresent()) {
            job = optJob.get();
        }
        else {
            throw new RuntimeException("Job not found for ID : " + id);
        }
        return job;
    }

    @Override
    public void deleteJobById(int id) {
        repo.deleteById(id);
    }
}
