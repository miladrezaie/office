package com.javasampleapproach.jqueryboostraptable.controller;


import com.javasampleapproach.jqueryboostraptable.Service.RoleService;
import com.javasampleapproach.jqueryboostraptable.enums.Authority;
import com.javasampleapproach.jqueryboostraptable.model.Role;

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
public class RoleController {

    private final RoleService roleService;


    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping(value = "/admin/roles")
//    @PreAuthorize("hasAuthority('OP_ACCESS_ROLES')")
    public String index(Model model, @PageableDefault(size = 10) Pageable pageable) {

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findByUsername(auth.getName());
//
//        model.addAttribute("userName", "خوش آمدید " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("authorities", Authority.values());
        Page<Role> roles = roleService.getAllRoles(pageable);
        model.addAttribute("page", roles);

        return "admin/roles/index";
    }

    @PostMapping(value = "/admin/roles/create")
    public String create(@ModelAttribute @Valid Role role, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY : " + bindingResult.getAllErrors());
//                for (Object object : bindingResult.getAllErrors()) {
//                    redirectAttributes.addFlashAttribute("message", object+"\n");
//
//                    if(object instanceof FieldError) {
//                        FieldError fieldError = (FieldError) object;
//
//                        System.out.println("field error : " + fieldError.getCode());
//                    }
//
//                    if(object instanceof ObjectError) {
//                        ObjectError objectError = (ObjectError) object;
//
//                        System.out.println("object error : " +objectError.getCode().);
//                    }
//                }
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/admin/roles";
            }
            roleService.saveRole(role);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفیت انجام گردید.");
            return "redirect:/admin/roles";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/admin/roles";
        }
    }


    @GetMapping(value = "/admin/roles/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            roleService.deleteRole(id);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "دسترسی مورنظر با موفقیت حذف گردید.");
            return "redirect:/admin/roles";
        } catch (Exception exception) {
//            redirectAttributes.addFlashAttribute("message", "امکان وجود دادن دسترسی به کاربر وجود دارد.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "امکان دسترسی دادن به کاربر وجود دارد.");
            return "redirect:/admin/roles";
        }

    }

    @GetMapping("/admin/roles/find/{id}")
    @ResponseBody
    public Optional<Role> fiOptionalRole(@PathVariable long id) {
        return roleService.findByIdRole(id);
    }
}
