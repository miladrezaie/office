package com.javasampleapproach.jqueryboostraptable.controller;


import com.javasampleapproach.jqueryboostraptable.Service.Impl.ProgramServiceImp;
import com.javasampleapproach.jqueryboostraptable.enums.RozHafteh;
import com.javasampleapproach.jqueryboostraptable.model.Program;
import com.javasampleapproach.jqueryboostraptable.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProgramController {

    private final ProgramServiceImp programService;

    private final UserService userService;

    @Autowired
    public ProgramController(ProgramServiceImp programService, UserService userService) {
        this.programService = programService;
        this.userService = userService;
    }

    @GetMapping(value = "/admin/programs")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("userName", "خوش آمدید " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("programs", programService.getAllPrograms());
        model.addAttribute("rozhaehafte", RozHafteh.values());

        return "programs/programs";
    }

    @PostMapping(value = "/admin/programs/create")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String create(@ModelAttribute Program program) {
        programService.saveProgram(program);
        return "redirect:/admin/programs";
    }

    @GetMapping(value = "/admin/programs/edit/{id}")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String update(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("program", programService.getProgram(id));
        return "redirect:/admin/programs";
    }

    @GetMapping(value = "/admin/programs/delete/{id}")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String delete(@PathVariable Long id) {
        programService.deleteProgram(id);
        return "redirect:/admin/programs";
    }
}
