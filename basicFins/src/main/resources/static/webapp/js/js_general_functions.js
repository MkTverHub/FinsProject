/*
    Created by Ivribin
*/

//========================================================
/*
    Парсинг Json в контекст таблицы
        strTableName: Имя таблицы
        strIdName: Имя поля с Row_id БД
        arrFields: перечисление имен полей
        strJsonContext: Json контекст
 */
function JsonToTableBody(strTableName,strIdName,arrFields,strJsonContext){
    try {
        var strBody = "";
        var strRow = "";
        var obj = $.parseJSON(strJsonContext);
        $.each(obj, function (index, value) {
            strRow = "<tr class='" + strTableName + "_t_row_class' " + strTableName + "_row_id='" + value[strIdName] + "'>";
            for (var i = 0; i < arrFields.length; i++) {
                strRow = strRow + "<th class='" + arrFields[i] + "_t_cell_class' value='" + value[arrFields[i]] + "'>" + value[arrFields[i]] + "</th>"
            }
            strRow = strRow + "</tr>"
            strBody = strBody + strRow;
        });
        return strBody;
    }catch (e) {
        console.log("Error JsonToTableBody: " + e);
        return "";
    }

}

//========================================================
//Ajax получение списка проектов в левой панели
function doAjaxGetProjectListLeft(PageName,ActiveProjectId) {
    SpinnerOn("doAjaxGetProjectListLeft");
    try {
        $.ajax({
            url: 'GetFinsProjectList',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({}),
            success: function (data) {
                var strProjectListContext = "";
                var obj = jQuery.parseJSON(data.text);
                var strLiClass = 'left-menu-item finsproject_list_row_li';
                var strLiContext = "";
                $.each(obj, function (index, value) {
                    if (PageName == "RoProject") {
                        strLiContext = '<div class="finsproject_list_link_row">' + value["name"] + '</div>';
                    } else {
                        strLiContext = '<a class="finsproject_list_link_row" href="/' + PageName + '?ProjectId=' + value["id"].toString() + '" projnum="' + value["id"].toString() + '">' + value["name"] + '</a>';
                    }
                    if (value["id"].toString() == ActiveProjectId && PageName != "RoProject") {
                        strLiClass = 'left-menu-item left-menu-selected-link finsproject_list_row';
                    } else {
                        strLiClass = 'left-menu-item finsproject_list_row_li finsproject_list_row';
                    }
                    strProjectListContext = strProjectListContext
                        + '<li id="' + value["id"].toString()
                        + '_rowid" class="' + strLiClass + '" projnum="' + value["id"].toString() + '">'
                        + strLiContext
                        + '</li>';
                });
                strProjectListContext = strProjectListContext
                    + '<li class="left-menu-item finsproject_list_row_li finsproject_list_row finsproject_list_row_addproject_btn">'
                    + '<a class="finsproject_list_link_row finsproject_list_link_row_addproject_btn" href="/Projects">Все проекты</a>'
                    + '</li>';
                $("#projectlistpanel").html(strProjectListContext);

                //Сообщение если не выбран проект
                if (ActiveProjectId == '0') {
                    //alert("Выбирите проект в левой панели!");
                    $("#success-alert-modal").attr('style', 'display: block; padding-right: 17px;');
                }
                SpinnerOff("doAjaxGetProjectListLeft");
            }
        });
    }catch (e) {
        SpinnerOff("doAjaxGetProjectListLeft");
        console.log("Error doAjaxGetProjectListLeft: " + e);
    }

};
//========================================================
//Ajax получение UserCache
function doAjaxGetUserCache(PageName) {
    SpinnerOn("doAjaxGetUserCache");
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
                //document.body.HashData = {ActiveProjectId:strActiveProjectId};
                doAjaxGetProjectListLeft(PageName, strActiveProjectId);
                SpinnerOff("doAjaxGetUserCache");
            }
        });
    }catch (e) {
        console.log("Error doAjaxGetUserCache: " + e);
        SpinnerOff("doAjaxGetUserCache");
    }
};


//=========================================================
//Spinner On
function SpinnerOn(AjaxChainName) {
    $("#spinner_ajax_chain").addClass(AjaxChainName);
    $("#spinner_main_div").removeClass('f-d-n');
};
//Spinner Off
/*Чтобы функция не падала на split не забудь включиь фрагмент спиннера на html шаблоне*/
function SpinnerOff(AjaxChainName) {
    $("#spinner_ajax_chain").removeClass(AjaxChainName);
    if($('#spinner_ajax_chain').attr('class').split(/\s+/).length == 1){
        $("#spinner_main_div").addClass('f-d-n');
    }
};

//========================================================
//Валидация
function validator(field_name,field_key,field_id,field_value,ErrorFlg) {
    var ErrorMsg = '';
    var regex = /g/;
    switch(field_key) {
        case 'CARD_NUM':
            regex = /\D/;
            if(field_value.match(regex)){
                ErrorMsg = field_name + ' должен содержать только числа';
                if(ErrorFlg == 'N'){ErrorFlg = 'Y';}
            }
        break;
        case 'INN':
            regex = /\D/;
            if(field_value.match(regex)){
                ErrorMsg = field_name + ' должен содержать только числа';
                if(ErrorFlg == 'N'){ErrorFlg = 'Y';}
            }
        break;
        case 'KPP':
            regex = /\D/;
            if(field_value.match(regex)){
                ErrorMsg = field_name + ' должен содержать только числа';
                if(ErrorFlg == 'N'){ErrorFlg = 'Y';}
            }
        break;
        case 'FINC_ACC':
            regex = /\D/;
            if(field_value.match(regex)){
                ErrorMsg = field_name + ' должен содержать только числа';
                if(ErrorFlg == 'N'){ErrorFlg = 'Y';}
            }
        break;
        case 'BIK':
            regex = /\D/;
            if(field_value.match(regex)){
                ErrorMsg = field_name + ' должен содержать только числа';
                if(ErrorFlg == 'N'){ErrorFlg = 'Y';}
            }
        break;
        case 'REQ_CRS':
            regex = /\D/;
            if(field_value.match(regex)){
                ErrorMsg = field_name + ' должен содержать только числа';
                if(ErrorFlg == 'N'){ErrorFlg = 'Y';}
            }
        break;
        case 'REQ_ADDR_INDEX':
            regex = /\D/;
            if(field_value.match(regex)){
                ErrorMsg = field_name + ' должен содержать только числа';
                if(ErrorFlg == 'N'){ErrorFlg = 'Y';}
            }
        break;
    }
    if(ErrorFlg == 'Y'){
        $(field_id + "_ex_lbl").css("display", "block");
        $(field_id + "_ex_lbl").text(ErrorMsg);
    }
    return ErrorFlg;
}