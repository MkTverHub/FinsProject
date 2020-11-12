/**
 * Created by IVRibin on 20.03.2020.
 */
//Функция при загрузки страницы
function StartPage() {
    doAjaxGetProjectList();//Получение списка проектов в левой панели
    doAjaxGetContragentsList();//Заполнение таблицы контрагентов
    document.body.HashData = {ActiveContragentId:''};
    SetROContragentForm();
    SetRORequisitForm();
};

//-------------Функции событий-----------------
//Событие нажатия на строку контрагента
$(function(){
    var strDBOperation = 'update';//update/insert/delete
    var strContragentId = '';
    var strContragentName = '';
    var strContragenDescription = '';
    var strContragenPhone = '';
    var strContragenMail = '';
    $("#contr_agent_table_body").on("click", ".contragentlist_row", function () {
        UnSetROContragentForm();
        strContragentId = $(this).find('.cntr_id').attr('value');
        strContragentName = $(this).find('.cntr_name').attr('value');
        strContragenDescription = $(this).find('.cntr_description').attr('value');
        strContragenPhone = $(this).find('.cntr_phone_num').attr('value');
        strContragenMail = $(this).find('.cntr_email_addr').attr('value');

        $('#cntragnt_db_action').attr('value',strDBOperation);//update/insert/delete
        $('#cntragnt_id').attr('value',strContragentId);
        $('#cntragnt_name').attr('value',strContragentName);
        $('#cntragnt_desqription').attr('value',strContragenDescription);
        $('#cntragnt_mail').attr('value',strContragenMail);
        $('#cntragnt_phone').attr('value',strContragenPhone);

        $('#cntragnt_name').val(strContragentName);
        $('#cntragnt_desqription').val(strContragenDescription);
        $('#cntragnt_mail').val(strContragenMail);
        $('#cntragnt_phone').val(strContragenPhone);

        //Обновить список реквезитов
        document.body.HashData = {ActiveContragentId:strContragentId};//Устанавливаем Id активного контрагента
        doAjaxGetContragentRequisits(strContragentId);
    });
});

//Событие нажатия на кнопку "Сохранить" контрагента
function SaveContragent(){
    doAjaxContragentDBOperation();
    ClearContragentForm();
    SetROContragentForm();
};
//Событие нажатия на кнопку "Удалить" контрагента
function DeleteContragent(){
    $('#cntragnt_db_action').attr('value','delete');//update/insert/delete
    doAjaxContragentDBOperation();
    ClearContragentForm();
    SetROContragentForm();
};
//Событие нажатия на кнопку "Создать" контрагента
function InsertContragent(){
    UnSetROContragentForm();
    ClearContragentForm();
    $('#cntragnt_db_action').attr('value','insert');//update/insert/delete
};
//Событие нажатия на кнопку "Отменить" контрагента
function ResetContragent(){
    ClearContragentForm();
    SetROContragentForm();
};


//Событие нажатия на кнопку "Сохранить" Реквезит
function SaveContragentReq(){
    doAjaxRequisitDBOperation();
    ClearRequisitForm();
    SetRORequisitForm();
};
//Событие нажатия на кнопку "Создать" Реквезит
function InsertContragentReq(){
    UnSetRORequisitForm();
    ClearRequisitForm();
    $('#requisit_db_action').attr('value','insert');//update/insert/delete
};
//Событие нажатия на кнопку "Удалить" Реквезит
function DeleteContragentReq(){
    $('#requisit_db_action').attr('value','delete');//update/insert/delete
    doAjaxRequisitDBOperation();
    ClearRequisitForm();
    SetRORequisitForm();
};
//Событие нажатия на кнопку "Отменить" Реквезит
function ResetContragentReq(){
    ClearRequisitForm();
    SetRORequisitForm();
};

