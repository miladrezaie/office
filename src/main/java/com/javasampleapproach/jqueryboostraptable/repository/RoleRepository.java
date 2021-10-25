package com.javasampleapproach.jqueryboostraptable.repository;


import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import com.javasampleapproach.jqueryboostraptable.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.jqueryboostraptable.model.Role;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByname(String name);
    Optional<Role> findById(Long id);
}
