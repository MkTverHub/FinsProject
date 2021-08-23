/**
 * Created by IVRibin on 20.03.2020.
 */
//Функция при загрузки страницы
function StartPage() {
    //doAjaxGetProjectListLeft();//Получение списка проектов в левой панели
    doAjaxGetUserCache("ContragentsEditor");
    SetFormatData('h4.formatnumber_1');
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
        SetActiveSelect('#contragent_type_list',$(this).find('.cntr_type').attr('value'));

        //Обновить список реквезитов
        document.body.HashData = {ActiveContragentId:strContragentId};//Устанавливаем Id активного контрагента
        doAjaxGetContragentRequisits(strContragentId);
    });
});

//Событие нажатия на кнопку "Сохранить" контрагента
function SaveContragent(){
    doAjaxContragentDBOperation();

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
        if($(this).find('.cntr_req_main_flg').attr('value') == 'true'){$('#requisit_main').prop('checked', true);}else{$('#requisit_main').prop('checked', false);}
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
        $('#requisit_card_number').val($(this).find('.cntr_req_card_number').attr('value'));
    });
});

//Событие выбора значения выпадающего "Тип"
$(function(){
    $("#contragent_type_list").change( function(){
        $('#contragent_type_list').attr('select_value',$('#contragent_type_list').val());
    });
});


//-----------Ajax Functions------------
//Ajax получение списка Контрагентов
function doAjaxGetContragentsList() {
    SpinnerOn("doAjaxGetContragentsList");
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
            SpinnerOff("doAjaxGetContragentsList");
        }
    });
};

//Ajax формы операции DB Контрагента (Создать/Обновить/Удалить)
function doAjaxContragentDBOperation() {
    SpinnerOn("doAjaxContragentDBOperation");
    try {
        var strDBOperation = $('#cntragnt_db_action').attr('value');//update/insert/delete
        var strContragentId = $('#cntragnt_id').attr('value');
        var strContragentName = $('#cntragnt_name').val();
        var strContragenDescription = $('#cntragnt_desqription').val();
        var strContragenPhone = $('#cntragnt_phone').val();
        var strContragenMail = $('#cntragnt_mail').val();
        var strContragenType = $('#contragent_type_list').attr('select_value');

        //Валидация
        var strErrorFlg = 'N';
        if(strDBOperation == 'update' || strDBOperation == 'insert' ) {
            strErrorFlg = validator('Название', 'STRING_36', '#cntragnt_name', strContragentName, strErrorFlg);//Проверка "Имя"
            strErrorFlg = validator('Описание', 'STRING_255', '#cntragnt_desqription', strContragenDescription, strErrorFlg);//Проверка "Описпнин"
            strErrorFlg = validator('Почта', 'EMAIL', '#cntragnt_mail', strContragenMail, strErrorFlg);//Проверка "Почта"
            strErrorFlg = validator('Телефон', 'PHONE', '#cntragnt_phone', strContragenPhone, strErrorFlg);//Проверка "Телефон"
            strErrorFlg = validator('Вид', 'NOT_NULL', '#contragent_type_list', strContragenType, strErrorFlg);//Проверка "Вид"

            if (strErrorFlg == 'Y') {
                throw new SyntaxError("Ошибка валидации");
            }
        }

        $.ajax({
            url: 'OperationFinsContragent',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                DBOperation: strDBOperation,
                ContragentId: strContragentId,
                ContragentName: strContragentName,
                ContragenDescription: strContragenDescription,
                ContragenPhone: strContragenPhone,
                ContragenMail: strContragenMail,
                ContragenType: strContragenType
            }),
            success: function (data) {
                doAjaxGetContragentsList();//Обновить таблицу контрагентов
                ClearContragentForm();
                SetROContragentForm();
                SpinnerOff("doAjaxContragentDBOperation");
            }
        });
    }catch (e) {
        console.log(e);
        SpinnerOff("doAjaxContragentDBOperation");
    }
};

//Ajax получение реквезитов Контрагента
function doAjaxGetContragentRequisits(ContragentId_In) {
    SpinnerOn("doAjaxGetContragentRequisits");
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
            SpinnerOff("doAjaxGetContragentRequisits");
        }
    });
};



