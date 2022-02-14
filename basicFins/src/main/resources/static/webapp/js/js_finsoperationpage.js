

//Функция при загрузки страницы
function StartPage() {
    $("#fins_operation_count_id").val("5");
    $("#fins_operation_counter_id").val("0");
    doAjaxGetUserCache("FinsOperations");//Получение списка проектов в левой панели
    doAjaxGetActiveProjectContext();//Заполнение контекста экрана для активного проекта
    doAjaxGetContragentsList();//Получение списка контрагентов
    doAjaxGetLovList();
    doAjaxGetPurposeList();
    SetROForm();//Сделать форму RO

};


//-------------Функции событий-----------------
//Событие нажатия на проект в левой панели
/*ВАЖНО: jqury слушает только элементы существующие на момент инициализации скрипта,
т.к. проекты создаются из ajax, то слушатель нужно поставить на родительский элемент div,
который существует изначально на странице */

//Событие нажатия на финансовую операцию
$(function(){
    var strFinsRecordId = "";
    var strFinsBlockFlg = "";
    var strFinsOpertype = "";//Тип транзакции profit/expense/transfer
    var strOperDt = "";
    var strOperDtUser = "";
    var strFinsAmount = "";
    var strFinsDetail = "";
    var strPaymentAccIn = "";
    var strPaymentAccOut = "";
    var strFinsArticle = "";
    var strFinsContrAgent = "";
    var strFinsRequisites = "";
    var strFinsPurposeId = "";

    $("#financetable").on("click", ".fincrowlink", function () {
        UnSetROForm();
        strFinsRecordId = $(this).find('.fieldid').html();
        strFinsBlockFlg = $(this).find('.fieldlockflg').html();
        strFinsOpertype = $(this).find('.fieldfinsopertype').html();
        strOperDt = $(this).find('.fieldoperdate').html();
        strOperDtUser = $(this).find('.fieldoperdate_user').html();
        strFinsAmount = DisbandAmount($(this).find('.fieldamount').html());
        strFinsDetail = $(this).find('.fielddetail').html();
        strPaymentAccIn = $(this).find('.fieldpayaccin').attr('acc_num');
        strPaymentAccOut = $(this).find('.fieldpayaccout').attr('acc_num');
        strFinsArticle = $(this).find('.fieldfinsarticle').html();
        strFinsContrAgent = $(this).find('.fieldfinscontragent').html();
        strFinsRequisites = $(this).find('.fieldrequisites').html();
        strFinsPurposeId = $(this).find('.fieldpurposeid').html();

        $('#finsedittypeid').attr('value','update');//update/new/delete
        $('#recordid').attr('value',strFinsRecordId);
        $('#fieldlockflgid').attr('value',strFinsBlockFlg);
        $('#finsopertypeid').attr('value',strFinsOpertype);//profit/expense/transfer
        $('#fieldoperdateid').attr('value',strOperDt);
        $('#fieldoperdateuserid').attr('value',strOperDtUser);
        $('#fieldamountid').attr('value',strFinsAmount);
        $('#fielddetailid').attr('value',strFinsDetail);
        $('#paymentaccinid_list').attr('select_value',strPaymentAccIn);
        SetActiveSelect('#paymentaccinid_list',strPaymentAccIn);
        $('#paymentaccoutid_list').attr('select_value',strPaymentAccOut);
        SetActiveSelect('#paymentaccoutid_list',strPaymentAccOut);
        $('#finsarticleid_list').attr('select_value',strFinsArticle);
        SetActiveSelect('#finsarticleid_list',strFinsArticle);
        $('#finscontragentid').attr('value',strFinsContrAgent);
        SetActiveSelect('#contr_agent_select_field',strFinsContrAgent);

        //Отклчил спинер внутри, чтобы не обновлялся экран при нажатии на строку
        doAjaxGetContragentRequisits(strFinsContrAgent,'SetActiveSelect','#contr_agent_requisits_list',strFinsRequisites);//Получить список реквезитов контрагента + сделать активный реквезит

        $('#requisitesid').attr('value',strFinsRequisites);

        $('#finsedittypeid').val('update');//update/new/delete
        $('#recordid').val(strFinsRecordId);
        $('#fieldlockflgid').val(strFinsBlockFlg);
        $('#finsopertypeid').val(strFinsOpertype);//profit/expense/transfer
        $('#fieldoperdateid').val(strOperDt);
        $('#fieldoperdateuserid').val(strOperDtUser);
        $('#fieldamountid').val(strFinsAmount);
        $('#fielddetailid').val(strFinsDetail);
        $('#finscontragentid').val(strFinsContrAgent);
        $('#requisitesid').val(strFinsRequisites);

        SetActiveSelect('#purpose_list_id',strFinsPurposeId);

        //Подсвет Приход,Расход,Перевод
        if(strFinsOpertype == 'profit'){
            $('#arrivalbt').addClass('active');
            $('#transferbt').removeClass('active');
            $('#expensebt').removeClass('active');
            $('#divpaymentaccoutid').hide();
            $('#divpaymentaccinid').show();
            $('#divcontragentrequisitsid').show();
            $('#divcontragentfieldid').show();
        }
        if(strFinsOpertype == 'expense'){
            $('#arrivalbt').removeClass('active');
            $('#transferbt').removeClass('active');
            $('#expensebt').addClass('active');
            $('#divpaymentaccoutid').show();
            $('#divpaymentaccinid').hide();
            $('#divcontragentrequisitsid').show();
            $('#divcontragentfieldid').show();
        }
        if(strFinsOpertype == 'transfer'){
            $('#arrivalbt').removeClass('active');
            $('#transferbt').addClass('active');
            $('#expensebt').removeClass('active');
            $('#divpaymentaccoutid').show();
            $('#divpaymentaccinid').show();
            $('#divcontragentrequisitsid').hide();
            $('#divcontragentfieldid').hide();
        }
    });
});

