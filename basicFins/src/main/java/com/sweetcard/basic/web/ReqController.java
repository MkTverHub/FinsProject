package com.sweetcard.basic.web;


import com.google.gson.Gson;
import com.sweetcard.basic.appuser.AppUser;
import com.sweetcard.basic.appuser.AppUserRepository;
import com.sweetcard.basic.dao.entities.*;
import com.sweetcard.basic.dao.jdbc.*;
import com.sweetcard.basic.dao.repository.*;
import com.sweetcard.basic.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by Admin on 15.02.2020.
 */
@Controller
@RequestMapping("/")
public class ReqController {
    private Logger logger = LoggerFactory.getLogger(ReqController.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(8);


    //---------------------Конструктор класса-----------------------
    public ReqController(){
        //
    }

    @Autowired
    RequisitRepository requisitRepository;
    @Autowired
    FinsprojectRepository finsprojectRepository;
    @Autowired
    FinsprojectJdbc finsprojectJdbc;
    @Autowired
    FinancedataRepository financedataRepository;
    @Autowired
    FinancedataJdbc financedataJdbc;
    @Autowired
    ContragentRepository contragentRepository;
    @Autowired
    ContragentJdbc contragentJdbc;
    @Autowired
    RequisitJdbc requisitJdbc;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanyJdbc companyJdbc;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    ContactJdbc contactJdbc;
    @Autowired
    LovRepository lovRepository;
    @Autowired
    LovJdbc lovJdbc;
    @Autowired
    AggregateDataReport aggregateDataReport;
    @Autowired
    UsercacheRepository usercacheRepository;
    @Autowired
    UsercacheJdbc usercacheJdbc;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    AggregateDataSubUser aggregateDataSubUser;
    @Autowired
    SubUserJdbc subUserJdbc;
    @Autowired
    UserJdbc userJdbc;
    @Autowired
    PurposeJdbc purposeJdbc;
    @Autowired
    PurposeRepository purposeRepository;

    @RequestMapping(value = "/SetContrAgentRequisits", method = RequestMethod.GET)
    public @ResponseBody Response GetRequisitsList(@RequestParam String ContragentId) {
        System.out.println("ReqController.GetRequisitsList -> " + ContragentId);
        try{
            //Получить реквизиты контрагента
            List<Requisits> requisitsList = requisitRepository.GetAllByContragent(Integer.parseInt(ContragentId));

            String strHtmlRequisits = "";
            //Получения html контекста
            for(int i = 0; i < requisitsList.size(); i++){
                Requisits requisits = requisitsList.get(i);
                strHtmlRequisits = strHtmlRequisits +
                        "<option value=\""+requisits.id+"\">"+requisits.name+"</option>";
            }

            Response result = new Response();
            if (strHtmlRequisits != null) {
                result.setText(strHtmlRequisits);
                result.setCount(strHtmlRequisits.length());
            }
            return result;
        }catch (Exception ex_1){
            System.out.println("ReqController.GetRequisitsList -> Error: " + ex_1);
            Response result = new Response();
            result.setText("<option value=\"Error sql\">Error sql</option>");
            result.setCount(0);
            return result;
        }
    }

    //--------------------Экран Финансовых операций---------------------
    //-------Получить список финансовых операций в рамках проекта
    @RequestMapping(value = "/GetProjFinsOperList", method = RequestMethod.GET)
    public @ResponseBody Response GetProjFinsOperList(@RequestParam String FinsProjectId){
        logger.info("ReqController.GetProjFinsOperList -> " + FinsProjectId);
        try{
            return GetProjectFinsOperationList(FinsProjectId);
        }catch (Exception ex_1){
            logger.info("ReqController.GetProjFinsOperList -> Error: " + ex_1);
            Response result = new Response();
            result.setText(null);
            result.setCount(0);
            return result;
        }

    }

    //------Получение AJAX списка контрагентов---------------
    @RequestMapping(value = "/GetContragentsList", method = RequestMethod.GET)
    public @ResponseBody Response GetContragentsList(){
        try{
            logger.info("ReqController.GetContragentsList -> ");
            Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
            Response result = new Response();
            Gson gson = new Gson();
            if(usercache.active_proj != 0){
                //Получить список ВСЕХ контрагентов
                List<Contragent> contragentList = contragentRepository.GetAllByProject(usercache.active_proj);
                result.setText(gson.toJson(contragentList));
            }
            return result;
        }catch (Exception cag_ex){
            logger.info("ReqController.GetContragentsList -> Error: " + cag_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }

    //------Получение AJAX списка реквезитов контрагента---------------
    @RequestMapping(value = "/GetContragentRequisits", method = RequestMethod.GET)
    public @ResponseBody Response GetContragentRequisits(@RequestParam String ContragentId){
        try{
            logger.info("ReqController.GetContragentRequisits -> ContragentId = " + ContragentId);
            //Получение списка реквизитов контрагента
            Integer intContragentId = Integer.parseInt(ContragentId);
            List<Requisits> requisitsList = requisitRepository.GetAllByContragent(intContragentId);
            //Создать экземпляр ответа и отправить JSON строку
            Response result = new Response();
            Gson gson = new Gson();
            result.setText(gson.toJson(requisitsList));
            return result;
        }catch (Exception cag_ex){
            logger.info("ReqController.GetContragentRequisits -> Error: " + cag_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }

    //------Получение AJAX списка счетов сотрудников компаний проекта---------------
    @RequestMapping(value = "/GetContactFinsAccProject", method = RequestMethod.GET)
    public @ResponseBody Response GetContactFinsAccProject(@RequestParam String ProjectId){
        try{
            logger.info("ReqController.GetContactFinsAccProject -> " + ProjectId);
            Integer intProjectId = Integer.parseInt(ProjectId);
            //Получить список счетов сотрудников компаний проекта
            List<Contact> contactList = contactRepository.GetContactFinsAccProj(intProjectId);
            logger.info("ReqController.GetContactFinsAccProject -> Size " + contactList.size());
            //Создать экземпляр ответа и отправить JSON строку
            Response result = new Response();
            Gson gson = new Gson();
            result.setText(gson.toJson(contactList));
            return result;
        }catch (Exception cag_ex){
            logger.info("ReqController.GetContactFinsAccProject -> Error: " + cag_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }

    //-------Объединенная точка работы с операцией--------------------------------
    @RequestMapping(value = "/FinsOperationForm", method = RequestMethod.GET)
    public @ResponseBody Response AjaxFinsOperationForm(@RequestParam String RecordOperation,
                                                @RequestParam String Row_Id,
                                                @RequestParam String Lock_Flg,
                                                @RequestParam String Operation_Dt_User,
                                                @RequestParam String Amount,
                                                @RequestParam String Detail,
                                                @RequestParam String Fins_Transaction_Type,
                                                @RequestParam String Pay_Acc_In,
                                                @RequestParam String Pay_Acc_Out,
                                                @RequestParam String Fins_Article,
                                                //@RequestParam String ProjectId,
                                                @RequestParam String Contragent,
                                                @RequestParam String Requisite){
        try{
            //Установка Id активного проекта
            Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
            if(usercache.active_proj != 0) {

                financedataJdbc.setActivProjectId(usercache.active_proj);

                Financedataform financedataform = new Financedataform();
                financedataform.setFinsedittype(RecordOperation);
                financedataform.setFinsOperType(Fins_Transaction_Type);
                financedataform.setFinsrecordid(Row_Id);
                financedataform.setFinsblockflg(Lock_Flg);
                financedataform.setFinsoperdateUser(Operation_Dt_User);
                financedataform.setFinsamount(Amount);
                financedataform.setFinsdetail(Detail);
                financedataform.setPaymentAccIn(Pay_Acc_In);
                financedataform.setPaymentAccOut(Pay_Acc_Out);
                financedataform.setFinsArticle(Fins_Article);
                //financedataform.setProjectId(ProjectId);
                financedataform.setFinscontragent(Contragent);
                financedataform.setRequisites(Requisite);
                switch (RecordOperation) {
                    case ("update"): {
                        logger.info("ReqController.FinsOperation -> Case update: ");
                        if (RecordOperation == null) {
                            throw new Exception("RecordOperation IS NULL");
                        }
                        financedataJdbc.RecordOperation(financedataform);
                    }
                    break;
                    case ("insert"): {
                        logger.info("ReqController.FinsOperation -> Case insert: ");
                        financedataJdbc.RecordOperation(financedataform);
                    }
                    break;
                    case ("delete"): {
                        logger.info("ReqController.FinsOperation -> Case delete: ");
                        financedataJdbc.RecordOperation(financedataform);
                    }
                    break;
                    default: {

                    }
                    break;
                }
            }

            Response result = new Response();
            return result;

        }catch (Exception fins_oper_ex){
            logger.info("ReqController.FinsOperation -> Error: " + fins_oper_ex);
            Response result = new Response();
            result.setText("Error " + fins_oper_ex);
            result.setCount(0);
            return result;
        }
    }

    //--------------------Экран Проектов---------------------------------
    //-------Объединенная точка работы с формой Проекта
    @RequestMapping(value = "/OperationFinsProject", method = RequestMethod.GET)
    public @ResponseBody Response DeleteFinsProject(@RequestParam String FinsProjectOperation,
                                                    @RequestParam String FinsProjectId,
                                                    @RequestParam String FinsProjectName,
                                                    @RequestParam String FinsProjectDescription) {
        logger.info("ReqController.OperationFinsProject -> " + FinsProjectOperation + " " + FinsProjectId + " " + FinsProjectName + " " + FinsProjectDescription);
        try{
            Finsprojectform finsprojectform = new Finsprojectform();
            finsprojectform.setFinsprojectid(FinsProjectId);
            finsprojectform.setFinsprojectname(FinsProjectName);
            finsprojectform.setFinsprojectdescription(FinsProjectDescription);
            switch (FinsProjectOperation) {
                case "new": {
                    finsprojectJdbc.NewFinsProject(finsprojectform);
                    logger.info("ReqController.OperationFinsProject -> new");
                    System.out.println("ReqController.OperationFinsProject -> insert");
                }
                break;
                case "delete": {
                    finsprojectJdbc.DeleteFinsProject(finsprojectform);
                    logger.info("ReqController.OperationFinsProject -> delete");
                    System.out.println("ReqController.OperationFinsProject -> delete");
                }
                break;
                case "edit": {
                    finsprojectJdbc.EditFinsProject(finsprojectform);
                    logger.info("ReqController.OperationFinsProject -> edit");
                    System.out.println("ReqController.OperationFinsProject -> edit");
                }
                case "ShareProject":{
                    logger.info("ReqController.OperationFinsProject -> ShareProject");
                    finsprojectJdbc.ShareProject(finsprojectform);
                }
                break;
                default:{
                    logger.info("ReqController.OperationFinsProject ERROR: -> Неизвестный тип операции " + FinsProjectOperation);
                }
            }


            //Получить Response с списком проектов пользователя
            return GetAllUserProject();

        }catch (Exception proj_oper_ex){
            logger.info("ReqController.OperationFinsProject -> Error: " + proj_oper_ex);
            Response result = new Response();
            result.setText("[{\"id\":0,\"name\":\"Error\",\"description\":\"Error\"}]");
            result.setCount(0);
            return result;
        }
    }

    //--------------------Экран Контрагентов---------------------------------
    //-------Объединенная точка работы с формой  Контрагента
    @RequestMapping(value = "/OperationFinsContragent", method = RequestMethod.GET)
    public @ResponseBody Response OperationFinsContragent(@RequestParam String DBOperation,
                                                    @RequestParam String ContragentId,
                                                    @RequestParam String ContragentName,
                                                    @RequestParam String ContragenDescription,
                                                    @RequestParam String ContragenPhone,
                                                    @RequestParam String ContragenMail,
                                                    @RequestParam String ContragenType)
    {
        logger.info("ReqController.OperationFinsContragent -> " + DBOperation + "/" + ContragentId + "/" + ContragentName + "/" + ContragenDescription + "/" + ContragenPhone + "/" + ContragenMail);
        try{
            //Установка Id активного проекта
            Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
            if(usercache.active_proj != 0) {
                contragentJdbc.setActivProjectId(usercache.active_proj);
                Contragentform contragentform = new Contragentform();
                contragentform.setContragentaction(DBOperation);
                contragentform.setContragentid(ContragentId);
                contragentform.setContragentname(ContragentName);
                contragentform.setContragentdescription(ContragenDescription);
                contragentform.setContragentphone(ContragenPhone);
                contragentform.setContragentemail(ContragenMail);
                contragentform.setContragentType(ContragenType);
                contragentJdbc.Contragentaction(contragentform);
            }
            //Просто устой ответ
            Response result = new Response();
            return result;

        }catch (Exception proj_oper_ex){
            logger.info("ReqController.OperationFinsContragent -> Error: " + proj_oper_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }

    //-------Объединенная точка работы с формой Реквезита
    @RequestMapping(value = "/OperationFinsRequisit", method = RequestMethod.GET)
    public @ResponseBody Response OperationFinsRequisit(@RequestParam String DBOperation,
                                                        @RequestParam Integer ContragentId,
                                                        @RequestParam Integer RequisitId,
                                                        @RequestParam Boolean MainFlg,
                                                        @RequestParam String RequisitName,
                                                        @RequestParam String RequisitDescription,
                                                        @RequestParam String RequisitINN,
                                                        @RequestParam String RequisitKPP,
                                                        @RequestParam String RequisitFinsAcc,
                                                        @RequestParam String RequisitFinsBIK,
                                                        @RequestParam String RequisitBankName,
                                                        @RequestParam String RequisitCrspAcc,
                                                        @RequestParam String RequisitAddrIndex,
                                                        @RequestParam String RequisitAddrCity,
                                                        @RequestParam String RequisitAddrString,
                                                        @RequestParam String RequisitPhoneNum,
                                                        @RequestParam String RequisitEmail,
                                                        @RequestParam String RequisitWebSite,
                                                        @RequestParam String RequisitCardNum)
    {
        logger.info("ReqController.OperationFinsRequisit -> DBOperation = " + DBOperation + ", ContragentId = " + ContragentId + ", RequisitId = " + RequisitId);
        try{
            Cntragntreqform cntragntreqform = new Cntragntreqform();
            cntragntreqform.setReqaction(DBOperation);
            cntragntreqform.setReqId(RequisitId);
            cntragntreqform.setContragentId(ContragentId);
            cntragntreqform.setBlMainFlg(MainFlg);
            cntragntreqform.setReqName(RequisitName);
            cntragntreqform.setReqDescription(RequisitDescription);
            cntragntreqform.setReqINN(RequisitINN);
            cntragntreqform.setReqKPP(RequisitKPP);
            cntragntreqform.setReqFinsAcc(RequisitFinsAcc);
            cntragntreqform.setReqBIK(RequisitFinsBIK);
            cntragntreqform.setReqBankName(RequisitBankName);
            cntragntreqform.setReqCrspAcc(RequisitCrspAcc);
            cntragntreqform.setReqAddrIndex(RequisitAddrIndex);
            cntragntreqform.setReqAddrCity(RequisitAddrCity);
            cntragntreqform.setReqAddrString(RequisitAddrString);
            cntragntreqform.setReqPhoneNum(RequisitPhoneNum);
            cntragntreqform.setReqEmail(RequisitEmail);
            cntragntreqform.setReqWebSite(RequisitWebSite);
            cntragntreqform.setCardNumber(RequisitCardNum);

            requisitJdbc.Requisitaction(cntragntreqform);

            //Просто устой ответ
            Response result = new Response();
            return result;

        }catch (Exception req_oper_ex){
            logger.info("ReqController.OperationFinsRequisit -> Error: " + req_oper_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }

    //--------------------Экран Компании---------------------------------
    //------Получение списка компаний-----------------------------------
    @RequestMapping(value = "/GetCompanyList", method = RequestMethod.GET)
    public @ResponseBody Response GetCompanyList() {
        return GetOwnerCompanyList();
    }
    //------Получение списка сотрудников компании-----------------------------------
    @RequestMapping(value = "/GetCompanyEmployees", method = RequestMethod.GET)
    public @ResponseBody Response GetCompanyContacts(@RequestParam String CompanyId) {
        return GetCompanyContactsList(CompanyId);
    }


    //-------Объединенная точка работы с формой Компании
    @RequestMapping(value = "/OperationCompany", method = RequestMethod.GET)
    public @ResponseBody Response OperationCompany(@RequestParam String DBOperation,
                                                   @RequestParam String CompanyId,
                                                   @RequestParam String CompanyName,
                                                   @RequestParam String CompanyFullName,
                                                   @RequestParam String CompanyINN,
                                                   @RequestParam String CompanyKPP,
                                                   @RequestParam String CompanyFinsAcc)
    {
        //logger.info("ReqController.OperationCompany -> " + DBOperation + "/" + CompanyId + "/" + CompanyName + "/" + CompanyFullName + "/" + CompanyINN + "/" + CompanyKPP + "/" + CompanyFinsAcc + "/" + CompanyProjectId);
        try{
            //Установка Id активного проекта
            Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
            if(usercache.active_proj != 0) {
                companyJdbc.setActivProjectId(usercache.active_proj);
                Companyform companyform = new Companyform();
                companyform.setCompanyAction(DBOperation);
                companyform.setCompanyId(CompanyId);
                companyform.setCompanyName(CompanyName);
                companyform.setCompanyFullName(CompanyFullName);
                companyform.setCompanyINN(CompanyINN);
                companyform.setCompanyKPP(CompanyKPP);
                companyform.setCompanyFinsAcc(CompanyFinsAcc);
                companyform.setCompanyOwner(GetUserLogin());
                companyJdbc.CompanyAction(companyform);
            }

            //Просто устой ответ
            Response result = new Response();
            return result;

        }catch (Exception company_oper_ex){
            logger.info("ReqController.OperationCompany -> Error: " + company_oper_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }

    //-------Объединенная точка работы с формой Контакта
    @RequestMapping(value = "/OperationContact", method = RequestMethod.GET)
    public @ResponseBody Response OperationContact(@RequestParam String DBOperation,
                                                   @RequestParam String ContactId,
                                                   @RequestParam String ContactFirstName,
                                                   @RequestParam String ContactLastName,
                                                   @RequestParam String ContactFinsAcc,
                                                   @RequestParam String ContactBalance,
                                                   @RequestParam String ContactDescription,
                                                   @RequestParam String ContactCompanyId)
    {
        logger.info("ReqController.OperationContact -> " + DBOperation + "/" + ContactId + "/" + ContactFirstName + "/" + ContactLastName + "/" + ContactFinsAcc + "/" + ContactBalance + "/" + ContactCompanyId);
        try{
            Integer intContactCompanyId = Integer.parseInt(ContactCompanyId);
            Integer intBalance = Integer.parseInt(ContactBalance);

            Contactform contactform = new Contactform();
            contactform.setContactAction(DBOperation);
            contactform.setContactId(ContactId);
            contactform.setContactFirstName(ContactFirstName);
            contactform.setContactLastName(ContactLastName);
            contactform.setContactFinsAcc(ContactFinsAcc);
            contactform.setContactBalance(intBalance);
            contactform.setContactDescription(ContactDescription);
            contactform.setContactParRowId(intContactCompanyId);
            contactJdbc.ContactAction(contactform);

            //Просто устой ответ
            Response result = new Response();
            return result;

        }catch (Exception contact_oper_ex){
            logger.info("ReqController.OperationContact -> Error: " + contact_oper_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }

    //--------------------Экран LOV---------------------------------
    //------Получение списка компаний-----------------------------------
    @RequestMapping(value = "/GetLovList", method = RequestMethod.GET)
    public @ResponseBody Response GetLovList() {
        return GetLovListResponse();
    }

    //-------Объединенная точка работы с формой LOV
    @RequestMapping(value = "/OperationLov", method = RequestMethod.GET)
    public @ResponseBody Response OperationLov(@RequestParam String DBOperation,
                                               @RequestParam String LovId,
                                               @RequestParam String LovValue,
                                               @RequestParam String LovDescription,
                                               @RequestParam String LovOptions,
                                               @RequestParam String LovType)
    {
        logger.info("ReqController.OperationLov -> " + DBOperation + "/" + LovId + "/" + LovValue + "/" + LovDescription + "/" + LovOptions + "/" + LovType);
        try{
            //Установка Id активного проекта
            Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
            if(usercache.active_proj != 0) {
                lovJdbc.setActiveProjectId(usercache.active_proj);
                LovForm lovForm = new LovForm();
                lovForm.setLovAction(DBOperation);
                lovForm.setLovId(LovId);
                lovForm.setLovVal(LovValue);
                lovForm.setLovDescription(LovDescription);
                lovForm.setLovOptions(LovOptions);
                lovForm.setLovType(LovType);
                lovJdbc.LovAction(lovForm);
            }
            //Просто устой ответ
            Response result = new Response();
            return result;

        }catch (Exception lov_oper_ex){
            logger.info("ReqController.OperationLov -> Error: " + lov_oper_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }

    //--------------------Экран ЦЕЛИ---------------------------------
    //------Получение списка компаний-----------------------------------
    @RequestMapping(value = "/GetPurposeList", method = RequestMethod.GET)
    public @ResponseBody Response GetPurposeList() {
        try{
            logger.info("ReqController.GetPurposeList");
            Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
            purposeJdbc.setActiveProjectId(usercache.active_proj);
            List<Purpose> purposeList = purposeRepository.GetAllByProject(usercache.active_proj);
            //Создать экземпляр ответа и отправить JSON строку
            Response result = new Response();
            Gson gson = new Gson();
            result.setText(gson.toJson(purposeList));
            return result;
        }catch (Exception get_purpose_list_ex){
            logger.info("ReqController.GetPurposeList -> Error: " + get_purpose_list_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }
    //-------Объединенная точка работы с формой ЦЕЛИ
    @RequestMapping(value = "/OperationPurpose", method = RequestMethod.GET)
    public @ResponseBody Response OperationPurpose(@RequestParam String DBOperation,
                                                   @RequestParam String PurposeParRowId,
                                                   @RequestParam String PurposeId,
                                                   @RequestParam String PurposeName,
                                                   @RequestParam String PurposeDescription,
                                                   @RequestParam String PurposeExpense,
                                                   @RequestParam String PurposeProfit)
    {
        logger.info("ReqController.OperationPurpose -> " + DBOperation + "/" + PurposeParRowId + "/" + PurposeId + "/" + PurposeName + "/" + PurposeDescription + "/" + PurposeExpense  + "/" + PurposeProfit);
        try{
            //Установка Id активного проекта
            Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
            if(usercache.active_proj != 0) {
                Integer intId = 0;
                if(PurposeId.compareTo("")!=0){
                    intId = Integer.parseInt(PurposeId);
                }

                purposeJdbc.setActiveProjectId(usercache.active_proj);
                PurposeForm purposeForm = new PurposeForm();
                purposeForm.setPurposeAction(DBOperation);
                purposeForm.setId(intId);
                purposeForm.setName(PurposeName);
                purposeForm.setDescription(PurposeDescription);
                purposeForm.setExpense(PurposeExpense);
                purposeForm.setProfit(PurposeProfit);
                purposeJdbc.PurposeAction(purposeForm);
            }
            //Просто устой ответ
            Response result = new Response();
            return result;

        }catch (Exception lov_oper_ex){
            logger.info("ReqController.OperationPurpose -> Error: " + lov_oper_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }


    //--------------------Экран Дочерних пользователей---------------------------------
    //------Получение списка Дочерних пользователей-----------------------------------
    @RequestMapping(value = "/GetSubUserList", method = RequestMethod.GET)
    public @ResponseBody Response GetSubUserList() {
        try{
            logger.info("ReqController.GetSubUserList ");
            Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
            List<AppUser> appUserList = appUserRepository.GetSubUserList(usercache.user_id);

            //Создать экземпляр ответа и отправить JSON строку
            Response result = new Response();
            Gson gson = new Gson();
            result.setText(gson.toJson(appUserList));
            return result;
        }catch (Exception ex_rep_1){
            logger.info("ReqController.GetSubUserList -> Error: " + ex_rep_1);
            return null;
        }
    }

    //-------Объединенная точка работы с формой SubUser
    @RequestMapping(value = "/OperationSubUser", method = RequestMethod.GET)
    public @ResponseBody Response OperationSubUser(@RequestParam String DBOperation,
                                                   @RequestParam String SubUserId,
                                                   @RequestParam String SubUserFstName,
                                                   @RequestParam String SubUserLstName,
                                                   @RequestParam String SubUserMdlName,
                                                   @RequestParam String SubUserPhone,
                                                   @RequestParam String SubUserPosition,
                                                   @RequestParam String SubUserEmail,
                                                   @RequestParam String SubUserPassword)
    {
        try{
            logger.info("ReqController.OperationSubUser -> " + DBOperation);
            Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
            Integer intActiveProject = usercache.active_proj;
            Integer intUserId = usercache.user_id;
            String encodedPassword = bCryptPasswordEncoder.encode(SubUserPassword);
            Integer intSubUserId;
            Integer intCountSubUser = aggregateDataSubUser.GetSubUserCount(intUserId);

            if(intCountSubUser < 5 && 0==usercache.role.compareTo("USER")) {

                if(SubUserId == null){SubUserId="";}
                if (0 == SubUserId.compareTo("")) {
                    intSubUserId = 0;
                } else {
                    intSubUserId = Integer.parseInt(SubUserId);
                }

                SubUserForm subUserForm = new SubUserForm();
                subUserForm.setDBOperation(DBOperation);
                subUserForm.setId(intSubUserId);
                subUserForm.setAppUserRole("SUB_USER");
                subUserForm.setEmail(SubUserEmail);
                subUserForm.setEnabled(true);
                subUserForm.setFirstName(SubUserFstName);
                subUserForm.setLastName(SubUserLstName);
                subUserForm.setMiddleName(SubUserMdlName);
                subUserForm.setPhone(SubUserPhone);
                subUserForm.setPosition(SubUserPosition);
                subUserForm.setLocked(false);
                subUserForm.setParentId(intUserId);
                subUserForm.setPassword(encodedPassword);

                subUserJdbc.SubUserAction(subUserForm);
            }

            //Просто устой ответ
            Response result = new Response();
            return result;

        }catch (Exception lov_oper_ex){
            logger.info("ReqController.OperationSubUser -> Error: " + lov_oper_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }

    //--------------------Экран Настройки пользователя---------------------------------
    //------Получить пользователя---------------------------------
    @RequestMapping(value = "/OperationUser", method = RequestMethod.GET)
    public @ResponseBody Response GetUserInfo(@RequestParam String DBOperation,
                                              @RequestParam Integer UserId,
                                              @RequestParam Integer UserParentId,
                                              @RequestParam String UserFstName,
                                              @RequestParam String UserLstName,
                                              @RequestParam String UserMdlName,
                                              @RequestParam String UserPhone,
                                              @RequestParam String UserPosition,
                                              @RequestParam String UserEmail,
                                              @RequestParam String UserPassword,
                                              @RequestParam String UserLocked,
                                              @RequestParam String UserAccessDt,
                                              @RequestParam String UserAccessStatus)
    {
        try{
            logger.info("ReqController.OperationUser ");
            //Создать экземпляр ответа и отправить JSON строку
            Response result = new Response();
            Gson gson = new Gson();
            switch(DBOperation) {
                case "GetUser": {
                    AppUser appUser = appUserRepository.GetUserByEmail(GetUserLogin());
                    appUser.Decode();
                    result.setText(gson.toJson(appUser));
                    System.out.println(gson.toJson(appUser));
                }
                break;
                default: {
                    UserForm userForm= new UserForm();
                    userForm.setUserAction(DBOperation);
                    userForm.setEmail(GetUserLogin());
                    userForm.setFirstName(UserFstName);
                    userForm.setLastName(UserLstName);
                    userForm.setMiddleName(UserMdlName);
                    userForm.setPhone(UserPhone);
                    userJdbc.UserAction(userForm);
                    AppUser appUser = appUserRepository.GetUserByEmail(GetUserLogin());
                    result.setText(gson.toJson(appUser));
                }
            }

            return result;
        }catch (Exception ex_rep_1){
            logger.info("ReqController.OperationUser -> Error: " + ex_rep_1);
            return null;
        }
    }


    //--------------Общие методы для экранов--------------------------------
    //------Получение списка проектов (вызов при загрузке страницы) used: Fins_Operations,Fins_Projects
    @RequestMapping(value = "/GetFinsProjectList", method = RequestMethod.GET)
    public @ResponseBody Response GetProjectList() {
        //Получить Response с списком проектов пользователя
        return GetAllUserProject();

    }

    //------Получение активного проекта пользователя
    @RequestMapping(value = "/GetUserCache", method = RequestMethod.GET)
    public @ResponseBody Response GetUserCache() {
        try{
            Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
            if(usercache == null){
                //Установка дефолтного проекта
                Finsprojectform finsprojectform = new Finsprojectform();
                finsprojectform.setFinsprojectname("Начало работы");
                finsprojectform.setFinsprojectdescription("Проект по уолчанию");
                Integer intNewProjectId = finsprojectJdbc.NewFinsProject(finsprojectform);
                Integer intUserId = appUserRepository.GetUserIdbyEmail(GetUserLogin());
                AppUser appUser = appUserRepository.GetUserByEmail(GetUserLogin());
                String strUserRole = appUser.getRole().name();

                Usercacheform usercacheform = new Usercacheform();
                usercacheform.setLogin(GetUserLogin());
                usercacheform.setActiveProject(intNewProjectId);
                usercacheform.setUserId(intUserId);
                usercacheform.setRole(strUserRole);
                usercacheform.setUsercacheAction("insert");
                usercacheJdbc.UsercacheAction(usercacheform);
                usercache = usercacheRepository.GetUsercache(GetUserLogin());
                logger.info("ReqController.GetUserCache -> New UserCache " + GetUserLogin() + " " + intNewProjectId + " " + strUserRole);
            }

            //Создать экземпляр ответа и отправить JSON строку
            Response result = new Response();
            Gson gson = new Gson();
            result.setText(gson.toJson(usercache));
            return result;
        }catch (Exception ex_rep){
            logger.info("ReqController.GetUserCache -> Error: " + ex_rep);
            return null;
        }

    }

    //-------Объединенная точка работы с формой UserCache
    @RequestMapping(value = "/OperationUserCache", method = RequestMethod.GET)
    public @ResponseBody Response OperationUserCache(@RequestParam String ActiveProjectId)
    {
        logger.info("ReqController.OperationUserCache -> ");
        try{
            Usercacheform usercacheform = new Usercacheform();
            usercacheform.setLogin(GetUserLogin());
            usercacheform.setActiveProject(Integer.parseInt(ActiveProjectId));
            usercacheform.setUsercacheAction("update");
            usercacheJdbc.UsercacheAction(usercacheform);

            //Просто устой ответ
            Response result = new Response();
            return result;
        }catch (Exception lov_oper_ex){
            logger.info("ReqController.OperationUserCache -> Error: " + lov_oper_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }



    //------Получение отчетов
    @RequestMapping(value = "/GetReport", method = RequestMethod.GET)
    public @ResponseBody Response GetReport(@RequestParam String ReportName) {
        try{
            logger.info("ReqController.GetReport -> ReportName: " + ReportName);
            Response result = new Response();
            //Установка Id активного проекта
            Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
            Integer intProjectId = usercache.active_proj;

            switch (ReportName){
                case "GetProjectProfit":{
                    String strProjectProfit = "";
                    String strProjectIncome = "";
                    String strProjectExpense = "";
                    String strJsonResult = "";
                    strProjectProfit = financedataRepository.GetProjectProfit(intProjectId);
                    strProjectIncome = financedataRepository.GetProjectIncome(intProjectId);
                    strProjectExpense = financedataRepository.GetProjectExpense(intProjectId);
                    strJsonResult = "{\"ProjectProfit\":" + strProjectProfit + ",\"ProjectIncome\":" + strProjectIncome + ",\"ProjectExpense\":" + strProjectExpense + "}";
                    result.setText(strJsonResult);
                }break;
                case "GetYearProfitList":{
                    logger.info("ReqController.GetReport -> GetYearProfitList: ");
                    List<AggrReport> aggrReport = aggregateDataReport.GetYearProjectProfit(intProjectId);
                    Gson gson = new Gson();
                    result.setText(gson.toJson(aggrReport));
                }
            }
            return result;
        }catch (Exception ex_rep){
            logger.info("ReqController.GetReport -> Error: " + ex_rep);
            return null;
        }
    }



    //--------------------Внутринние методы---------------------------------

    //Получить Ajax Response с списком всех проектов пользователя в виде JSON строки
    private Response GetAllUserProject(){
        try{
            logger.info("ReqController.GetAllUserProject");
            //Получение логина пользователя
            String strUserLogin = GetUserLogin();

            //Получить список проектов пользователя
            List<Finsproject> finsprojectlist = finsprojectRepository.GetAllUserProjects(strUserLogin);

            //Создать экземпляр ответа и отправить JSON строку
            Response result = new Response();
            Gson gson = new Gson();
            result.setText(gson.toJson(finsprojectlist));

            return result;
        }catch (Exception get_project_list_ex){
            logger.info("ReqController.GetProjectList -> Error: " + get_project_list_ex);
            Response result = new Response();
            result.setText("[{\"id\":0,\"name\":\"Error\",\"description\":\"Error\"}]");
            result.setCount(0);
            return result;
        }
    }

    //Получить Ajax Response с списком всех финансовых операций проекта в виде JSON строки
    private Response GetProjectFinsOperationList(String FinsProjectId){
        try{
            logger.info("ReqController.GetProjectFinsOperationList -> Project: " + FinsProjectId);
            //Получение логина пользователя
            String strUserLogin = GetUserLogin();
            Integer intProjectId = Integer.parseInt(FinsProjectId);
            //Получить список финансовых операций по проекту
            List<AggrFinsdata> financedataList = financedataRepository.GetAllByProj(intProjectId);
            logger.info("ReqController.GetProjectFinsOperationList -> row_count: " + financedataList.size());

            //Создать экземпляр ответа и отправить JSON строку
            Response result = new Response();
            Gson gson = new Gson();
            result.setText(gson.toJson(financedataList));
            logger.info("ReqController.GetProjectFinsOperationList -> gson: " + gson.toJson(financedataList));
            return result;
        }catch (Exception get_fins_oper_list_ex){
            logger.info("ReqController.GetProjectFinsOperationList -> Error: " + get_fins_oper_list_ex);
            Response result = new Response();
            result.setText("Error");
            result.setCount(0);
            return result;
        }
    }

    //Получить список компаний пользователя
    private Response GetOwnerCompanyList(){
        try{
            logger.info("ReqController.GetOwnerCompanyList");
            //Получение логина пользователя
            String strUserLogin = GetUserLogin();

            //Получить список проектов пользователя
            List<Company> companyList = companyRepository.GetAllByOwner(strUserLogin);

            //Создать экземпляр ответа и отправить JSON строку
            Response result = new Response();
            Gson gson = new Gson();
            result.setText(gson.toJson(companyList));

            return result;
        }catch (Exception get_project_list_ex){
            logger.info("ReqController.GetOwnerCompanyList -> Error: " + get_project_list_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }

    //Получить список работников компании
    private Response GetCompanyContactsList(String CompanyId){
        try{
            logger.info("ReqController.GetCompanyContactsList");
            //Получение логина пользователя
            String strUserLogin = GetUserLogin();

            Integer intCompanyId = Integer.parseInt(CompanyId);

            //Получить список проектов пользователя
            List<Contact> contactList = contactRepository.GetAllByCompany(intCompanyId);

            //Создать экземпляр ответа и отправить JSON строку
            Response result = new Response();
            Gson gson = new Gson();
            result.setText(gson.toJson(contactList));

            return result;
        }catch (Exception get_project_list_ex){
            logger.info("ReqController.GetCompanyContactsList -> Error: " + get_project_list_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }

    //Получить список LOV
    private Response GetLovListResponse(){
        try{
            logger.info("ReqController.GetLovList + Задержка 4 сек для демонстрации спиннера");
            TimeUnit.SECONDS.sleep(2);//Демонстрация спиннера
            Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
            lovJdbc.setActiveProjectId(usercache.active_proj);
            List<Lov> lovList = lovRepository.GetAllByProject(usercache.active_proj);
            //Создать экземпляр ответа и отправить JSON строку
            Response result = new Response();
            Gson gson = new Gson();
            result.setText(gson.toJson(lovList));
            return result;
        }catch (Exception get_lov_list_ex){
            logger.info("ReqController.GetLovList -> Error: " + get_lov_list_ex);
            Response result = new Response();
            result.setText("");
            result.setCount(0);
            return result;
        }
    }



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
