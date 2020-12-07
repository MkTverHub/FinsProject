package com.sweetcard.basic.web;

import com.sweetcard.basic.dao.entities.Contragent;
import com.sweetcard.basic.dao.entities.Financedata;
import com.sweetcard.basic.dao.entities.Finsproject;
import com.sweetcard.basic.dao.entities.Requisits;
import com.sweetcard.basic.dao.jdbc.ContragentJdbc;
import com.sweetcard.basic.dao.jdbc.FinsprojectJdbc;
import com.sweetcard.basic.dao.jdbc.RequisitJdbc;
import com.sweetcard.basic.dao.repository.ContragentRepository;
import com.sweetcard.basic.dao.repository.FinsprojectRepository;
import com.sweetcard.basic.dao.repository.RequisitRepository;
import com.sweetcard.basic.model.Cntragntreqform;
import com.sweetcard.basic.model.Contragentform;
import com.sweetcard.basic.model.Financedataform;
import com.sweetcard.basic.dao.jdbc.FinancedataJdbc;
import com.sweetcard.basic.dao.repository.FinancedataRepository;
import com.sweetcard.basic.model.Finsprojectform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Controller
public class WebController {
    private Logger logger = LoggerFactory.getLogger(WebController.class);
    //@Autowired
    //CustomerRepository customerRepository;
    @Autowired
    FinancedataRepository financedataRepository;
    @Autowired
    FinsprojectRepository finsprojectRepository;
    @Autowired
    ContragentRepository contragentRepository;
    @Autowired
    RequisitRepository requisitRepository;
    @Autowired
    FinancedataJdbc financedataJdbc;
    @Autowired
    FinsprojectJdbc finsprojectJdbc;
    @Autowired
    ContragentJdbc contragentJdbc;
    @Autowired
    RequisitJdbc requisitJdbc;

    //Здесь вход на app
    @GetMapping({"/", "/index","/FinsOperations"})
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "весна ботинок") String name, Model model)
    {
        return "Fins_Operations";
    }

    //Переход на страницу Проектов
    @RequestMapping(value = "/Projects", method = RequestMethod.POST)
    public String GoToProjects(Model model){
        logger.info("WebController.GoToProjects -> ");
        List<Finsproject> finsprojectList = finsprojectRepository.GetAllUserProjects(GetUserLogin());
        model.addAttribute("finsprojectList",finsprojectList);
        return "Fins_Projects_Add";
    }
    //Переход на страницу Проектов (Редактор)
    @RequestMapping(value = "/ProjectsEditor", method = RequestMethod.POST)
    public String GoToProjectsEditor(Model model){
        logger.info("WebController.GoToProjectsEditor -> ");
        return "Fins_Projects";
    }

    //Переход на страницу Финвнсовых операций
    @RequestMapping(value = "/FinsOperations", method = RequestMethod.POST)
    public String GoToFinsOperations(Model model){
        logger.info("WebController.GoToFinsOperations -> ");
        return "Fins_Operations";
    }

    //Переход на страницу LOV
    //@RequestMapping(value = "/FinsLOV", method = RequestMethod.POST)
    @RequestMapping(value = "/FinsLOV")
    public String GoToFinsLOV(Model model){
        logger.info("WebController.GoToFinsLOV -> ");
        return "Fins_LOV";
    }

    //Переход на страницу контрагентов (Плитка)
    @RequestMapping(value = "/Contragents", method = RequestMethod.POST)
    public String GoToContragents(Model model){
        try{
            logger.info("WebController.GoToContragents -> ");
            List<Contragent> contragentList = contragentRepository.findAllByOrderByIdAsc();
            model.addAttribute("contragentList",contragentList);
            return "Fins_Contragents_Add";
        }catch (Exception req_ex1){
            logger.info("WebController.GoToContragents -> ERROR: " + req_ex1);
            return "error";
        }
    }

    //Переход на страницу контрагентов (Редактор)
    @RequestMapping(value = "/ContragentsEditor", method = RequestMethod.POST)
    public String GoToContragentsEditor(Model model){
        try{
            logger.info("WebController.ContragentsEditor -> ");
            return "Fins_Contragents";
        }catch (Exception req_ex1){
            logger.info("WebController.ContragentsEditor -> ERROR: " + req_ex1);
            return "error";
        }
    }

    //Переход на страницу Компании
    //@RequestMapping(value = "/FinsCompany", method = RequestMethod.POST)
    @RequestMapping(value = "/FinsCompany")
    public String GoToFinsCompany(Model model){
        try{
            logger.info("WebController.GoToFinsCompany -> ");
            return "Fins_Company";
        }catch (Exception req_ex1){
            logger.info("WebController.GoToFinsCompany -> ERROR: " + req_ex1);
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
}

