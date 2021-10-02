package com.javasampleapproach.jqueryboostraptable.repository;





import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.model.officeForm;

@Repository
@Transactional
public interface OfficeFormRepository extends JpaRepository<officeForm, Long> {

	//List<officeForm> findByUsers(Set<User> users);
	
	List<officeForm> findByUsers(List<User> user);
	
}
