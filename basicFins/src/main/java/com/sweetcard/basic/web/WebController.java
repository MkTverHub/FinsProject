package com.sweetcard.basic.web;

import com.sweetcard.basic.dao.jdbc.*;
import com.sweetcard.basic.dao.repository.AggregateDataContragent;
import com.sweetcard.basic.dao.entities.*;
import com.sweetcard.basic.dao.repository.*;
import com.sweetcard.basic.model.Usercacheform;
import com.sweetcard.basic.registration.RegistrationForm;
import com.sweetcard.basic.registration.RegistrationRequest;
import com.sweetcard.basic.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {
    private Logger logger = LoggerFactory.getLogger(WebController.class);
    @Autowired
    private RegistrationService registrationService;
    //@Autowired
    //CustomerRepository customerRepository;
    @Autowired
    FinancedataRepository financedataRepository;
    @Autowired
    FinsprojectRepository finsprojectRepository;
    @Autowired
    AggregateDataFinsprojectRepository aggregateDataFinsprojectRepository;
    @Autowired
    ContragentRepository contragentRepository;
    @Autowired
    RequisitRepository requisitRepository;
    @Autowired
    AggregateDataContragent aggregateDataContragent;
    @Autowired
    FinancedataJdbc financedataJdbc;
    @Autowired
    FinsprojectJdbc finsprojectJdbc;
    @Autowired
    ContragentJdbc contragentJdbc;
    @Autowired
    RequisitJdbc requisitJdbc;
    @Autowired
    UsercacheJdbc usercacheJdbc;
    @Autowired
    UsercacheRepository usercacheRepository;
    @Autowired
    LovRepository lovRepository;

    //Здесь вход на app
    @GetMapping({"/", "/index","/FinsOperations"})
    public String greeting(@RequestParam(name = "ProjectId", required = false, defaultValue = "no_value") String ProjectId, Model model)
    {
        try {
            model.addAttribute("ModelProjectId", ProjectId);
            if (0 != ProjectId.compareTo("no_value")) {
                Usercacheform usercacheform = new Usercacheform();
                usercacheform.setLogin(GetUserLogin());
                usercacheform.setActiveProject(Integer.parseInt(ProjectId));
                usercacheform.setUsercacheAction("update");
                usercacheJdbc.UsercacheAction(usercacheform);
            }

            logger.info("WebController.FinsOperations -> " + ProjectId);
            return "Fins_Operations";
        }catch (Exception ex){
            logger.info("WebController.FinsOperations -> ERROR:" + ex);
            return "Fins_Operations";
        }
    }

    //Переход на страницу Регистрации
    @GetMapping(value = "/Registration")
    public String GoToUserRegistration(Model model){
        logger.info("WebController.GoToUserRegistration -> ");
        model.addAttribute("registrationform", new RegistrationForm());
        return "UserRegistration";
    }

    //Получение формы регистрации
    @RequestMapping(value = "/RegConfirm", method = RequestMethod.POST)
    public String GoToUserRegConfirm(@ModelAttribute RegistrationForm registrationForm, Model model){
        logger.info("WebController.GoToUserRegConfirm -> " + registrationForm.getFirstName() + " " + registrationForm.getLastName() + " " + registrationForm.getEmail() + " " + registrationForm.getPassword());
        registrationService.register2(registrationForm);
        return "UserRegConfirm";
    }


    //Переход на страницу Финвнсовых операций (из верхней шапки)
    @RequestMapping(value = "/FinsOperations", method = RequestMethod.POST)
    public String GoToFinsOperations(Model model){
        logger.info("WebController.GoToFinsOperations -> ");
        return "Fins_Operations";
    }

    //Переход на страницу Проектов
    //@RequestMapping(value = "/Projects", method = RequestMethod.POST)
    @RequestMapping(value = "/Projects")
    public String GoToProjects( Model model){
        logger.info("WebController.GoToProjects -> ");
        try {
            List<AggrFinsproject> aggrFinsprojectList = aggregateDataFinsprojectRepository.GetAllUserProjects(GetUserLogin());
            model.addAttribute("finsprojectList",aggrFinsprojectList);
        }catch (Exception ex1){
            //SQL aggregateDataFinsprojectRepository.GetAllUserProjects падает, если в нет записей в операциях
        }
        return "Fins_Projects_Add";
    }



    //Переход на страницу Проектов (Редактор)
    //@RequestMapping(value = "/ProjectsEditor", method = RequestMethod.GET)
    @RequestMapping(value = "/ProjectsEditor")
    public String GoToProjectsEditor(@RequestParam(name = "ProjectId", required = false, defaultValue = "def_val") String project_id, Model model){
        logger.info("WebController.GoToProjectsEditor -> " + project_id);
        return "Fins_Projects";
    }

    //Переход на страницу LOV
    //@RequestMapping(value = "/FinsLOV", method = RequestMethod.POST)
    @RequestMapping(value = "/FinsLOV")
    public String GoToFinsLOV(@RequestParam(name = "ProjectId", required = false, defaultValue = "no_value") String ProjectId, Model model){
        logger.info("WebController.GoToFinsLOV -> ");
        try {
            if (0 != ProjectId.compareTo("no_value")) {
                //Переход из левой понели проектов кликом по проекту
                Usercacheform usercacheform = new Usercacheform();
                usercacheform.setLogin(GetUserLogin());
                usercacheform.setActiveProject(Integer.parseInt(ProjectId));
                usercacheform.setUsercacheAction("update");
                usercacheJdbc.UsercacheAction(usercacheform);
            }

            //Получение активного проекта пользователя
            Usercache usercache = GetUsercache();
            List<Lov> lovList = lovRepository.GetAllByProject(usercache.active_proj);
            model.addAttribute("lovList",lovList);
        }catch (Exception ex1){
            //SQL aggregateDataFinsprojectRepository.GetAllUserProjects падает, если в нет записей в операциях
        }
        return "Fins_LOV_Add";
    }

    @RequestMapping(value = "/FinsLOVEditor")
    public String GoToFinsLOVEditor(@RequestParam(name = "ProjectId", required = false, defaultValue = "no_value") String ProjectId, Model model){
        logger.info("WebController.GoToFinsLOVEditor -> ");
        if (0 != ProjectId.compareTo("no_value")) {
            //Переход из левой понели проектов кликом по проекту
            Usercacheform usercacheform = new Usercacheform();
            usercacheform.setLogin(GetUserLogin());
            usercacheform.setActiveProject(Integer.parseInt(ProjectId));
            usercacheform.setUsercacheAction("update");
            usercacheJdbc.UsercacheAction(usercacheform);
        }
        return "Fins_LOV";
    }


    //Переход на страницу контрагентов (Плитка)
   // @RequestMapping(value = "/Contragents", method = RequestMethod.GET)
    @RequestMapping(value = "/Contragents")
    public String GoToContragents(@RequestParam(name = "ProjectId", required = false, defaultValue = "no_value") String ProjectId, Model model){
        try{
            logger.info("WebController.GoToContragents -> " + ProjectId);

            if (0 != ProjectId.compareTo("no_value")) {
                //Переход из левой понели проектов кликом по проекту
                Usercacheform usercacheform = new Usercacheform();
                usercacheform.setLogin(GetUserLogin());
                usercacheform.setActiveProject(Integer.parseInt(ProjectId));
                usercacheform.setUsercacheAction("update");
                usercacheJdbc.UsercacheAction(usercacheform);
            }

            //Получение активного проекта пользователя
            Usercache usercache = GetUsercache();

            List<AggrContragent> contragentList = aggregateDataContragent.GetProjectContragent(usercache.active_proj);
            model.addAttribute("contragentList",contragentList);
            return "Fins_Contragents_Add";
        }catch (Exception req_ex1){
            logger.info("WebController.GoToContragents -> ERROR: " + req_ex1);
            return "error";
        }
    }


    //Переход на страницу контрагентов (Редактор)
    //@RequestMapping(value = "/ContragentsEditor", method = RequestMethod.POST)
    @RequestMapping(value = "/ContragentsEditor")
    public String GoToContragentsEditor(@RequestParam(name = "ProjectId", required = false, defaultValue = "no_value") String ProjectId, Model model){
        try{
            logger.info("WebController.ContragentsEditor -> ");

            if (0 != ProjectId.compareTo("no_value")) {
                //Переход из левой понели проектов кликом по проекту
                Usercacheform usercacheform = new Usercacheform();
                usercacheform.setLogin(GetUserLogin());
                usercacheform.setActiveProject(Integer.parseInt(ProjectId));
                usercacheform.setUsercacheAction("update");
                usercacheJdbc.UsercacheAction(usercacheform);
            }

            return "Fins_Contragents";
        }catch (Exception req_ex1){
            logger.info("WebController.ContragentsEditor -> ERROR: " + req_ex1);
            return "error";
        }
    }

    //Переход на страницу Компании
    @RequestMapping(value = "/FinsCompany")
    public String GoToFinsCompany(@RequestParam(name = "ProjectId", required = false, defaultValue = "no_value") String ProjectId, Model model){
        try{
            logger.info("WebController.GoToFinsCompany -> ");
            return "Fins_Company_Info";
        }catch (Exception req_ex1){
            logger.info("WebController.GoToFinsCompany -> ERROR: " + req_ex1);
            return "error";
        }
    }

    //Переход на страницу Компании Редактор
    @RequestMapping(value = "/FinsCompanyEditor")
    public String GoToFinsCompanyEditor(@RequestParam(name = "ProjectId", required = false, defaultValue = "no_value") String ProjectId, Model model){
        try{
            logger.info("WebController.GoToFinsCompanyEditor -> ");
            return "Fins_Company";
        }catch (Exception req_ex1){
            logger.info("WebController.GoToFinsCompanyEditor -> ERROR: " + req_ex1);
            return "error";
        }
    }

    //Переход на страницу Отчеты
    @RequestMapping(value = "/Reports", method = RequestMethod.POST)
    public String GoToReports(Model model){
        return "Fins_Reports";
    }

    //Переход на экран настройки пользователя
    @RequestMapping(value = "/UserSettings")
    public String GoToUserSettings(Model model){
        return "Fins_Account_Settings";
    }

    //Переход на экран настройки пользователя
    @RequestMapping(value = "/UserSettingsInfo")
    public String GoToUserSettingsInfo(Model model){
        return "Fins_Account_Settings_Info";
    }

    //Переход на экран Fins_Account_User_Info
    @RequestMapping(value = "/FinsAccountUsersInfo")
    public String GoToFinsAccountUsersInfo(Model model){
        return "Fins_Account_Users_Info";
    }

    //Переход на экран Fins_Account_User_Add
    @RequestMapping(value = "/FinsAccountUsersAdd")
    public String GoToFinsAccountUsersAdd(Model model){
        return "Fins_Account_Users_Add";
    }



    //--------------------Внутринние методы---------------------------------
    //Получить логин пользователя
    private String GetUserLogin(){
        String strUserName = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            strUserName = ((UserDetails)principal).getUsername();
        } else {
            strUserName = principal.toString();
        }
        return strUserName;
    }

    //Получить UserCach
    private Usercache GetUsercache(){
        Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
        if(usercache == null){
            logger.info("ReqController.GetUserCache -> get null");
            Usercacheform usercacheform = new Usercacheform();
            usercacheform.setLogin(GetUserLogin());
            usercacheform.setActiveProject(0);
            usercacheform.setUsercacheAction("insert");
            usercacheJdbc.UsercacheAction(usercacheform);
            usercache = usercacheRepository.GetUsercache(GetUserLogin());
        }
        return usercache;
    }

}

