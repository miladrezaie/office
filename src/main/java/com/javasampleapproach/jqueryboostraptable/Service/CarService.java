package com.javasampleapproach.jqueryboostraptable.Service;

import com.javasampleapproach.jqueryboostraptable.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CarService {
    Page<Car> getAllCars(Pageable page);

    List<Car> findAll();

    Optional<Car> findById(long brand);

    void saveCar(Car brand);

    void deleteCar(Long id);

    Car getCar(Long id);

    Optional<Car> findByIdCar(Long id);

}
