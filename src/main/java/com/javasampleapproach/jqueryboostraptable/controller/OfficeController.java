package com.javasampleapproach.jqueryboostraptable.controller;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.time.format.DateTimeFormatter;
import java.util.*;


import com.github.mfathi91.time.PersianDate;
import com.github.mfathi91.time.PersianDateTime;
import com.itextpdf.text.DocumentException;
import com.javasampleapproach.jqueryboostraptable.Service.Impl.*;

import com.javasampleapproach.jqueryboostraptable.Service.OfficePdfGenerator;

import com.javasampleapproach.jqueryboostraptable.enums.OfficeForm;
import com.javasampleapproach.jqueryboostraptable.enums.RozHafteh;
import com.javasampleapproach.jqueryboostraptable.model.*;
import com.javasampleapproach.jqueryboostraptable.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import org.springframework.util.StringUtils;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

    @Autowired
    private OfficeFormUserTajhizatRepository officeFormUserTajhizatRepository;

    @Autowired
    private OfficeFormUserTajhizatServiceImp officeFormUserTajhizatw;

    @Autowired
    private UserCancelOfficeDescriptionImp userCancelOfficeDescriptionImp;

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
//            carServiceImp.findById()
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
    public String viewoffice(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        List<officeForm> office_form = new ArrayList<>();

        Page<officeForm> ll = formRepo.findByStatusIsFalse(pageable);

        for (officeForm oo : ll.getContent()) {
            if (oo.getType() == OfficeForm.OFFICE_FORM_ESTEDIO_SIMA) {
                for (User userof : oo.getUsers()) {
                    if (userof.getId() == user.getId()) {
                        office_form.add(oo);
                    }
                }
//                if (userHasAuthority("OP_TAHIEKONANDEH") || userHasAuthority("OP_HAMAHANGIE")) {
//                    office_form.add(oo);
//                } else
                if (oo.getTahayeemza() != null && userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
                    office_form.add(oo);
                } else if (oo.getMdarkhastemza() != null && userHasAuthority("OP_MODIR_POSHTIBANIT")) {
                    office_form.add(oo);
                }
            } else if (oo.getType() == OfficeForm.OFFICE_FORM_BARNAME_TOLIDIE_KHABARIE) {
                for (User userof : oo.getUsers()) {
                    if (userof.getId() == user.getId()) {
                        office_form.add(oo);
                    }
                }
                if (oo.getTahayeemza() != null && userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
                    office_form.add(oo);
                } else if (oo.getMdarkhastemza() != null && userHasAuthority("OP_MODIR_POSHTIBANIT")) {
                    office_form.add(oo);
                } else if (oo.getPoshemza() != null && userHasAuthority("OP_ANBARDAR")) {
                    office_form.add(oo);
                } else if (oo.getPoshemza() != null && userHasAuthority("OP_HAML_NAGHL")) {
                    office_form.add(oo);
                } else if (oo.getHamlonaghlemza() != null && userHasAuthority("OP_HERASAT")) {
                    office_form.add(oo);
                } else if (oo.getPoshemza() != null && userHasAuthority("OP_SEDABARDAR")) {
                    office_form.add(oo);
                }
//                else if (oo.getPoshemza() != null && userHasAuthority("OP_TASVIRBARDAR_1")) {
//                    office_form.add(oo);
//                }
            } else if (oo.getType() == OfficeForm.OFFICE_FORM_VAHED_SAIAR) {
                for (User userof : oo.getUsers()) {
                    if (userof.getId() == user.getId()) {
                        office_form.add(oo);
                    }
                }
//                if (userHasAuthority("OP_TAHIEKONANDEH") || userHasAuthority("OP_HAMAHANGIE")) {
//                    office_form.add(oo);
//                } else
                if (oo.getTahayeemza() != null && userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
                    office_form.add(oo);
                } else if (oo.getMdarkhastemza() != null && userHasAuthority("OP_MODIR_POSHTIBANIT")) {
                    office_form.add(oo);
                } else if (oo.getPoshemza() != null && userHasAuthority("OP_UP_LINK")) {
                    office_form.add(oo);
                } else if (oo.getUplinkemza() != null && userHasAuthority("OP_TASISAT")) {
                    office_form.add(oo);
                } else if (oo.getTasisatemza() != null && userHasAuthority("OP_HAML_NAGHL")) {
                    office_form.add(oo);
                } else if (oo.getHamlonaghlemza() != null && userHasAuthority("OP_HERASAT")) {
                    office_form.add(oo);
                }
//                else if (oo.getPoshemza() != null && userHasAuthority("OP_SEDABARDAR")) {
//                    office_form.add(oo);
//                }
//                else if (oo.getPoshemza() != null && userHasAuthority("OP_TASVIRBARDAR_1")) {
//                    office_form.add(oo);
//                }
            } else if (oo.getType() == OfficeForm.OFFICE_FORM_ERTEBATAT) {
                for (User userof : oo.getUsers()) {
                    if (userof.getId() == user.getId()) {
                        office_form.add(oo);
                    }
                }
//                if (userHasAuthority("OP_TAHIEKONANDEH") || userHasAuthority("OP_HAMAHANGIE")) {
//                    office_form.add(oo);
//                } else
                if (oo.getTahayeemza() != null && userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
                    office_form.add(oo);
                } else if (oo.getMdarkhastemza() != null && userHasAuthority("OP_UP_LINK")) {
                    office_form.add(oo);
                }
            }

        }
        model.addAttribute("officeforms", office_form);
        model.addAttribute("page", ll);
        model.addAttribute("enumField", OfficeForm.OFFICE_FORM_BARNAME_TOLIDIE_KHABARIE);
        model.addAttribute("ertebatat", OfficeForm.OFFICE_FORM_ERTEBATAT);
        model.addAttribute("sima", OfficeForm.OFFICE_FORM_ESTEDIO_SIMA);
        model.addAttribute("sayar", OfficeForm.OFFICE_FORM_VAHED_SAIAR);
        model.addAttribute("user", user);
        model.addAttribute("rozhaehafte", RozHafteh.values());
        model.addAttribute("locations", locationServiceImp.getAllLocations());
        model.addAttribute("tahie", user.getFullname());
        model.addAttribute("officeTypes", OfficeForm.values());

        if (userHasAuthority("OP_HAMAHANGIE") || userHasAuthority("OP_TAHIEKONANDEH")) {
            model.addAttribute("programs", user.getPrograms());
        } else {
            model.addAttribute("programs", programServiceImp.getAllPrograms());
        }
        return "admin/office/index";
    }

    @GetMapping("/office-print/{id}")
    public String printOffice(Model model, @PathVariable Long id) {
        model.addAttribute("enumField", OfficeForm.OFFICE_FORM_BARNAME_TOLIDIE_KHABARIE);
        model.addAttribute("ertebatat", OfficeForm.OFFICE_FORM_ERTEBATAT);
        model.addAttribute("sima", OfficeForm.OFFICE_FORM_ESTEDIO_SIMA);
        model.addAttribute("sayar", OfficeForm.OFFICE_FORM_VAHED_SAIAR);
        model.addAttribute("form", formRepo.findById((long) id).get());
        return "admin/office/office-print";
    }

    @GetMapping("/officeTrue")
    public String viewofficeTrue(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
//        List<User> us = new ArrayList<>();
//        List<officeForm> office_form = new ArrayList<>();
//        us.add(userRepo.findByPersonalId(user.getPersonalId()));
        Page<officeForm> ll = formRepo.findByStatusIsTrue(pageable);

//        for (officeForm oo : ll.getContent()) {
//            for (User userof : oo.getUsers()) {
//                if (userof.getId() == user.getId()) {
//                    office_form.add(oo);
//                }
//            }
//        }

//        for (officeForm oo : ll.getContent()) {
//            if (oo.getType() == OfficeForm.OFFICE_FORM_ESTEDIO_SIMA) {
//                if (userHasAuthority("OP_TAHIEKONANDEH") || userHasAuthority("OP_HAMAHANGIE")) {
//                    office_form.add(oo);
//                } else if (oo.getTahayeemza() != null && userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
//                    office_form.add(oo);
//                } else if (oo.getMdarkhastemza() != null && userHasAuthority("OP_MODIR_POSHTIBANIT")) {
//                    office_form.add(oo);
//                }
//            } else if (oo.getType() == OfficeForm.OFFICE_FORM_BARNAME_TOLIDIE_KHABARIE) {
//                if (userHasAuthority("OP_TAHIEKONANDEH") || userHasAuthority("OP_HAMAHANGIE")) {
//                    office_form.add(oo);
//                } else if (oo.getTahayeemza() != null && userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
//                    office_form.add(oo);
//                } else if (oo.getMdarkhastemza() != null && userHasAuthority("OP_MODIR_POSHTIBANIT")) {
//                    office_form.add(oo);
//                } else if (oo.getPoshemza() != null && userHasAuthority("OP_ANBARDAR")) {
//                    office_form.add(oo);
//                } else if (oo.getAnbaremza() != null && oo.getPoshemza() != null && userHasAuthority("OP_HAML_NAGHL")) {
//                    office_form.add(oo);
//                } else if (oo.getHamlonaghlemza() != null && userHasAuthority("OP_HERASAT")) {
//                    office_form.add(oo);
//                } else if (oo.getPoshemza() != null && userHasAuthority("OP_SEDABARDAR")) {
//                    office_form.add(oo);
//                } else if (oo.getPoshemza() != null && userHasAuthority("OP_TASVIRBARDAR_1")) {
//                    office_form.add(oo);
//                }
//            } else if (oo.getType() == OfficeForm.OFFICE_FORM_VAHED_SAIAR) {
//                if (userHasAuthority("OP_TAHIEKONANDEH") || userHasAuthority("OP_HAMAHANGIE")) {
//                    office_form.add(oo);
//                } else if (oo.getTahayeemza() != null && userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
//                    office_form.add(oo);
//                } else if (oo.getMdarkhastemza() != null && userHasAuthority("OP_MODIR_POSHTIBANIT")) {
//                    office_form.add(oo);
//                } else if (oo.getPoshemza() != null && userHasAuthority("OP_UP_LINK")) {
//                    office_form.add(oo);
//                } else if (oo.getUplinkemza() != null && userHasAuthority("OP_TASISAT")) {
//                    office_form.add(oo);
//                } else if (oo.getTasisatemza() != null && userHasAuthority("OP_HAML_NAGHL")) {
//                    office_form.add(oo);
//                } else if (oo.getHamlonaghlemza() != null && userHasAuthority("OP_HERASAT")) {
//                    office_form.add(oo);
//                } else if (oo.getPoshemza() != null && userHasAuthority("OP_SEDABARDAR")) {
//                    office_form.add(oo);
//                } else if (oo.getPoshemza() != null && userHasAuthority("OP_TASVIRBARDAR_1")) {
//                    office_form.add(oo);
//                }
//            } else if (oo.getType() == OfficeForm.OFFICE_FORM_ERTEBATAT) {
//                if (userHasAuthority("OP_TAHIEKONANDEH") || userHasAuthority("OP_HAMAHANGIE")) {
//                    office_form.add(oo);
//                } else if (oo.getTahayeemza() != null && userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
//                    office_form.add(oo);
//                } else if (oo.getMdarkhastemza() != null && userHasAuthority("OP_UP_LINK")) {
//                    office_form.add(oo);
//                }
//            }
//        }
        model.addAttribute("officeforms", ll.getContent());
        model.addAttribute("page", ll);
        model.addAttribute("user", user);
        model.addAttribute("enumField", OfficeForm.OFFICE_FORM_BARNAME_TOLIDIE_KHABARIE);
        model.addAttribute("ertebatat", OfficeForm.OFFICE_FORM_ERTEBATAT);
        model.addAttribute("sima", OfficeForm.OFFICE_FORM_ESTEDIO_SIMA);
        model.addAttribute("sayar", OfficeForm.OFFICE_FORM_VAHED_SAIAR);
        model.addAttribute("tahie", user.getFullname());
        model.addAttribute("officeTypes", OfficeForm.values());
        model.addAttribute("rozhaehafte", RozHafteh.values());
        model.addAttribute("locations", locationServiceImp.getAllLocations());

        if (userHasAuthority("OP_HAMAHANGIE") || userHasAuthority("OP_TAHIEKONANDEH")) {
            model.addAttribute("programs", user.getPrograms());
        } else {
            model.addAttribute("programs", programServiceImp.getAllPrograms());
        }
        return "admin/office/index";
    }

    @GetMapping("/form")
    public String viewform(Model model, int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        Set<User> usersWithJob = findU(user.getJob().getId());
        model.addAttribute("userJob", usersWithJob);
        model.addAttribute("form", formRepo.findById((long) id).get());

//        System.out.println(" ############################## : "+formRepo.findById((long) id).get().getTarikhsodur());

//        PersianDate persianDate = PersianDate.parse(formRepo.findById((long) id).get().getTarikhsodur());

//        LocalDate gregDate = persianDate.toGregorian();
//        PersianDate persianDate6 = PersianDate.fromGregorian(gregDate);

//        System.out.println(" ############################## : " + gregDate.plusDays(13));
//        PersianDate persianDate6 = PersianDate.fromGregorian(gregDate.plusDays(13));

//        PersianDate persianDate1 = PersianDate.parse(gregDate.plusDays(13).toString());
//        System.out.println(" ############################## 2 : "+persianDate6);

//        Set<OfficeFormUserTajhizat> ss = formRepo.findById((long) id).get().getOfficeFormUserTajhizats();
        List<Tajhizat> tajhizat_ = new ArrayList<Tajhizat>();
        tajhizat_.addAll(formRepo.findById((long) id).get().getTajhizatss());


        for (Iterator<OfficeFormUserTajhizat> iterator = formRepo.findById((long) id).get().getOfficeFormUserTajhizats().iterator(); iterator.hasNext(); ) {
            OfficeFormUserTajhizat ff = iterator.next();
            if (tajhizat_.contains(ff.getTajhizat())) {
                tajhizat_.remove(ff.getTajhizat());
//                System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU : " + ff.getTajhizat());
            }
        }

        model.addAttribute("userss", userRepo.findAll());
        model.addAttribute("tajhizat_ff", tajhizat_);
        model.addAttribute("enumField", OfficeForm.OFFICE_FORM_BARNAME_TOLIDIE_KHABARIE);
        model.addAttribute("ertebatat", OfficeForm.OFFICE_FORM_ERTEBATAT);
        model.addAttribute("sima", OfficeForm.OFFICE_FORM_ESTEDIO_SIMA);
        model.addAttribute("sayar", OfficeForm.OFFICE_FORM_VAHED_SAIAR);
        model.addAttribute("user", user);
        model.addAttribute("cars", carServiceImp.getAllCars());
        model.addAttribute("tajhizats", tRepo.findAll());
        model.addAttribute("jobs", jobServiceImp.getAllJobs());
        return "admin/office/single_office";
    }

    public long officeFormCount() {
        return formRepo.count();
    }

    @GetMapping("/panel")
    public String home(Model model) {
//        LocalDate dateBefore30Days = LocalDate.now().minusDays(10);
//        dateBefore30Days.minusDays(30);
//        Calendar cal = Calendar.getInstance();
//        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR : " + dateBefore30Days);
//        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR : " + cal.getTime());
//        PersianDate today = PersianDate.now();
//        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR : " + today);
//        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR : " + today.toGregorian());
//        PersianDate persianDate6 = PersianDate.fromGregorian(gregDate);
//        PersianDate persianDate1 = PersianDate.of(1396, 7, 15);
//        PersianDate persianDate2 = PersianDate.of(1396, PersianMonth.MEHR, 15);

        model.addAttribute("userCount", userService.count());
        model.addAttribute("officeCount", officeFormCount());
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
//        System.out.println("***************** + " + id);
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
//                        OfficeFormUserTajhizatId id = new OfficeFormUserTajhizatId(us.getId(), office_form.getId(), office_form.);
//                        officeFormUserTajhizatRepository.deleteOfficeFormUserTajhizatById(id);
//                        office_form.getOfficeFormUserTajhizats().
                    }
                }
            }
            formRepo.save(office_form);

            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "کاربر مورد نظر از آفیش حذف گردید.");
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
            redirectAttributes.addFlashAttribute("message", "عملیات با موفقیت انجام گردید.");
            return "redirect:/tajhizats";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/tajhizats";
        }
    }

    @PostMapping("/saveForm")
    public String saveForm(@ModelAttribute @Valid officeForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        System.out.println("save form function");
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/office";
            }

