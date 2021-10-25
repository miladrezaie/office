package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.BrandServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.Brand;
import com.javasampleapproach.jqueryboostraptable.model.Location;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

@Controller
public class BrandController {

    private final BrandServiceImp brandService;

    private final UserService userService;

    private final BrandRepository brandRepository;

    @Autowired
    public BrandController(BrandServiceImp brandService, UserService userService, BrandRepository brandRepository) {
        this.brandService = brandService;
        this.userService = userService;
        this.brandRepository = brandRepository;
    }

    @GetMapping(value = "/admin/brands")
    @PreAuthorize("hasAuthority('OP_ACCESS_BRANDS')")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        model.addAttribute("userName", "خوش آمدید " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("brands", brandRepository.findAll(new PageRequest(page,10)));
        model.addAttribute("currentPage", page);

        return "brands/brands";
    }

    @PostMapping(value = "/admin/brands/create")
    @PreAuthorize("hasAuthority('OP_ACCESS_BRANDS')")
    @Transactional
    public String create(@ModelAttribute Brand brand,Model model) {
        try{
//            if (errors.hasErrors()){
//                throw new Exception("اطلاعات ارسالی نادرست است مجدد تلاش نمایید");
//            }
            brandService.saveBrand(brand);
            return "redirect:/admin/brands";

        }catch (ConstraintViolationException exception){
            model.addAttribute("message","خطایی به وجود آمده مجددا تلاش نمایید");
            return "errorPage";
        }
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

    @GetMapping("/admin/brands/find/{id}")
    @ResponseBody
    public Optional<Brand> fiOptionalBrand(@PathVariable long id){
        System.out.println("***************** + "+ id);
        return brandService.findByIdBrand(id);
    }
}
