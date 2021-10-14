package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.CarServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.Car;
import com.javasampleapproach.jqueryboostraptable.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CarController {

    private final CarServiceImp carService;

    private final UserService userService;

    @Autowired
    public CarController(CarServiceImp carService, UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @GetMapping(value = "/admin/cars")
    public String index(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        model.addAttribute("userName", "خوش آمدید " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("cars",carService.getAllCars());
        return "cars/cars";
    }

    @PostMapping(value = "/admin/cars/create")
    public String create(@ModelAttribute Car car) {
        carService.saveCar(car);
        return "redirect:/admin/cars";
    }

    @GetMapping(value = "/admin/cars/edit/{id}")
    public String update(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("car", carService.getCar(id));
        return "redirect:/admin/cars";
    }

    @GetMapping(value = "/admin/cars/delete/{id}")
    public String delete(@PathVariable Long id) {
        carService.deleteCar(id);
        return "redirect:/admin/cars";
    }
}
