package com.javasampleapproach.jqueryboostraptable.repository;



import java.util.List;
import java.util.Optional;


import javax.transaction.Transactional;

import com.javasampleapproach.jqueryboostraptable.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.jqueryboostraptable.model.Tajhizat;
import com.javasampleapproach.jqueryboostraptable.model.User;

@Repository
@Transactional
public interface TajhizatRepository extends JpaRepository<Tajhizat, Long> {
	
	List<Tajhizat> findByTofficeforme(String tofficeforme);

	Optional<Tajhizat> findById(Long id);

	@Override
	void deleteById(Long id);

	
}
