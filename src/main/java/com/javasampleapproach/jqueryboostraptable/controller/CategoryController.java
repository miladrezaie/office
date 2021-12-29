package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.CategoryServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.Category;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    private final CategoryServiceImp categoryService;

    private final UserService userService;

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryServiceImp categoryService, UserService userService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping(value = "/admin/categories")
//    @PreAuthorize("hasAuthority('OP_ACCESS_BRANDS')")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {

        model.addAttribute("categories", categoryRepository.findAll(new PageRequest(page, 10)));
        model.addAttribute("currentPage", page);

        return "admin/categories/index";
    }

    @PostMapping(value = "/admin/categories/create")
//    @PreAuthorize("hasAuthority('OP_ACCESS_BRANDS')")
    public String create(@ModelAttribute @Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/admin/categories";
            }
            categoryService.saveCategory(category);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفیت انجام گردید.");
            return "redirect:/admin/categories";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/admin/categories";
        }
    }


    @GetMapping(value = "/admin/categories/delete/{id}")
//    @PreAuthorize("hasAuthority('OP_ACCESS_BRANDS')")
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
    public Optional<Category> fiOptionalCategory(@PathVariable long id) {
        System.out.println("***************** + " + id);
        return categoryService.findByIdCategory(id);
    }
}
