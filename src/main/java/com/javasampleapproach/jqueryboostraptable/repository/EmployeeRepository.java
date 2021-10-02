package com.javasampleapproach.jqueryboostraptable.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.jqueryboostraptable.model.employee;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<employee, Integer> {

	List<employee> findByMac(String mac);
	List<employee> findByImei(String imei);
	List<employee> findByBarcode(String barcode);

}