//Ajax формы операции DB Реквезита (Создать/Обновить/Удалить)
function doAjaxRequisitDBOperation() {
    SpinnerOn("doAjaxRequisitDBOperation");
    try {
        var strDBOperation = $('#requisit_db_action').attr('value');//update/insert/delete
        if (strDBOperation == null) {
            strDBOperation = '';
        }
        var strRequisitContragentId = document.body.HashData.ActiveContragentId;
        if (strRequisitContragentId == null) {
            strRequisitContragentId = '';
        }
        var strRequisitId = $('#requisit_id').attr('value');
        if (strRequisitId == '') {
            strRequisitId = '0';
        }
        if ($('#requisit_main').prop('checked')) {
            strMainFlg = 'true';
        } else {
            strMainFlg = 'false';
        }
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
        var strRequisitCardNum = $('#requisit_card_number').val();

        var strErrorFlg = 'N';
        //Валидация
        if(strDBOperation == 'update' || strDBOperation == 'insert' && 1==0) {
            strErrorFlg = validator('Название', 'STRING_36', '#requisit_name', strRequisitName, strErrorFlg);//Название
            strErrorFlg = validator('Описание', 'STRING_255', '#requisit_description', strRequisitDescription, strErrorFlg);//Описание
            strErrorFlg = validator('№ карты', 'NUMBER_16', '#requisit_card_number', strRequisitCardNum, strErrorFlg);//Проверка № карты
            strErrorFlg = validator('ИНН', 'NUMBER_10_12', '#requisit_ReqINN', strRequisitINN, strErrorFlg);//Проверка ИНН
            strErrorFlg = validator('КПП', 'NUMBER_9', '#requisit_ReqKPP', strRequisitKPP, strErrorFlg);//Проверка КПП
            strErrorFlg = validator('Счет', 'NUMBER_20', '#requisit_ReqFinsAcc', strRequisitFinsAcc, strErrorFlg);//Проверка Счет
            strErrorFlg = validator('БИК', 'NUMBER_9', '#requisit_ReqBIK', strRequisitFinsBIK, strErrorFlg);//Проверка БИК
            strErrorFlg = validator('Банк', 'STRING_255', '#requisit_ReqBankName', strRequisitBankName, strErrorFlg);//Проверка Банк
            strErrorFlg = validator('Корр. счет', 'NUMBER_20', '#requisit_ReqCrspAcc', strRequisitFinsBIK, strErrorFlg);//Корреспондентский счет
            strErrorFlg = validator('Индекс', 'NUMBER_6', '#requisit_ReqAddrIndex', strRequisitFinsBIK, strErrorFlg);//Индекс
            strErrorFlg = validator('Город', 'STRING_255', '#requisit_ReqAddrCity', strRequisitAddrCity, strErrorFlg);//Проверка Город
            strErrorFlg = validator('Адрес', 'STRING_255', '#requisit_ReqAddrString', strRequisitAddrString, strErrorFlg);//Проверка Адрес
            strErrorFlg = validator('Телефон', 'PHONE', '#requisit_ReqPhoneNum', strRequisitPhoneNum, strErrorFlg);//Телефон
            strErrorFlg = validator('Почта', 'EMAIL', '#requisit_ReqEmail', strRequisitEmail, strErrorFlg);//Почта


            if (strErrorFlg == 'Y') {
                throw new SyntaxError("Ошибка валидации");
            }
        }

        $.ajax({
            url: 'OperationFinsRequisit',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                DBOperation: strDBOperation,
                ContragentId: strRequisitContragentId,
                RequisitId: strRequisitId,
                MainFlg: strMainFlg,
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
                RequisitWebSite: strRequisitWebSite,
                RequisitCardNum: strRequisitCardNum
            }),
            success: function (data) {
                doAjaxGetContragentRequisits(document.body.HashData.ActiveContragentId);
                ClearRequisitForm();
                SetRORequisitForm();
                SpinnerOff("doAjaxRequisitDBOperation");
            }
        });
    }catch (e) {
        console.log(e);
        SpinnerOff("doAjaxRequisitDBOperation");
    }
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
    var strContragenType = '';
    var strContragenBalance = '';
    var obj = jQuery.parseJSON(JSONString);
    $.each(obj, function (index, value) {
        strContragentTableContext = strContragentTableContext + '<tr class="fincrowlink contragentlist_row">';
        if(value['id'] == null){strContragentId = 'null';} else {strContragentId = value['id'].toString();}
        if(value['name'] == null){strContragentName = 'null';} else {strContragentName = value['name'].toString();}
        if(value['description'] == null){strContragenDescription = 'null';} else {strContragenDescription = value['description'].toString();}
        if(value['phone_num'] == null){strContragenPhone = 'null';} else {strContragenPhone = value['phone_num'].toString();}
        if(value['email_addr'] == null){strContragenMail = 'null';} else {strContragenMail = value['email_addr'].toString();}
        if(value['type'] == null){strContragenType = 'null';} else {strContragenType = value['type'].toString();}
        strContragentTableContext = strContragentTableContext +
            '<th class="cntr_id" value="' + strContragentId + '">' + strContragentId + '</th>' +
            '<th class="cntr_name" value="' + strContragentName + '">' + strContragentName + '</th>' +
            '<th class="cntr_description" value="' + strContragenDescription + '">' + strContragenDescription + '</th>' +
            '<th class="cntr_phone_num" value="' + strContragenPhone + '">' + strContragenPhone + '</th>' +
            '<th class="cntr_email_addr" value="' + strContragenMail + '">' + strContragenMail + '</th>' +
            '<th class="cntr_type" value="' + strContragenType + '">' + strContragenType + '</th>' +
            '</tr>';
    });
    $("#contr_agent_table_body").html(strContragentTableContext);
};

