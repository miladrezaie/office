package com.javasampleapproach.jqueryboostraptable.controller;


import java.io.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


import com.github.mfathi91.time.PersianDate;
import com.github.mfathi91.time.PersianDateTime;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.DocumentException;
import com.javasampleapproach.jqueryboostraptable.Service.*;
import com.javasampleapproach.jqueryboostraptable.Service.Impl.*;

import com.javasampleapproach.jqueryboostraptable.enums.OfficeForm;
import com.javasampleapproach.jqueryboostraptable.enums.RozHafteh;
import com.javasampleapproach.jqueryboostraptable.model.*;
import com.javasampleapproach.jqueryboostraptable.repository.*;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Service
@Controller
public class OfficeController {

    @Autowired
    ServletContext servletContext;

    private final TemplateEngine templateEngine;

    @Autowired
    private ExportPdfService exportPdfService;

    @Autowired
    private TajhizatRepository tRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private OfficeFormRepository formRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LocationService locationService;

    @Autowired
    private JobService jobService;

    @Autowired
    private CarService carService;

    @Autowired
    private ProgramService programService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OfficeFormUserTajhizatRepository officeFormUserTajhizatRepository;

    @Autowired
    private OfficeFormUserTajhizatServiceImp officeFormUserTajhizatw;

    @Autowired
    private UserCancelOfficeDescriptionImp userCancelOfficeDescriptionImp;

    public OfficeController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @GetMapping("/result")
    public ResponseEntity<?> viewTajhizat() {
        return (ResponseEntity<?>) locationService.findAll();
    }

    @GetMapping(value = "/admin/officeform/delete/{id}")
    @PreAuthorize("hasAuthority('OP_DELETE_OFFICE')")
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
    public String viewoffice(Model model, @PageableDefault(size = 10) Pageable pageable) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        List<officeForm> office_form = new ArrayList<>();

