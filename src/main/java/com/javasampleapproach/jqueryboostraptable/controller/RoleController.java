package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.RoleServiceImp;
import com.javasampleapproach.jqueryboostraptable.enums.Authority;
import com.javasampleapproach.jqueryboostraptable.model.Role;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
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
        return "roles/roles";
    }

    @PostMapping(value = "/admin/roles/create")
    @Transactional
    public String create(@ModelAttribute Role role, Model model) {
        try {
//            if (errors.hasErrors()){
//                throw new Exception("اطلاعات ارسالی نادرست است مجدد تلاش نمایید");
//            }
            roleService.saveRole(role);
            return "redirect:/admin/roles";
        } catch (ConstraintViolationException exception) {
            model.addAttribute("message", "خطایی به وجود آمده مجددا تلاش نمایید");
            return "errorPage";
        }
    }


    @GetMapping(value = "/admin/roles/delete/{id}")
    public String delete(@PathVariable Long id) {
        System.out.println("saaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        roleService.deleteRole(id);
        return "redirect:/admin/roles";
    }

    @GetMapping("/admin/roles/find/{id}")
    @ResponseBody
    public Optional<Role> fiOptionalRole(@PathVariable long id) {
        System.out.println("***************** + " + id);
        return roleService.findByIdRole(id);
    }
}
