/**
 * Created by IVRibin on 20.03.2020.
 */

//Функция при загрузки страницы
function StartPage() {
    //doAjaxGetProjectListLeft();//Получение списка проектов в левой панели
    doAjaxGetUserCache();
    doAjaxGetCompanyList();
    //doAjaxGetProjectList();//Получение списка проектов в левой панели
    //doAjaxGetContragentsList();//Заполнение таблицы контрагентов
    document.body.HashData = {ActiveCompanytId:''};
    SetROCompanyForm();
    SetROContactForm();
    
    // Site loader 
    //$(".loader-inner").fadeOut();
    //$(".loader").delay(200).fadeOut("slow");
};

//-------------Функции событий-----------------
//Событие нажатия на кнопку "Создать" компанию
function InsertCompany(){
    UnSetROCompanyForm();
    CleanCompanyForm();
    $('#company_db_action').attr('value','insert');//update/insert/delete
};
//Событие нажатия на кнопку "Удалить" компанию
function DeleteCompany(){
    $('#company_db_action').attr('value','delete');
    doAjaxCompanytDBOperation();
    CleanCompanyForm();
    SetROCompanyForm();
};
//Событие нажатия на кнопку "Сохранить" компанию
function SaveCompany(){
    doAjaxCompanytDBOperation();
    CleanCompanyForm();
    SetROCompanyForm();
};
//Событие нажатия на кнопку "Отменить" компанию
function ResetCompany(){
    CleanCompanyForm();
    SetROCompanyForm();
};

//Событие нажатия на строку компании
$(function(){
    var strDBOperation = 'update';//update/insert/delete
    var strCompanytId = '';

    $('#company_table_body_id').on('click', '.companylist_row', function () {
        UnSetROCompanyForm();
        $('#company_db_action').attr('value',strDBOperation);
        strCompanytId = $(this).find('.company_id').attr('value');
        doAjaxGetCompanyContactsList(strCompanytId);
        document.body.HashData = {ActiveCompanytId:strCompanytId};//Устанавливаем Id активной компании
        $('#company_id').attr('value',strCompanytId);
        $('#company_name').val($(this).find('.company_name').html());
        $('#company_full_name').val($(this).find('.company_full_name').attr('value'));
        $('#company_inn').val($(this).find('.company_inn').attr('value'));
        $('#company_kpp').val($(this).find('.company_kpp').attr('value'));
        $('#company_fins_acc').val($(this).find('.company_fins_acc').attr('value'));
    });
});

//Событие нажатия на кнопку "Создать" "Сотрудники"
function InsertContact(){
    UnSetROContactForm();
    CleanContactForm();
    $('#contact_db_action').attr('value','insert');//update/insert/delete
};

//Событие нажатия на кнопку "Удалить" "Сотрудники"
function DeleteContact(){
    $('#contact_db_action').attr('value','delete');
    doAjaxContactDBOperation();
    CleanContactForm();
    SetROContactForm();
};

//Событие нажатия на кнопку "Сохранить" "Сотрудники"
function SaveContact(){
    doAjaxContactDBOperation();
    CleanContactForm();
    SetROContactForm();
};

//Событие нажатия на кнопку "Отменить" "Сотрудники"
function ResetContact(){
    CleanContactForm();
    SetROContactForm();
};

//Событие нажатия на строку "Сотрудники"
$(function(){
    var strDBOperation = "update";//update/insert/delete

    $("#contact_table_body_id").on("click", ".contactlist_row", function () {
        UnSetROContactForm();
        $('#contact_db_action').attr('value',strDBOperation);
        $('#contact_id').attr('value',$(this).find('.contact_id').attr('value'));
        $('#contact_first_name_id').val($(this).find('.contact_first_name').attr('value'));
        $('#contact_balance_id').val($(this).find('.contact_balance').attr('value'));
        $('#contact_fins_acc_id').val($(this).find('.contact_fins_acc').attr('value'));
    });
});


