package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.BrandServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.Brand;
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
public class BrandController {

    private final BrandServiceImp brandService;

    private final UserService userService;

    @Autowired
    public BrandController(BrandServiceImp brandService, UserService userService) {
        this.brandService = brandService;
        this.userService = userService;
    }

    @GetMapping(value = "/admin/brands")
    @PreAuthorize("hasAuthority('OP_ACCESS_BRANDS')")
    public String index(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        model.addAttribute("userName", "خوش آمدید " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("brands", brandService.getAllBrands());

        return "brands/brands";
    }

    @PostMapping(value = "/admin/brands/create")
    @PreAuthorize("hasAuthority('OP_ACCESS_BRANDS')")
    public String create(@ModelAttribute Brand brand) {
        brandService.saveBrand(brand);
        return "redirect:/admin/brands";
    }

    @GetMapping(value = "/admin/brands/edit/{id}")
    @PreAuthorize("hasAuthority('OP_ACCESS_BRANDS')")
    public String update(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("brand", brandService.getBrand(id));
        return "redirect:/admin/brands";
    }

    @GetMapping(value = "/admin/brands/delete/{id}")
    @PreAuthorize("hasAuthority('OP_ACCESS_BRANDS')")
    public String delete(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return "redirect:/admin/brands";
    }
}
