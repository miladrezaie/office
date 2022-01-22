package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.BrandService;
import com.javasampleapproach.jqueryboostraptable.Service.LocationService;
import com.javasampleapproach.jqueryboostraptable.model.Tajhizat;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.TajhizatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Controller
public class TajhizatController {

    @Autowired
    private TajhizatRepository tRepo;

    private final UserService userService;
    private final LocationService locationService;
    private final BrandService brandService;

    @Autowired
    public TajhizatController(UserService userService, LocationService locationService, BrandService brandService) {
        this.userService = userService;
        this.locationService = locationService;
        this.brandService = brandService;
    }

    @GetMapping("/tajhizats")
    @PreAuthorize("hasAuthority('OP_ACCESS_TAJHIZATS')")
    public String viewTajhizat(Model model, @RequestParam(defaultValue = "0") int page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("tajhizats", tRepo.findAll(new PageRequest(page, 10)));
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("currentPage", page);
        return "admin/tajhizats/index";
    }

    @GetMapping("/findOneTajhiz")
    @ResponseBody
    @PreAuthorize("hasAuthority('OP_ACCESS_TAJHIZATS')")
    public Optional<Tajhizat> findOneTajhiz(long id) {
        return tRepo.findById(id);
    }

    @GetMapping("/admin/tajhizats/find/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('OP_ACCESS_TAJHIZATS')")
    public Optional<Tajhizat> fiOptionalTajhizat(@PathVariable long id) {
        return tRepo.findById(id);
    }

    @GetMapping("/admin/tajhizats/delete/{id}")
    @PreAuthorize("hasAuthority('OP_ACCESS_TAJHIZATS')")
    public String deleteTajhiz(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            tRepo.deleteById(id);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "تجهیز مود نظر با موفقیت حذف گردید.");
            return "redirect:/tajhizats";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تجهیز مود نظر به آفیش خاصی تعلق دارد است.");
            return "redirect:/tajhizats";
        }
    }

    @PostMapping("/admin/tajhizats/create")
    @PreAuthorize("hasAuthority('OP_ACCESS_TAJHIZATS')")
    public String Esave(@RequestParam MultipartFile file, @ModelAttribute @Valid Tajhizat t, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")) {
                System.out.println("not a valid file");
            }
            try {
                t.setImg(Base64.getEncoder().encodeToString(file.getBytes()));
            } catch (IOException er) {
                er.printStackTrace();
            }
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/tajhizats";
            }
            tRepo.save(t);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفقیت انجام گردید.");
            return "redirect:/tajhizats";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/tajhizats";
        }
    }

}
