package com.javasampleapproach.jqueryboostraptable.repository;

import com.javasampleapproach.jqueryboostraptable.model.OfficeFormUserTajhizat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfficeFormUserTajhizatRepository extends JpaRepository<OfficeFormUserTajhizat,Long> {
    Optional<OfficeFormUserTajhizat> findById(Long id);
}