//Парсинг JSON списка реквезитов контрагентов в таблицу
function JSONStringToContragentReqTable(JSONString) {
    var strContragentReqTableContext = '';
    var strReqId = '';
    var strContragentId = '';
    var strMainFlg = '';
    var strMainFlg_UI = '';
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
    var strCardNumber = '';
    var obj = jQuery.parseJSON(JSONString);
    $.each(obj, function (index, value) {
        strContragentReqTableContext = strContragentReqTableContext + '<tr class="fincrowlink requisitslist_row">';
        if(value['id'] == null){strReqId = '';}else{strReqId = value['id'].toString();}
        if(value['par_row_id'] == null){strContragentId = '';}else{strContragentId = value['par_row_id'].toString();}
        if(value['main_flg'] == null){strMainFlg= 'false';}else{strMainFlg = value['main_flg'].toString();}
        if(value['main_flg'] == true){strMainFlg_UI = 'Да';}else{strMainFlg_UI = 'Нет';}
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
        if(value['card_num'] == null){strCardNumber = '';}else{strCardNumber = value['card_num'].toString();}
        strContragentReqTableContext = strContragentReqTableContext + '<th class="cntr_req_id" value="' + strReqId + '">' + strReqId + '</th>' +
            '<th class="cntr_req_main_flg f-d-n" value="' + strMainFlg + '">' + strMainFlg + '</th>' +
            '<th class="cntr_req_main_flg_ui" value="' + strMainFlg_UI + '">' + strMainFlg_UI + '</th>' +
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
            '<th class="cntr_req_email_addr" value="' + strReqEmail + '">' + strReqEmail + '</th>' +
            '<th class="cntr_req_card_number" value="' + strCardNumber + '">' + strCardNumber + '</th>' +
            '</tr>';

    });
    $("#contragent_req_body").html(strContragentReqTableContext);
};


//Чистка формы контрагента
function ClearContragentForm(){
    $('#cntragnt_db_action').attr('value','');//update/insert/delete
    $('#cntragnt_id').attr('value','');
    $('#cntragnt_name').attr('value','');
    $('#cntragnt_name').removeClass('error-valid-field');
    $('#cntragnt_name').val('');
    $('#cntragnt_desqription').attr('value','');
    $('#cntragnt_desqription').removeClass('error-valid-field');
    $('#cntragnt_desqription').val('');
    $('#cntragnt_mail').attr('value','');
    $('#cntragnt_mail').removeClass('error-valid-field');
    $('#cntragnt_mail').val('');
    $('#cntragnt_phone').attr('value','');
    $('#cntragnt_phone').removeClass('error-valid-field');
    $('#cntragnt_phone').val('');

    ResetPickList('#contragent_type_list');
    $('#contragent_type_list option:contains("Выберете значение")').prop('selected', true);

};

