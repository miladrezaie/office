/***
 * @author amir-reza abbasi
 */
package com.javasampleapproach.jqueryboostraptable.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.CategoryServiceImp;
import com.javasampleapproach.jqueryboostraptable.Service.Impl.JobServiceImp;
import com.javasampleapproach.jqueryboostraptable.Service.Impl.RoleServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.javasampleapproach.jqueryboostraptable.repository.EmployeeRepository;
import com.javasampleapproach.jqueryboostraptable.repository.Roozh;
import com.javasampleapproach.jqueryboostraptable.repository.TimeRepository;
import com.javasampleapproach.jqueryboostraptable.repository.UserRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@CrossOrigin(origins = "http://localhost:4200")
@Service
@Controller
public class WebController {

    @Autowired
    private TimeRepository timeRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private JobServiceImp jobServiceImp;

    @Autowired
    private RoleServiceImp roleServiceImp;

    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/employee")
    public String viewEmployee(Model model, @RequestParam(defaultValue = "0") int page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("employees", employeeRepo.findAll(new PageRequest(page, 10)));
        model.addAttribute("currentPage", page);

        return "employeePage";
    }

    @GetMapping("/times")
    public String viewTimes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        //	model.addAttribute("form",formRepo.findByCreatorid(user.getId()));

        model.addAttribute("user", user);
        model.addAttribute("userName", "Welcome " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        return "timepage2";
    }

    @GetMapping("/members")
    public String viewMembers(Model model, String keyword, @RequestParam(defaultValue = "0") int page, HttpServletRequest request) {
        //data have devices information
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        List<Job> jobs = jobServiceImp.getAllJobs();
        List<Role> roles = roleServiceImp.getAllRoles();
        List<Category> categories = categoryServiceImp.getAllCategories();
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

    @GetMapping("/Scanner")
    public String qrScan() {
        return "scanner";
    }



    @PostMapping("/Esave")
    public String Esave(employee e) {
        employeeRepo.save(e);
        return "redirect:/employee";
    }

    @PostMapping("/saveUser")
    public String save(User u) {
        userService.save(u);
        return "redirect:/members";
    }

    @PostMapping("/saveeUser")
    public String saveue(@ModelAttribute @Valid User user, BindingResult bindingResult, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        System.out.println("EEEEEEEEEEEEEEEEEEEEEEEE");
        System.out.println("EEEEEEEEEEEEEEEEEEEEEEEE 2 : "+user);

        try {
            if (bindingResult.hasErrors()) {
                System.out.println("ERRRRRRRRRRRRRRRRRRR: "+bindingResult.getAllErrors());
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/members";
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
                redirectAttributes.addFlashAttribute("message", "عملیات با موفیت انجام گردید.");
                return "redirect:/members";
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "لطفا مجددا تلاش نمایید.");
//            model.addAttribute("message", "لطفا مجددا تلاش نمایید");
            System.out.println("************" + e);
            return "redirect:/members";
        }
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        System.out.println("************************* login method start");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/auth/login");
        System.out.println("************************* login method end");

        return modelAndView;
    }

    @RequestMapping(value = {"/SetRole"}, method = RequestMethod.GET)
    public ModelAndView SetRole(Integer uid, Integer rid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/panel/login");
        return modelAndView;
    }

    @GetMapping("/admin/profile")
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user",  user);
        return "admin/auth/user-profile";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model, User user) {
        List<Job> jobs = jobServiceImp.getAllJobs();
        model.addAttribute("jobs", jobs);
        model.addAttribute("user", user);
        return "/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createNewUser(@ModelAttribute @Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        System.out.println("create user form");
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
            redirectAttributes.addFlashAttribute("message", "عملیات با موفیت انجام گردید.");
            return "redirect:/members";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/members";
        }
    }

    @GetMapping("/deleteUser/{id}")
