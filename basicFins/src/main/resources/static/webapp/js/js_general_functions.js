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
                //var strLiClass = 'left-menu-item finsproject_list_row_li';
                var strLiClass = '';
                var strLiContext = "";
                $.each(obj, function (index, value) {
                    if (value["id"].toString() == ActiveProjectId && PageName != "RoProject") {
                        strLiClass = 'nav-link finsproject_list_row_li left-menu-selected-link';
                    } else {
                        strLiClass = 'nav-link finsproject_list_row_li';
                    }

                    if (PageName == "RoProject") {
                        strLiContext = '<div class="finsproject_list_link_row">' + value["name"] + '</div>';
                    } else {
                        strLiContext = '<li class="'+strLiClass+'"><a href="/' + PageName + '?ProjectId=' + value["id"].toString() + '" projnum="' + value["id"].toString() + '"><i class="bx bx-cube-alt icon"></i><span class="text nav-text">' + value["name"] + '</span></a>';

                    }
                    /*
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

                     */
                    strProjectListContext = strProjectListContext + strLiContext;
                });

                /*
                strProjectListContext = strProjectListContext
                    + '<li class="left-menu-item finsproject_list_row_li finsproject_list_row finsproject_list_row_addproject_btn">'
                    + '<a class="finsproject_list_link_row finsproject_list_link_row_addproject_btn" href="/Projects">Все проекты</a>'
                    + '</li>';
                */
                strProjectListContext =
                    + '<li class="list-divider"></li><li class="nav-small-cap"><span class="menu-list-title">Ваши проекты</span></li>'
                    + strProjectListContext;

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
    var ErrorFlgLocal = 'N';
    var regex = /g/;
    switch(field_key) {
        case 'STRING_36': //Строка 36 символов
            if(field_value.length > 36){
                ErrorMsg = 'Данные превышают лимит';
                ErrorFlgLocal = 'Y';
            }
        break;
        case 'STRING_255': //Строка 255
            if(field_value.length > 255){
                ErrorMsg = 'Данные превышают лимит';
                ErrorFlgLocal = 'Y';
            }
        break;
        case 'NUMBER_16': //16 цифр
            regex = /^[\d]{16}$/;
            if(field_value.match(regex) == null){
                ErrorMsg = 'Нужно указать 16 цифр';
                ErrorFlgLocal = 'Y';
            }
        break;
        case 'NUMBER_9': //9 цифр
            regex = /^[\d]{9}$/;
            if(field_value.match(regex) == null){
                ErrorMsg = 'Нужно указать 9 цифр';
                ErrorFlgLocal = 'Y';
            }
        break;
        case 'NUMBER_10_12': //10 или 12 цифр
            regex = /^[\d]{10}$|^[\d]{12}$/;
            if(field_value.match(regex) == null){
                ErrorMsg = 'Нужно указать 10 или 12 цифр';
                ErrorFlgLocal = 'Y';
            }
        break;
        case 'NUMBER_20': //20 цифр
            regex = /^[\d]{20}$/;
            if(field_value.match(regex) == null){
                ErrorMsg = 'Нужно указать 20 цифр';
                ErrorFlgLocal = 'Y';
            }
        break;
        case 'NUMBER_6': //10 или 12 цифр
            regex = /^[\d]{6}$/;
            if(field_value.match(regex) == null){
                ErrorMsg = 'Нужно указать 6 цифр';
                ErrorFlgLocal = 'Y';
            }
        break;
        case 'EMAIL': //EMAIL
            regex = /^[A-Z0-9._%+-]+@[A-Z0-9-]+.+.[A-Z]{2,4}$/i;
            if(field_value.match(regex) == null){
                ErrorMsg = 'Некорректный email';
                ErrorFlgLocal = 'Y';
            }
        break;
        case 'PHONE': //PHONE
            regex = /^\+?(\d{1,3})?[- .]?\(?(?:\d{2,3})\)?[- .]?\d\d\d[- .]?\d\d\d\d$/;
            if(field_value.match(regex) == null){
                ErrorMsg = 'Некорректный телефон';
                ErrorFlgLocal = 'Y';
            }
        break;
        case 'NOT_NULL': //CONTR_TYPE
            if(field_value == ''){
                ErrorMsg = 'Обязательно для заполнения';
                ErrorFlgLocal = 'Y';
            }
        break;

    }

    if(ErrorFlgLocal == 'Y'){
        $(field_id + "_ex_lbl").css("display", "block");
        $(field_id + "_ex_lbl").text(ErrorMsg);
        $(field_id).addClass("error-valid-field");
    }else{
        $(field_id + "_ex_lbl").css("display", "none");
        $(field_id).removeClass("error-valid-field");
    }

    if(ErrorFlgLocal == 'Y' || ErrorFlg == 'Y'){
        ErrorFlg = 'Y';
    }

    return ErrorFlg;
}