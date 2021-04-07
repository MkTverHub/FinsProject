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
    var strBody = "";
    var strRow = "";
    var obj = $.parseJSON(strJsonContext);
    $.each(obj, function (index, value) {
        strRow = "<tr class='"+ strTableName + "_t_row_class' " + strTableName + "_row_id='" + value[strIdName] + "'>";
        for(var i = 0; i < arrFields.length; i++){
            strRow = strRow + "<th class='" + arrFields[i] + "_t_cell_class' value='" + value[arrFields[i]]  + "'>" + value[arrFields[i]] + "</th>"
        }
        strRow = strRow + "</tr>"
        strBody = strBody + strRow;
    });
    return strBody;
}

//========================================================
//Ajax получение списка проектов в левой панели
function doAjaxGetProjectListLeft(PageName,ActiveProjectId) {
    SpinnerOn("doAjaxGetProjectListLeft");
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
            var strLiClass = 'left-menu-item finsproject_list_row_li';
            var strLiContext = "";
            $.each(obj, function (index, value) {
                if(PageName == "RoProject"){
                    strLiContext = '<div class="finsproject_list_link_row">' + value["name"] +'</div>';
                }else{
                    strLiContext = '<a class="finsproject_list_link_row" href="/' + PageName + '?ProjectId=' + value["id"].toString() + '" projnum="' + value["id"].toString() + '">' + value["name"] + '</a>';
                }
                if( value["id"].toString() == ActiveProjectId && PageName != "RoProject"){
                    strLiClass = 'left-menu-item left-menu-selected-link finsproject_list_row';
                }else{
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
            if(ActiveProjectId == '0'){
                //alert("Выбирите проект в левой панели!");
                $("#success-alert-modal").attr('style','display: block; padding-right: 17px;');
            }
            SpinnerOff("doAjaxGetProjectListLeft");
        }
    });

};
//========================================================
//Ajax получение UserCache
function doAjaxGetUserCache(PageName) {
    SpinnerOn("doAjaxGetUserCache");
    $.ajax({
        url : 'GetUserCache',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            //
        }),
        success: function (data) {
            var obj = jQuery.parseJSON(data.text);
            var strActiveProjectId = obj.active_proj;
            //document.body.HashData = {ActiveProjectId:strActiveProjectId};
            doAjaxGetProjectListLeft(PageName,strActiveProjectId);
            SpinnerOff("doAjaxGetUserCache");
        }
    });
};

//========================================================
//Ajax получение Оновления UserCache
function doAjaxUserCacheOperation(ProjectId) {
    $.ajax({
        url : 'OperationUserCache',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            ActiveProjectId: ProjectId
        }),
        success: function (data) {
            //
        }
    });
};

//=========================================================
//Spinner On
function SpinnerOn(AjaxChainName) {
    $("#spinner_ajax_chain").addClass(AjaxChainName);
    $("#spinner_main_div").removeClass('f-d-n');
};
//Spinner Off
function SpinnerOff(AjaxChainName) {
    $("#spinner_ajax_chain").removeClass(AjaxChainName);
    if($('#spinner_ajax_chain').attr('class').split(/\s+/).length == 1){
        $("#spinner_main_div").addClass('f-d-n');
    }
};