//    @PreAuthorize("#userRepo.name != authentication.name")
    public String deleteUser(@PathVariable Integer id,RedirectAttributes redirectAttributes) {
        try {
            userRepo.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "کاربر مورد نظر در یک آفیش حضور دارد .");
            return "redirect:/members";
        }
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        redirectAttributes.addFlashAttribute("message", "کاربر مورد نظر با موفیت حذف گردید .");
        return "redirect:/members";
    }

    @PostMapping("/admin/profile/user")
    public String editProfile(@ModelAttribute @Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        User userE = userRepo.findByPersonalId(user.getPersonalId());

        try {
            if (bindingResult.hasErrors()) {
                System.out.println("********************** + "+bindingResult.getAllErrors());
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/admin/profile";
            } else {
//            if (userRepo.findByPersonalId(user.getPersonalId()) != null) {
//                bindingResult.rejectValue("personalId", "error.user",
//                        "هم اکنون کاربری با این شماره کارمندی موجود است");
//            }
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYyyy : "+user);
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYyyy : "+user.getFName());
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYyyy : "+user.getLname());
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYyyy : "+user.getPass());

                userE.setFName(user.getFName());
                userE.setLname(user.getLname());
                userE.setPass(user.getPass());

                System.out.println("create user form else");

                userService.save(userE);
            }
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفقیت انجام گردید.");
            return "redirect:/admin/profile";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
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

    @ResponseBody
    @PostMapping("/saveEmployee")
    public String saveEmployee(employee d) {
        String res = "";
        List<employee> emo = employeeRepo.findByBarcode(d.getBarcode());
        List<employee> memp = employeeRepo.findByMac(d.getMac_address());
        List<employee> imei = employeeRepo.findByImei(d.getImei());
        if (!emo.isEmpty()) {
            res = "این کاربر قبلا ثبت نام کرده است";
        } else if (!memp.isEmpty()) {
            res = "این مک آدرس در سیستم موجود است";
        } else if (!imei.isEmpty()) {
            res = "این دستگاه قبلا ثبت نام کرده است";
        } else {
            employeeRepo.save(d);
            res = "با موفقیت ثبت شد";
        }
        return res;
    }

    @ResponseBody
    @GetMapping("/saat/{pid}/{mac}/{q}")
    public String findeUser(@PathVariable String pid, @PathVariable String mac,
                            @PathVariable String q) {

        String exp = "";
        if (q == null) {
            exp = "لطفا بارکد را اسکن کنید";
            return exp;
        } else if (q.contentEquals("wer123as2wsx")) {
            System.out.println("ddddd" + pid + "   " + mac);
            List<employee> emp = employeeRepo.findByImei(pid);
            System.out.println(emp);
            employee us = new employee();


            if (emp.isEmpty()) { //if Imei not exist in database
                List<employee> me = employeeRepo.findByMac(mac);
                if (me.isEmpty()) { // and mac address not exist too
                    exp = "این دستگاه در سیستم ثبت نشده است";
                    return exp;
                } else {

                    us = me.get(0);
                }
            } else {

                us = emp.get(0);
                System.out.println(pid);
            }

            List<time> timeOptional = timeRepo.findByBarcode(us.getBarcode());

            System.out.println("mac1" + mac);

            Integer hour = LocalTime.now().getHour();
            Integer minute = LocalTime.now().getMinute();
            Integer now = hour * 60 + minute;

            Roozh jCal = new Roozh();
            int myear = LocalDate.now().getYear();
            int mmonth = LocalDate.now().getMonthValue();
            int mday = LocalDate.now().getDayOfMonth();
            jCal.gregorianToPersian(myear, mmonth, mday);


            int y = jCal.getYear();
            int m = jCal.getMonth();
            int d = jCal.getDay();

            String dnow = y + "/" + m + "/" + d;
            System.out.println(now);

            System.out.println(timeOptional);

            if (timeOptional.isEmpty()) {
                time Tm = new time();
                Tm.setBarcode(us.getBarcode());

                Tm.setBrd_barcode(1);

                Tm.setTime(now);
                Tm.setDate(dnow);
                timeRepo.save(Tm);

                exp = us.getFirst_name() + " " + us.getLast_name();
            } else {
                time t = timeOptional.get(timeOptional.size() - 1);

                if (now - t.getTime() < 5 && dnow.contentEquals(t.getDate())) {
                    System.out.println(now - t.getTime());
                    exp = us.getFirst_name() + " " + us.getLast_name() + " لطفا 5 دقیقه صبر کنید";
                } else {
                    time Tm = new time();
                    Tm.setBarcode(us.getBarcode());

                    Tm.setBrd_barcode(1);

                    Tm.setTime(now);
                    Tm.setDate(dnow);
                    timeRepo.save(Tm);
                    System.out.println(dnow);
                    exp = us.getFirst_name() + " " + us.getLast_name();
                }
            }


            return exp;
        } else {
            exp = "بارکد تایید نشد";
            return exp;
        }

    }

    @GetMapping("/deleteEmployee")
    @ResponseBody
    public String deleteEmployee(Integer id) {
        employeeRepo.deleteById(id);
        return "Deleted Successfully";
    }

    @GetMapping("/findOneEmployee")
    @ResponseBody
    public Optional<employee> findOneEmployee(Integer id) {
        return employeeRepo.findById(id);
    }



}