//Событие нажатия на кнопку "Создать" финансовую операцию
function NewFinsOperation(){
    $('#finsedittypeid').attr('value','insert');
    $('#finsedittypeid').val('insert');
    $('#arrivalbt').removeAttr('disabled');
    $('#expensebt').removeAttr('disabled');
    $('#transferbt').removeAttr('disabled');
    ClearFinsForm();
};

//Событие нажатия на кнопку "Удалить" финансовую операцию
function DeleteFinsOperation(){
    $('#finsedittypeid').attr('value','delete');
    $('#finsedittypeid').val('delete');
    doAjaxFinsOperation();
    ClearFinsForm();
    SetROForm();
};


//Событие нажатия на кнопку "Отменить" финансовую операцию
function ResetFinsOperation(){
    ClearFinsForm();
    SetROForm();
};

//Событие нажатия на кнопку "Сохранить" финансовую операцию
function SaveFinsOperation(){
    doAjaxFinsOperation()
    ClearFinsForm ();
    SetROForm();
};

//Событие нажатия на кнопку "Вперед (Финс. опер.)"
function NextFinsOperationRowGroup(){
    try{
        var intCounter = $("#fins_operation_counter_id").val();
        intCounter++;
        $("#fins_operation_counter_id").val(intCounter);
    }catch (err_1){
        console.log("NextFinsOperationRowGroup err_1: " + err_1)
        $("#fins_operation_count_id").val("5");
        $("#fins_operation_counter_id").val("0");
    }
    doAjaxGetActiveProjectContext();
    ClearFinsForm ();
    SetROForm();
};

//Событие нажатия на кнопку "Назад (Финс. опер.)"
function BackFinsOperationRowGroup(){
    try{
        var intCounter = $("#fins_operation_counter_id").val();
        intCounter = intCounter - 1;
        if(intCounter < 0){
            intCounter = 0;
        }
        $("#fins_operation_counter_id").val(intCounter);
    }catch (err_1){
        console.log("NextFinsOperationRowGroup err_1: " + err_1)
        $("#fins_operation_count_id").val("5");
        $("#fins_operation_counter_id").val("0");
    }
    doAjaxGetActiveProjectContext();
    ClearFinsForm ();
    SetROForm();
};

//Событие выбора значения выпадающего списка контрагента
$(function(){
    $("#contr_agent_select_field").change( function(){
        var strContragentId = $('#contr_agent_select_field').val();
        $('#finscontragentid').attr('value',strContragentId);
        $('#finscontragentid').val(strContragentId);
        doAjaxGetContragentRequisits(strContragentId,'','','');//Получить список реквезитов контрагента
    });
});

