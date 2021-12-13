package com.javasampleapproach.jqueryboostraptable.repository;





import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.jqueryboostraptable.model.officeForm;

@Repository
@Transactional
public interface OfficeFormRepository extends JpaRepository<officeForm, Long> {

	//List<officeForm> findByUsers(Set<User> users);
	
	List<officeForm> findByStatusIsFalse();
	List<officeForm> findByStatusIsTrue();

	@Modifying
	@Query("update officeForm ear set ear.status = :status where ear.id = :id")
	int setStatusForOfficeForm(Boolean status,Long id);

	List<officeForm> findAllByStatusTrue();
	
}
