package com.javasampleapproach.jqueryboostraptable.repository;






import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.jqueryboostraptable.model.officeForm;

@Repository
@Transactional
public interface OfficeFormRepository extends JpaRepository<officeForm, Long> {

	//List<officeForm> findByUsers(Set<User> users);
	
//	List<officeForm> findByStatusIsFalse();

	@Query(value = "SELECT *  FROM `office_form` a where a.status =true order by a.id asc",
			countQuery = "SELECT count(*) FROM `office_form` a where a.status =true",
			nativeQuery = true)
	Page<officeForm> findByStatusIsTrue(Pageable pageable);


	@Query(value = "SELECT *  FROM `office_form` a where a.status =false order by a.id asc",
			countQuery = "SELECT count(*) FROM `office_form` a where a.status =false",
			nativeQuery = true)
	Page<officeForm> findByStatusIsFalse(Pageable pageable);

	@Modifying
	@Query("update officeForm ear set ear.status = :status where ear.id = :id")
	int setStatusForOfficeForm(Boolean status,Long id);

	List<officeForm> findAllByStatusTrue();
	
}
