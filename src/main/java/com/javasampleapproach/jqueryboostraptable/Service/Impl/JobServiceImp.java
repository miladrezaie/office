package com.javasampleapproach.jqueryboostraptable.Service.Impl;

import com.javasampleapproach.jqueryboostraptable.Service.JobService;
import com.javasampleapproach.jqueryboostraptable.model.Job;
import com.javasampleapproach.jqueryboostraptable.model.Job;
import com.javasampleapproach.jqueryboostraptable.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImp implements JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobServiceImp(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    @Override
    public Page<Job> getAllJobs(Pageable page) {
        return jobRepository.findAll(page);
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job findById(long job) {
        return jobRepository.findById(job);
    }

    @Override
    public void saveJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    @Override
    public Job getJob(Long id) {
        return jobRepository.findById(id).get();
    }

    @Override
    public Optional<Job> findByIdJob(Long id) {
        return jobRepository.findById(id);
    }
}
