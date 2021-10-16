package com.javasampleapproach.jqueryboostraptable.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


import com.javasampleapproach.jqueryboostraptable.Service.Impl.BrandServiceImp;
import com.javasampleapproach.jqueryboostraptable.Service.Impl.CarServiceImp;
import com.javasampleapproach.jqueryboostraptable.Service.Impl.JobServiceImp;
import com.javasampleapproach.jqueryboostraptable.Service.Impl.LocationServiceImp;
import com.javasampleapproach.jqueryboostraptable.enums.OfficeForm;
import com.javasampleapproach.jqueryboostraptable.enums.RozHafteh;
import com.javasampleapproach.jqueryboostraptable.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.javasampleapproach.jqueryboostraptable.repository.OfficeFormRepository;
import com.javasampleapproach.jqueryboostraptable.repository.Roozh;
import com.javasampleapproach.jqueryboostraptable.repository.TajhizatRepository;
import com.javasampleapproach.jqueryboostraptable.repository.UserRepository;

@Service
@Controller
public class OfficeController {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasksLogin.class);

    @Autowired
    private TajhizatRepository tRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private OfficeFormRepository formRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LocationServiceImp locationServiceImp;

    @Autowired
    private JobServiceImp jobServiceImp;

    @Autowired
    private BrandServiceImp brandServiceImp;

    @Autowired
    private CarServiceImp carServiceImp;


    @GetMapping("/tajhizats")
    public String viewTajhizat(Model model, @RequestParam(defaultValue = "0") int page) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        model.addAttribute("userName", "Welcome " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("tajhizats", tRepo.findAll(new PageRequest(page, 10)));
        model.addAttribute("locations", locationServiceImp.getAllLocations());
        model.addAttribute("brands", brandServiceImp.getAllBrands());
        System.out.println("ttttttttttttttttttt" + tRepo.findAll(new PageRequest(page, 10)));
        System.out.println("lllllllllllllllllllll" + locationServiceImp.getAllLocations());

        model.addAttribute("currentPage", page);
        return "Tajhizat";
    }

    @GetMapping("/result")
    public ResponseEntity<?> viewTajhizat() {
        return (ResponseEntity<?>) locationServiceImp.getAllLocations();
    }

    @GetMapping(value = "/admin/officeform/delete/{id}")
    public String delete(@PathVariable Long id) {
        System.out.println("saaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        formRepo.deleteById(id);
        return "redirect:/office";
    }

    @GetMapping("/office")
    public String viewoffice(Model model) {
        log.trace("view from office");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("milad >>>>>>>>>" + auth);
        System.out.println("milad >>>>>>>>>" + auth.getName());
        User user = userService.findByUsername(auth.getName());
        List<User> us = new ArrayList<>();
        List<officeForm> office_form = new ArrayList<>();

        us.add(userRepo.findByPersonalId(user.getPersonalId()));
        if (userHasAuthority("OP_ACCESS_ADMIN_PANEL")) {
            model.addAttribute("forms", formRepo.findAll());
        } else {
            for (officeForm oo : formRepo.findByUsers(us)) {
                if (userHasAuthority("OP_TAHIEKONANDEH")) {
                    office_form.add(oo);
//                    model.addAttribute("forms", oo);
                } else if (oo.getTahayeemza() != null && userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
                    office_form.add(oo);
//                    model.addAttribute("forms", oo);
                } else if (oo.getMdarkhastemza() != null && userHasAuthority("OP_MODIR_POSHTIBANIT")) {
                    office_form.add(oo);
//                    model.addAttribute("forms", oo);
                } else if (oo.getPoshemza() != null && userHasAuthority("OP_ANBARDAR")) {
                    office_form.add(oo);
//                    model.addAttribute("forms", oo);
                } else if (oo.getAnbaremza() != null && oo.getPoshemza() != null && userHasAuthority("OP_HAML_NAGHL")) {
                    office_form.add(oo);
//                    model.addAttribute("forms", oo);
                } else if (oo.getHamlonaghlemza() != null && userHasAuthority("OP_HERASAT")) {
                    office_form.add(oo);
//                    model.addAttribute("forms", oo);
                } else if (oo.getPoshemza() != null && userHasAuthority("OP_SEDABARDAR")) {
                    office_form.add(oo);
//                    model.addAttribute("forms", oo);
                } else if (oo.getPoshemza() != null && userHasAuthority("OP_TASVIRBARDAR_1")) {
                    office_form.add(oo);
//                    model.addAttribute("forms", oo);
                }else if (userHasAuthority("OP_HAMAHANGIE")){
                    office_form.add(oo);
//                    model.addAttribute("forms", oo);
                }
            }
            model.addAttribute("forms", office_form);
            System.out.println("form************ : " + formRepo.findByUsers(us));
//            System.out.println("form************ : "+formRepo.);
        }

        model.addAttribute("user", user);
        if (userHasAuthority("OP_HAMAHANGIE") || userHasAuthority("OP_TAHIEKONANDEH")) {
            model.addAttribute("tahie", user.getFullname());
        }
        model.addAttribute("officeTypes", OfficeForm.values());
        model.addAttribute("rozhaehafte", RozHafteh.values());
        model.addAttribute("locations", locationServiceImp.getAllLocations());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("jobs", jobServiceImp.getAllJobs());
        model.addAttribute("tajhiz", tRepo.findAll());
        model.addAttribute("userName", "Welcome " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        return "officeForm";
    }

    @GetMapping("/form")
    public String viewform(Model model, int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("form", formRepo.findById((long) id).get());
        model.addAttribute("userName", "Welcome " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("userss", userRepo.findAll());
        model.addAttribute("jobs", jobServiceImp.getAllJobs());
        model.addAttribute("user", user);
        model.addAttribute("cars", carServiceImp.getAllCars());
        return "office";
    }

    @GetMapping("/")
    public String h() {
        return "redirect:/office";
    }

    @GetMapping("/findOneTajhiz")
    @ResponseBody
    public Optional<Tajhizat> findOneTajhiz(Integer id) {
        return tRepo.findById(id);
    }

    @GetMapping("/deleteTajhiz")
    public String deleteTajhiz(Integer id) {
        tRepo.deleteById(id);
        return "redirect:/tajhizats";
    }

    @PostMapping("/saveTajhiz")
    public String Esave(Tajhizat t, MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("not a valid file");
        }
        try {
            t.setImg(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException er) {
            System.out.println("file ");
            er.printStackTrace();
        }
        tRepo.save(t);
        return "redirect:/tajhizats";
    }

    //	 @PostMapping("/addToForm")
//	 public String AddToForm(String pid) {
//		 User user = userRepo.findBypersonalId(pid).get(0);
//		 officeForm form = new officeForm();
//		 form.getUsers().add(user);

    //	 }

    @PostMapping("/saveForm")
    public String saveForm(officeForm form) {

        Roozh jCal = new Roozh();
        int myear = LocalDate.now().getYear();
        int mmonth = LocalDate.now().getMonthValue();
        int mday = LocalDate.now().getDayOfMonth();
        jCal.gregorianToPersian(myear, mmonth, mday);

        form.setTarikhsodur(jCal.toString());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<User> u = new ArrayList<>();
        u.add(userRepo.findByPersonalId(auth.getName()));

        System.out.println("nameeeeeeeeeeeeeeeeeeee" + auth.getName());
        System.out.println("-------------------->" + form.toString());
        System.out.println("---------formgetuser----------->" + (form.getUsers() == null));
        if (form.getUsers() == null) {
            form.setUsers(u);
            System.out.println("foorm if is empty");
            // form.setUsers(new HashSet<>(userRepo.findBypersonalId(u.getPersonalId())));
        } else {
            form.getUsers().add(u.get(0));
            System.out.println("foorm add user else ");
        }
        System.out.println("foorrrmmm" + form);
        System.out.println("ussserrrrr" + u);

        System.out.println("user saved");

        formRepo.save(form);
        System.out.println("form saved");
        return "redirect:/office";

    }

//    public  User findUserByAuthority(String authority) {
//        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        System.out.println("authority : " + authorities);
//        for (GrantedAuthority grantedAuthority : authorities) {
//            if (authority.equals(grantedAuthority.getAuthority())) {
//                return
//            }
//        }
//
//
//    }

    public static boolean userHasAuthority(String authority) {
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        System.out.println("authority : " + authorities);
        for (GrantedAuthority grantedAuthority : authorities) {
            if (authority.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/saveEmza")
    public String saveEmza(FormJob fj) {
        String redirect = "redirect:/form/?id=" + fj.getFid();
        officeForm office_form = formRepo.findById(fj.getFid()).get();
        User u = userRepo.findById(fj.getUid()).get();
        System.out.println("userrr ---" + u.getJob() + "    " + u.getPersonalId());
        if (userHasAuthority("OP_MODIR_POSHTIBANIT") && office_form.getPoshemza() == null) {
            office_form.setPoshemza(u.getEmza());
        } else if (userHasAuthority("OP_SEDABARDAR") && office_form.getSedaemza() == null) {
            office_form.setSedaemza(u.getEmza());
        } else if (userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH") && office_form.getMdarkhastemza() == null) {
            office_form.setMdarkhastemza(u.getEmza());
        } else if (userHasAuthority("OP_TAHIEKONANDEH") && office_form.getTahayeemza() == null) {
            office_form.setTahayeemza(u.getEmza());
        } else if (userHasAuthority("OP_TASVIRBARDAR_1") && office_form.getTasviremza() == null) {
            office_form.setTasviremza(u.getEmza());
        } else if (userHasAuthority("OP_TASVIRBARDAR_2") && office_form.getTasviremza2() == null) {
            office_form.setTasviremza2(u.getEmza());
        } else if (userHasAuthority("OP_TASVIRBARDAR_3") && office_form.getTasviremza3() == null) {
            office_form.setTasviremza3(u.getEmza());
        } else if (userHasAuthority("OP_TASVIRBARDAR_4") && office_form.getTasviremza4() == null) {
            office_form.setTasviremza4(u.getEmza());
        } else if (userHasAuthority("OP_ANBARDAR") && office_form.getAnbaremza() == null) {
//            office_form.seta(u.getEmza());
            office_form.setAnbaremza(u.getEmza());
        }else if (userHasAuthority("OP_HAMAHANGIE") && office_form.getTahayeemza() == null) {
            office_form.setTahayeemza(u.getEmza());
        }
//        else if (userHasAuthority("OP_ANBARDAR")&& office_form.get() == null) {
//            office_form.set(u.getEmza());
//        }
        else if (userHasAuthority("OP_HAML_NAGHL") && office_form.getHamlonaghlemza() == null) {
            User us = userRepo.findById(fj.getTid()).get();
            office_form.setRanande(us.getFullname());
            office_form.setRanandeid(us.getPersonalId());
            Car car = carServiceImp.findById(fj.getCar()).get();
            office_form.getCars().add(car);
            office_form.setHamlonaghlemza(u.getEmza());
        } else if (userHasAuthority("OP_HERASAT") && office_form.getVherasatemza() == null) {
            LocalTime time = LocalTime.now();
            String h = time.getHour() + " : " + time.getMinute();
            if (fj.getTid().equals(1)) {
                office_form.setSaatvorod(h);
                office_form.setVherasatemza(u.getEmza());
            } else {
                office_form.setSaatkhoroj(h);
                office_form.setKhherasatemza(u.getEmza());
            }
        } else {
            System.out.println("----------------emzaa nashod 1-------------------------");
        }
//        switch (u.getEmza()) {
//            case "حراست":
//                LocalTime time = LocalTime.now();
//                String h = time.getHour() + " : " + time.getMinute();
//                if (fj.getTid().equals(1)) {
//                    office_form.setSaatvorod(h);
//                    office_form.setVherasatemza(u.getEmza());
//                } else {
//                    office_form.setSaatkhoroj(h);
//                    office_form.setKhherasatemza(u.getEmza());
//                }
//                break;
//            case "معاونت سیما":
//                System.out.println("moaven------majazi---beforset");
//                User ux = userService.findByUsername("999999");
//                System.out.println("---------->" + ux);
//                if (!office_form.getUsers().contains(ux))
//                    office_form.getUsers().add(ux);
//                office_form.setMdarkhastemza(u.getEmza());
//                System.out.println("moaven------majazi---afterset");
//                // office_form.getUsers().add(userRepo.findByJob("مدیر پشتیبانی فنی").get(0));
//                break;
//            case "مسئول حمل و نقل":
//                User us = userRepo.findById(fj.getTid()).get();
//                office_form.setRanande(us.getFullname());
//                office_form.setRanandeid(us.getPersonalId());
//                office_form.setKhodro(fj.getJob());
//                office_form.setHamlonaghlemza(u.getEmza());
//                break;
//            case "مدیر پشتیبانی فنی":
//                office_form.setPoshemza(u.getEmza());
//
////                office_form.getUsers().add(userRepo.findByJob("هماهنگی سیما").get(0));
////                System.out.println("after add hamahangi");
////                office_form.getUsers().add(userRepo.findByJob("مسئول حمل و نقل").get(0));
////                System.out.println("after add hamlonaghl");
////                office_form.getUsers().add(userRepo.findByJob("حراست").get(0));
////                System.out.println("after add herasat");
////                office_form.getUsers().add(userRepo.findByJob("انباردار").get(0));
//                break;
//            case "هماهنگی سیما":
////			 office_form.getUsers().add(userRepo.findByJob("مسئول حمل و نقل").get(0));
////			 office_form.getUsers().add(userRepo.findByJob("حراست").get(0));
//                break;
//            case "تهیه کننده":
//                office_form.setTahayeemza(u.getEmza());
//                User user = userService.findByUsername("11000010");
//                if (!office_form.getUsers().contains(user))
//                    office_form.getUsers().add(user);
//                break;
//            case "تصویربردار":
//                if (office_form.getTasviremza() == null)
//                    office_form.setTasviremza(u.getEmza());
//                else if (office_form.getTasviremza2() == null)
//                    office_form.setTasviremza2(u.getEmza());
//                else if (office_form.getTasviremza3() == null)
//                    office_form.setTasviremza3(u.getEmza());
//                else if (office_form.getTasviremza4() == null)
//                    office_form.setTasviremza4(u.getEmza());
//                else if (office_form.getTasviremza5() == null)
//                    office_form.setTasviremza5(u.getEmza());
//                else
//                    office_form.setTasviremza6(u.getEmza());
//                break;
//            case "صدابردار":
//                office_form.setSedaemza(u.getEmza());
//                break;
//
//            default:
//                System.out.println("----------------emzaa nashod -------------------------");
//        }
        formRepo.save(office_form);
        return redirect;
    }

    @PostMapping("/addForm")
    public String addutoform(officeForm f) {
        System.out.println("ffffffffffffffffffffff : " + f.getUsers());
        System.out.println("ffffffffffffffffffffff : " + f.getNameBarname());
        System.out.println("ffffffffffffffffffffff : " + f.getId());
        officeForm form = formRepo.findById((long) f.getId()).get();

        boolean found = false;
        if (f.getUsers() != null) {
            Iterator<User> itr = f.getUsers().iterator();
            while (itr.hasNext()) {
                if (form.getUsers().contains(itr.next())) {
                    found = true;
                }
            }
            if (found) {
                return "redirect:/office";
            }
            for (User us : f.getUsers()) {
                form.getUsers().add(us);
            }
        }

        if (f.getTajhizats() != null) {
            Iterator<Tajhizat> itr = f.getTajhizats().iterator();
            while (itr.hasNext()) {
                if (form.getTajhizats().contains(itr.next())) {
                    found = true;
                }
            }
            if (found) {
                return "redirect:/office";
            }
            for (Tajhizat tj : f.getTajhizats()) {
                form.getTajhizats().add(tj);
            }
        }
        formRepo.save(form);
        return "redirect:/office";
    }

    @GetMapping("/findbyjob/{job}")
    @ResponseBody
    public Set<User> findU(@PathVariable long job) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("user : " + auth.getName());
        System.out.println("user : " + job);
        System.out.println("*****************user : " + auth.getDetails());
        Job job1 = jobServiceImp.findById(job);
        System.out.println("***************** users &&&&&&&&&&&&& : " + job1.getUsers());

        return job1.getUsers();
    }

}
