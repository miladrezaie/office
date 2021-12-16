package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.RoleServiceImp;
import com.javasampleapproach.jqueryboostraptable.enums.Authority;
import com.javasampleapproach.jqueryboostraptable.model.Role;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class RoleController {

    private final RoleServiceImp roleService;

    private final UserService userService;

    private final RoleRepository roleRepository;

    private Authority authority;

    @Autowired
    public RoleController(RoleServiceImp roleService, UserService userService, RoleRepository roleRepository) {
        this.roleService = roleService;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }


    @GetMapping(value = "/admin/roles")
//    @PreAuthorize("hasAuthority('OP_ACCESS_ROLES')")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        model.addAttribute("userName", "خوش آمدید " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("authorities", Authority.values());
        model.addAttribute("roles", roleRepository.findAll(new PageRequest(page,10)));
        model.addAttribute("currentPage", page);
        return "admin/roles/index";
    }

    @PostMapping(value = "/admin/roles/create")
    public String create(@ModelAttribute @Valid Role role , BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()){
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY : "+bindingResult.getAllErrors());
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
        System.out.println("***************** + " + id);
        return roleService.findByIdRole(id);
    }
}
