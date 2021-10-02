package com.javasampleapproach.jqueryboostraptable.repository;

import com.javasampleapproach.jqueryboostraptable.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job,Long> {
}
