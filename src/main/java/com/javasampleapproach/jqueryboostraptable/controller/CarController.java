package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.CarService;
import com.javasampleapproach.jqueryboostraptable.Service.Impl.CarServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.Brand;
import com.javasampleapproach.jqueryboostraptable.model.Car;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final CarService carService;


    @Autowired
    public CarController(CarServiceImp carService ) {
        this.carService = carService;
    }

    @GetMapping(value = "/admin/cars")
    @PreAuthorize("hasAuthority('OP_CAR_OFFICE')")
    @Transactional
    public String index(Model model,  @PageableDefault(size = 10) Pageable pageable) {
        Page<Car> cars = carService.getAllCars(pageable);
        model.addAttribute("page", cars);

        return "admin/cars/index";
    }

    @PostMapping(value = "/admin/cars/create")
    @PreAuthorize("hasAuthority('OP_CAR_OFFICE')")
    public String create(@ModelAttribute @Valid Car car, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/admin/cars";
            }
            carService.saveCar(car);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفقیت انجام گردید.");
            return "redirect:/admin/cars";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/admin/cars";
        }
    }

    @GetMapping(value = "/admin/cars/delete/{id}")
    @PreAuthorize("hasAuthority('OP_CAR_OFFICE')")
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
    @PreAuthorize("hasAuthority('OP_CAR_OFFICE')")
    @ResponseBody
    public Optional<Car> fiOptionalCar(@PathVariable long id){
        return carService.findByIdCar(id);
    }
}
