package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.JobServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.Job;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.JobRepository;
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
public class JobController {

    private final JobServiceImp jobService;

    private final UserService userService;

    private final JobRepository jobRepository;

    @Autowired
    public JobController(JobServiceImp jobService, UserService userService, JobRepository jobRepository) {
        this.jobService = jobService;
        this.userService = userService;
        this.jobRepository = jobRepository;
    }

    @GetMapping(value = "/admin/jobs")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("userName", "خوش آمدید " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("jobs", jobRepository.findAll(new PageRequest(page,10)));
        model.addAttribute("currentPage", page);
        return "jobs/jobs";
    }

    @PostMapping(value = "/admin/jobs/create")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    @Transactional
    public String create(@ModelAttribute Job job,Model model) {
        try{
//            if (errors.hasErrors()){
//                throw new Exception("اطلاعات ارسالی نادرست است مجدد تلاش نمایید");
//            }
            jobService.saveJob(job);
            return "redirect:/admin/jobs";
        }catch (ConstraintViolationException exception){
            model.addAttribute("message","خطایی به وجود آمده مجددا تلاش نمایید");
            return "errorPage";
        }
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

    @GetMapping("/admin/jobs/find/{id}")
    @ResponseBody
    public Optional<Job> fiOptionalJob(@PathVariable long id){
        System.out.println("***************** + "+ id);
        return jobService.findByIdJob(id);
    }
}
