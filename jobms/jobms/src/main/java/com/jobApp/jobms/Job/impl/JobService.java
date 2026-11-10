package com.jobApp.jobms.Job.impl;


import com.jobApp.jobms.Job.Clients.CompanyClient;
import com.jobApp.jobms.Job.Clients.ReviewClient;
import com.jobApp.jobms.Job.Job;
import com.jobApp.jobms.Job.JobRepository;
import com.jobApp.jobms.Job.Mapper.JobMapper;
import com.jobApp.jobms.Job.dto.JobDTO;
import com.jobApp.jobms.Job.external.Company;
import com.jobApp.jobms.Job.external.Review;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService implements com.jobApp.jobms.Job.JobService {

    private JobRepository jobRepository;

    int attempt = 0;


    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private CompanyClient companyClient;
    @Autowired
    private ReviewClient reviewClient;

    public JobService(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

      @Override
     //@CircuitBreaker(name = "companyBreaker",fallbackMethod = "companyBreakerFallBack")

    //   @Retry(name = "companyRetry", fallbackMethod = "companyBreakerFallBack")
      @RateLimiter(name = "companyLimiter", fallbackMethod = "companyBreakerFallBack")
    public List<JobDTO> findAll() {
            System.out.println("Attempt" + ++attempt);
            List<Job> jobs = jobRepository.findAll();
            return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<String> companyBreakerFallBack(Exception e){
        List<String> list= new ArrayList<>();
        list.add("Dummy");
        return list;
    }

    public JobDTO convertToDTO(Job job){

            Company company = companyClient.getCompany(job.getCompanyId());
            List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
            JobDTO jobDTO = JobMapper.mapToJobWithCompanyDTO(job,company, reviews);
            return jobDTO;

    }

    public JobDTO getJobById(Long id){
        Job job =  jobRepository.findById(id).orElse(null);
        return convertToDTO(job);
    }


    @Override
    public void createJob(Job job) {
       jobRepository.save(job);
    }
    @Override
    public boolean deleteJob(Long id){
        try {
            jobRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }

    }

    @Override
    public boolean updateJob(Long id, Job updateJob) {
        Optional<Job> byId = jobRepository.findById(id);
        if(byId.isPresent()){
            Job job = byId.get();
            job.setTitle(updateJob.getTitle());
            job.setDescription(updateJob.getDescription());
            job.setMinSalary(updateJob.getMinSalary());
            job.setMaxSalary(updateJob.getMaxSalary());
            job.setLocation(updateJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;

    }

}
