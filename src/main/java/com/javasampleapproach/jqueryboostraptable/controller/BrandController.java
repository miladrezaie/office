package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.BrandService;
import com.javasampleapproach.jqueryboostraptable.model.Brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class BrandController {

    private final BrandService brandService;


    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping(value = "/admin/brands")
//    @PreAuthorize("hasAuthority('OP_ACCESS_BRANDS')")
    public String index(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<Brand> brands = brandService.getAllBrands(pageable);
        model.addAttribute("page", brands);

        return "admin/brands/index";
    }

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
        return brandService.findByIdBrand(id);
    }
}
