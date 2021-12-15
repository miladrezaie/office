package com.javasampleapproach.jqueryboostraptable.repository;

import com.javasampleapproach.jqueryboostraptable.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfficeFormUserTajhizatRepository extends JpaRepository<OfficeFormUserTajhizat,Long> {
    Optional<OfficeFormUserTajhizat> findById(Long id);

    @Transactional
    @Modifying
    @Query("delete FROM OfficeFormUserTajhizat ear where ear.id = :id")
    int deleteOfficeFormUserTajhizatById(OfficeFormUserTajhizatId id);



    @Query(value = "SELECT tajhiz_id FROM office_form_user_tajhizats ff WHERE ff.user_id = :user_id",nativeQuery = true)
    Integer findById_UserIdAndId_OfficeFormsId(int user_id);


}