//Событие выбора значения выпадающего списка контрагента ПОИСК
$(function(){
    $("#contr_agent_select_field_2").change( function(){
        var strContragentId = $('#contr_agent_select_field').val();
        $("#contr_agent_select_field_2").attr("select_value",strContragentId)
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

//Событие выбора значения выпадающего "Счет поступления"
$(function(){
    $("#paymentaccinid_list").change( function(){
        var strSelectValue = $('#paymentaccinid_list').val();
        $('#paymentaccinid_list').attr('select_value',strSelectValue);
    });
});

//Событие выбора значения выпадающего "Счет списания"
$(function(){
    $("#paymentaccoutid_list").change( function(){
        var strSelectValue = $('#paymentaccoutid_list').val();
        $('#paymentaccoutid_list').attr('select_value',strSelectValue);
    });
});

//Событие выбора значения выпадающего "Статья"
$(function(){
    $("#finsarticleid_list").change( function(){
        var strSelectValue = $('#finsarticleid_list').val();
        $('#finsarticleid_list').attr('select_value',strSelectValue);
    });
});

//Событие выбора значения выпадающего "Цель"
$(function(){
    $("#purpose_list_id").change( function(){
        var strSelectValue = $(this).val();
        $('#purpose_list_id').attr('select_value',strSelectValue);
    });
});

//Событие Нажатия на "Приход"
$(function(){
    $("#arrivalbt").on('click', function(){
        if($('#arrivalbt').attr('disabled') != 'disabled') {
            UnSetROForm();
            $('#finsopertypeid').attr('value', 'profit');
            $('#finsopertypeid').val('profit');
            $('#divpaymentaccoutid').hide();
            $('#divpaymentaccinid').show();
            $('#divcontragentrequisitsid').show();
            $('#divcontragentfieldid').show();
        }
    });
});

//Событие Нажатия на "Расход"
$(function(){
    $("#expensebt").on('click', function(){
        if($('#expensebt').attr('disabled') != 'disabled') {
            UnSetROForm();
            $('#finsopertypeid').attr('value', 'expense');
            $('#finsopertypeid').val('expense');
            $('#divpaymentaccoutid').show();
            $('#divpaymentaccinid').hide();
            $('#divcontragentrequisitsid').show();
            $('#divcontragentfieldid').show();
        }
    });
});

//Событие Нажатия на "Перевод"
$(function(){
    $("#transferbt").on('click', function(){
        if($('#transferbt').attr('disabled') != 'disabled'){
            UnSetROForm();
            $('#finsopertypeid').attr('value','transfer');
            $('#finsopertypeid').val('transfer');
            $('#divpaymentaccoutid').show();
            $('#divpaymentaccinid').show();
            $('#divcontragentrequisitsid').hide();
            $('#divcontragentfieldid').hide();
        }
    });
});

//------------Общие функции----------------------------------
//Очистить форму финансовой операции
function ClearFinsForm (){
    $('#recordid').attr('value','');
    $('#fieldlockflgid').attr('value','');
    $('#finsopertypeid').attr('value','');
    $('#fieldoperdateid').attr('value','');
    $('#fieldoperdateuserid').attr('value','');
    $('#fieldamountid').attr('value','');
    $('#fielddetailid').attr('value','');
    $('#paymentaccinid_list').attr('select_value','');
    $('#paymentaccoutid_list').attr('select_value','');
    $('#finsarticleid_list').attr('select_value','');
    $('#finscontragentid').attr('value','');
    $('#requisitesid').attr('value','');
    $('#recordid').val('');
    $('#fieldlockflgid').val('');
    $('#finsopertypeid').val('');
    $('#fieldoperdateid').val('');
    $('#fieldoperdateuserid').val('');
    $('#fieldamountid').val('');
    $('#fielddetailid').val('');
    $('#finscontragentid').val('');
    $('#requisitesid').val('');
    $('#purpose_list_id').attr('select_value','');
    $('#arrivalbt').removeClass('active');
    $('#transferbt').removeClass('active');
    $('#expensebt').removeClass('active');

    //Сброс списков
    $('#paymentaccinid_list option:contains("Выберите значение")').prop('selected', true);
    $('#paymentaccoutid_list option:contains("Выберите значение")').prop('selected', true);
    $('#finsarticleid_list option:contains("Выберите значение")').prop('selected', true);
    $('#contr_agent_select_field option:contains("Выберите значение")').prop('selected', true);
    $('#contr_agent_requisits_list option:contains("Выберите значение")').prop('selected', true);
    $('#purpose_list_id option:contains("Выберите значение")').prop('selected', true);

};

//Парсинг строки JSON в список финансовых операций и заполнение таблицы
function JSONStringToFinsOperationList(JSONString) {
    try{
        var strFinsOpertype = "";
        var strFinsOpertypeRU = "";
        var strFinsOpertypeColor = "";
        var strFinsOperationListContext = "";
        var strRowId = "";
        var strLockFlg = "";
        var strOperDate = "";
        var strOperDateUser = "";
        var strOperLoginUser = "";
        var strFinsAmount = "";
        var strFinsDetail = "";
        var strPaymentAccIn = "";
        var strPaymentAccInName = "";
        var strPaymentAccInPosition = "";
        var strPaymentAccOut = "";
        var strPaymentAccOutName = "";
        var strPaymentAccOutPosition = "";
        var strFinsArticle = "";
        var strProjectId = "";
        var strFinsContrAgent = "";
        var strFinsRequisites = "";
        var strArticleName = "";
        var strContrAgentName = "";
        var strRequisitesName = "";
        var strPurposeName = "";
        var strPurposeId = "";

        var obj = jQuery.parseJSON(JSONString);
        $.each(obj, function (index, value) {
            if(value["id"] == null){strRowId = 'null';} else {strRowId = value["id"].toString();}
            if(value["lockflg"] == null){strLockFlg = 'null';} else {strLockFlg = value["lockflg"].toString();}
            if(value["operdate"] == null){strOperDate = '';} else {strOperDate = value["operdate"].toString();}
            if(value["operdate_user"] == null){strOperDateUser = '';} else {strOperDateUser = value["operdate_user"].toString();}
            if(value["oper_login_user"] == null){strOperLoginUser = '';} else {strOperLoginUser = value["oper_login_user"].toString();}
            if(value["detail"] == null){strFinsDetail = '';} else {strFinsDetail = value["detail"].toString();}
            if(value["finsopertype"] == null){strFinsOpertype = '';} else {strFinsOpertype = value["finsopertype"].toString();}
            if(value["amount"] == null){strFinsAmount = '';} else {
                strFinsAmount = value["amount"].toString();
                strFinsAmount = FormatAmount(strFinsAmount,strFinsOpertype);
            }
            if(value["payaccin"] == null){strPaymentAccIn = '';} else {strPaymentAccIn = value["payaccin"].toString();}
            if(value["payaccin_name"] == null){strPaymentAccInName = '';} else {strPaymentAccInName = value["payaccin_name"].toString();}
            if(value["payaccin_position"] == null){strPaymentAccInPosition = '';} else {strPaymentAccInPosition = value["payaccin_position"].toString();}
            if(value["payaccout"] == null){strPaymentAccOut = '';} else {strPaymentAccOut = value["payaccout"].toString();}
            if(value["payaccout_name"] == null){strPaymentAccOutName = '';} else {strPaymentAccOutName = value["payaccout_name"].toString();}
            if(value["payaccout_position"] == null){strPaymentAccOutPosition = '';} else {strPaymentAccOutPosition = value["payaccout_position"].toString();}
            if(value["finsarticle"] == null){strFinsArticle = '';} else {strFinsArticle = value["finsarticle"].toString();}
            if(value["projectid"] == null){strProjectId = '';} else {strProjectId = value["projectid"].toString();}
            if(value["finscontragent"] == null){strFinsContrAgent = '';} else {strFinsContrAgent = value["finscontragent"].toString();}
            if(value["requisites"] == null){strFinsRequisites = '';} else {strFinsRequisites = value["requisites"].toString();}

            if(value["requisites_name"] == null){strRequisitesName = '';} else {strRequisitesName = value["requisites_name"].toString();}
            if(value["contragent_name"] == null){strContrAgentName = '';} else {strContrAgentName = value["contragent_name"].toString();}
            if(value["article_name"] == null){strArticleName = '';} else {strArticleName = value["article_name"].toString();}
            if(value["purpose_id"] == null){strPurposeId = '';} else {strPurposeId = value["purpose_id"].toString();}
            if(value["purpose_name"] == null){strPurposeName = '';} else {strPurposeName = value["purpose_name"].toString();}

            switch(strFinsOpertype) {
                case "profit":
                    strFinsOpertypeRU = "Приход";
                    strFinsOpertypeColor = " fin-operation-color-green";
                    break;
                case "expense":
                    strFinsOpertypeRU = "Расход";
                    strFinsOpertypeColor = " fin-operation-color-red";
                    break;
                case "transfer":
                    strFinsOpertypeRU = "Перевод";
                    strFinsOpertypeColor = "";
                    break;
                default:{
                    strFinsOpertypeRU = "null";
                    strFinsOpertypeColor = "";
                }
            }

            strFinsOperationListContext = strFinsOperationListContext
                + '<tr class="fincrowlink">'
                + '<th class="fieldfinsopertype f-d-n">' + strFinsOpertype + '</th>'
                + '<th class="fieldfinsopertyperu">' + strFinsOpertypeRU + '</th>'
                + '<th class="fieldid f-d-n">' + strRowId + '</th>'
                + '<th class="fieldlockflg f-d-n">' + strLockFlg + '</th>'
                + '<th class="fieldoperdate_user">' + strOperDateUser + '</th>'
                + '<th class="fieldamount f-d-n">' + strFinsAmount + '</th>'
                + '<th class="fieldamountprint' + strFinsOpertypeColor +'">' + strFinsAmount + ' руб.' + '</th>'
                + '<th class="fieldpayaccin" acc_num="' + strPaymentAccIn + '">' + strPaymentAccIn + '<div><small>' + strPaymentAccInName + '</small></div>' + '<div><small>' + strPaymentAccInPosition + '</small></div></th>'
                + '<th class="fieldpayaccout" acc_num="' + strPaymentAccOut + '">' + strPaymentAccOut + '<div><small>' + strPaymentAccOutName + '</small></div>' + '<div><small>' + strPaymentAccOutPosition + '</small></div></th>'
                + '<th class="fieldfinsarticle f-d-n">' + strFinsArticle + '</th>'
                + '<th class="fieldprojectid f-d-n">' + strProjectId + '</th>'
                + '<th class="fieldfinscontragent f-d-n">' + strFinsContrAgent + '</th>'
                + '<th class="fieldrequisites f-d-n">' + strFinsRequisites + '</th>'

                + '<th class="fieldfinsarticle_name">' + strArticleName + '</th>'
                + '<th class="fieldpurposeid f-d-n">' + strPurposeId + '</th>'
                + '<th class="fieldpurposename">' + strPurposeName + '</th>'
                + '<th class="fieldfinscontragent_name f-d-n">' + strContrAgentName + '</th>'
                + '<th class="fieldrequisites_name f-d-n">' + strRequisitesName + '</th>'

                + '<th class="fielddetail">' + strFinsDetail + '</th>'
                + '<th class="fieldoperdate">' + strOperDate + '</th>'
                + '<th class="fieldoperlogin">' + strOperLoginUser + '</th>'
                + '</tr>';
        });
    }catch (e_1) {
        console.log("JSONStringToFinsOperationList ERROR: " + e_1);
    }
    $("#financetable").html(strFinsOperationListContext);
}

//Парсинг JSON списка контрагентов в выпадающий список
function JSONStringToContragentPickList(JSONString) {
    var strContragentId = '';
    var strContragentName = '';
    var strContragentPickListContext = '<option value="0">Выберите значение</option>';

    try{
        var obj = jQuery.parseJSON(JSONString);
        $.each(obj, function (index, value) {
            if(value['id'] == null){strContragentId = 'null';} else {strContragentId = value['id'].toString();}
            if(value['name'] == null){strContragentName = 'null';} else {strContragentName = value['name'].toString();}
            strContragentPickListContext = strContragentPickListContext + '<option value = "' + strContragentId + '">' + strContragentName + '</option>';
        });
    }catch (e_1) {
        console.log("JSONStringToContragentPickList ERROR: " + e_1);
    }
    $("#contr_agent_select_field").html(strContragentPickListContext);
    $("#contr_agent_select_field_2").html(strContragentPickListContext);

}

//Парсинг JSON списка реквизитов контрагента в выпадающий список
function JSONStringToRequisitsPickList(JSONString) {
    var strRequisitId = '';
    var strRequisitName = '';
    var strRequisitsPickListContext = '<option value="0">Выберите значение</option>';

    var obj = jQuery.parseJSON(JSONString);
    $.each(obj, function (index, value) {
        if(value['id'] == null){strRequisitId = 'null';} else {strRequisitId = value['id'].toString();}
        if(value['name'] == null){strRequisitName = 'null';} else {strRequisitName = value['name'].toString();}
        strRequisitsPickListContext = strRequisitsPickListContext + '<option value = "' + strRequisitId + '">' + strRequisitName + '</option>';
    });
    $("#contr_agent_requisits_list").html(strRequisitsPickListContext);
}

//Парсинг JSON списка счетов сотрудников компаний проекта
function JSONStringToContactFinsAccList(JSONString) {
    var strContactName = '';
    var strContactFinsAcc = '';
    var strContactFinsAccListContext = '<option value="0">Выберите значение</option>';

    var obj = jQuery.parseJSON(JSONString);
    $.each(obj, function (index, value) {
        if(value['first_name'] == null){strContactName = 'null';} else {strContactName = value['first_name'].toString();}
        if(value['fins_acc'] == null){strContactFinsAcc = 'null';} else {strContactFinsAcc = value['fins_acc'].toString();}
        strContactFinsAccListContext = strContactFinsAccListContext + '<option value = "' + strContactFinsAcc + '">' + strContactName + '</option>';
    });
    $("#paymentaccoutid_list").html(strContactFinsAccListContext);
    $("#paymentaccinid_list").html(strContactFinsAccListContext);
    $("#paymentacc_select_id_list").html(strContactFinsAccListContext);

}

//Парсинг JSON списка LOV (Поле "Статья")
function JSONStringToFinsArticleList(JSONString) {
    var strFinsArticleId = '';
    var strFinsArticleValue = '';
    var strFinsArticleListContext = '<option value="0">Выберите значение</option>';

    var obj = jQuery.parseJSON(JSONString);
    $.each(obj, function (index, value) {
        strFinsArticleId = value['id'].toString();
        if(value['text_val'] == null){strFinsArticleValue = 'null';} else {strFinsArticleValue = value['text_val'].toString();}
        strFinsArticleListContext = strFinsArticleListContext + '<option value = "' + strFinsArticleId + '">' + strFinsArticleValue + '</option>';
    });
    $("#finsarticleid_list").html(strFinsArticleListContext);
}

//Парсинг JSON списка целей (Поле "Цель")
function JSONStringToFinsPurposeList(JSONString) {
    try{
        var strFinsPurposeId = '';
        var strFinsPurposeName = '';
        var strFinsPurposeListContext = '<option value="0" key="0">Выберите значение</option>';

        var obj = jQuery.parseJSON(JSONString);
        $.each(obj, function (index, value) {
            strFinsPurposeId = value['id'].toString();
            if(value['name'] == null){strFinsPurposeName = 'null';} else {strFinsPurposeName = value['name'].toString();}
            strFinsPurposeListContext = strFinsPurposeListContext + '<option value = "' + strFinsPurposeId + '" key="' + strFinsPurposeId +'">' + strFinsPurposeName + '</option>';
        });
        $("#purpose_list_id").html(strFinsPurposeListContext);
    }catch (e_1) {
        console.log("JSONStringToFinsPurposeList ERROR: " + e_1);
    }
}

//Функция установки выбранного значения
function SetActiveSelect(ListSelector,SelectedVal){
    $(ListSelector).find('[selected]').prop('selected', false);//Сбросить текущее активное значение
    $(ListSelector).find('[value = "' + SelectedVal + '"]').prop('selected', true);
    $(ListSelector).attr('select_value',SelectedVal);

}

//Сделать форму RO
function SetROForm(){
    $('#fieldoperdateuserid').attr('readonly', true);
    $('#fieldamountid').attr('readonly', true);
    $('#paymentaccinid_list').attr('disabled', true);
    $('#paymentaccoutid_list').attr('disabled', true);
    $('#contr_agent_select_field').attr('disabled', true);
    $('#finsarticleid_list').attr('disabled', true);
    $('#fielddetailid').attr('readonly', true);
    $('#contr_agent_requisits_list').attr('disabled', true);
    $('#purpose_list_id').attr('disabled', true);

    $('#expensebt').attr('disabled', true);
    $('#transferbt').attr('disabled', true);
    $('#arrivalbt').attr('disabled', true);
}

//Сделать форму not RO
function UnSetROForm(){
    $('#fieldoperdateuserid').attr('readonly', false);
    $('#fieldamountid').attr('readonly', false);
    $('#paymentaccinid_list').attr('disabled', false);
    $('#paymentaccoutid_list').attr('disabled', false);
    $('#contr_agent_select_field').attr('disabled', false);
    $('#finsarticleid_list').attr('disabled', false);
    $('#fielddetailid').attr('readonly', false);
    $('#contr_agent_requisits_list').attr('disabled', false);
    $('#purpose_list_id').attr('disabled', false);

    $('#expensebt').attr('disabled', true);
    $('#transferbt').attr('disabled', true);
    $('#arrivalbt').attr('disabled', true);
}


//-----------Ajax Functions------------
//Ajax получение UserCache. Заполнение Контекста экрана в зависимости от активного проекта
function doAjaxGetActiveProjectContext() {
    SpinnerOn("doAjaxGetActiveProjectContext");
    try {
        $.ajax({
            url: 'GetUserCache',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                //
            }),
            success: function (data) {
                var obj = jQuery.parseJSON(data.text);
                var strActiveProjectId = obj.active_proj;
                var strCount = $('#fins_operation_count_id').val();
                var strCounter = $('#fins_operation_counter_id').val();
                if(strCount=""){strCount="5";}
                if(strCounter=""){strCounter="0";}
                doAjaxGetProjectOperationList(strActiveProjectId,strCount,strCounter);
                doAjaxGetProjectProfit();
                doAjaxGetContactFinsAccProject(strActiveProjectId);
                SpinnerOff("doAjaxGetActiveProjectContext");
            }
        });
    }catch (e) {
        SpinnerOff("doAjaxGetActiveProjectContext");
        console.log("Error doAjaxGetActiveProjectContext: " + e);
        return "";
    }
};



//Ajax получение списка операций по проекту
function doAjaxGetProjectOperationList(ProjectNum,Count,Counter) {
    SpinnerOn("doAjaxGetProjectOperationList");
    try {
        $.ajax({
            url: 'GetProjFinsOperList',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                FinsProjectId: ProjectNum,
                RowCount: Count,
                RowCounter: Counter

            }),
            success: function (data) {
                if (data.text != null) {
                    JSONStringToFinsOperationList(data.text);
                    SpinnerOff("doAjaxGetProjectOperationList");
                }
            }
        });
    }catch (e) {
        SpinnerOff("doAjaxGetProjectOperationList");
        console.log("Error doAjaxGetProjectOperationList: " + e);
        return "";
    }
};

