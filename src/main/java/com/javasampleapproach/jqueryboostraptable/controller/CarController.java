package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.CarServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.Car;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CarController {

    private final CarServiceImp carService;

    private final UserService userService;

    private final CarRepository carRepository;

    @Autowired
    public CarController(CarServiceImp carService, UserService userService, CarRepository carRepository) {
        this.carService = carService;
        this.userService = userService;
        this.carRepository = carRepository;
    }

    @GetMapping(value = "/admin/cars")
    @Transactional
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("userName", "خوش آمدید " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("cars",carService.getAllCars());
        model.addAttribute("cars",carRepository.findAll(new PageRequest(page,10)));
        model.addAttribute("currentPage", page);
        return "cars/cars";
    }

    @PostMapping(value = "/admin/cars/create")
    @Transactional
    public String create(@ModelAttribute @Valid Car car, Model model,Errors errors) {
        try{
            System.out.println("asdasdasdadsadd");
//            if () {
//                System.out.println("try");
//                throw new RuntimeException("test exception");
//
//            }
            carService.saveCar(car);
            return "redirect:/admin/cars";
        }catch (ConstraintViolationException exception){
            System.out.println("sadasdasd");
            model.addAttribute("message","خطایی به وجود آمده مجددا تلاش نمایید");
            return "errorPage";
        }

    }

    @GetMapping(value = "/admin/cars/delete/{id}")
    public String delete(@PathVariable Long id) {
        carService.deleteCar(id);
        return "redirect:/admin/cars";
    }

    @GetMapping("/admin/cars/find/{id}")
    @ResponseBody
    public Optional<Car> fiOptionalCar(@PathVariable long id){
        System.out.println("***************** + "+ id);
        return carService.findByIdCar(id);
    }
}
