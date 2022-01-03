package com.javasampleapproach.jqueryboostraptable.Service;


import com.javasampleapproach.jqueryboostraptable.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface JobService {
    Page<Job> getAllJobs(Pageable page);

    List<Job> findAll();

    Job findById(long job);

    void saveJob(Job job);

    void deleteJob(Long id);

    Job getJob(Long id);

    Optional<Job> findByIdJob(Long id);
}
