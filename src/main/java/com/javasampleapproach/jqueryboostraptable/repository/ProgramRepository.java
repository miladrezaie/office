package com.javasampleapproach.jqueryboostraptable.repository;

import com.javasampleapproach.jqueryboostraptable.model.Location;
import com.javasampleapproach.jqueryboostraptable.model.Program;
import com.javasampleapproach.jqueryboostraptable.model.officeForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program,Long> {


    @Query(value = "SELECT *  FROM `programs` a where a.status =true order by a.id desc",
            countQuery = "SELECT count(*) FROM `programs` a where a.status =true",
            nativeQuery = true)
    Page<Program> findByStatusIsTrue(Pageable pageable);


    @Query(value = "SELECT *  FROM `programs` a where a.status =false order by a.id desc",
            countQuery = "SELECT count(*) FROM `programs` a where a.status =false",
            nativeQuery = true)
    Page<Program> findByStatusIsFalse(Pageable pageable);

    @Modifying
    @Query("update Program ear set ear.status = :status where ear.id = :id")
    int setStatusForProgram(Boolean status, Long id);


    Optional<Program> findById(Long id);

    @Override
    @Query(value = "SELECT *  FROM `programs` a where a.status =true order by a.id desc",
            nativeQuery = true)
    List<Program> findAll();

//    @Query(value = "SELECT *  FROM `programs` a where a.status =true order by a.id desc",
//            nativeQuery = true)
//    List<Program> findAll();
}
