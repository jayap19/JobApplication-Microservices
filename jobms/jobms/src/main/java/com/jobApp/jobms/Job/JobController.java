package com.jobApp.jobms.Job;

import com.jobApp.jobms.Job.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> findJobById(@PathVariable Long id){
        JobDTO jobDTO = jobService.getJobById(id);
        if(jobDTO != null){
            return new ResponseEntity<>(jobDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("job added successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJobById(@PathVariable Long id){
        boolean flag = jobService.deleteJob(id);
        if(flag)
        return new ResponseEntity("Job with the specified id has been deleted", HttpStatus.OK);
        else
            return new ResponseEntity<>("Job with the specified id is not found...",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job updateJob){
        boolean updated = jobService.updateJob(id, updateJob);
        if(updated){
            return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
