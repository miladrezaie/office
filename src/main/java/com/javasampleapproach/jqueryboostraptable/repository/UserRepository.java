package com.javasampleapproach.jqueryboostraptable.repository;



import java.util.List;


import javax.transaction.Transactional;

import com.javasampleapproach.jqueryboostraptable.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.jqueryboostraptable.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByJob(Job job);

	@Query(value = "SELECT * FROM user u where u.fullname like %:keyword% or u.job like %:keyword%",nativeQuery = true)
	public List<User> search(@Param("keyword") String keyword);

	User findByPersonalId(String personalId);

}
