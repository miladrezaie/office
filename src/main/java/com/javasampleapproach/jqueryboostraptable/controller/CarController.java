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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
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
    public String create(@ModelAttribute @Valid Car car, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/admin/cars";
            }
            carService.saveCar(car);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفیت انجام گردید.");
            return "redirect:/admin/cars";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/admin/cars";
        }
    }

    @GetMapping(value = "/admin/cars/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            carService.deleteCar(id);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "وسیله نقلیه مورنظر با موفقیت حذف گردید.");
            return "redirect:/admin/cars";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "وسیله نقلیه به آفیش خاص مرتبط است.");
            return "redirect:/admin/cars";
        }
    }

    @GetMapping("/admin/cars/find/{id}")
    @ResponseBody
    public Optional<Car> fiOptionalCar(@PathVariable long id){
        System.out.println("***************** + "+ id);
        return carService.findByIdCar(id);
    }
}
