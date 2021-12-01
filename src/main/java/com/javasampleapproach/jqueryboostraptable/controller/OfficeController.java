package com.javasampleapproach.jqueryboostraptable.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


import com.itextpdf.text.DocumentException;
import com.javasampleapproach.jqueryboostraptable.Service.Impl.*;
import com.javasampleapproach.jqueryboostraptable.Service.OfficePdfGenerator;
import com.javasampleapproach.jqueryboostraptable.enums.Authority;
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
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.javasampleapproach.jqueryboostraptable.repository.OfficeFormRepository;
import com.javasampleapproach.jqueryboostraptable.repository.Roozh;
import com.javasampleapproach.jqueryboostraptable.repository.TajhizatRepository;
import com.javasampleapproach.jqueryboostraptable.repository.UserRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Service
@Controller
public class OfficeController {

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

    @Autowired
    private ProgramServiceImp programServiceImp;

    @Autowired
    private CategoryServiceImp categoryServiceImp;


    @GetMapping("/tajhizats")
    public String viewTajhizat(Model model, @RequestParam(defaultValue = "0") int page) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        model.addAttribute("userName", "Welcome " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        model.addAttribute("tajhizats", tRepo.findAll(new PageRequest(page, 10)));
        model.addAttribute("locations", locationServiceImp.getAllLocations());
        model.addAttribute("brands", brandServiceImp.getAllBrands());
        model.addAttribute("currentPage", page);

        return "admin/tajhizats/index";
    }

    @GetMapping("/result")
    public ResponseEntity<?> viewTajhizat() {
        return (ResponseEntity<?>) locationServiceImp.getAllLocations();
    }