//Ajax формы финансовой операции
function doAjaxFinsOperation() {
    SpinnerOn("doAjaxFinsOperation");
    try {
        //Скрытые поля
        var strRecordOperation = $('#finsedittypeid').attr('value');//update/new/delete
        var strFinsRecordId = $('#recordid').attr('value');//Id записи
        var strFinsBlockFlg = $('#fieldlockflgid').attr('value');//Признак блокировки
        var strFinsOpertype = $('#finsopertypeid').attr('value');//Тип транзакции profit/expense/transfer
        var strFinsContrAgent = $('#finscontragentid').attr('value');//Id контрагента
        var strFinsRequisites = $('#requisitesid').attr('value');//Id реквезита
        var strPurposeId = $('#purpose_list_id').attr('select_value');

        //Доступные поля
        var strOperationDtUser = $('#fieldoperdateuserid').val();//Сумма
        var strFinsAmount = $('#fieldamountid').val();//Сумма
        var strFinsDetail = $('#fielddetailid').val();//Детали
        var strPaymentAccIn = $('#paymentaccinid_list').attr('select_value');
        var strPaymentAccOut = $('#paymentaccoutid_list').attr('select_value');
        var strFinsArticle = $('#finsarticleid_list').attr('select_value');


        $.ajax({
            url: 'FinsOperationForm',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                RecordOperation: strRecordOperation,
                Row_Id: strFinsRecordId,
                Lock_Flg: strFinsBlockFlg,
                Operation_Dt_User: strOperationDtUser,
                Amount: strFinsAmount,
                Detail: strFinsDetail,
                Fins_Transaction_Type: strFinsOpertype,
                Pay_Acc_In: strPaymentAccIn,
                Pay_Acc_Out: strPaymentAccOut,
                Fins_Article: strFinsArticle,
                Contragent: strFinsContrAgent,
                Requisite: strFinsRequisites,
                PurposeId: strPurposeId
            }),
            success: function (data) {
                console.log(data);
                doAjaxGetActiveProjectContext();
                SpinnerOff("doAjaxFinsOperation");
            }
        });
    }catch (e) {
        SpinnerOff("doAjaxFinsOperation");
        console.log("Error doAjaxFinsOperation: " + e);
        return "";
    }
};

