package com.javasampleapproach.jqueryboostraptable.Service;


import com.javasampleapproach.jqueryboostraptable.model.Location;

import java.util.Optional;

public interface LocationService  {
    public Optional<Location> findByIdLocation(Long id);
}
