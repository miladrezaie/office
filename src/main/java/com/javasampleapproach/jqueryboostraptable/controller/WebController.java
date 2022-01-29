/***
 * @author amir-reza abbasi
 */
package com.javasampleapproach.jqueryboostraptable.controller;


import java.util.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.javasampleapproach.jqueryboostraptable.Service.CategoryService;
import com.javasampleapproach.jqueryboostraptable.Service.JobService;
import com.javasampleapproach.jqueryboostraptable.Service.RoleService;
import com.javasampleapproach.jqueryboostraptable.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.javasampleapproach.jqueryboostraptable.repository.UserRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Service
@Controller
public class WebController {


    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/members")
//    @PreAuthorize("hasAuthority('OP_ACCESS_ROLES')")
    public String viewMembers(Model model, String keyword, @RequestParam(defaultValue = "0") int page, HttpServletRequest request) {
        //data have devices information
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        List<Job> jobs = jobService.findAll();
        List<Role> roles = roleService.findAll();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("userName", "Welcome " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        if (keyword != null) {
            model.addAttribute("users", userRepo.search(keyword));
        } else {
            model.addAttribute("jobs", jobs);
            model.addAttribute("roles", roles);
            model.addAttribute("users", userRepo.findAll(new PageRequest(page, 10)));
            model.addAttribute("currentPage", page);
            model.addAttribute("categories", categories);
        }
        return "admin/users/index";
    }


    @PostMapping("/saveUser")
    public String save(User u) {
        userService.save(u);
        return "redirect:/members";
    }

    @PostMapping("/saveeUser")
    public String saveue(@ModelAttribute @Valid User user, BindingResult bindingResult, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> message = new ArrayList<>();
                for (Object object : bindingResult.getAllErrors()) {
                    if (object instanceof FieldError) {
                        FieldError fieldError = (FieldError) object;
                        message.add(messageSource.getMessage(fieldError, null));
                        System.out.println(message);
                    }
                    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                    redirectAttributes.addFlashAttribute("message", message);
                    return "redirect:/members";
                }
            } else {
                if (userRepo.findByPersonalId(user.getPersonalId()) != null) {
                    bindingResult.rejectValue("personalId", "error.user",
                            "هم اکنون کاربری با این شماره کارمندی موجود است");
                }
                if (!file.isEmpty()) {
                    userService.saveuemza(user, file);
                } else {
                    User us = userRepo.findByPersonalId(user.getPersonalId());
                    user.setEmza(us.getEmza());
                }
                userService.save(user);
                redirectAttributes.addFlashAttribute("alertClass", "alert-success");
                redirectAttributes.addFlashAttribute("message_s", "عملیات با موفقیت انجام گردید.");
                return "redirect:/members";
            }
            throw new Exception();

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message_s", "لطفا مجددا تلاش نمایید.");
            return "redirect:/members";
        }
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/auth/login");

        return modelAndView;
    }


    @GetMapping("/admin/profile")
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);
        return "admin/auth/user-profile";
    }

//    @RequestMapping(value = "/registration", method = RequestMethod.GET)
//    public String registration(Model model, User user) {
//        List<Job> jobs = jobService.findAll();
//        model.addAttribute("jobs", jobs);
//        model.addAttribute("user", user);
//        return "/registration";
//    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createNewUser(@ModelAttribute @Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/members";
            } else {
//            if (userRepo.findByPersonalId(user.getPersonalId()) != null) {
//                bindingResult.rejectValue("personalId", "error.user",
//                        "هم اکنون کاربری با این شماره کارمندی موجود است");
//            }
                System.out.println("create user form else");
                userService.save(user);
            }
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفقیت انجام گردید.");
            return "redirect:/members";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/members";
        }
    }

    @GetMapping("/deleteUser/{id}")
//    @PreAuthorize("#userRepo.name != authentication.name")
    @PreAuthorize("hasAuthority('OP_ACCESS_ROLES')")
    public String deleteUser(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            userRepo.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "کاربر مورد نظر در یک آفیش حضور دارد .");
            return "redirect:/members";
        }
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        redirectAttributes.addFlashAttribute("message", "کاربر مورد نظر با موفقیت حذف گردید .");
        return "redirect:/members";
    }

    @PostMapping("/admin/profile/user")
    public String editProfile(@ModelAttribute @Valid User user, BindingResult bindingResult, @RequestParam("oldPass") String oldPass, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<String> message = new ArrayList<>();
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    message.add(messageSource.getMessage(fieldError, null));
                    System.out.println(message);
                }
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", message);
                System.out.println("if condition " + message);

                return "redirect:/admin/profile";
            }
        }
        System.out.println("sadasssssssddd");
        try {
            System.out.println("sadasssssssddd");

            User userE = userRepo.findByPersonalId(user.getPersonalId());
            System.out.println("sadasssssssddd");


            System.out.println("else condition ");
            if (bCryptPasswordEncoder.matches(oldPass, userE.getPass())) {
                System.out.println("else condition ");

                userE.setFName(user.getFName());
                userE.setLname(user.getLname());
                userE.setPass(user.getPass());
                userService.save(userE);
                redirectAttributes.addFlashAttribute("alertClass", "alert-success");
                redirectAttributes.addFlashAttribute("message_s", "عملیات با موفقیت انجام گردید.");
                return "redirect:/admin/profile";
            }

            throw new Exception();
        } catch (Exception exception) {
            System.out.println("Has Errors : " + exception);
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message_s", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/admin/profile";

        }
    }

    @GetMapping("/members/findOneUser/{id}")
    @ResponseBody
    public Optional<User> findOneUser(@PathVariable Integer id) {
        return userRepo.findById(id);
    }

    @GetMapping("/findAllUser")
    @ResponseBody
    public List<User> findAllUser() {
        return userRepo.findAll();
    }


}


