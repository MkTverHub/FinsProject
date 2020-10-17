

//Функция при загрузки страницы
function StartPage() {
    //alert('StartPage');
    doAjaxGetProjectList();//Получение списка проектов в левой панели
    doAjaxGetContragentsList();//Получение списка контрагентов
};


//-------------Функции событий-----------------
//Событие нажатия на проект в левой панели
/*ВАЖНО: jqury слушает только элементы существующие на момент инициализации скрипта,
т.к. проекты создаются из ajax, то слушатель нужно поставить на родительский элемент div,
который существует изначально на странице */
$(function(){
    var strProjectId = "";
    $("#projectlistpanel").on("click", ".finsproject_list_row", function () {
        strProjectId = $(this).attr("projnum");
        $('#projectidid').attr("value",strProjectId);
        $('#projectidid').val(strProjectId);
        doAjaxGetProjectOperationList(strProjectId);
        doAjaxGetProjectProfit(strProjectId);
    });

});

//Событие нажатия на финансовую операцию
$(function(){
    var strFinsRecordId = "";
    var strFinsBlockFlg = "";
    var strFinsOpertype = "";//Тип транзакции profit/expense/transfer
    var strOperDt = "";
    var strFinsAmount = "";
    var strFinsDetail = "";
    var strPaymentAccIn = "";
    var strPaymentAccOut = "";
    var strFinsArticle = "";
    var strProjectId = "";
    var strFinsContrAgent = "";
    var strFinsRequisites = "";

    $("#financetable").on("click", ".fincrowlink", function () {
        strFinsRecordId = $(this).find('.fieldid').html();
        strFinsBlockFlg = $(this).find('.fieldlockflg').html();
        strFinsOpertype = $(this).find('.fieldfinsopertype').html();
        strOperDt = $(this).find('.fieldoperdate').html();
        strFinsAmount = $(this).find('.fieldamount').html();
        strFinsDetail = $(this).find('.fielddetail').html();
        strPaymentAccIn = $(this).find('.fieldpayaccin').html();
        strPaymentAccOut = $(this).find('.fieldpayaccout').html();
        strFinsArticle = $(this).find('.fieldfinsarticle').html();
        strProjectId = $(this).find('.fieldprojectid').html();
        strFinsContrAgent = $(this).find('.fieldfinscontragent').html();
        strFinsRequisites = $(this).find('.fieldrequisites').html();

        $('#finsedittypeid').attr('value','update');//update/new/delete
        $('#recordid').attr('value',strFinsRecordId);
        $('#fieldlockflgid').attr('value',strFinsBlockFlg);
        $('#finsopertypeid').attr('value',strFinsOpertype);//profit/expense/transfer
        $('#fieldoperdateid').attr('value',strOperDt);
        $('#fieldamountid').attr('value',strFinsAmount);
        $('#fielddetailid').attr('value',strFinsDetail);
        $('#paymentaccinid').attr('value',strPaymentAccIn);
        $('#paymentaccoutid').attr('value',strPaymentAccOut);
        $('#finsarticleid').attr('value',strFinsArticle);
        $('#projectidid').attr('value',strProjectId);
        $('#finscontragentid').attr('value',strFinsContrAgent);
        $('#requisitesid').attr('value',strFinsRequisites);

        $('#finsedittypeid').val('update');//update/new/delete
        $('#recordid').val(strFinsRecordId);
        $('#fieldlockflgid').val(strFinsBlockFlg);
        $('#finsopertypeid').val(strFinsOpertype);//profit/expense/transfer
        $('#fieldoperdateid').val(strOperDt);
        $('#fieldamountid').val(strFinsAmount);
        $('#fielddetailid').val(strFinsDetail);
        $('#paymentaccinid').val(strPaymentAccIn);
        $('#paymentaccoutid').val(strPaymentAccOut);
        $('#finsarticleid').val(strFinsArticle);
        $('#projectidid').val(strProjectId);
        $('#finscontragentid').val(strFinsContrAgent);
        $('#requisitesid').val(strFinsRequisites);

        //Подсвет Приход,Расход,Перевод
        if(strFinsOpertype == 'profit'){
            $('#arrivalbt').addClass('active');
            $('#transferbt').removeClass('active');
            $('#expensebt').removeClass('active');
        }
        if(strFinsOpertype == 'expense'){
            $('#arrivalbt').removeClass('active');
            $('#transferbt').removeClass('active');
            $('#expensebt').addClass('active');
        }
        if(strFinsOpertype == 'transfer'){
            $('#arrivalbt').removeClass('active');
            $('#transferbt').addClass('active');
            $('#expensebt').removeClass('active');
        }
    });
});

