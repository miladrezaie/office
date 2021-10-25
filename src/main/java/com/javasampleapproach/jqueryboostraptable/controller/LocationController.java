package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.LocationServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.Location;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Optional;


@Controller
public class LocationController {

    private final LocationServiceImp locationService;

    private final UserService userService;

    private final LocationRepository locationRepository;

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    public LocationController(LocationServiceImp locationService, UserService userService, LocationRepository locationRepository) {
        this.locationService = locationService;
        this.userService = userService;
        this.locationRepository = locationRepository;
    }

    @GetMapping( value = "/admin/locations")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("userName", "خوش آمدید " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("locations", locationRepository.findAll(new PageRequest(page,10)));
        model.addAttribute("currentPage", page);
        return "locations/locations";
    }

    @PostMapping(value = "/admin/locations/create")
    @Transactional
    public String create(@Valid Location location ,Model model) {
        try{
//            if (errors.hasErrors()){
//                throw new Exception("اطلاعات ارسالی نادرست است مجدد تلاش نمایید");
//            }
            locationService.saveLocation(location);
            return "redirect:/admin/locations";
        }catch (ConstraintViolationException exception){
            model.addAttribute("message","خطایی به وجود آمده مجددا تلاش نمایید");
            return "errorPage";
        }
    }

    @GetMapping(value = "/admin/locations/delete/{id}")
    public String delete(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return "redirect:/admin/locations";
    }

    @GetMapping("/admin/locations/find/{id}")
    @ResponseBody
    public Optional<Location> fiOptionalLocation(@PathVariable long id){
        System.out.println("***************** + "+ id);
        return locationService.findByIdLocation(id);
    }
}