//-----------Ajax Functions------------
//Ajax формы операции DB Компании (Создать/Обновить/Удалить)
function doAjaxCompanytDBOperation() {
    var strDBOperation = $('#company_db_action').attr('value');//update/insert/delete
    var strCompanyId = $('#company_id').attr('value');
    var strCompanyName = $('#company_name').val();
    var strCompanyFullName = $('#company_full_name').val();
    var strCompanyINN = $('#company_inn').val();
    var strCompanyKPP = $('#company_kpp').val();
    var strCompanyFinsAcc = $('#company_fins_acc').val();

    $.ajax({
        url : 'OperationCompany',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            DBOperation: strDBOperation,
            CompanyId: strCompanyId,
            CompanyName: strCompanyName,
            CompanyFullName: strCompanyFullName,
            CompanyINN: strCompanyINN,
            CompanyKPP: strCompanyKPP,
            CompanyFinsAcc: strCompanyFinsAcc
        }),
        success: function (data) {
            //alert('Ajax: OperationCompany');
            doAjaxGetCompanyList();
        }
    });
};

//Ajax формы операции DB Контакта (Создать/Обновить/Удалить)
function doAjaxContactDBOperation() {
    var strDBOperation = $('#contact_db_action').attr('value');//update/insert/delete
    var strContactId = $('#contact_id').attr('value');
    var strContactFirstName = $('#contact_first_name_id').val();
    var strContactLastName = '';
    var strContactFinsAcc = $('#contact_fins_acc_id').val();
    var strContactBalance = $('#contact_balance_id').val();
    var strCompanyId = document.body.HashData.ActiveCompanytId;

    $.ajax({
        url : 'OperationContact',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            DBOperation: strDBOperation,
            ContactId: strContactId,
            ContactFirstName: strContactFirstName,
            ContactLastName: strContactLastName,
            ContactFinsAcc: strContactFinsAcc,
            ContactBalance: strContactBalance,
            ContactCompanyId: strCompanyId
        }),
        success: function (data) {
            //alert('Ajax: OperationCompany');
            doAjaxGetCompanyContactsList(strCompanyId);
        }
    });
};

//Ajax получение списка Компаний
function doAjaxGetCompanyList() {
    $.ajax({
        url : 'GetCompanyList',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            //
        }),
        success: function (data) {
            JSONStringToCompanyTable(data.text);
        }
    });
};

//Ajax получение списка Работников
function doAjaxGetCompanyContactsList(CompanyId) {
    $.ajax({
        url : 'GetCompanyEmployees',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            CompanyId : CompanyId
        }),
        success: function (data) {
            JSONStringToContactsTable(data.text);
        }
    });
};

//Ajax получение списка проектов в левой панели
function doAjaxGetProjectList() {
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
                    + '_rowid" class="left-menu-item finsproject_list_row_li"><input type="button" class="left-menu-link finsproject_list_row" projnum="'
                    + value["id"].toString() + '" value="' + value["name"] + '"/></li>';
            });
            $("#projectlistpanel").html(strProjectListContext);
        }
    });
};

//--------Функции заполнения----------------
//Парсинг JSON списка компаний в таблицу
function JSONStringToCompanyTable(JSONString) {
    var strCompanyTableContext = '';
    var strCompanyId = '';
    var strCompanyName = '';
    var strCompanyFullName = '';
    var strCompanyINN = '';
    var strCompanyKPP = '';
    var strCompanyFinsAcc = '';
    var obj = jQuery.parseJSON(JSONString);
    $.each(obj, function (index, value) {
        strCompanyTableContext = strCompanyTableContext + '<tr class="fincrowlink companylist_row">';
        if(value['id'] == null){strCompanyId = 'null';} else {strCompanyId = value['id'].toString();}
        if(value['name'] == null){strCompanyName = 'null';} else {strCompanyName = value['name'].toString();}
        if(value['full_name'] == null){strCompanyFullName = 'null';} else {strCompanyFullName = value['full_name'].toString();}
        if(value['inn'] == null){strCompanyINN = 'null';} else {strCompanyINN = value['inn'].toString();}
        if(value['kpp'] == null){strCompanyKPP = 'null';} else {strCompanyKPP = value['kpp'].toString();}
        if(value['fins_acc'] == null){strCompanyFinsAcc = 'null';} else {strCompanyFinsAcc = value['fins_acc'].toString();}
        strCompanyTableContext = strCompanyTableContext + '<th class="company_id" value="' + strCompanyId + '">' + strCompanyId + '</th>' + '<th class="company_name" value="' + strCompanyName + '">' + strCompanyName + '</th>' + '<th class="company_full_name" value="' + strCompanyFullName + '">' + strCompanyFullName + '</th>' + '<th class="company_inn" value="' + strCompanyINN + '">' + strCompanyINN + '</th>' + '<th class="company_kpp" value="' + strCompanyKPP + '">' + strCompanyKPP + '</th>' + '<th class="company_fins_acc" value="' + strCompanyFinsAcc + '">' + strCompanyFinsAcc + '</th>' + '</tr>';
    });
    $("#company_table_body_id").html(strCompanyTableContext);
};