//Ajax получение списка Контрагентов
function doAjaxGetContragentsList() {
    SpinnerOn("doAjaxGetContragentsList");
    try {
        $.ajax({
            url: 'GetContragentsList',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                //
            }),
            success: function (data) {
                JSONStringToContragentPickList(data.text);
                SpinnerOff("doAjaxGetContragentsList");
            }
        });
    }catch (e) {
        SpinnerOff("doAjaxGetContragentsList");
        console.log("Error doAjaxGetContragentsList: " + e);
        return "";
    }
};

//Ajax получение списка реквезитов для контрагента
function doAjaxGetContragentRequisits(ContragentId,Operation,Selector,SelectValue) {
    //Отклчил спинер внутри, чтобы не обновлялся экран при нажатии на строку
    //SpinnerOn("doAjaxGetContragentRequisits");
    try {
        $.ajax({
            url: 'GetContragentRequisits',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                ContragentId: ContragentId
            }),
            success: function (data) {
                JSONStringToRequisitsPickList(data.text);
                if(Operation == 'SetActiveSelect'){
                    SetActiveSelect(Selector,SelectValue);
                }
                //SpinnerOff("doAjaxGetContragentRequisits");
            }
        });
    }catch (e) {
        //SpinnerOff("doAjaxGetContragentRequisits");
        console.log("Error doAjaxGetContragentRequisits: " + e);
        return "";
    }
};

