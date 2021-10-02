package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.LocationServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.Location;
import com.javasampleapproach.jqueryboostraptable.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LocationController {

    private final LocationServiceImp locationService;

    private final UserService userService;

    @Autowired
    public LocationController(LocationServiceImp locationService, UserService userService) {
        this.locationService = locationService;
        this.userService = userService;
    }

    @GetMapping( value = "/admin/locations")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        model.addAttribute("userName", "خوش آمدید " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("locations", locationService.getAllLocations());

        return "locations/locations";
    }

    @PostMapping(value = "/admin/locations/create")
    public String create(@ModelAttribute Location location) {
        locationService.saveLocation(location);
        return "redirect:/admin/locations";
    }

    @GetMapping(value = "/admin/locations/edit/{id}")
    public String update(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("location", locationService.getLocation(id));
        return "redirect:/admin/locations";
    }

    @GetMapping(value = "/admin/locations/delete/{id}")
    public String delete(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return "redirect:/admin/locations";
    }
}
