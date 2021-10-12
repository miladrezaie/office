package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.JobServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.Job;
import com.javasampleapproach.jqueryboostraptable.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class JobController {

    private final JobServiceImp jobService;

    private final UserService userService;

    @Autowired
    public JobController(JobServiceImp jobService, UserService userService) {
        this.jobService = jobService;
        this.userService = userService;
    }

    @GetMapping(value = "/admin/jobs")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String index(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        model.addAttribute("userName", "خوش آمدید " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("jobs", jobService.getAllJobs());

        return "jobs/jobs";
    }

    @PostMapping(value = "/admin/jobs/create")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String create(@ModelAttribute Job job) {
        jobService.saveJob(job);
        return "redirect:/admin/jobs";
    }

    @GetMapping(value = "/admin/jobs/edit/{id}")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String update(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("job", jobService.getJob(id));
        return "redirect:/admin/jobs";
    }

    @GetMapping(value = "/admin/jobs/delete/{id}")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String delete(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/admin/jobs";
    }
}
