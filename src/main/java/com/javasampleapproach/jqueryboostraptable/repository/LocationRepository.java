package com.javasampleapproach.jqueryboostraptable.repository;

import com.javasampleapproach.jqueryboostraptable.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