//Ajax получение списка счетов сотрудников проекта
function doAjaxGetContactFinsAccProject(ProjectId) {
    SpinnerOn("doAjaxGetContactFinsAccProject");
    try {
        $.ajax({
            url: 'GetContactFinsAccProject',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                ProjectId: ProjectId
            }),
            success: function (data) {
                JSONStringToContactFinsAccList(data.text);
                SpinnerOff("doAjaxGetContactFinsAccProject");
            }
        });
    }catch (e) {
        SpinnerOff("doAjaxGetContactFinsAccProject");
        console.log("Error doAjaxGetContactFinsAccProject: " + e);
        return "";
    }
};

//Ajax получения отчета
function doAjaxGetProjectProfit() {
    SpinnerOn("doAjaxGetProjectProfit");
    try {
        $.ajax({
            url: 'GetReport',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                ReportName: 'GetProjectProfit'
            }),
            success: function (data) {
                var obj = jQuery.parseJSON(data.text);
                var strProjectProfit = obj.ProjectProfit;
                var strProjectIncome = obj.ProjectIncome;
                var strProjectExpense = obj.ProjectExpense;
                strProjectProfit = FormatAmount(strProjectProfit,'none');
                strProjectIncome = FormatAmount(strProjectIncome,'none');
                strProjectExpense = FormatAmount(strProjectExpense,'none');

                $('#projectprofitid').html(strProjectProfit);
                $('#projectprofitid_sub').html(strProjectIncome);
                $('#projectexpenseid_sub').html(strProjectExpense);
                $('#projectsaldoid_sub').html(strProjectProfit);
                SpinnerOff("doAjaxGetProjectProfit");
            }
        });
    }catch (e) {
        SpinnerOff("doAjaxGetProjectProfit");
        console.log("Error doAjaxGetProjectProfit: " + e);
        return "";
    }
};

