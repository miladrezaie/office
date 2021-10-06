package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.RoleServiceImp;
import com.javasampleapproach.jqueryboostraptable.enums.Authority;
import com.javasampleapproach.jqueryboostraptable.model.Role;
import com.javasampleapproach.jqueryboostraptable.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class RoleController {

    private final RoleServiceImp roleService;

    private final UserService userService;


    private Authority authority;

    @Autowired
    public RoleController(RoleServiceImp roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @GetMapping(value = "/admin/roles")
//    @PreAuthorize("hasAuthority('OP_ACCESS_ROLES')")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        model.addAttribute("userName", "خوش آمدید " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("authorities", Authority.values());
//        System.out.println("***********************************"+Authority.values());

//        System.out.println("---------------"+authority.values());
//        model.addAttribute("authorities",authority.getAuthority() );
        return "roles/roles";
    }

    @PostMapping(value = "/admin/roles/create")
    public String create(@ModelAttribute Role role, BindingResult result ) {
//        List<Authority> authorities =authority
        System.out.println("sssssssssssssssssssssssssss   ");
//
//        System.out.println("sssssssssssssssssssssssssss   "+result.getModel());
//        System.out.println("sssssssssssssssssssssssssss   "+role.getAuthorities());

        roleService.saveRole(role);
        return "redirect:/admin/roles";
    }

    @GetMapping(value = "/admin/roles/edit/{id}")
    public String update(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("role", roleService.getRole(id));
        return "redirect:/admin/roles";
    }

    @GetMapping(value = "/admin/roles/delete/{id}")
    public String delete(@PathVariable Long id) {
        System.out.println("saaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        roleService.deleteRole(id);
        return "redirect:/admin/roles";
    }
}