//Событие нажатия на строку реквизита
$(function(){
    $("#tb_cntragnt_requisits").on("click", ".requisitslist_row", function () {
        UnSetRORequisitForm();
        $('#requisit_db_action').attr('value','update');
        $('#requisit_id').attr('value',$(this).find('.cntr_req_id').attr('value'));
        $('#requisit_name').val($(this).find('.cntr_req_name').attr('value'));
        $('#requisit_description').val($(this).find('.cntr_req_description').attr('value'));
        $('#requisit_ReqINN').val($(this).find('.cntr_req_inn').attr('value'));
        $('#requisit_ReqKPP').val($(this).find('.cntr_req_kpp').attr('value'));
        $('#requisit_ReqFinsAcc').val($(this).find('.cntr_req_acc').attr('value'));
        $('#requisit_ReqBIK').val($(this).find('.cntr_req_bik').attr('value'));
        $('#requisit_ReqBankName').val($(this).find('.bank_name').attr('value'));
        $('#requisit_ReqCrspAcc').val($(this).find('.cntr_req_crsp_acc').attr('value'));
        $('#requisit_ReqAddrIndex').val($(this).find('.cntr_req_addr_index').attr('value'));
        $('#requisit_ReqAddrCity').val($(this).find('.cntr_req_addr_city').attr('value'));
        $('#requisit_ReqAddrString').val($(this).find('.cntr_req_addr_string').attr('value'));
        $('#requisit_ReqPhoneNum').val($(this).find('.cntr_req_phone_num').attr('value'));
        $('#requisit_ReqEmail').val($(this).find('.cntr_req_email_addr').attr('value'));
        $('#requisit_ReqWebSite').val('');
    });
});


//-----------Ajax Functions------------
//Ajax получение списка проектов в левой панели
function doAjaxGetProjectList(){
    $.ajax({
        url : 'GetFinsProjectList',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({

        }),
        success: function (data) {
            var strProjectListContext = "";
            var obj = jQuery.parseJSON(data.text);
            $.each(obj, function (index, value) {
                strProjectListContext = strProjectListContext
                    + '<li id="' + value["id"].toString()
                    + '_rowid" class="left-menu-item finsproject_list_row"><input type="button" class="left-menu-link finsproject_list_row" projnum="'
                    + value["id"].toString() + '" value="' + value["name"] + '"/></li>';
            });
            $("#projectlistpanel").html(strProjectListContext);
        }
    });
};

//Ajax получение списка Контрагентов
function doAjaxGetContragentsList() {
    $.ajax({
        url : 'GetContragentsList',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            //
        }),
        success: function (data) {
            JSONStringToContragentTable(data.text);
        }
    });
};

//Ajax формы операции DB Контрагента (Создать/Обновить/Удалить)
function doAjaxContragentDBOperation() {
    var strDBOperation = $('#cntragnt_db_action').attr('value');//update/insert/delete
    var strContragentId = $('#cntragnt_id').attr('value');
    var strContragentName = $('#cntragnt_name').val();
    var strContragenDescription = $('#cntragnt_desqription').val();
    var strContragenPhone = $('#cntragnt_mail').val();
    var strContragenMail = $('#cntragnt_phone').val();
    $.ajax({
        url : 'OperationFinsContragent',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            DBOperation: strDBOperation,
            ContragentId: strContragentId,
            ContragentName: strContragentName,
            ContragenDescription: strContragenDescription,
            ContragenPhone: strContragenPhone,
            ContragenMail: strContragenMail
        }),
        success: function (data) {
            doAjaxGetContragentsList();//Обновить таблицу контрагентов
        }
    });
};

//Ajax получение реквезитов Контрагента
function doAjaxGetContragentRequisits(ContragentId_In) {
    $.ajax({
        url : 'GetContragentRequisits',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            ContragentId: ContragentId_In
        }),
        success: function (data) {
            JSONStringToContragentReqTable(data.text)
        }
    });
};



