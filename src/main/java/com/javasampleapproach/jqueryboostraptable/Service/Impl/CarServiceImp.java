package com.javasampleapproach.jqueryboostraptable.Service.Impl;

import com.javasampleapproach.jqueryboostraptable.Service.CarService;
import com.javasampleapproach.jqueryboostraptable.model.Car;
import com.javasampleapproach.jqueryboostraptable.model.Location;
import com.javasampleapproach.jqueryboostraptable.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImp implements CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImp(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    @Override
    public Page<Car> getAllCars(Pageable page) {
        return carRepository.findAll(page);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> findById(long car) {
        return carRepository.findById(car);
    }

    @Override
    public void saveCar(Car car) {
        carRepository.save(car);
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public Car getCar(Long id) {
        return carRepository.findById(id).get();
    }

    @Override
    public Optional<Car> findByIdCar(Long id) {
        return carRepository.findById(id);
    }
}
