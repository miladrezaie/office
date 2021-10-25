package com.javasampleapproach.jqueryboostraptable.repository;

import com.javasampleapproach.jqueryboostraptable.model.Brand;
import com.javasampleapproach.jqueryboostraptable.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findById(Long id);
}
