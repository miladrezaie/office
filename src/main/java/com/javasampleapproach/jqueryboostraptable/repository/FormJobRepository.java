package com.javasampleapproach.jqueryboostraptable.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.javasampleapproach.jqueryboostraptable.model.FormJob;

@Repository
@Transactional
public interface FormJobRepository extends JpaRepository<FormJob, Long> {

	List<FormJob> findByJob(String job);
	List<FormJob> findByUid(Integer uid);
	List<FormJob> findByFid(Long fid);
	List<FormJob> findByFidAndTaeid(Long fid,Integer taeid);
}
