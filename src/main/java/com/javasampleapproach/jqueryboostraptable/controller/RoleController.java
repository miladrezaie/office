package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.RoleServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.Role;
import com.javasampleapproach.jqueryboostraptable.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RoleController {

    private final RoleServiceImp roleService;

    private final UserService userService;

    @Autowired
    public RoleController(RoleServiceImp roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping( value = "/admin/roles")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        model.addAttribute("userName", "خوش آمدید " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("roles", roleService.getAllRoles());

        return "roles/roles";
    }

    @PostMapping(value = "/admin/roles/create")
    public String create(@ModelAttribute Role role) {
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
        roleService.deleteRole(id);
        return "redirect:/admin/roles";
    }
}
