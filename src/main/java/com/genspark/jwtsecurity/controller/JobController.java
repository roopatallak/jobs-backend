package com.genspark.jwtsecurity.controller;

import com.genspark.jwtsecurity.entity.Job;
import com.genspark.jwtsecurity.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j

@RestController
@CrossOrigin
public class JobController {

    @Autowired
    private JobService service;

    @RequestMapping("/")
    public String welcome() {
        log.info("In welcome controller");
        return "This is a message from welcome controller";
    }

    @GetMapping("/jobs")
    public List<Job> showJobsList() {
        return service.getAllJobs();
    }

    @PostMapping("/admin/addJob")
    public Job saveJob(@RequestBody Job job) {
        return service.saveJob(job);
    }

}
