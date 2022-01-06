package com.sweetcard.basic.web;

import com.sweetcard.basic.appuser.AppUser;
import com.sweetcard.basic.appuser.AppUserRepository;
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
    AggregateDataContactRepository aggregateDataContactRepository;
    @Autowired
    AggregateDataContragent aggregateDataContragent;
    @Autowired
    AggregateDataCompanyRepository aggregateDataCompanyRepository;
    @Autowired
    AggregateDataPurposeRepository aggregateDataPurposeRepository;
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
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    AggregateDataSubUser aggregateDataSubUser;

    //Здесь вход на app
    @GetMapping({"/", "/index","/Fins_Index"})
    public String greeting(@RequestParam(name = "ProjectId", required = false, defaultValue = "no_value") String ProjectId, Model model)
    {
        try {
            return "Fins_index";
        }catch (Exception ex){
            logger.info("WebController.Fins_Index -> ERROR:" + ex);
            return "Fins_index";
        }
    }

    //Переход на страницу Финвнсовых операций (из верхней шапки)
    //@RequestMapping(value = "/FinsOperations", method = RequestMethod.POST)
    @RequestMapping(value = "/FinsOperations")
    public String GoToFinsOperations(@RequestParam(name = "ProjectId", required = false, defaultValue = "no_value") String ProjectId, Model model){
        try {
            model.addAttribute("ModelProjectId", ProjectId);
            if (0 != ProjectId.compareTo("no_value")) {
                Integer intUserId = appUserRepository.GetUserIdbyEmail(GetUserLogin());
                Usercacheform usercacheform = new Usercacheform();
                usercacheform.setLogin(GetUserLogin());
                usercacheform.setUserId(intUserId);
                usercacheform.setActiveProject(Integer.parseInt(ProjectId));
                usercacheform.setUsercacheAction("update");
                usercacheJdbc.UsercacheAction(usercacheform);
            }

            Usercache usercache = GetUsercache();
            model.addAttribute("AccountMail",usercache.login);

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
        model.addAttribute("attrErrorFlg", "false");
        model.addAttribute("attrFieldClass", "form-control");
        model.addAttribute("attrFieldClassEx", "invalid-feedback");
        return "UserRegistration";
    }

    //Получение формы регистрации
    @RequestMapping(value = "/RegConfirm", method = RequestMethod.POST)
    public String GoToUserRegConfirm(@ModelAttribute RegistrationForm registrationForm, Model model){
        String strPageName = "UserRegConfirm";
        try {
            logger.info("WebController.GoToUserRegConfirm -> " + registrationForm.getFirstName() + " " + registrationForm.getLastName() + " " + registrationForm.getEmail() + " " + registrationForm.getPassword());
            boolean blErrorFlg = false;
            if(registrationForm.getFirstName().compareTo("")==0 || registrationForm.getLastName().compareTo("")==0 || registrationForm.getEmail().compareTo("")==0 || registrationForm.getPassword().compareTo("")==0){
                model.addAttribute("attrFieldClass", "form-control error-valid-field");
                model.addAttribute("attrFieldClassEx", "invalid-feedback-display");
                blErrorFlg = true;
            }
            if(blErrorFlg){
                model.addAttribute("attrErrorFlg", "true");
                model.addAttribute("registrationform", new RegistrationForm());
                strPageName = "UserRegistration";
            }else {
                registrationService.register2(registrationForm);
                model.addAttribute("attrErrorFlg", "false");
                model.addAttribute("attrFieldClass", "form-control");
            }
        }catch (Exception e){
            String strErrorMsg = "Ошибка регистрации";
            if(e.toString().indexOf("Bad recipient address syntax") >= 0){
                strErrorMsg = "Ошибка email адреса";
                model.addAttribute("attrErrorMsg", strErrorMsg);
            }
            if(e.toString().indexOf("email already taken") >= 0){
                strErrorMsg = "email адреса уже зарегестрирован";
                model.addAttribute("attrErrorMsg", strErrorMsg);

            }
            model.addAttribute("attrErrorFlg", "true");
            model.addAttribute("attrErrorMsg", strErrorMsg);
            model.addAttribute("attrFieldClass", "form-control error-valid-field");
            model.addAttribute("attrFieldClassEx", "invalid-feedback-display");
            model.addAttribute("registrationform", new RegistrationForm());

            strPageName = "UserRegistration";

        }
        return strPageName;
    }




    //Переход на страницу Проектов
    //@RequestMapping(value = "/Projects", method = RequestMethod.POST)
    @RequestMapping(value = "/Projects")
    public String GoToProjects( Model model){
        logger.info("WebController.GoToProjects -> ");
        try {
            List<AggrFinsproject> aggrFinsprojectList = aggregateDataFinsprojectRepository.GetAllUserProjects(GetUserLogin());
            model.addAttribute("finsprojectList",aggrFinsprojectList);
            Usercache usercache = GetUsercache();
            model.addAttribute("AccountMail",usercache.login);
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
        Usercache usercache = GetUsercache();
        model.addAttribute("AccountMail",usercache.login);
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
                SetActiveProjectUserCache(Integer.parseInt(ProjectId));
            }
            //Получение активного проекта пользователя
            Usercache usercache = GetUsercache();
            List<Lov> lovList = lovRepository.GetAllByProject(usercache.active_proj);
            model.addAttribute("lovList",lovList);
            model.addAttribute("AccountMail",usercache.login);
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
            SetActiveProjectUserCache(Integer.parseInt(ProjectId));
        }
        Usercache usercache = GetUsercache();
        model.addAttribute("AccountMail",usercache.login);
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
                SetActiveProjectUserCache(Integer.parseInt(ProjectId));
            }
            //Получение активного проекта пользователя
            Usercache usercache = GetUsercache();
            List<AggrContragent> contragentList = aggregateDataContragent.GetProjectContragent(usercache.active_proj);
            model.addAttribute("contragentList",contragentList);
            model.addAttribute("AccountMail",usercache.login);
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
                SetActiveProjectUserCache(Integer.parseInt(ProjectId));
            }
            Usercache usercache = GetUsercache();
            model.addAttribute("AccountMail",usercache.login);
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

            if (0 != ProjectId.compareTo("no_value")) {
                //Переход из левой понели проектов кликом по проекту
                SetActiveProjectUserCache(Integer.parseInt(ProjectId));
            }

            List<AggrCompany> aggrCompanyList = aggregateDataCompanyRepository.GetAllByOwner(GetUserLogin());
            model.addAttribute("companyList",aggrCompanyList);

            Usercache usercache = GetUsercache();
            model.addAttribute("AccountMail",usercache.login);
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

            if (0 != ProjectId.compareTo("no_value")) {
                //Переход из левой понели проектов кликом по проекту
                SetActiveProjectUserCache(Integer.parseInt(ProjectId));
            }

            Usercache usercache = GetUsercache();
            model.addAttribute("AccountMail",usercache.login);
            return "Fins_Company";
        }catch (Exception req_ex1){
            logger.info("WebController.GoToFinsCompanyEditor -> ERROR: " + req_ex1);
            return "error";
        }
    }

    //Переход на страницу Отчеты
    @RequestMapping(value = "/Reports")
    public String GoToReports(@RequestParam(name = "ProjectId", required = false, defaultValue = "no_value") String ProjectId, Model model){
        try {
            if (0 != ProjectId.compareTo("no_value")) {
                //Переход из левой понели проектов кликом по проекту
                SetActiveProjectUserCache(Integer.parseInt(ProjectId));
            }
            Usercache usercache = GetUsercache();
            model.addAttribute("AccountMail",usercache.login);
            return "Fins_Reports";
        }catch (Exception ex){
            logger.info("WebController.GoToReports -> ERROR: " + ex);
            return "Fins_Reports";
        }

    }

    //Переход на экран Панель пользователя
    @RequestMapping(value = "/EmployeePanel")
    public String GoToEmployeePanel(@RequestParam(name = "ProjectId", required = false, defaultValue = "no_value") String ProjectId, Model model){
        try{
            logger.info("WebController.GoToEmployeePanel -> ");
            //Получение usercache
            Usercache usercache = GetUsercache();
            AppUser appUser = appUserRepository.GetMainUser(usercache.user_id);

            if(ProjectId.compareTo("no_value")==0){
                ProjectId = usercache.active_proj.toString();
            }

            model.addAttribute("AccountMail",usercache.login);
            List<AggrContact> AggrContactList = aggregateDataContactRepository.GetAllProject(Integer.parseInt(ProjectId));
            model.addAttribute("contactList",AggrContactList);

            return "Fins_Users_Info";
        }catch (Exception req_ex1){
            logger.info("WebController.GoToEmployeePanel -> ERROR: " + req_ex1);
            return "error";
        }

    }

    //Переход на экран Цели Инфо
    @RequestMapping(value = "/FinsPurposeInfo")
    public String GoToFinsPurposeInfo(@RequestParam(name = "ProjectId", required = false, defaultValue = "no_value") String ProjectId, Model model){
        try{
            logger.info("WebController.FinsPurposeInfo -> ");

            if (0 != ProjectId.compareTo("no_value")) {
                //Переход из левой понели проектов кликом по проекту
                SetActiveProjectUserCache(Integer.parseInt(ProjectId));
            }

            Usercache usercache = GetUsercache();
            List<AggrPurpose> aggrPurposeList = aggregateDataPurposeRepository.GetPurposeData(usercache.active_proj);
            model.addAttribute("aggrPurposeList",aggrPurposeList);
            model.addAttribute("AccountMail",usercache.login);
            logger.info("WebController.FinsPurposeInfo -> aggrPurposeList: " + aggrPurposeList.size());

            return "Fins_Purpose_Info";
        }catch (Exception req_ex1){
            logger.info("WebController.FinsPurposeInfo -> ERROR: " + req_ex1);
            return "error";
        }
    }

    //Переход на экран Цели Редактор
    @RequestMapping(value = "/FinsPurposeAdd")
    public String GoToFinsPurposeAdd(@RequestParam(name = "ProjectId", required = false, defaultValue = "no_value") String ProjectId, Model model){
        try{
            logger.info("WebController.FinsPurposeAdd -> ");
            //Получение usercache
            Usercache usercache = GetUsercache();
            AppUser appUser = appUserRepository.GetMainUser(usercache.user_id);

            if (0 != ProjectId.compareTo("no_value")) {
                //Переход из левой понели проектов кликом по проекту
                SetActiveProjectUserCache(Integer.parseInt(ProjectId));
            }

            model.addAttribute("AccountMail",usercache.login);
            return "Fins_Purpose_Add";
        }catch (Exception req_ex1){
            logger.info("WebController.FinsPurposeAdd -> ERROR: " + req_ex1);
            return "error";
        }
    }

    //Переход на экран настройки пользователя
    @RequestMapping(value = "/UserSettings")
    public String GoToUserSettings(Model model){
        try{
            logger.info("WebController.GoToUserSettings -> ");
            //Получение usercache
            Usercache usercache = GetUsercache();
            AppUser appUser = appUserRepository.GetMainUser(usercache.user_id);
            String AdminFlg = "false";
            if(0==usercache.role.compareTo("USER")){AdminFlg="true";}

            logger.info("WebController.GoToUserSettings -> usercache.role: " + usercache.role.compareTo("USER"));

            model.addAttribute("attrUserFstName",appUser.getFirstName());
            model.addAttribute("attrUserLstName",appUser.getLastName());
            model.addAttribute("attrUserMdlName",appUser.getMiddleName());
            model.addAttribute("attrUserMail",appUser.getEmail());
            model.addAttribute("attrUserPhone",appUser.getPhone());
            model.addAttribute("attrAccessStatus",appUser.getAccess_status());
            model.addAttribute("attrAccessDt",appUser.getAccess_dt());
            model.addAttribute("attrUserAdmFlg",AdminFlg);
            return "Fins_Account_Settings";
        }catch (Exception req_ex1){
            logger.info("WebController.GoToUserSettings -> ERROR: " + req_ex1);
            return "error";
        }

    }

    //Переход на экран настройки пользователя
    @RequestMapping(value = "/UserSettingsInfo")
    public String GoToUserSettingsInfo(Model model){
        Usercache usercache = GetUsercache();
        model.addAttribute("AccountMail",usercache.login);
        return "Fins_Account_Settings_Info";
    }

    //Переход на экран Fins_Account_User_Info
    @RequestMapping(value = "/FinsAccountUsersInfo")
    public String GoToFinsAccountUsersInfo(Model model){
        try{
            logger.info("WebController.GoToFinsAccountUsersInfo -> ");
            //Получение usercache
            Usercache usercache = GetUsercache();
            List<AggrSubUser> aggrSubUserList = aggregateDataSubUser.GetSubUserList(usercache.user_id);

            model.addAttribute("sub_user_list",aggrSubUserList);
            model.addAttribute("AccountMail",usercache.login);
            return "Fins_Account_Users_Info";
        }catch (Exception req_ex1){
            logger.info("WebController.GoToFinsAccountUsersInfo -> ERROR: " + req_ex1);
            return "error";
        }
    }

    //Переход на экран Fins_Account_User_Add
    @RequestMapping(value = "/FinsAccountUsersAdd")
    public String GoToFinsAccountUsersAdd(Model model){
        Usercache usercache = GetUsercache();
        model.addAttribute("AccountMail",usercache.login);
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
        AppUser appUser = appUserRepository.GetUserByEmail(GetUserLogin());
        Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
        if(usercache == null){
            logger.info("ReqController.GetUserCache -> get null, insert");
            Usercacheform usercacheform = new Usercacheform();
            usercacheform.setLogin(GetUserLogin());
            usercacheform.setActiveProject(0);
            usercacheform.setRole(appUser.getRole().name());
            usercacheform.setUsercacheAction("insert");
            usercacheJdbc.UsercacheAction(usercacheform);
            usercache = usercacheRepository.GetUsercache(GetUserLogin());
        }
        return usercache;
    }

    //Обновить активный проект в Usercache
    private void SetActiveProjectUserCache(Integer ProjectId){
        try{
            Integer intUserId = appUserRepository.GetUserIdbyEmail(GetUserLogin());
            Usercacheform usercacheform = new Usercacheform();
            usercacheform.setLogin(GetUserLogin());
            usercacheform.setUserId(intUserId);
            usercacheform.setActiveProject(ProjectId);
            usercacheform.setUsercacheAction("update");
            usercacheJdbc.UsercacheAction(usercacheform);
        }catch (Exception ex_cach){
            logger.info("WebController.SetActiveProjectUserCache -> ERROR: " + ex_cach);
        }
    }

}

