package com.javasampleapproach.jqueryboostraptable.repository;



import java.util.List;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.jqueryboostraptable.model.Tajhizat;
import com.javasampleapproach.jqueryboostraptable.model.User;

@Repository
@Transactional
public interface TajhizatRepository extends JpaRepository<Tajhizat, Integer> {
	
	List<Tajhizat> findByTofficeforme(String tofficeforme);
	
	
}
