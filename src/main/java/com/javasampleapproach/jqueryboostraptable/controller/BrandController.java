package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.BrandServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.Brand;
import com.javasampleapproach.jqueryboostraptable.model.Location;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
//    @PreAuthorize("hasAuthority('OP_ACCESS_BRANDS')")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("brands", brandRepository.findAll(new PageRequest(page,10)));
        model.addAttribute("currentPage", page);

        return "admin/brands/index";
    }
//    private Sort sortByIdDesc() {
//        return new Sort(Sort.Direction.DESC, "id");
//    }
    @PostMapping(value = "/admin/brands/create")
//    @PreAuthorize("hasAuthority('OP_ACCESS_BRANDS')")
    public String create(@ModelAttribute @Valid Brand brand, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/admin/brands";
            }
            brandService.saveBrand(brand);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفیت انجام گردید.");
            return "redirect:/admin/brands";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/admin/brands";
        }
    }


    @GetMapping(value = "/admin/brands/delete/{id}")
//    @PreAuthorize("hasAuthority('OP_ACCESS_BRANDS')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            brandService.deleteBrand(id);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "برند مورنظر با موفقیت حذف گردید.");
            return "redirect:/admin/brands";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "برند به تجهیز خاص مرتبط است.");
            return "redirect:/admin/brands";
        }
    }


    @GetMapping("/admin/brands/find/{id}")
    @ResponseBody
    public Optional<Brand> fiOptionalBrand(@PathVariable long id) {
        System.out.println("***************** + " + id);
        return brandService.findByIdBrand(id);
    }
}