//Событие нажатия на кнопку "Создать" финансовую операцию
function NewFinsOperation(){
    $('#finsedittypeid').attr('value','insert');
    $('#finsedittypeid').val('insert');
    ClearFinsForm();
};

//Событие нажатия на кнопку "Удалить" финансовую операцию
function DeleteFinsOperation(){
    $('#finsedittypeid').attr('value','delete');
    $('#finsedittypeid').val('delete');
    doAjaxFinsOperation();
    ClearFinsForm();
};

//Событие нажатия на кнопку "Сохранить" финансовую операцию
function SaveFinsOperation(){
    doAjaxFinsOperation();
};

//Событие выбора значения выпадающего списка контрагента
$(function(){
    $("#contr_agent_select_field").change( function(){
        var strContragentId = $('#contr_agent_select_field').val();
        $('#finscontragentid').attr('value',strContragentId);
        $('#finscontragentid').val(strContragentId);
        doAjaxGetContragentRequisits(strContragentId);//Получить список реквезитов контрагента
    });
});

//Событие выбора значения выпадающего списка реквизитов
$(function(){
    $("#contr_agent_requisits_list").change( function(){
        var strContragentRequisitId = $('#contr_agent_requisits_list').val();
        $('#requisitesid').attr('value',strContragentRequisitId);
        $('#requisitesid').val(strContragentRequisitId);
    });
});

//Событие Нажатия на "Приход"
$(function(){
    $("#arrivalbt").on('click', function(){
        $('#finsopertypeid').attr('value','profit');
        $('#finsopertypeid').val('profit');
    });
});

//Событие Нажатия на "Расход"
$(function(){
    $("#expensebt").on('click', function(){
        $('#finsopertypeid').attr('value','expense');
        $('#finsopertypeid').val('expense');
    });
});

//Событие Нажатия на "Перевод"
$(function(){
    $("#transferbt").on('click', function(){
        $('#finsopertypeid').attr('value','transfer');
        $('#finsopertypeid').val('transfer');
    });
});

//------------Общие функции----------------------------------
//Очистить форму финансовой операции
function ClearFinsForm (){
    //$('#finsedittypeid').attr('value','');
    $('#recordid').attr('value','');
    $('#fieldlockflgid').attr('value','');
    $('#finsopertypeid').attr('value','');
    $('#fieldoperdateid').attr('value','');
    $('#fieldamountid').attr('value','');
    $('#fielddetailid').attr('value','');
    $('#paymentaccinid').attr('value','');
    $('#paymentaccoutid').attr('value','');
    $('#finsarticleid').attr('value','');
    //$('#projectidid').attr('value','');
    $('#finscontragentid').attr('value','');
    $('#requisitesid').attr('value','');

    //$('#finsedittypeid').val('');
    $('#recordid').val('');
    $('#fieldlockflgid').val('');
    $('#finsopertypeid').val('');
    $('#fieldoperdateid').val('');
    $('#fieldamountid').val('');
    $('#fielddetailid').val('');
    $('#paymentaccinid').val('');
    $('#paymentaccoutid').val('');
    $('#finsarticleid').val('');
    //$('#projectidid').val('');
    $('#finscontragentid').val('');
    $('#requisitesid').val('');
};

