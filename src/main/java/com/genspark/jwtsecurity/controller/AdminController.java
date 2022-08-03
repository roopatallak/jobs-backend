package com.genspark.jwtsecurity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genspark.jwtsecurity.entity.Candidate;
import com.genspark.jwtsecurity.entity.Job;
import com.genspark.jwtsecurity.service.CandidateService;
import com.genspark.jwtsecurity.service.EmailSenderService;
import com.genspark.jwtsecurity.service.JobService;
import com.genspark.jwtsecurity.util.JobStatusObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

@Slf4j

@RestController
@CrossOrigin
public class AdminController {

    @Autowired
    private CandidateService service;

    @Autowired
    private JobService jobService;

    @Autowired
    EmailSenderService emailService;

/*
    @GetMapping("/sendEmail/{id}")
    public String sendEmail(@PathVariable(value = "id") int candidateId) {
        Candidate candidate = service.getCandidateById(candidateId);
        String emailId = candidate.getEmail();
        //send email to actual candidate email ID in future
        emailService.sendEmail();
        return "Success";
    }
*/

    //hardcoded to send email to the same ID,
    // so no logic to lookup candidate
    // implement in future => actual email from candidate
    @GetMapping("/admin/sendEmail")
    public String sendEmail() {
        log.info("Sending email");
        emailService.sendEmail();
        return "Success";
    }



    @GetMapping("/admin/candidates")
    public List<Candidate> showCandidatesList(@RequestParam("skills") String skills) {
        log.info("Skills = " + skills);
        if (skills.equals("")) {
            return service.getAllCandidates();
        }
        return service.findBySkills(skills);
    }

    @GetMapping("/admin/candidate/{id}")
    public Candidate getCandidateById(@PathVariable(value = "id") int candidateId) {
        return service.getCandidateById(candidateId);
    }

    @GetMapping("/admin/reject/{id}")
    public String rejectCandidate(@PathVariable(value="id") int candidateId) {
        service.deleteCandidateById(candidateId);
        return "Successfully deleted";
    }

    //Test File Upload
    @Value("${file.upload.dir}")
    String FILE_DIR;

    @PostMapping("/addCandidate")
    public Candidate saveCandidate(
            @RequestParam("jobId") int jobid,
            @RequestParam("resume") MultipartFile file,
            @RequestParam("candidatedetails") String candidatedetails) throws Exception{

        log.info("In save candidate method..");
        String filename = FILE_DIR + file.getOriginalFilename();
        File myFile = new File(filename);

        log.info("File name is : " + myFile.getName());
        myFile.createNewFile();

        FileOutputStream fos = null;
        fos = new FileOutputStream(myFile);
        fos.write(file.getBytes());
        fos.close();

        log.info("In saveCandidate candidate details : {}", candidatedetails);
        ObjectMapper mapper = new ObjectMapper();
        Candidate candidate = mapper.readValue(candidatedetails, Candidate.class);
        log.info("Mapped Candidate");
        candidate.setResumeLink(filename);

        //set candidate's default values
        candidate.setJobId(jobid);

        candidate.setRole("ROLE_USER");
        candidate.setStatus("SCREEN");
        candidate.setUsername("user");
        candidate.setPassword("pass");
        //candidate.setEmail("springinbox99@gmail.com");

        //Get the job from JobID and save the candidate to the job

        Job job = jobService.getJobById(jobid);
        job.addCandidate(candidate);
        jobService.saveJob(job);

        return candidate;

    }

    @PostMapping("/admin/updateCandidate")
    public Candidate updateCandidate(
            @RequestParam("candidateId") int candidateId,
            @RequestParam("status") String status) {
        return service.updateCandidateStatus(candidateId, status);
    }

    @GetMapping("/admin/jobStatus")
    public List<JobStatusObject> getJobStatus() {
        return service.getStatusResults();
    }


    @SneakyThrows
    @GetMapping(value = "/readFile/{id}")
    public ResponseEntity<InputStreamResource> readFile(@PathVariable(value = "id") String id) {
        int candidateId = Integer.parseInt(id);
        Candidate candidate = this.getCandidateById(candidateId);
        String filename = candidate.getResumeLink();

//        String filePath = "/path/to/file/";
//        String fileName = "fileName.pdf";
//        File file = new File(filePath+fileName);
        //String filename2 = "C:\\GenSpark\\uploads\\RobertLittmanTesting.pdf";
        log.info("Got file name : " + filename);
        File file = new File(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" +filename);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        var response = ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length());

        if (filename.endsWith("pdf")) {
            response.contentType(MediaType.parseMediaType("application/pdf"));
        }

        return response.body(resource);
    }
}
