package com.javasampleapproach.jqueryboostraptable.repository;

import com.javasampleapproach.jqueryboostraptable.model.OfficeFormUserTajhizat;
import com.javasampleapproach.jqueryboostraptable.model.OfficeFormUserTajhizatId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface OfficeFormUserTajhizatRepository extends JpaRepository<OfficeFormUserTajhizat,Long> {
    Optional<OfficeFormUserTajhizat> findById(Long id);

    @Transactional
    @Modifying
    @Query("delete FROM OfficeFormUserTajhizat ear where ear.id = :id")
    int deleteOfficeFormUserTajhizatById(OfficeFormUserTajhizatId id);
}
