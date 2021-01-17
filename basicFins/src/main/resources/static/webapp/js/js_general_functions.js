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
    //console.log('doAjaxGetProjectListLeft: ' + ActiveProjectId)
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
            $.each(obj, function (index, value) {
                if( value["id"].toString() == ActiveProjectId){
                    strLiClass = 'left-menu-item left-menu-selected-link finsproject_list_row';
                }else{
                    strLiClass = 'left-menu-item finsproject_list_row_li finsproject_list_row';
                }
                strProjectListContext = strProjectListContext
                    + '<li id="' + value["id"].toString()
                    + '_rowid" class="' + strLiClass + '" projnum="' + value["id"].toString() + '">' +
                    //'<input type="button" class="left-menu-link finsproject_list_row" projnum="' + value["id"].toString() + '" value="' + value["name"] + '"/>' +
                    //'<a href="/ProjectsEditor?ProjectId=' + value["id"].toString() + '">' + value["name"] + '</a>' +
                    '<a class="finsproject_list_link_row" href="/' + PageName + '?ProjectId=' + value["id"].toString() + '" projnum="' + value["id"].toString() + '">' + value["name"] + '</a>' +
                    '</li>';
            });
            $("#projectlistpanel").html(strProjectListContext);
        }
    });

};
//========================================================
//Ajax получение UserCache
function doAjaxGetUserCache(PageName) {
    //document.body.HashData = {ActiveProjectId:''};
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