//Парсинг строки JSON в список финансовых операций и заполнение таблицы
function JSONStringToFinsOperationList(JSONString) {
    var strFinsOpertype = "";
    var strFinsOperationListContext = "";
    var strRowId = "";
    var strLockFlg = "";
    var strOperDate = "";
    var strFinsAmount = "";
    var strFinsDetail = "";
    var strPaymentAccIn = "";
    var strPaymentAccOut = "";
    var strFinsArticle = "";
    var strProjectId = "";
    var strFinsContrAgent = "";
    var strFinsRequisites = "";

    var obj = jQuery.parseJSON(JSONString);
    $.each(obj, function (index, value) {
        if(value["id"] == null){strRowId = 'null';} else {strRowId = value["id"].toString();}
        if(value["lockflg"] == null){strLockFlg = 'null';} else {strLockFlg = value["lockflg"].toString();}
        if(value["operdate"] == null){strOperDate = 'null';} else {strOperDate = value["operdate"].toString();}
        if(value["amount"] == null){strFinsAmount = 'null';} else {strFinsAmount = value["amount"].toString();}
        if(value["detail"] == null){strFinsDetail = 'null';} else {strFinsDetail = value["detail"].toString();}
        if(value["finsopertype"] == null){strFinsOpertype = 'null';} else {strFinsOpertype = value["finsopertype"].toString();}
        if(value["payaccin"] == null){strPaymentAccIn = 'null';} else {strPaymentAccIn = value["payaccin"].toString();}
        if(value["payaccout"] == null){strPaymentAccOut = 'null';} else {strPaymentAccOut = value["payaccout"].toString();}
        if(value["finsarticle"] == null){strFinsArticle = 'null';} else {strFinsArticle = value["finsarticle"].toString();}
        if(value["projectid"] == null){strProjectId = 'null';} else {strProjectId = value["projectid"].toString();}
        if(value["finscontragent"] == null){strFinsContrAgent = 'null';} else {strFinsContrAgent = value["finscontragent"].toString();}
        if(value["requisites"] == null){strFinsRequisites = 'null';} else {strFinsRequisites = value["requisites"].toString();}

        strFinsOperationListContext = strFinsOperationListContext
            + '<tr class="fincrowlink">'
            + '<th class="fieldfinsopertype">' + strFinsOpertype + '</th>'
            + '<th class="fieldid">' + strRowId + '</th>'
            + '<th class="fieldlockflg">' + strLockFlg + '</th>'
            + '<th class="fieldoperdate">' + strOperDate + '</th>'
            + '<th class="fieldamount">' + strFinsAmount + '</th>'
            + '<th class="fieldpayaccin">' + strPaymentAccIn + '</th>'
            + '<th class="fieldpayaccout">' + strPaymentAccOut + '</th>'
            + '<th class="fieldfinsarticle">' + strFinsArticle + '</th>'
            + '<th class="fieldprojectid">' + strProjectId + '</th>'
            + '<th class="fieldfinscontragent">' + strFinsContrAgent + '</th>'
            + '<th class="fieldrequisites">' + strFinsRequisites + '</th>'
            + '<th class="fielddetail">' + strFinsDetail + '</th>'
            + '</tr>';
    });

    $("#financetable").html(strFinsOperationListContext);
}

//Парсинг JSON списка контрагентов в выпадающий список
function JSONStringToContragentPickList(JSONString) {
    var strContragentId = '';
    var strContragentName = '';
    var strContragentPickListContext = '<option value="0">Выберете значение</option>';

    var obj = jQuery.parseJSON(JSONString);
    $.each(obj, function (index, value) {
        if(value['id'] == null){strContragentId = 'null';} else {strContragentId = value['id'].toString();}
        if(value['name'] == null){strContragentName = 'null';} else {strContragentName = value['name'].toString();}
        strContragentPickListContext = strContragentPickListContext + '<option value = "' + strContragentId + '">' + strContragentName + '</option>';
    });
    $("#contr_agent_select_field").html(strContragentPickListContext);
}