//Чистка формы Реквезита
function ClearRequisitForm(){
    $('#requisit_db_action').attr('value','');
    $('#requisit_id').attr('value','');
    $('#requisit_main').prop('checked',false);
    $('#requisit_name').val('');
    $('#requisit_name').removeClass('error-valid-field');
    $('#requisit_description').val('');
    $('#requisit_description').removeClass('error-valid-field');
    $('#requisit_ReqINN').val('');
    $('#requisit_ReqINN').removeClass('error-valid-field');
    $('#requisit_ReqKPP').val('');
    $('#requisit_ReqKPP').removeClass('error-valid-field');
    $('#requisit_ReqFinsAcc').val('');
    $('#requisit_ReqFinsAcc').removeClass('error-valid-field');
    $('#requisit_ReqBIK').val('');
    $('#requisit_ReqBIK').removeClass('error-valid-field');
    $('#requisit_ReqBankName').val('');
    $('#requisit_ReqBankName').removeClass('error-valid-field');
    $('#requisit_ReqCrspAcc').val('');
    $('#requisit_ReqCrspAcc').removeClass('error-valid-field');
    $('#requisit_ReqAddrIndex').val('');
    $('#requisit_ReqAddrIndex').removeClass('error-valid-field');
    $('#requisit_ReqAddrCity').val('');
    $('#requisit_ReqAddrCity').removeClass('error-valid-field');
    $('#requisit_ReqAddrString').val('');
    $('#requisit_ReqAddrString').removeClass('error-valid-field');
    $('#requisit_ReqPhoneNum').val('');
    $('#requisit_ReqPhoneNum').removeClass('error-valid-field');
    $('#requisit_ReqEmail').val('');
    $('#requisit_ReqEmail').removeClass('error-valid-field');
    $('#requisit_ReqWebSite').val('');
    $('#requisit_ReqWebSite').removeClass('error-valid-field');
    $('#requisit_card_number').val('');
    $('#requisit_card_number').removeClass('error-valid-field');
};

//Сделать форму Контрагента RO
function SetROContragentForm(){
    $('#cntragnt_name').attr('readonly', true);
    $('#cntragnt_desqription').attr('readonly', true);
    $('#cntragnt_mail').attr('readonly', true);
    $('#cntragnt_phone').attr('readonly', true);
    $('#contragent_type_list').attr('disabled', true);
}

//Сделать форму Контрагента not RO
function UnSetROContragentForm(){
    $('#cntragnt_name').attr('readonly', false);
    $('#cntragnt_desqription').attr('readonly', false);
    $('#cntragnt_mail').attr('readonly', false);
    $('#cntragnt_phone').attr('readonly', false);
    $('#contragent_type_list').attr('disabled', false);
}

//Сделать форму Реквезита RO
function SetRORequisitForm(){
    $('#requisit_main').attr("onclick","return false");
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
    $('#requisit_card_number').attr('readonly', true);
}

//Сделать форму Реквезита not RO
function UnSetRORequisitForm(){
    $('#requisit_main').removeAttr("onclick");
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
    $('#requisit_card_number').attr('readonly', false);
}

//Функция установки выбранного значения
function SetActiveSelect(ListSelector,SelectedVal){
    $(ListSelector).find('[selected]').prop('selected', false);//Сбросить текущее активное значение
    $(ListSelector).find('[value = "' + SelectedVal + '"]').prop('selected', true);
    $(ListSelector).attr('select_value',SelectedVal);
}
//Функция сброса списка
function ResetPickList(ListSelector){
    $(ListSelector).find('[selected]').prop('selected', false);//Сбросить текущее активное значение
    $(ListSelector).find('[selected]').attr('select_value', null);

}

//=========================================================================================================
//Форматировать числа в полях (вызывается при загрузки Fins_Contragents_Add.html)
function SetFormatData(selectop_in){
    let elements = document.querySelectorAll(selectop_in);
    for (let elem of elements) {
        elem.innerHTML = number_format(elem.innerHTML, 2, '.', ' ');
    }
}

function number_format(number, decimals, dec_point, thousands_sep) {
    number = (number + '').replace(/[^0-9+\-Ee.]/g, '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function (n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.round(n * k) / k;
        };
    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    if (s[0].length > 3) {
        s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
    }
    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}