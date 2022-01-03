package com.javasampleapproach.jqueryboostraptable.Service;


import com.javasampleapproach.jqueryboostraptable.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LocationService  {
    Page<Location> getAllLocations(Pageable page);

    List<Location> findAll();

    void saveLocation(Location location);

    void deleteLocation(Long id);

    Location getLocation(Long id);

    Optional<Location> findByIdLocation(Long id);
}