//Парсинг JSON списка реквизитов контрагента в выпадающий список
function JSONStringToRequisitsPickList(JSONString) {
    var strRequisitId = '';
    var strRequisitName = '';
    var strRequisitsPickListContext = '<option value="0">Выберете значение</option>';

    var obj = jQuery.parseJSON(JSONString);
    $.each(obj, function (index, value) {
        if(value['id'] == null){strRequisitId = 'null';} else {strRequisitId = value['id'].toString();}
        if(value['name'] == null){strRequisitName = 'null';} else {strRequisitName = value['name'].toString();}
        strRequisitsPickListContext = strRequisitsPickListContext + '<option value = "' + strRequisitId + '">' + strRequisitName + '</option>';
    });
    $("#contr_agent_requisits_list").html(strRequisitsPickListContext);
}

//-----------Ajax Functions------------
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

//Ajax получение списка операций по проекту
function doAjaxGetProjectOperationList(ProjectNum) {
    $.ajax({
        url : 'GetProjFinsOperList',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            FinsProjectId: ProjectNum
        }),
        success: function (data) {
            JSONStringToFinsOperationList(data.text);
        }
    });
};

//Ajax формы финансовой операции
function doAjaxFinsOperation() {
    //Скрытые поля
    var strRecordOperation = $('#finsedittypeid').attr('value');//update/new/delete
    var strFinsRecordId = $('#recordid').attr('value');//Id записи
    var strFinsBlockFlg = $('#fieldlockflgid').attr('value');//Признак блокировки
    var strFinsOpertype = $('#finsopertypeid').attr('value');//Тип транзакции profit/expense/transfer
    var strProjectId = $('#projectidid').attr('value');//Id проекта
    var strFinsContrAgent = $('#finscontragentid').attr('value');//Id контрагента
    var strFinsRequisites = $('#requisitesid').attr('value');//Id реквезита
    //var strOperDt = $('#fieldoperdateid').attr('value');

    //Доступные поля
    var strFinsAmount = $('#fieldamountid').val();//Сумма
    var strFinsDetail = $('#fielddetailid').val();//Детали
    var strPaymentAccIn = $('#paymentaccinid').val();//Счет поступления
    var strPaymentAccOut = $('#paymentaccoutid').val();//Счет списания
    var strFinsArticle = $('#finsarticleid').val();//Статья

    $.ajax({
        url : 'FinsOperationForm',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            RecordOperation: strRecordOperation,
            Row_Id: strFinsRecordId,
            Lock_Flg: strFinsBlockFlg,
            //Operation_Dt: strOperDt,
            Amount: strFinsAmount,
            Detail: strFinsDetail,
            Fins_Transaction_Type: strFinsOpertype,
            Pay_Acc_In: strPaymentAccIn,
            Pay_Acc_Out: strPaymentAccOut,
            Fins_Article: strFinsArticle,
            ProjectId: strProjectId,
            Contragent: strFinsContrAgent,
            Requisite: strFinsRequisites
        }),
        success: function (data) {
            //JSONStringToFinsOperationList(data.text);
            doAjaxGetProjectOperationList(strProjectId);
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
            JSONStringToContragentPickList(data.text);
        }
    });
};

//Ajax получение списка реквезитов для контрагента
function doAjaxGetContragentRequisits(ContragentId) {
    $.ajax({
        url : 'GetContragentRequisits',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            ContragentId: ContragentId
        }),
        success: function (data) {
            JSONStringToRequisitsPickList(data.text);
        }
    });
};

//Ajax получения отчета
function doAjaxGetProjectProfit(ProjectId) {
    $.ajax({
        url : 'GetReport',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            ReportName: 'GetProjectProfit',
            ProjectId: ProjectId
        }),
        success: function (data) {
            $('#projectprofitid').html(data.text);
        }
    });
};