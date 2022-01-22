package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.CategoryService;
import com.javasampleapproach.jqueryboostraptable.Service.Impl.CategoryServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.Category;
import com.javasampleapproach.jqueryboostraptable.model.Category;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.CategoryRepository;
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

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CategoryController {

    private final CategoryService categoryService;


    @Autowired
    public CategoryController(CategoryServiceImp categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/admin/categories")
    @PreAuthorize("hasAuthority('OP_CATEGORY_OFFICE')")
    public String index(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<Category> categories = categoryService.getAllCategories(pageable);
        model.addAttribute("page", categories);

        return "admin/categories/index";
    }

    @PostMapping(value = "/admin/categories/create")
    @PreAuthorize("hasAuthority('OP_CATEGORY_OFFICE')")
    public String create(@ModelAttribute @Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/admin/categories";
            }
            categoryService.saveCategory(category);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفقیت انجام گردید.");
            return "redirect:/admin/categories";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/admin/categories";
        }
    }


    @GetMapping(value = "/admin/categories/delete/{id}")
    @PreAuthorize("hasAuthority('OP_CATEGORY_OFFICE')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "دسته بندی مورنظر با موفقیت حذف گردید.");
            return "redirect:/admin/categories";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "دسته بندی به تجهیز خاص مرتبط است.");
            return "redirect:/admin/categories";
        }
    }

    @GetMapping("/admin/categories/find/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('OP_CATEGORY_OFFICE')")
    public Optional<Category> fiOptionalCategory(@PathVariable long id) {
        return categoryService.findByIdCategory(id);
    }
}
