package com.javasampleapproach.jqueryboostraptable.controller;


import com.javasampleapproach.jqueryboostraptable.Service.Impl.ProgramServiceImp;
import com.javasampleapproach.jqueryboostraptable.enums.RozHafteh;
import com.javasampleapproach.jqueryboostraptable.model.Program;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.ProgramRepository;
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
public class ProgramController {

    private final ProgramServiceImp programService;

    private final UserService userService;

    private final ProgramRepository programRepository;

    @Autowired
    public ProgramController(ProgramServiceImp programService, UserService userService, ProgramRepository programRepository) {
        this.programService = programService;
        this.userService = userService;
        this.programRepository = programRepository;
    }

    @GetMapping(value = "/admin/programs")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        model.addAttribute("userName", "خوش آمدید " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("rozhaehafte", RozHafteh.values());
        model.addAttribute("programs", programRepository.findAll(new PageRequest(page,10)));
        model.addAttribute("currentPage", page);

        return "programs/programs";
    }

    @PostMapping(value = "/admin/programs/create")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    @Transactional
    public String create(@ModelAttribute Program program,Model model) {
        try{
//            if (errors.hasErrors()){
//                throw new Exception("اطلاعات ارسالی نادرست است مجدد تلاش نمایید");
//            }
            programService.saveProgram(program);
            return "redirect:/admin/programs";
        }catch (ConstraintViolationException exception){
            model.addAttribute("message","خطایی به وجود آمده مجددا تلاش نمایید");
            return "errorPage";
        }
    }

    @GetMapping(value = "/admin/programs/delete/{id}")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String delete(@PathVariable Long id) {
        programService.deleteProgram(id);
        return "redirect:/admin/programs";
    }

    @GetMapping("/admin/programs/find/{id}")
    @ResponseBody
    public Optional<Program> fiOptionalProgram(@PathVariable long id){
        System.out.println("***************** + "+ id);
        return programService.findByIdProgram(id);
    }
}