//Ajax формы операции DB Реквезита (Создать/Обновить/Удалить)
function doAjaxRequisitDBOperation() {
    var strDBOperation = $('#requisit_db_action').attr('value');//update/insert/delete
    if (strDBOperation == null){strDBOperation = '';}
    var strRequisitContragentId = document.body.HashData.ActiveContragentId;
    if (strRequisitContragentId == null){strRequisitContragentId = '';}
    var strRequisitId = $('#requisit_id').attr('value');
    if (strRequisitId == null){strRequisitId = '';}
    var strRequisitName = $('#requisit_name').val();
    var strRequisitDescription = $('#requisit_description').val();
    var strRequisitINN = $('#requisit_ReqINN').val();
    var strRequisitKPP = $('#requisit_ReqKPP').val();
    var strRequisitFinsAcc = $('#requisit_ReqFinsAcc').val();
    var strRequisitFinsBIK = $('#requisit_ReqBIK').val();
    var strRequisitBankName = $('#requisit_ReqBankName').val();
    var strRequisitCrspAcc = $('#requisit_ReqCrspAcc').val();
    var strRequisitAddrIndex = $('#requisit_ReqAddrIndex').val();
    var strRequisitAddrCity = $('#requisit_ReqAddrCity').val();
    var strRequisitAddrString = $('#requisit_ReqAddrString').val();
    var strRequisitPhoneNum = $('#requisit_ReqPhoneNum').val();
    var strRequisitEmail = $('#requisit_ReqEmail').val();
    var strRequisitWebSite = $('#requisit_ReqWebSite').val();
    $.ajax({
        url : 'OperationFinsRequisit',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            DBOperation: strDBOperation,
            ContragentId: strRequisitContragentId,
            RequisitId: strRequisitId,
            RequisitName: strRequisitName,
            RequisitDescription: strRequisitDescription,
            RequisitINN: strRequisitINN,
            RequisitKPP: strRequisitKPP,
            RequisitFinsAcc: strRequisitFinsAcc,
            RequisitFinsBIK: strRequisitFinsBIK,
            RequisitBankName: strRequisitBankName,
            RequisitCrspAcc: strRequisitCrspAcc,
            RequisitAddrIndex: strRequisitAddrIndex,
            RequisitAddrCity: strRequisitAddrCity,
            RequisitAddrString: strRequisitAddrString,
            RequisitPhoneNum: strRequisitPhoneNum,
            RequisitEmail: strRequisitEmail,
            RequisitWebSite: strRequisitWebSite
        }),
        success: function (data) {
            doAjaxGetContragentRequisits(document.body.HashData.ActiveContragentId);
        }
    });
};

//--------Функции заполнения----------------
//Парсинг JSON списка контрагентов в таблицу
function JSONStringToContragentTable(JSONString) {
    var strContragentTableContext = '';
    var strContragentId = '';
    var strContragentName = '';
    var strContragenDescription = '';
    var strContragenPhone = '';
    var strContragenMail = '';
    var obj = jQuery.parseJSON(JSONString);
    $.each(obj, function (index, value) {
        strContragentTableContext = strContragentTableContext + '<tr class="fincrowlink contragentlist_row">';
        if(value['id'] == null){strContragentId = 'null';} else {strContragentId = value['id'].toString();}
        if(value['name'] == null){strContragentName = 'null';} else {strContragentName = value['name'].toString();}
        if(value['description'] == null){strContragenDescription = 'null';} else {strContragenDescription = value['description'].toString();}
        if(value['phone_num'] == null){strContragenPhone = 'null';} else {strContragenPhone = value['phone_num'].toString();}
        if(value['email_addr'] == null){strContragenMail = 'null';} else {strContragenMail = value['email_addr'].toString();}
        strContragentTableContext = strContragentTableContext + '<th class="cntr_id" value="' + strContragentId + '">' + strContragentId + '</th>' + '<th class="cntr_name" value="' + strContragentName + '">' + strContragentName + '</th>' + '<th class="cntr_description" value="' + strContragenDescription + '">' + strContragenDescription + '</th>' + '<th class="cntr_phone_num" value="' + strContragenPhone + '">' + strContragenPhone + '</th>' + '<th class="cntr_email_addr" value="' + strContragenMail + '">' + strContragenMail + '</th>' + '</tr>';
    });
    $("#contr_agent_table_body").html(strContragentTableContext);
};

