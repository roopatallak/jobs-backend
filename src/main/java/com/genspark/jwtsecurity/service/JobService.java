package com.genspark.jwtsecurity.service;

import com.genspark.jwtsecurity.entity.Job;
import org.springframework.stereotype.Service;

import java.util.List;

public interface JobService {
    List<Job> getAllJobs();
    Job saveJob(Job job);
    Job getJobById(int id);
    void deleteJobById(int id);
    //List<Product> getProductStartingWith(String str);

}
