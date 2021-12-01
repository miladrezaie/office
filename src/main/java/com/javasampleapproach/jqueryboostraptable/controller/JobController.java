package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.CategoryServiceImp;
import com.javasampleapproach.jqueryboostraptable.Service.Impl.JobServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.Job;
import com.javasampleapproach.jqueryboostraptable.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class JobController {

    private final JobServiceImp jobService;
    private final CategoryServiceImp categoryServiceImp;

    private final UserService userService;

    private final JobRepository jobRepository;

    @Autowired
    public JobController(JobServiceImp jobService, CategoryServiceImp categoryServiceImp, UserService userService, JobRepository jobRepository) {
        this.jobService = jobService;
        this.categoryServiceImp = categoryServiceImp;
        this.userService = userService;
        this.jobRepository = jobRepository;
    }

    @GetMapping(value = "/admin/jobs")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("jobs", jobRepository.findAll(new PageRequest(page,10)));
        model.addAttribute("currentPage", page);
        model.addAttribute("categories",categoryServiceImp.getAllCategories());
        return "admin/jobs/index";
    }

    @PostMapping(value = "/admin/jobs/create")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String create(@ModelAttribute @Valid Job job, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/admin/jobs";
            }
            jobService.saveJob(job);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفیت انجام گردید.");
            return "redirect:/admin/jobs";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/admin/jobs";
        }
    }


    @GetMapping(value = "/admin/jobs/delete/{id}")
//    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            jobService.deleteJob(id);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عنوان شغلی مورنظر با موفقیت حذف گردید.");
            return "redirect:/admin/jobs";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "عنوان شغلی به آفیش خاص مرتبط است.");
            return "redirect:/admin/jobs";
        }
    }

    @GetMapping("/admin/jobs/find/{id}")
    @ResponseBody
    public Optional<Job> fiOptionalJob(@PathVariable long id){
        System.out.println("***************** + "+ id);
        return jobService.findByIdJob(id);
    }
}