//Парсинг JSON списка реквезитов контрагентов в таблицу
function JSONStringToContragentReqTable(JSONString) {
    var strContragentReqTableContext = '';
    var strReqId = '';
    var strContragentId = '';
    var strReqName = '';
    var strReqDescription = '';
    var strReqINN = '';
    var strReqKPP = '';
    var strReqFinsAcc = '';
    var strReqBIK = '';
    var strReqBankName = '';
    var strReqCrspAcc = '';
    var strReqAddrIndex = '';
    var strReqAddrCity = '';
    var strReqAddrString = '';
    var strReqPhoneNum = '';
    var strReqEmail = '';
    var strReqWebSite = '';
    var obj = jQuery.parseJSON(JSONString);
    $.each(obj, function (index, value) {
        strContragentReqTableContext = strContragentReqTableContext + '<tr class="fincrowlink requisitslist_row">';
        if(value['id'] == null){strReqId = '';}else{strReqId = value['id'].toString();}
        if(value['par_row_id'] == null){strContragentId = '';}else{strContragentId = value['par_row_id'].toString();}
        if(value['name'] == null){strReqName = '';}else{strReqName = value['name'].toString();}
        if(value['description'] == null){strReqDescription = '';}else{strReqDescription = value['description'].toString();}
        if(value['inn'] == null){strReqINN = '';}else{strReqINN = value['inn'].toString();}
        if(value['kpp'] == null){strReqKPP = '';}else{strReqKPP = value['kpp'].toString();}
        if(value['fins_acc'] == null){strReqFinsAcc = '';}else{strReqFinsAcc = value['fins_acc'].toString();}
        if(value['bik'] == null){strReqBIK = '';}else{strReqBIK = value['bik'].toString();}
        if(value['bank_name'] == null){strReqBankName = '';}else{strReqBankName = value['bank_name'].toString();}
        if(value['crsp_acc'] == null){strReqCrspAcc = '';}else{strReqCrspAcc = value['crsp_acc'].toString();}
        if(value['addr_index'] == null){strReqAddrIndex = '';}else{strReqAddrIndex = value['addr_index'].toString();}
        if(value['addr_city'] == null){strReqAddrCity = '';}else{strReqAddrCity = value['addr_city'].toString();}
        if(value['addr_string'] == null){strReqAddrString = '';}else{strReqAddrString = value['addr_string'].toString();}
        if(value['phone_num'] == null){strReqPhoneNum = '';}else{strReqPhoneNum = value['phone_num'].toString();}
        if(value['email_addr'] == null){strReqEmail = '';}else{strReqEmail = value['email_addr'].toString();}
        if(value['web_site'] == null){strReqWebSite = '';}else{strReqWebSite = value['web_site'].toString();}
        strContragentReqTableContext = strContragentReqTableContext + '<th class="cntr_req_id" value="' + strReqId + '">' + strReqId + '</th>' +
            '<th class="cntr_req_name" value="' + strReqName + '">' + strReqName + '</th>' +
            '<th class="cntr_req_description" value="' + strReqDescription + '">' + strReqDescription + '</th>' +
            '<th class="cntr_req_inn" value="' + strReqINN + '">' + strReqINN + '</th>' +
            '<th class="cntr_req_kpp" value="' + strReqKPP + '">' + strReqKPP + '</th>' +
            '<th class="cntr_req_acc" value="' + strReqFinsAcc + '">' + strReqFinsAcc + '</th>' +
            '<th class="cntr_req_bik" value="' + strReqBIK + '">' + strReqBIK + '</th>' +
            '<th class="bank_name" value="' + strReqBankName + '">' + strReqBankName + '</th>' +
            '<th class="cntr_req_crsp_acc" value="' + strReqCrspAcc + '">' + strReqCrspAcc + '</th>' +
            '<th class="cntr_req_addr_index" value="' + strReqAddrIndex + '">' + strReqAddrIndex + '</th>' +
            '<th class="cntr_req_addr_city" value="' + strReqAddrCity + '">' + strReqAddrCity + '</th>' +
            '<th class="cntr_req_addr_string" value="' + strReqAddrString + '">' + strReqAddrString + '</th>' +
            '<th class="cntr_req_phone_num" value="' + strReqPhoneNum + '">' + strReqPhoneNum + '</th>' +
            '<th class="cntr_req_email_addr" value="' + strReqEmail + '">' + strReqEmail + '</th>' + '</tr>';

    });
    $("#contragent_req_body").html(strContragentReqTableContext);
};


