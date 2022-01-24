package com.javasampleapproach.jqueryboostraptable.controller;


import com.javasampleapproach.jqueryboostraptable.Service.ProgramService;
import com.javasampleapproach.jqueryboostraptable.enums.RozHafteh;
import com.javasampleapproach.jqueryboostraptable.model.Program;
import com.javasampleapproach.jqueryboostraptable.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

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
public class ProgramController {

    private final ProgramService programService;
    private final UserService userService;

    @Autowired
    public ProgramController(ProgramService programService, UserService userService) {
        this.programService = programService;
        this.userService = userService;
    }

    @GetMapping(value = "/admin/programs/trueindex")
    @PreAuthorize("hasAuthority('OP_PROGRAMS_OFFICE')")
    public String indexTrue(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<Program> programs = programService.getAllTruePrograms(pageable);
//        System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGgg 111: " + programs.getContent());
        model.addAttribute("page", programs);
        model.addAttribute("rozhaehafte", RozHafteh.values());

        return "admin/programs/indexTrue";
    }

    @GetMapping(value = "/admin/programs/await")
    @PreAuthorize("hasAuthority('OP_PROGRAMS_OFFICE')")
    public String indexAwait(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<Program> programs = programService.getAllAwaitPrograms(pageable);
//        System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGgg  222: " + programs.getContent());

        model.addAttribute("page", programs);
        model.addAttribute("rozhaehafte", RozHafteh.values());

        return "admin/programs/indexAwait";
    }

    @PostMapping(value = "/admin/programs/create")
    @PreAuthorize("hasAuthority('OP_PROGRAMS_OFFICE')")
//    @Transactional
    public String create(@ModelAttribute @Valid Program program, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/admin/programs/await";
            }
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByUsername(auth.getName());
            program.setUser(user);
            program.setStatus(false);
            programService.saveProgram(program);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفقیت انجام گردید.");
            return "redirect:/admin/programs/await";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/admin/programs/await";
        }
    }

    @PostMapping(value = "/admin/programs/approved")
    @PreAuthorize("hasAuthority('OP_PROGRAM_APPROVE')")
    public String setStatusTrue(@ModelAttribute("id") Long id, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/admin/programs/trueindex";
            }
            Program program = programService.findByIdProgram(id).get();
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            program.setStatus(true);
            programService.saveProgram(program);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفقیت انجام گردید.");
            return "redirect:/admin/programs/trueindex";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/admin/programs/trueindex";
        }
    }

    @GetMapping(value = "/admin/programs/delete/{id}")
    @PreAuthorize("hasAuthority('OP_PROGRAMS_OFFICE')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            programService.deleteProgram(id);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "برنامه مورنظر با موفقیت حذف گردید.");
            return "redirect:/admin/programs/trueindex";
        } catch (Exception exception) {
//            redirectAttributes.addFlashAttribute("message", "امکان وجود دادن برنامه به کاربر وجود دارد.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "برنامه به آفیش خاص مربوط است.");
            return "redirect:/admin/programs/trueindex";
        }
    }

    @GetMapping("/admin/programs/find/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('OP_PROGRAMS_OFFICE')")
    public Optional<Program> fiOptionalProgram(@PathVariable long id) {
//        System.out.println("***************** + "+ id);
        return programService.findByIdProgram(id);
    }
}
