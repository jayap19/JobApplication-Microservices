package com.jobApp.jobms.Job.Mapper;

import com.jobApp.jobms.Job.Job;
import com.jobApp.jobms.Job.dto.JobDTO;
import com.jobApp.jobms.Job.external.Company;
import com.jobApp.jobms.Job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDTO(Job job, Company company, List<Review> reviewList){
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setCompany(company);
        jobDTO.setReview(reviewList);

        return jobDTO;
    }



}