//Парсинг JSON списка компаний в таблицу
function JSONStringToContactsTable(JSONString) {
    var strContactTableContext = '';
    var strContactId = '';
    var strContactFirstName = '';
    var strContactFinsAcc = '';
    var strContactBalance = '';

    var obj = jQuery.parseJSON(JSONString);
    $.each(obj, function (index, value) {
        strContactTableContext = strContactTableContext + '<tr class="fincrowlink contactlist_row">';
        if(value['id'] == null){strContactId = 'null';} else {strContactId = value['id'].toString();}
        if(value['first_name'] == null){strContactFirstName = 'null';} else {strContactFirstName = value['first_name'].toString();}
        if(value['fins_acc'] == null){strContactFinsAcc = 'null';} else {strContactFinsAcc = value['fins_acc'].toString();}
        if(value['balance'] == null){strContactBalance = 'null';} else {strContactBalance = value['balance'].toString();}
        strContactTableContext = strContactTableContext + '<th class="contact_id" value="' + strContactId + '">' + strContactId + '</th>' + '<th class="contact_first_name" value="' + strContactFirstName + '">' + strContactFirstName + '</th>' + '<th class="contact_fins_acc" value="' + strContactFinsAcc + '">' + strContactFinsAcc + '</th>' + '<th class="contact_balance" value="' + strContactBalance + '">' + strContactBalance + '</th>' + '</tr>';
    });

    $("#contact_table_body_id").html(strContactTableContext);
};

//Чистка формы Компания
function CleanCompanyForm() {
    $('#company_db_action').attr('value','');
    $('#company_id').attr('value','');
    $('#company_name').val('');
    $('#company_full_name').val('');
    $('#company_inn').val('');
    $('#company_kpp').val('');
    $('#company_fins_acc').val('');
};

//Чистка формы Сотрудник
function CleanContactForm() {
    $('#contact_db_action').attr('value','');
    $('#contact_id').attr('value','');
    $('#contact_first_name_id').val('');
    $('#contact_balance_id').val('');
    $('#contact_fins_acc_id').val('');
};

//Сделать форму Компания RO
function SetROCompanyForm(){
    $('#company_name').attr('readonly', true);
    $('#company_full_name').attr('readonly', true);
    $('#company_inn').attr('readonly', true);
    $('#company_kpp').attr('readonly', true);
    $('#company_fins_acc').attr('readonly', true);
}

//Сделать форму Компания not RO
function UnSetROCompanyForm(){
    $('#company_name').attr('readonly', false);
    $('#company_full_name').attr('readonly', false);
    $('#company_inn').attr('readonly', false);
    $('#company_kpp').attr('readonly', false);
    $('#company_fins_acc').attr('readonly', false);
}

//Сделать форму Сотрудник RO
function SetROContactForm(){
    $('#contact_first_name_id').attr('readonly', true);
    $('#contact_balance_id').attr('readonly', true);
    $('#contact_fins_acc_id').attr('readonly', true);
}

//Сделать форму Сотрудник not RO
function UnSetROContactForm(){
    $('#contact_first_name_id').attr('readonly', false);
    $('#contact_balance_id').attr('readonly', false);
    $('#contact_fins_acc_id').attr('readonly', false);
}