//            Roozh jCal = new Roozh();
//            int myear = LocalDate.now().getYear();
//            int mmonth = LocalDate.now().getMonthValue();
//            int mday = LocalDate.now().getDayOfMonth();
//
//            jCal.gregorianToPersian(myear, mmonth, mday);
            PersianDate today = PersianDate.now();
            form.setTarikhsodur(today.toString());

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
            redirectAttributes.addFlashAttribute("message", "عملیات با موفقیت انجام گردید.");
            return "redirect:/office";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/office";
        }


    }

    public static boolean userHasAuthority(String authority) {
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        System.out.println("authority : " + authorities);
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
            if ((userHasAuthority("OP_TAHIEKONANDEH") || userHasAuthority("OP_HAMAHANGIE")) && office_form.getTahayeemza() == null) {
                office_form.setTahayeemza(u.getEmza());
            } else if (userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH") && office_form.getMdarkhastemza() == null) {
                office_form.setMdarkhastemza(u.getEmza());
            } else if (userHasAuthority("OP_MODIR_POSHTIBANIT") && office_form.getPoshemza() == null) {
                office_form.setPoshemza(u.getEmza());
                formRepo.setStatusForOfficeForm(true, office_form.getId());
                System.out.println("status ok ");
            } else {
                System.out.println("----------------emzaa nashod OFFICE_FORM_ESTEDIO_SIMA-------------------------");
            }
        } else if (office_form.getType() == OfficeForm.OFFICE_FORM_BARNAME_TOLIDIE_KHABARIE) {
            if ((userHasAuthority("OP_TAHIEKONANDEH") || userHasAuthority("OP_HAMAHANGIE")) && office_form.getTahayeemza() == null) {
                office_form.setTahayeemza(u.getEmza());
            } else if (userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH") && office_form.getMdarkhastemza() == null) {
                office_form.setMdarkhastemza(u.getEmza());
            } else if (userHasAuthority("OP_MODIR_POSHTIBANIT") && office_form.getPoshemza() == null) {
                office_form.setPoshemza(u.getEmza());
            } else if (userHasAuthority("OP_ANBARDAR") && office_form.getAnbaremza() == null) {
                office_form.setAnbaremza(u.getEmza());
            } else if (userHasAuthority("OP_HAML_NAGHL") && office_form.getHamlonaghlemza() == null) {
                User us = userRepo.findById(fj.getTid()).get();
                office_form.setRanande(us.getFullname());
                office_form.setRanandeid(us.getPersonalId());
                Car car = carServiceImp.findById(fj.getCar()).get();
                office_form.setCar(car);
//                of
//                car.setOfficeForm(office_form);
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
            } else if (userHasAuthority("OP_SEDABARDAR") && office_form.getSedaemza() == null) {
                office_form.setSedaemza(u.getEmza());
            } else if (userHasAuthority("OP_TASVIRBARDAR_1") && office_form.getTasviremza() == null) {
                office_form.setTasviremza(u.getEmza());
            } else if (userHasAuthority("OP_TASVIRBARDAR_2") && office_form.getTasviremza2() == null) {
                office_form.setTasviremza2(u.getEmza());
            } else if (userHasAuthority("OP_TASVIRBARDAR_3") && office_form.getTasviremza3() == null) {
                office_form.setTasviremza3(u.getEmza());
            } else if (userHasAuthority("OP_TASVIRBARDAR_4") && office_form.getTasviremza4() == null) {
                office_form.setTasviremza4(u.getEmza());
            } else {
                System.out.println("----------------emzaa nashod OFFICE_FORM_BARNAME_TOLIDIE_KHABARIE-------------------------");
            }
        } else if (office_form.getType() == OfficeForm.OFFICE_FORM_VAHED_SAIAR) {
            if ((userHasAuthority("OP_TAHIEKONANDEH") || userHasAuthority("OP_HAMAHANGIE")) && office_form.getTahayeemza() == null) {
                office_form.setTahayeemza(u.getEmza());
            } else if (userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH") && office_form.getMdarkhastemza() == null) {
                office_form.setMdarkhastemza(u.getEmza());
            } else if (userHasAuthority("OP_MODIR_POSHTIBANIT") && office_form.getPoshemza() == null) {
                office_form.setPoshemza(u.getEmza());
            } else if (userHasAuthority("OP_UP_LINK") && office_form.getUplinkemza() == null) {
                office_form.setUplinkemza(u.getEmza());
            } else if (userHasAuthority("OP_TASISAT") && office_form.getTasisatemza() == null) {
                office_form.setTasisatemza(u.getEmza());
            } else if (userHasAuthority("OP_HAML_NAGHL") && office_form.getHamlonaghlemza() == null) {
                User us = userRepo.findById(fj.getTid()).get();
                office_form.setRanande(us.getFullname());
                office_form.setRanandeid(us.getPersonalId());
                Car car = carServiceImp.findById(fj.getCar()).get();
                office_form.setCar(car);
//                car.setOfficeForm(office_form);
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
                System.out.println("----------------emzaa nashod OFFICE_FORM_VAHED_SAIAR-------------------------");
            }
        } else if (office_form.getType() == OfficeForm.OFFICE_FORM_ERTEBATAT) {
            if ((userHasAuthority("OP_TAHIEKONANDEH") || userHasAuthority("OP_HAMAHANGIE")) && office_form.getTahayeemza() == null) {
                office_form.setTahayeemza(u.getEmza());
            } else if (userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH") && office_form.getMdarkhastemza() == null) {
                office_form.setMdarkhastemza(u.getEmza());
            } else if (userHasAuthority("OP_UP_LINK") && office_form.getUplinkemza() == null) {
                office_form.setUplinkemza(u.getEmza());
                formRepo.setStatusForOfficeForm(true, office_form.getId());
            } else {
                System.out.println("----------------emzaa nashod OFFICE_FORM_ERTEBATAT-------------------------");
            }
        }
        formRepo.save(office_form);

        return redirect;
    }

    @PostMapping("/addForm")
    public String addutoform(@ModelAttribute officeForm f, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        System.out.println("TTTTTDDDDDDDDDDDDDDDDDDDDDDD : " + f.getTajhizatss());
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
            redirectAttributes.addFlashAttribute("message", "عوامل با موفقیت به آفیش اضافه گردید.");
        }
        if (f.getTajhizatss() != null) {
            form.setTajhizatss(f.getTajhizatss());
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "تجهیزات مورد نظر با موفقیت در آفیش ثبت شد.");
        }
        formRepo.save(form);
        System.out.println("form save ");
        return "redirect:/form/?id=" + id_form;

    }

    @PostMapping("/addTajhizToUser")
    public String addTajhizToUserOfficeForm(@ModelAttribute officeForm f, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTT : " + f.getUsers().get(0).getId());
//        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTT : " + f.getTajhizatss().get(0).getId());
//        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTT : " + f.getId());
        Long id_form = f.getId();
        try {
            officeForm office_form = formRepo.findById(f.getId()).get();
            OfficeFormUserTajhizat officeFormUserTajhizat = new OfficeFormUserTajhizat();
            officeFormUserTajhizat.setUser(f.getUsers().get(0));
            officeFormUserTajhizat.setOfficeForms(office_form);
            officeFormUserTajhizat.setTajhizat(f.getTajhizatss().get(0));
            officeFormUserTajhizatw.saveOfficeFormUserTajhizat(officeFormUserTajhizat);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "تجهیز مورد نظر به کاربر مورد نظر اختصاص یافت .");
            return "redirect:/form/?id=" + id_form;
        } catch (Exception exception) {
//            System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU : " + exception.toString());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تجهیز تکراری وارد کردید لطفا دقت کنید");
            return "redirect:/form/?id=" + id_form;
        }
    }

    @PostMapping("/laghv")
    public String cancelOfficeForm(@ModelAttribute officeForm f, String description, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        Long id_form = f.getId();
        try {
            officeForm office_form = formRepo.findById(f.getId()).get();
            office_form.setStatus(true);
            formRepo.save(office_form);
            UserCancelOfficeDescription userCancelOfficeDescription = new UserCancelOfficeDescription();
            userCancelOfficeDescription.setUser(user);
            userCancelOfficeDescription.setOfficeForms(office_form);
            userCancelOfficeDescription.setDescription(description);
            userCancelOfficeDescriptionImp.saveUserCancelOfficeDescription(userCancelOfficeDescription);

            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "آفیش مورد نظر لغو گردید .");
            return "redirect:/form/?id=" + id_form;
        } catch (Exception exception) {
            System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU : " + exception);
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "خطایی در سمت سرور به وجود آمده مجددا تلاش نمایید");
            return "redirect:/form/?id=" + id_form;
        }
    }

    @GetMapping(value = "/deleteTajhizToUser/{tajhiz_id}/{user_id}/{officeForms_id}")
    public String deletet(@PathVariable Long tajhiz_id, @PathVariable int user_id, @PathVariable Long officeForms_id, RedirectAttributes redirectAttributes) {
        Long id_form = officeForms_id;

        try {
            OfficeFormUserTajhizatId id = new OfficeFormUserTajhizatId(user_id, officeForms_id, tajhiz_id);
            officeFormUserTajhizatRepository.deleteOfficeFormUserTajhizatById(id);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "تجهیز از کاربر گرفته شد.");
            return "redirect:/form/?id=" + id_form;

        } catch (Exception exception) {
//            System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU : " + exception.toString());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تجهیز تکراری وارد کردید لطفا دقت کنید");
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
//        System.out.println("*********************************** : " + officeForms.get().getProgram().getName());
        OfficePdfGenerator generator = new OfficePdfGenerator(officeForms.get());
        generator.export(response);

    }


}
