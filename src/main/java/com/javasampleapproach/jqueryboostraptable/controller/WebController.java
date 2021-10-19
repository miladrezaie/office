/***
 * @author amir-reza abbasi
 */
package com.javasampleapproach.jqueryboostraptable.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.javasampleapproach.jqueryboostraptable.Service.Impl.JobServiceImp;
import com.javasampleapproach.jqueryboostraptable.Service.Impl.RoleServiceImp;
import com.javasampleapproach.jqueryboostraptable.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String viewMembers(Model model, String keyword, HttpServletRequest request) {
        //data have devices information
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        List<Job> jobs = jobServiceImp.getAllJobs();
        List<Role> roles = roleServiceImp.getAllRoles();
        model.addAttribute("userName", "Welcome " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        if (keyword != null) {
            model.addAttribute("members", userRepo.search(keyword));
        } else {
            model.addAttribute("members", userRepo.findAll());
            model.addAttribute("jobs", jobs);
            model.addAttribute("roles", roles);
        }
        return "membersPage";
    }

    @GetMapping("/Scanner")
    public String qrScan() {
        return "scanner";
    }


    @GetMapping("/access-denied")
    public String access(Model model, @RequestParam(defaultValue = "0") int page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        return "access-denied";
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
    public String saveue(@ModelAttribute User user, @RequestParam("file") MultipartFile file) {
        System.out.println("******************* user : " + user);
        System.out.println("******************* user : " + user.getRoles());

        if (!file.isEmpty()) {
            userService.saveuemza(user, file);
        } else {
            User us = userRepo.findById(user.getId()).get();
            user.setEmza(us.getEmza());
            userService.save(user);
        }

        return "redirect:/members";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        System.out.println("************************* login method start");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        System.out.println("************************* login method end");

        return modelAndView;
    }

    @RequestMapping(value = {"/SetRole"}, method = RequestMethod.GET)
    public ModelAndView SetRole(Integer uid, Integer rid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model, User user) {
        System.out.println("register Form ********************");

        List<Job> jobs = jobServiceImp.getAllJobs();
        model.addAttribute("jobs", jobs);
        model.addAttribute("user", user);
        return "/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    @Transactional
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        try {
            ModelAndView modelAndView = new ModelAndView();
            System.out.println("personal id : " + userRepo.findByPersonalId(user.getPersonalId()));
            System.out.println(user);

            System.out.println("***********************************");
            if (userRepo.findByPersonalId(user.getPersonalId()) != null) {
                bindingResult.rejectValue("personalId", "error.user",
                        "هم اکنون کاربری با این شماره کارمندی موجود است");
            }
            if (bindingResult.hasErrors()) {
                modelAndView.setViewName("registration");
            } else {
                userService.save(user);
            }
            return modelAndView;
        } catch (Exception exception){
            throw exception;
        }

    }

    @GetMapping("/deleteUser")
//    @PreAuthorize("#userRepo.name != authentication.name")
    public String deleteUser(Integer id, Model model) {
        try {
            userRepo.deleteById(id);

        } catch (Exception e) {
            model.addAttribute("messege", "این کاربر در یک آفیش حضور دارد");
            return "errorPage";
        }

        return "redirect:/members";
    }

    @GetMapping("/findOneUser")
    @ResponseBody
    public Optional<User> findOneUser(Integer id) {
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


