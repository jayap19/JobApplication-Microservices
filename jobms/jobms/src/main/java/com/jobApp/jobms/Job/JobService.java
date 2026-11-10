package com.jobApp.jobms.Job;

import com.jobApp.jobms.Job.dto.JobDTO;

import java.util.List;

public interface JobService {
    public List<JobDTO> findAll();
    public void createJob(Job job);

    JobDTO getJobById(Long id);

    boolean deleteJob(Long id);

    boolean updateJob(Long id, Job updateJob);
}
