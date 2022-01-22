package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.Service.CategoryService;
import com.javasampleapproach.jqueryboostraptable.Service.Impl.CategoryServiceImp;
import com.javasampleapproach.jqueryboostraptable.Service.Impl.JobServiceImp;
import com.javasampleapproach.jqueryboostraptable.Service.JobService;
import com.javasampleapproach.jqueryboostraptable.model.Category;
import com.javasampleapproach.jqueryboostraptable.model.Job;
import com.javasampleapproach.jqueryboostraptable.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class JobController {

    private final JobService jobService;
    private final CategoryService categoryService;

    @Autowired
    public JobController(JobService jobService, CategoryService categoryService) {
        this.jobService = jobService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/admin/jobs")
    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String index(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<Job> jobs = jobService.getAllJobs(pageable);
        model.addAttribute("page", jobs);
        model.addAttribute("categories", categoryService.findAll());
        return "admin/jobs/index";
    }

    @PostMapping(value = "/admin/jobs/create")
    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public String create(@ModelAttribute @Valid Job job, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/admin/jobs";
            }
            jobService.saveJob(job);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفقیت انجام گردید.");
            return "redirect:/admin/jobs";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/admin/jobs";
        }
    }


    @GetMapping(value = "/admin/jobs/delete/{id}")
    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
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
    @PreAuthorize("hasAuthority('OP_ACCESS_JOBS')")
    public Optional<Job> fiOptionalJob(@PathVariable long id) {
        return jobService.findByIdJob(id);
    }
}
