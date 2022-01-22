package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.LocationServiceImp;
import com.javasampleapproach.jqueryboostraptable.Service.LocationService;
import com.javasampleapproach.jqueryboostraptable.model.Category;
import com.javasampleapproach.jqueryboostraptable.model.Location;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Optional;


@Controller
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationServiceImp locationService) {
        this.locationService = locationService;
    }

    @GetMapping( value = "/admin/locations")
    @PreAuthorize("hasAuthority('OP_LOCATIONS_OFFICE')")
    public String index(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<Location> locations = locationService.getAllLocations(pageable);
        model.addAttribute("page", locations);
        return "admin/locations/index";
    }

    @PostMapping(value = "/admin/locations/create")
    @PreAuthorize("hasAuthority('OP_LOCATIONS_OFFICE')")
    public String create(@ModelAttribute @Valid Location location , BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/admin/locations";
            }
            locationService.saveLocation(location);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفقیت انجام گردید.");
            return "redirect:/admin/locations";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/admin/locations";
        }
    }

    @GetMapping(value = "/admin/locations/delete/{id}")
    @PreAuthorize("hasAuthority('OP_LOCATIONS_DELETE_OFFICE')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            locationService.deleteLocation(id);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "مکان موردنظر با موفقیت حذف گردید.");
            return "redirect:/admin/locations";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "مکان به آفیش خاص مرتبط است.");
            return "redirect:/admin/locations";
        }
    }

    @GetMapping("/admin/locations/find/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('OP_LOCATIONS_OFFICE')")
    public Optional<Location> fiOptionalLocation(@PathVariable long id){
//        System.out.println("***************** + "+ id);
        return locationService.findByIdLocation(id);
    }
}
