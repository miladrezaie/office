package com.javasampleapproach.jqueryboostraptable.Service.Impl;

import com.javasampleapproach.jqueryboostraptable.Service.JobService;
import com.javasampleapproach.jqueryboostraptable.model.Job;
import com.javasampleapproach.jqueryboostraptable.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImp implements JobService {
    private final JobRepository jobRepository;

    @Autowired
    public JobServiceImp(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public void saveJob(Job job) {
        jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    public Job getJob(Long id) {
        return jobRepository.findById(id).get();
    }
}
