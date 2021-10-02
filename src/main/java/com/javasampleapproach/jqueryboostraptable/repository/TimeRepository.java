package com.javasampleapproach.jqueryboostraptable.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.javasampleapproach.jqueryboostraptable.model.time;


@Repository
@Transactional
public interface TimeRepository extends JpaRepository<time,Integer> {

	
	
	List<time> findByBarcode(String barcode);

	
}