//Ajax получение списка Lov
function doAjaxGetLovList() {
    SpinnerOn("doAjaxGetLovList");
    try {
        $.ajax({
            url: 'GetLovList',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                //
            }),
            success: function (data) {
                JSONStringToFinsArticleList(data.text)//Формирование выпадающего списка
                SpinnerOff("doAjaxGetLovList");
            }
        });
    }catch (e) {
        SpinnerOff("doAjaxGetLovList");
        console.log("Error doAjaxGetLovList: " + e);
        return "";
    }
};

//Ajax получение списка целей
function doAjaxGetPurposeList() {
    SpinnerOn("doAjaxGetPurposeList");
    try {
        $.ajax({
            url: 'GetPurposeList',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                //
            }),
            success: function (data) {
                JSONStringToFinsPurposeList(data.text)//Формирование выпадающего списка целей
                SpinnerOff("doAjaxGetPurposeList");
            }
        });
    }catch (e) {
        SpinnerOff("doAjaxGetPurposeList");
        console.log("Error doAjaxGetPurposeList: " + e);
        return "";
    }
}


//Форматировать число
function FormatAmount(Amount,Type){
    var arr1 = Amount.toString().split(".");
    if(arr1.length == 1){
        Amount = Amount + ".00";
    }else{
        if(arr1[1].length == 1){
            Amount = Amount + "0";
        }
    }
    if(Type == 'profit'){
        Amount = "+"+Amount;
    }
    if(Type == 'expense'){
        Amount = "-"+Amount;
    }
    if(Type == 'transfer'){
        Amount = "→"+Amount;
    }
    return Amount;
}

function DisbandAmount(Amount) {
    var strVal1 = Amount;
    strVal1 = strVal1.replace('+','').replace('-','').replace('→','');
    return strVal1;
}

