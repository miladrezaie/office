package com.javasampleapproach.jqueryboostraptable.repository;

import com.javasampleapproach.jqueryboostraptable.model.Brand;
import com.javasampleapproach.jqueryboostraptable.model.Category;
import com.javasampleapproach.jqueryboostraptable.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    Optional<Brand> findById(Long id);
//        public Category findById(long job);
}