    @GetMapping(value = "/admin/officeform/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            formRepo.deleteById(id);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "آفیش مود نظر با موفقیت حذف گردید.");
            return "redirect:/office";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "خطایی رخ داده لطفا بعدا تلاش نمایید.");
            return "redirect:/office";
        }
    }

    @GetMapping("/office")
    public String viewoffice(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        List<User> us = new ArrayList<>();
        List<officeForm> office_form = new ArrayList<>();

        us.add(userRepo.findByPersonalId(user.getPersonalId()));

        for (officeForm oo : formRepo.findByStatusIsFalse()) {
            if (userHasAuthority("OP_TAHIEKONANDEH")) {
                office_form.add(oo);
//                    model.addAttribute("forms", oo);
            } else if (oo.getTahayeemza() != null && userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
                office_form.add(oo);
            } else if (oo.getMdarkhastemza() != null && userHasAuthority("OP_MODIR_POSHTIBANIT")) {
                office_form.add(oo);
            } else if (oo.getPoshemza() != null && userHasAuthority("OP_ANBARDAR")) {
                office_form.add(oo);
            } else if (oo.getAnbaremza() != null && oo.getPoshemza() != null && userHasAuthority("OP_HAML_NAGHL")) {
                office_form.add(oo);
            } else if (oo.getHamlonaghlemza() != null && userHasAuthority("OP_HERASAT")) {
                office_form.add(oo);
            } else if (oo.getPoshemza() != null && userHasAuthority("OP_SEDABARDAR")) {
                office_form.add(oo);
            } else if (oo.getPoshemza() != null && userHasAuthority("OP_TASVIRBARDAR_1")) {
                office_form.add(oo);
            } else if (userHasAuthority("OP_HAMAHANGIE")) {
                office_form.add(oo);
            }
        }
        model.addAttribute("forms", office_form);

        model.addAttribute("user", user);

        if (userHasAuthority("OP_HAMAHANGIE") || userHasAuthority("OP_TAHIEKONANDEH")) {
            model.addAttribute("tahie", user.getFullname());
        }
        model.addAttribute("officeTypes", OfficeForm.values());
        model.addAttribute("programs", programServiceImp.getAllPrograms());
        model.addAttribute("rozhaehafte", RozHafteh.values());
        model.addAttribute("locations", locationServiceImp.getAllLocations());
        return "admin/office/index";
    }

    @GetMapping("/officeTrue")
    public String viewofficeTrue(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        List<User> us = new ArrayList<>();
        List<officeForm> office_form = new ArrayList<>();

        us.add(userRepo.findByPersonalId(user.getPersonalId()));

        for (officeForm oo : formRepo.findByStatusIsTrue()) {
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$1");
            if (userHasAuthority("OP_TAHIEKONANDEH")) {
                office_form.add(oo);
//                    model.addAttribute("forms", oo);
            } else if (oo.getTahayeemza() != null && userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
                office_form.add(oo);
            } else if (oo.getMdarkhastemza() != null && userHasAuthority("OP_MODIR_POSHTIBANIT")) {
                office_form.add(oo);
            } else if (oo.getPoshemza() != null && userHasAuthority("OP_ANBARDAR")) {
                office_form.add(oo);
            } else if (oo.getAnbaremza() != null && oo.getPoshemza() != null && userHasAuthority("OP_HAML_NAGHL")) {
                office_form.add(oo);
            } else if (oo.getHamlonaghlemza() != null && userHasAuthority("OP_HERASAT")) {
                office_form.add(oo);
            } else if (oo.getPoshemza() != null && userHasAuthority("OP_SEDABARDAR")) {
                office_form.add(oo);
            } else if (oo.getPoshemza() != null && userHasAuthority("OP_TASVIRBARDAR_1")) {
                office_form.add(oo);
            } else if (userHasAuthority("OP_HAMAHANGIE")) {
                office_form.add(oo);
            }
        }
        model.addAttribute("forms", office_form);

        model.addAttribute("user", user);

        if (userHasAuthority("OP_HAMAHANGIE") || userHasAuthority("OP_TAHIEKONANDEH")) {
            model.addAttribute("tahie", user.getFullname());
        }
        model.addAttribute("officeTypes", OfficeForm.values());
        model.addAttribute("programs", programServiceImp.getAllPrograms());
        model.addAttribute("rozhaehafte", RozHafteh.values());
        model.addAttribute("locations", locationServiceImp.getAllLocations());
//        model.addAttribute("users", userRepo.findAll());
//        model.addAttribute("jobs", jobServiceImp.getAllJobs());
//        model.addAttribute("tajhizats", tRepo.findAll());
//        model.addAttribute("userName", "Welcome " + user.getFName() + " " + user.getLname() + " (" + user.getPersonalId() + ")");
        return "admin/office/index";
    }

    @GetMapping("/form")
    public String viewform(Model model, int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
//        System.out.println("authority : " +user.getAuthorities().contains(Authority.OP_HAML_NAGHL));
//        System.out.println("user Authority : "+userAuthority("OP_HAML_NAGHL"));
        Set<User> usersWithJob = findU(user.getJob().getId());
        model.addAttribute("userJob", usersWithJob);
        model.addAttribute("form", formRepo.findById((long) id).get());
        model.addAttribute("userss", userRepo.findAll());
        model.addAttribute("enumField", OfficeForm.OFFICE_FORM_BARNAME_TOLIDIE_KHABARIE);
        model.addAttribute("user", user);
        model.addAttribute("cars", carServiceImp.getAllCars());
        model.addAttribute("tajhizats", tRepo.findAll());
        model.addAttribute("categories", categoryServiceImp.getAllCategories());
        return "admin/office/single_office";
    }

    public String userAuthority(String ahh) {
        for (GrantedAuthority grantedAuthority : Authority.values()) {
            if (ahh.equals(grantedAuthority.getAuthority())) {
                return "ai :" + grantedAuthority.getAuthority();
            }
        }
        return "s";
    }

    @GetMapping("/panel")
    public String home() {
        return "admin/panel/index";
    }

    @GetMapping("/")
    public String panel() {
        return "redirect:/panel";
    }

    @GetMapping("/findOneTajhiz")
    @ResponseBody
    public Optional<Tajhizat> findOneTajhiz(long id) {
        return tRepo.findById(id);
    }

    @GetMapping("/admin/tajhizats/find/{id}")
    @ResponseBody
    public Optional<Tajhizat> fiOptionalTajhizat(@PathVariable long id) {
        System.out.println("***************** + " + id);
        return tRepo.findById(id);
    }

    @GetMapping("/admin/tajhizats/delete/{id}")
    public String deleteTajhiz(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            tRepo.deleteById(id);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "تجهیز مود نظر با موفقیت حذف گردید.");
            return "redirect:/tajhizats";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تجهیز مود نظر به آفیش خاصی تعلق دارد است.");
            return "redirect:/tajhizats";
        }
    }

    @PostMapping("/admin/officeform/delete-user")
    public String deleteUserOfficeForm(@ModelAttribute officeForm f, RedirectAttributes redirectAttributes) {
        long id_form = f.getId();
        try {
            officeForm office_form = formRepo.findById(f.getId()).get();
            Iterator<User> itr = f.getUsers().iterator();
            while (itr.hasNext()) {
                if (office_form.getUsers().contains(itr.next())) {
                    for (User us : f.getUsers()) {
//                        if (f.getTahayekonande().equals(us)){
//                            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
//                            redirectAttributes.addFlashAttribute("message", "حذف کاربر تهیه کننده امکان پذیر نیست.");
//                            return "redirect:/form/?id=" + id_form;
//                        }
                        office_form.getUsers().remove(us);
                    }
                }
            }
            formRepo.save(office_form);

            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "کاربر مورد نظر از آفیش  حذف گردید.");
            return "redirect:/form/?id=" + id_form;

        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "خطایی در سرور به وجود آمده مجددا تلاش نمایید.");
            return "redirect:/form/?id=" + id_form;
        }
    }

    @PostMapping("/admin/tajhizats/create")
    public String Esave(@RequestParam MultipartFile file, @ModelAttribute @Valid Tajhizat t, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
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
            if (bindingResult.hasErrors()) {
                System.out.println("############################ : " + bindingResult.getAllErrors());
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/tajhizats";
            }
            tRepo.save(t);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفیت انجام گردید.");
            return "redirect:/tajhizats";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/tajhizats";
        }

    }

    @PostMapping("/saveForm")
    public String saveForm(@ModelAttribute @Valid officeForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {

            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/office";
            }

            Roozh jCal = new Roozh();
            int myear = LocalDate.now().getYear();
            int mmonth = LocalDate.now().getMonthValue();
            int mday = LocalDate.now().getDayOfMonth();
            jCal.gregorianToPersian(myear, mmonth, mday);

            form.setTarikhsodur(jCal.toString());
            form.setStatus(false);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            List<User> u = new ArrayList<>();
            u.add(userRepo.findByPersonalId(auth.getName()));

            if (form.getUsers() == null) {
                form.setUsers(u);
                System.out.println("foorm if is empty");
                // form.setUsers(new HashSet<>(userRepo.findBypersonalId(u.getPersonalId())));
            } else {
                form.getUsers().add(u.get(0));
                System.out.println("foorm add user else ");
            }
            formRepo.save(form);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عملیات با موفیت انجام گردید.");
            return "redirect:/office";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/office";
        }


    }

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

        if (office_form.getType() == OfficeForm.OFFICE_FORM_ESTEDIO_SIMA) {
            if (userHasAuthority("OP_TAHIEKONANDEH") && office_form.getTahayeemza() == null) {
                office_form.setTahayeemza(u.getEmza());
            } else if (userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH") && office_form.getMdarkhastemza() == null) {
                office_form.setMdarkhastemza(u.getEmza());
            } else if (userHasAuthority("OP_MODIR_POSHTIBANIT") && office_form.getPoshemza() == null) {
                office_form.setPoshemza(u.getEmza());
                formRepo.setStatusForOfficeForm(true, office_form.getId());
                System.out.println("status ok ");
            } else {
                System.out.println("----------------emzaa nashod 1-------------------------");
            }
        } else if (office_form.getType() == OfficeForm.OFFICE_FORM_BARNAME_TOLIDIE_KHABARIE) {
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
            } else if (userHasAuthority("OP_HAMAHANGIE") && office_form.getTahayeemza() == null) {
                office_form.setTahayeemza(u.getEmza());
            } else if (userHasAuthority("OP_HAML_NAGHL") && office_form.getHamlonaghlemza() == null) {
                User us = userRepo.findById(fj.getTid()).get();
                office_form.setRanande(us.getFullname());
                office_form.setRanandeid(us.getPersonalId());
                Car car = carServiceImp.findById(fj.getCar()).get();
                car.setOfficeForm(office_form);
                office_form.setHamlonaghlemza(u.getEmza());
            } else if (userHasAuthority("OP_HERASAT") && office_form.getVherasatemza() == null) {
                LocalTime time = LocalTime.now();
                String h = time.getHour() + " : " + time.getMinute();
                if (fj.getTid().equals(1)) {
                    office_form.setSaatvorod(h);
                    office_form.setVherasatemza(u.getEmza());
                    formRepo.setStatusForOfficeForm(true, office_form.getId());
                } else {
                    office_form.setSaatkhoroj(h);
                    office_form.setKhherasatemza(u.getEmza());
                    System.out.println("----------------tayyyyyied shod-------------------------");
                }
            } else {
                System.out.println("----------------emzaa nashod 1-------------------------");
            }
        }

        formRepo.save(office_form);

        return redirect;
    }

    @PostMapping("/addForm")
    public String addutoform(@ModelAttribute officeForm f, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        System.out.println("TTTTTDDDDDDDDDDDDDDDDDDDDDDD : " + f.getTajhizatss());
        officeForm form = formRepo.findById(f.getId()).get();
        long id_form = f.getId();
        boolean found = false;
        if (f.getUsers() != null) {
            Iterator<User> itr = f.getUsers().iterator();
            while (itr.hasNext()) {
                if (form.getUsers().contains(itr.next())) {
                    found = true;
                }
            }
            if (found) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", "احتمال وجود عوامل تکراری وجود دارد یا مشکلی سمت سرور به وجود آمده مجددا تلاش نمایید.");
                return "redirect:/form/?id=" + id_form;
            }
            for (User us : f.getUsers()) {
                form.getUsers().add(us);
            }
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "عوامل با موفیت به آفیش اضافه گردید.");
        }
        if (f.getTajhizatss() != null) {
            form.setTajhizatss(f.getTajhizatss());
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "تجهیزات مورد نظر با موقیت در آفیش ثبت شد.");
        }
        formRepo.save(form);
        System.out.println("form save ");
        return "redirect:/form/?id=" + id_form;

    }


    @PostMapping("/addTajhizToUser")
    public String addTajhizToUserOfficeForm(@ModelAttribute officeForm f, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTT : " + f.getUsers());
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTT : " + f.getTajhizatss());
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTT : " + f.getId());
        long id_form = f.getId();
        try {
            officeForm office_form = formRepo.findById(f.getId()).get();

            Iterator<User> itr = f.getUsers().iterator();

            while (itr.hasNext()) {
                if (office_form.getUsers().contains(itr.next())) {
                    for (User us : f.getUsers()) {
                        for (Tajhizat tajhizat : f.getTajhizatss()) {
                            us.setTajhiz((Set<Tajhizat>) tajhizat);
                        }
//                        if (f.getTahayekonande().equals(us)){
//                            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
//                            redirectAttributes.addFlashAttribute("message", "حذف کاربر تهیه کننده امکان پذیر نیست.");
//                            return "redirect:/form/?id=" + id_form;
//                        }
//                        office_form.getUsers().remove(us);
                    }
                }
            }
            formRepo.save(office_form);

            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "تجهیز مورد نظر به کاربر مورد نظر اختصاص یافت .");
            return "redirect:/form/?id=" + id_form;

        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "خطایی در سرور به وجود آمده مجددا تلاش نمایید.");
            return "redirect:/form/?id=" + id_form;
        }
    }


    @GetMapping("/admin/office/find/{id}")
    @ResponseBody
    public Optional<officeForm> fiOptionalLocation(@PathVariable long id) {
//        System.out.println("***************** office form + " + id);
        return formRepo.findById(id);
    }

    @GetMapping("/findbyjob/{job}")
    @ResponseBody
    public Set<User> findU(@PathVariable long job) {
        Job job1 = jobServiceImp.findById(job);
//        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa s: " + job1.getUsers());
        return job1.getUsers();
    }

    @GetMapping("/findbycategory/{category}")
    @ResponseBody
    public Set<Job> findJob(@PathVariable long category) {
        Optional<Category> category1 = categoryServiceImp.findByIdCategory(category);
//        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa : " + category1.get().getJobs());
        return category1.get().getJobs();
    }

    @GetMapping("/office/exports/{id}")
    public void exportToPdf(HttpServletResponse response, @PathVariable long id) throws DocumentException, IOException {

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=offices_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Optional<officeForm> officeForms = formRepo.findById(id);
        //System.out.println("*********************************** : "+officeForms.get());
        System.out.println("*********************************** : " + officeForms.get().getProgram().getName());
        OfficePdfGenerator generator = new OfficePdfGenerator(officeForms.get());
        generator.export(response);

    }
}
