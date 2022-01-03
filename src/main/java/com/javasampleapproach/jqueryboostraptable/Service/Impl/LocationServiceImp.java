package com.javasampleapproach.jqueryboostraptable.Service.Impl;

import com.javasampleapproach.jqueryboostraptable.Service.LocationService;
import com.javasampleapproach.jqueryboostraptable.model.Location;
import com.javasampleapproach.jqueryboostraptable.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImp implements LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImp(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Page<Location> getAllLocations(Pageable page) {
        return locationRepository.findAll(page);
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

    @Override
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public Location getLocation(Long id) {
        return locationRepository.findById(id).get();
    }

    @Override
    public Optional<Location> findByIdLocation(Long id) {
        return locationRepository.findById(id);
    }

}