        Page<officeForm> ll = formRepo.findByStatusIsFalse(pageable);
        if (userHasAuthority("OP_ACCESS_ADMIN_PANEL")) {
            for (officeForm oo : ll.getContent()) {
                office_form.add(oo);
            }
        } else {
            for (officeForm oo : ll.getContent()) {
                if (oo.getType() == OfficeForm.OFFICE_FORM_ESTEDIO_SIMA) {
                    for (User userof : oo.getUsers()) {
                        if (userof.getId() == user.getId()) {
                            office_form.add(oo);
                        }
                    }
                    if (userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
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
                    if (userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
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
                } else if (oo.getType() == OfficeForm.OFFICE_FORM_VAHED_SAIAR) {
                    for (User userof : oo.getUsers()) {
                        if (userof.getId() == user.getId()) {
                            office_form.add(oo);
                        }
                    }
                    if (userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
                        office_form.add(oo);
                    } else if (oo.getMdarkhastemza() != null && userHasAuthority("OP_MODIR_POSHTIBANIT")) {
                        office_form.add(oo);
                    } else if (oo.getPoshemza() != null && userHasAuthority("OP_UP_LINK")) {
                        office_form.add(oo);
                    } else if (oo.getPoshemza() != null && userHasAuthority("OP_TASISAT")) {
                        office_form.add(oo);
                    } else if (oo.getPoshemza() != null && userHasAuthority("OP_HAML_NAGHL")) {
                        office_form.add(oo);
                    } else if (oo.getHamlonaghlemza() != null && userHasAuthority("OP_HERASAT")) {
                        office_form.add(oo);
                    }
                } else if (oo.getType() == OfficeForm.OFFICE_FORM_ERTEBATAT) {
                    for (User userof : oo.getUsers()) {
                        if (userof.getId() == user.getId()) {
                            office_form.add(oo);
                        }
                    }
                    if (userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
                        office_form.add(oo);
                    } else if (oo.getMdarkhastemza() != null && userHasAuthority("OP_UP_LINK")) {
                        office_form.add(oo);
                    }
                }
            }
        }
        List<Program> programss = new ArrayList<>();

        for (Program programe : user.getPrograms()) {
            if (programe.getStatus() == true) {
                programss.add(programe);
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
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("tahie", user.getFullname());
        model.addAttribute("officeTypes", OfficeForm.values());

        model.addAttribute("programs", programss);
        if (userHasAuthority("OP_SHOW_ALL_PROGRAM")) {
            model.addAttribute("programs", programService.findAll());
        }
        return "admin/office/index";
    }

    @GetMapping("/office-print/{id}")
    @PreAuthorize("hasAuthority('OP_PRINT_OFFICE')")
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

        Page<officeForm> ll = formRepo.findByStatusIsTrue(pageable);
        model.addAttribute("officeforms", ll.getContent());
        model.addAttribute("page", ll);
//        System.out.println("HHHHHHHHHHHHHHHHHHHHHHh : " + ll.getSize());

        return "admin/office/index_true";
    }

    @GetMapping("/form")
    public String viewform(Model model, int id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        officeForm office = formRepo.findById((long) id).get();

        PersianDateTime today = PersianDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (office.getMdarkhastSeen() == null && userHasAuthority("OP_MODEIR_VAHED_DARKHST_KONANDEH")) {
            office.setMdarkhastSeen(today.format(formatter));
        } else if (office.getPosheSeen() == null && userHasAuthority("OP_MODIR_POSHTIBANIT")) {
            office.setPosheSeen(today.format(formatter));
        } else if (office.getAnbaremzaSeen() == null && userHasAuthority("OP_ANBARDAR")) {
            office.setAnbaremzaSeen(today.format(formatter));
        } else if (office.getTasisatSeen() == null && userHasAuthority("OP_TASISAT")) {
            office.setTasisatSeen(today.format(formatter));
        } else if (office.getHamlonaghlSeen() == null && userHasAuthority("OP_HAML_NAGHL")) {
            office.setHamlonaghlSeen(today.format(formatter));
        } else if (office.getUplinkSeen() == null && userHasAuthority("OP_UP_LINK")) {
            office.setUplinkSeen(today.format(formatter));
        } else if (office.getTahayekonandeSeen() == null && userHasAuthority("OP_TAHIEKONANDEH")) {
            office.setTahayekonandeSeen(today.format(formatter));
        } else if (office.getHamahangiSeen() == null && userHasAuthority("OP_HAMAHANGIE")) {
            office.setHamahangiSeen(today.format(formatter));
        } else if (office.getKhherasatSeen() == null && userHasAuthority("OP_HERASAT")) {
            office.setKhherasatSeen(today.format(formatter));
        }
        formRepo.save(office);

        Set<User> usersWithJob = findU(user.getJob().getId());
        model.addAttribute("userJob", usersWithJob);
        model.addAttribute("form", office);

        List<Tajhizat> tajhizat_ = new ArrayList<Tajhizat>();
        tajhizat_.addAll(office.getTajhizatss());

        for (Iterator<OfficeFormUserTajhizat> iterator = office.getOfficeFormUserTajhizats().iterator(); iterator.hasNext(); ) {
            OfficeFormUserTajhizat ff = iterator.next();
            if (tajhizat_.contains(ff.getTajhizat())) {
                tajhizat_.remove(ff.getTajhizat());
            }
        }
        model.addAttribute("userss", userRepo.findAll());
        model.addAttribute("tajhizat_ff", tajhizat_);
        model.addAttribute("enumField", OfficeForm.OFFICE_FORM_BARNAME_TOLIDIE_KHABARIE);
        model.addAttribute("ertebatat", OfficeForm.OFFICE_FORM_ERTEBATAT);
        model.addAttribute("sima", OfficeForm.OFFICE_FORM_ESTEDIO_SIMA);
        model.addAttribute("sayar", OfficeForm.OFFICE_FORM_VAHED_SAIAR);
        model.addAttribute("user", user);
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("tajhizats", tRepo.findAll());
        model.addAttribute("jobs", jobService.findAll());
        return "admin/office/single_office";
    }

    public long officeFormCount() {
        return formRepo.count();
    }

    @GetMapping("/find/date-between")
    public String searchDao(Model model, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @PageableDefault(size = 10) Pageable pageable) {


//        DateFormat dateFormatStart = new SimpleDateFormat("yyyy-MM-dd");
//        String startDate = dateFormatStart.format(dateSearcherDto.getStartDate());

//        DateFormat dateFormatEnd = new SimpleDateFormat("yyyy-MM-dd");
//        String endDate = dateFormatEnd.format(dateSearcherDto.getEndDate());


//        Page<officeForm> officeForms = formRepo.getAllBetweenDates(dateSearcherDto.getStartDate(), dateSearcherDto.getEndDate(), pageable);

        Page<officeForm> officeForms = formRepo.getAllBetweenDates(startDate, endDate, pageable);
        model.addAttribute("officeforms", officeForms.getContent());
        model.addAttribute("dateSearcherDtoStart", startDate);
        model.addAttribute("dateSearcherDtoEnd", endDate);
        model.addAttribute("page", officeForms);
//        System.out.println("HHHHHHHHHHHHHHHHHHHHHHh : " + officeForms.getSize());

        return "admin/office/result_search";
    }

    @GetMapping("/panel")
    public String home(Model model) {

//        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTT : " +formRepo.findById((long) id).get().getDate_begin());
//        PersianDate persianDate = PersianDate.parse(formRepo.findById((long) id).get().getDate_begin().toString(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        LocalDate gregDate = persianDate.toGregorian();
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        PersianDate persianDate6 = PersianDate.fromGregorian(gregDate.plusDays(13));
//
//        System.out.println(" ############################## 2 : "+persianDate6);
//        System.out.println(" ############################## 2 : "+dtf.format(persianDate6));

//        Set<OfficeFormUserTajhizat> ss = formRepo.findById((long) id).get().getOfficeFormUserTajhizats();

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

    @PostMapping("/admin/officeform/delete-user")
    public String deleteUserOfficeForm(@ModelAttribute officeForm f, RedirectAttributes redirectAttributes) {
        long id_form = f.getId();
        try {
            officeForm office_form = formRepo.findById(f.getId()).get();
            Iterator<User> itr = f.getUsers().iterator();
            while (itr.hasNext()) {
                if (office_form.getUsers().contains(itr.next())) {
                    for (User us : f.getUsers()) {
                        if (us.getFullname().equals(office_form.getTahayekonande())){

                            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                            redirectAttributes.addFlashAttribute("message", "حذف کاربر تهیه کننده امکان پذیر نیست.");
                            return "redirect:/form/?id=" + id_form;
                        }
                        System.out.println("user office" + us.getFName());
                        for (Tajhizat tajhizat:office_form.getTajhizatss()){
                            OfficeFormUserTajhizatId id = new OfficeFormUserTajhizatId(us.getId(), office_form.getId(), tajhizat.getId());
                            officeFormUserTajhizatRepository.deleteOfficeFormUserTajhizatById(id);
                        }
                        office_form.getUsers().remove(us);

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

    @PostMapping("/saveForm")
    public String saveForm(@ModelAttribute @Valid officeForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYY : " + bindingResult.getAllErrors());

                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("message", " تمام فیلد ها را بادقت پر کنید .");
                return "redirect:/office";
            }
//        System.out.println("********************************* "+form.getProgram().getDate_end().compareTo(form.getDate_end())>0);
//            Roozh jCal = new Roozh();
//            int myear = LocalDate.now().getYear();
//            int mmonth = LocalDate.now().getMonthValue();
//            int mday = LocalDate.now().getDayOfMonth();

//            jCal.gregorianToPersian(myear, mmonth, mday);


//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        PersianDate persianDate6 = PersianDate.fromGregorian(gregDate.plusDays(13));

//        System.out.println(" ############################## 2 : "+persianDate6);
//        System.out.println(" ############################## 2 : "+dtf.format(persianDate6));

//        Set<OfficeFormUserTajhizat> ss = formRepo.findById((long) id).get().getOfficeFormUserTajhizats();

//        LocalDate dateBefore30Days = LocalDate.now().minusDays(10);
//        dateBefore30Days.minusDays(30);
//        Calendar cal = Calendar.getInstance();
//        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR : " + dateBefore30Days);
//        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR : " + cal.getTime());
//        PersianDate today = PersianDate.now();
//        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR : " + today);
//        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR : " + today.toGregorian());



            DateFormat dateFormatStart = new SimpleDateFormat("yyyy-MM-dd");


            String end_Date = dateFormatStart.format(form.getProgram().getDate_end());
            Calendar calendar_add = Calendar.getInstance();
            calendar_add.setTime(dateFormatStart.parse(end_Date));
            calendar_add.add(Calendar.DATE, 1);


            String start_Date = dateFormatStart.format(form.getProgram().getDate_begin());
            Calendar calendar_minus = Calendar.getInstance();
            calendar_minus.setTime(dateFormatStart.parse(start_Date));
            calendar_minus.add(Calendar.DATE, -1);

            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& : " +form.getProgram().getDate_end());
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& add: " +dateFormatStart.format(calendar_add.getTime()));

            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& : " +form.getProgram().getDate_begin());
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& mi: " +dateFormatStart.format(calendar_minus.getTime()));

        if (form.getDate_begin().after(calendar_minus.getTime()) && form.getDate_end().before(calendar_add.getTime())){
            PersianDate today = PersianDate.now();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

            form.setTarikhsodur(dtf.format(today));

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
        }else{
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", " در وارد کردن تاریخ دقت کنید .");
            return "redirect:/office";
        }



        } catch (Exception exception) {
            System.out.println("YYYYYYYYYYYYYYYYYYYYYYY : " + exception);
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تمام فیلد ها را بادقت پر کنید");
            return "redirect:/office";
        }


    }

    public static boolean userHasAuthority(String authority) {
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
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
                Car car = carService.findById(fj.getCar()).get();
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
                Car car = carService.findById(fj.getCar()).get();
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
//    @PreAuthorize("hasAuthority('OP_ADD_TAJHIZ_TO_OFFICE')")
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
//        System.out.println("form save ");
        return "redirect:/form/?id=" + id_form;

    }

    @PostMapping("/addTajhizToUser")
    @PreAuthorize("hasAuthority('OP_ADD_TAJHIZ_TO_USER_IN_OFFICE')")
    public String addTajhizToUserOfficeForm(@ModelAttribute officeForm f, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
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
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تجهیز تکراری وارد کردید لطفا دقت کنید");
            return "redirect:/form/?id=" + id_form;
        }
    }

    @PostMapping("/laghv")
    @PreAuthorize("hasAuthority('OP_LAGHV_OFFICE')")
    public String cancelOfficeForm(@ModelAttribute officeForm f, String description, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        Long id_form = f.getId();
        try {
            officeForm office_form = formRepo.findById(f.getId()).get();
            office_form.setStatus(true);
            formRepo.save(office_form);

            PersianDateTime today = PersianDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            UserCancelOfficeDescription userCancelOfficeDescription = new UserCancelOfficeDescription();
            userCancelOfficeDescription.setUser(user);
            userCancelOfficeDescription.setOfficeForms(office_form);
            userCancelOfficeDescription.setDescription(description);
            userCancelOfficeDescription.setCreated_at(today.format(formatter));

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
    @PreAuthorize("hasAuthority('OP_HAZF_TAJHIZ_FROM_USER_IN_OFFICE')")
    public String deletet(@PathVariable Long tajhiz_id, @PathVariable int user_id, @PathVariable Long officeForms_id, RedirectAttributes redirectAttributes) {
        Long id_form = officeForms_id;
        try {
            OfficeFormUserTajhizatId id = new OfficeFormUserTajhizatId(user_id, officeForms_id, tajhiz_id);
            officeFormUserTajhizatRepository.deleteOfficeFormUserTajhizatById(id);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("message", "تجهیز از کاربر گرفته شد.");
            return "redirect:/form/?id=" + id_form;

        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "تجهیز تکراری وارد کردید لطفا دقت کنید");
            return "redirect:/form/?id=" + id_form;
        }
    }

    @GetMapping("/admin/office/find/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('OP_FIND_BY_ID_OFFICE')")
    public Optional<officeForm> fiOptionalLocation(@PathVariable long id) {
        return formRepo.findById(id);
    }

    @GetMapping("/findbyjob/{job}")
    @ResponseBody
    @PreAuthorize("hasAuthority('OP_FIND_BY_JOBS_OFFICE')")
    public Set<User> findU(@PathVariable long job) {
        Job job1 = jobService.findById(job);
        return job1.getUsers();
    }

    @GetMapping("/findbycategory/{category}")
    @ResponseBody
    @PreAuthorize("hasAuthority('OP_FIND_BY_CATEGORY_OFFICE')")
    public Set<Job> findJob(@PathVariable long category) {
        Optional<Category> category1 = categoryService.findByIdCategory(category);
        return category1.get().getJobs();
    }

    @GetMapping("/office/exports/{id}")
//    @PreAuthorize("hasAuthority('OP_PRINT_PDF_OFFICE')")
    public void exportToPdf(HttpServletResponse response, @PathVariable long id) throws DocumentException, IOException {

        response.setContentType("application/pdf");

        PersianDateTime today = PersianDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=office_" + today.format(formatter) + ".pdf";
        response.setHeader(headerKey, headerValue);


        officeForm office_form = formRepo.findById(id).get();
        OfficePdfGenerator generator = new OfficePdfGenerator(office_form);

        generator.export(response);


    }
//    @GetMapping("/office/exports/{id}")
//////    @PreAuthorize("hasAuthority('OP_PRINT_PDF_OFFICE')")
//    public ResponseEntity<?> exportToPdf(HttpServletRequest request,HttpServletResponse response, @PathVariable long id) throws DocumentException, IOException {
//
//        officeForm office_form = formRepo.findById(id).get();
//        WebContext context = new WebContext(request, response, servletContext);
//
//        context.setVariable("form", office_form);
//        String orderHtml = templateEngine.process("admin/office/office-print", context);
//
//        ByteArrayOutputStream target = new ByteArrayOutputStream();
//
//        /*Setup converter properties. */
//        ConverterProperties converterProperties = new ConverterProperties();
//        converterProperties.setBaseUri("http://localhost:8087");
//
//        /* Call convert method */
//        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);
//
//
//        byte[] bytes = target.toByteArray();
//
//        /* Send the response as downloadable PDF */
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(bytes);
//    }




}
