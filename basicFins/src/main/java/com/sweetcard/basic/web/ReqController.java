package com.sweetcard.basic.web;


import com.google.gson.Gson;
import com.sweetcard.basic.dao.entities.*;
import com.sweetcard.basic.dao.jdbc.*;
import com.sweetcard.basic.dao.repository.*;
import com.sweetcard.basic.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by Admin on 15.02.2020.
 */
@Controller
@RequestMapping("/")
public class ReqController {
    private Logger logger = LoggerFactory.getLogger(ReqController.class);


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
            result.setText("");
            result.setCount(0);
            return result;
        }

    }

    //------Получение AJAX списка контрагентов---------------
    @RequestMapping(value = "/GetContragentsList", method = RequestMethod.GET)
    public @ResponseBody Response GetContragentsList(){
        try{
            logger.info("ReqController.GetContragentsList -> ");
            //Получение логина пользователя
            String strUserLogin = GetUserLogin();

            //Получить список ВСЕХ контрагентов
            List<Contragent> contragentList = contragentRepository.findAllByOrderByIdAsc();

            //Создать экземпляр ответа и отправить JSON строку
            Response result = new Response();
            Gson gson = new Gson();
            result.setText(gson.toJson(contragentList));
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
            //Получение логина пользователя
            String strUserLogin = GetUserLogin();

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
            logger.info("ReqController.GetContactFinsAcc -> " + ProjectId);
            //Получение логина пользователя
            String strUserLogin = GetUserLogin();
            Integer intProjectId = Integer.parseInt(ProjectId);

            //Получить список счетов сотрудников компаний проекта
            List<Contact> contactList = contactRepository.GetContactFinsAccProj(intProjectId);
            logger.info("ReqController.GetContactFinsAcc -> Size " + contactList.size());

            //Создать экземпляр ответа и отправить JSON строку
            Response result = new Response();
            Gson gson = new Gson();
            result.setText(gson.toJson(contactList));
            return result;

        }catch (Exception cag_ex){
            logger.info("ReqController.GetContactFinsAcc -> Error: " + cag_ex);
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
                                                //@RequestParam String Operation_Dt,
                                                @RequestParam String Amount,
                                                @RequestParam String Detail,
                                                @RequestParam String Fins_Transaction_Type,
                                                @RequestParam String Pay_Acc_In,
                                                @RequestParam String Pay_Acc_Out,
                                                @RequestParam String Fins_Article,
                                                //@RequestParam String ProjectId,
                                                @RequestParam String Contragent,
                                                @RequestParam String Requisite){
        //logger.info("ReqController.FinsOperation -> " + RecordOperation + " | " + Row_Id + " | " + Lock_Flg  + " | "
        //        + Amount + " | " + Detail + " | " + Fins_Transaction_Type + " | " + Pay_Acc_In + " | " + Pay_Acc_Out + " | " + Fins_Article
        //        + " | " + Contragent + " | " + Requisite);


        try{

            //Установка Id активного проекта
            Usercache usercache = usercacheRepository.GetUsercache(GetUserLogin());
            financedataJdbc.setActivProjectId(usercache.active_proj);

            Financedataform financedataform = new Financedataform();
            financedataform.setFinsedittype(RecordOperation);
            financedataform.setFinsOperType(Fins_Transaction_Type);
            financedataform.setFinsrecordid(Row_Id);
            financedataform.setFinsblockflg(Lock_Flg);
            financedataform.setFinsamount(Amount);
            financedataform.setFinsdetail(Detail);
            financedataform.setPaymentAccIn(Pay_Acc_In);
            financedataform.setPaymentAccOut(Pay_Acc_Out);
            financedataform.setFinsArticle(Fins_Article);
            //financedataform.setProjectId(ProjectId);
            financedataform.setFinscontragent(Contragent);
            financedataform.setRequisites(Requisite);
            switch(RecordOperation){
                case("update"):{
                    logger.info("ReqController.FinsOperation -> Case update: ");
                    if(RecordOperation == null){throw new Exception("RecordOperation IS NULL");}
                    financedataJdbc.RecordOperation(financedataform);
                }break;
                case("insert"):{
                    logger.info("ReqController.FinsOperation -> Case insert: ");
                    financedataJdbc.RecordOperation(financedataform);
                }break;
                case("delete"):{
                    logger.info("ReqController.FinsOperation -> Case delete: ");
                    financedataJdbc.RecordOperation(financedataform);
                }break;
                default:{

                }break;
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
                                                   @RequestParam String CompanyFinsAcc,
                                                   @RequestParam String CompanyProjectId)
    {
        logger.info("ReqController.OperationCompany -> " + DBOperation + "/" + CompanyId + "/" + CompanyName + "/" + CompanyFullName + "/" + CompanyINN + "/" + CompanyKPP + "/" + CompanyFinsAcc + "/" + CompanyProjectId);
        try{
            Companyform companyform = new Companyform();
            companyform.setCompanyAction(DBOperation);
            companyform.setCompanyId(CompanyId);
            companyform.setCompanyName(CompanyName);
            companyform.setCompanyFullName(CompanyFullName);
            companyform.setCompanyINN(CompanyINN);
            companyform.setCompanyKPP(CompanyKPP);
            companyform.setCompanyFinsAcc(CompanyFinsAcc);
            companyform.setCompanyOwner(GetUserLogin());
            companyform.setCompanyProjectId(CompanyProjectId);

            companyJdbc.CompanyAction(companyform);

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
            LovForm lovForm = new LovForm();
            lovForm.setLovAction(DBOperation);
            lovForm.setLovId(LovId);
            lovForm.setLovVal(LovValue);
            lovForm.setLovDescription(LovDescription);
            lovForm.setLovOptions(LovOptions);
            lovForm.setLovType(LovType);
            lovJdbc.LovAction(lovForm);

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
                logger.info("ReqController.GetUserCache -> get null");
                Usercacheform usercacheform = new Usercacheform();
                usercacheform.setLogin(GetUserLogin());
                usercacheform.setActiveProject(0);
                usercacheform.setUsercacheAction("insert");
                usercacheJdbc.UsercacheAction(usercacheform);
                usercache = usercacheRepository.GetUsercache(GetUserLogin());
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
    public @ResponseBody Response GetReport(@RequestParam String ReportName,
                                            @RequestParam String ProjectId) {
        try{
            logger.info("ReqController.GetReport -> ReportName: " + ReportName);
            Response result = new Response();
            switch (ReportName){
                case "GetProjectProfit":{
                    Integer intProjectId = Integer.parseInt(ProjectId);
                    String strProjectProfit = "";
                    String strProjectIncome = "";
                    String strProjectExpense = "";
                    String strJsonResult = "";

                    strProjectProfit = financedataRepository.GetProjectProfit(intProjectId);
                    strProjectIncome = financedataRepository.GetProjectIncome(intProjectId);
                    strProjectExpense = financedataRepository.GetProjectExpense(intProjectId);

                    strJsonResult = "{\"ProjectProfit\":" + strProjectProfit + ",\"ProjectIncome\":" + strProjectIncome + ",\"ProjectExpense\":" + strProjectExpense + "}";
                    //[{"id":0,"name":"Error","description":"Error"}]
                    result.setText(strJsonResult);
                }break;
                case "GetYearProfitList":{
                    logger.info("ReqController.GetReport -> GetYearProfitList: " + ProjectId);
                    List<AggrReport> aggrReport = aggregateDataReport.GetYearProjectProfit(Integer.parseInt(ProjectId));
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
            List<Financedata> financedataList = financedataRepository.GetAllByProj(intProjectId);
            logger.info("ReqController.GetProjectFinsOperationList -> row_count: " + financedataList.size());

            /*
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for(int i = 0; financedataList.size() > i; i++){
                Financedata financedata = financedataList.get(i);
                System.out.println(dateFormat.parse(financedata.operdate.toString()));
            }*/

            //Создать экземпляр ответа и отправить JSON строку
            Response result = new Response();
            Gson gson = new Gson();
            result.setText(gson.toJson(financedataList));
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
            logger.info("ReqController.GetLovList");
            //Получение логина пользователя
            String strUserLogin = GetUserLogin();
            //Получить список проектов пользователя
            List<Lov> lovList = lovRepository.findAllByOrderByIdAsc();
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