//Чистка формы контрагента
function ClearContragentForm(){
    $('#cntragnt_db_action').attr('value','');//update/insert/delete
    $('#cntragnt_id').attr('value','');
    $('#cntragnt_name').attr('value','');
    $('#cntragnt_desqription').attr('value','');
    $('#cntragnt_mail').attr('value','');
    $('#cntragnt_phone').attr('value','');

    $('#cntragnt_name').val('');
    $('#cntragnt_desqription').val('');
    $('#cntragnt_mail').val('');
    $('#cntragnt_phone').val('');
};

//Чистка формы Реквезита
function ClearRequisitForm(){
    $('#requisit_db_action').attr('value','');
    $('#requisit_id').attr('value','');
    $('#requisit_name').val('');
    $('#requisit_description').val('');
    $('#requisit_ReqINN').val('');
    $('#requisit_ReqKPP').val('');
    $('#requisit_ReqFinsAcc').val('');
    $('#requisit_ReqBIK').val('');
    $('#requisit_ReqBankName').val('');
    $('#requisit_ReqCrspAcc').val('');
    $('#requisit_ReqAddrIndex').val('');
    $('#requisit_ReqAddrCity').val('');
    $('#requisit_ReqAddrString').val('');
    $('#requisit_ReqPhoneNum').val('');
    $('#requisit_ReqEmail').val('');
    $('#requisit_ReqWebSite').val('');
};

//Сделать форму Контрагента RO
function SetROContragentForm(){
    $('#cntragnt_name').attr('readonly', true);
    $('#cntragnt_desqription').attr('readonly', true);
    $('#cntragnt_mail').attr('readonly', true);
    $('#cntragnt_phone').attr('readonly', true);
}

//Сделать форму Контрагента not RO
function UnSetROContragentForm(){
    $('#cntragnt_name').attr('readonly', false);
    $('#cntragnt_desqription').attr('readonly', false);
    $('#cntragnt_mail').attr('readonly', false);
    $('#cntragnt_phone').attr('readonly', false);
}

//Сделать форму Реквезита RO
function SetRORequisitForm(){
    $('#requisit_name').attr('readonly', true);
    $('#requisit_description').attr('readonly', true);
    $('#requisit_ReqINN').attr('readonly', true);
    $('#requisit_ReqKPP').attr('readonly', true);
    $('#requisit_ReqFinsAcc').attr('readonly', true);
    $('#requisit_ReqBIK').attr('readonly', true);
    $('#requisit_ReqBankName').attr('readonly', true);
    $('#requisit_ReqCrspAcc').attr('readonly', true);
    $('#requisit_ReqAddrIndex').attr('readonly', true);
    $('#requisit_ReqAddrCity').attr('readonly', true);
    $('#requisit_ReqAddrString').attr('readonly', true);
    $('#requisit_ReqPhoneNum').attr('readonly', true);
    $('#requisit_ReqEmail').attr('readonly', true);
    $('#requisit_ReqWebSite').attr('readonly', true);
}

//Сделать форму Реквезита not RO
function UnSetRORequisitForm(){
    $('#requisit_name').attr('readonly', false);
    $('#requisit_description').attr('readonly', false);
    $('#requisit_ReqINN').attr('readonly', false);
    $('#requisit_ReqKPP').attr('readonly', false);
    $('#requisit_ReqFinsAcc').attr('readonly', false);
    $('#requisit_ReqBIK').attr('readonly', false);
    $('#requisit_ReqBankName').attr('readonly', false);
    $('#requisit_ReqCrspAcc').attr('readonly', false);
    $('#requisit_ReqAddrIndex').attr('readonly', false);
    $('#requisit_ReqAddrCity').attr('readonly', false);
    $('#requisit_ReqAddrString').attr('readonly', false);
    $('#requisit_ReqPhoneNum').attr('readonly', false);
    $('#requisit_ReqEmail').attr('readonly', false);
    $('#requisit_ReqWebSite').attr('readonly', false);
}