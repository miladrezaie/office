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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
//    @Transactional
    public String create(@ModelAttribute @Valid Location location , BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/admin/locations";
            }
            locationService.saveLocation(location);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفیت انجام گردید.");
            return "redirect:/admin/locations";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/admin/locations";
        }
    }

    @GetMapping(value = "/admin/locations/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            locationService.deleteLocation(id);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "وسیله نقلیه مورنظر با موفقیت حذف گردید.");
            return "redirect:/admin/locations";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "وسیله نقلیه به آفیش خاص مرتبط است.");
            return "redirect:/admin/locations";
        }
    }

    @GetMapping("/admin/locations/find/{id}")
    @ResponseBody
    public Optional<Location> fiOptionalLocation(@PathVariable long id){
        System.out.println("***************** + "+ id);
        return locationService.findByIdLocation(id);
    }
}
