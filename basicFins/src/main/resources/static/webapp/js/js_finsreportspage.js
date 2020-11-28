/**
 * Created by Admin on 28.07.2020.
 */
//Функция при загрузки страницы
function StartPage() {
    //alert('StartPage');
    doAjaxGetProjectList();//Получение списка проектов в левой панели
    doAjaxGetProjectList();//Получение списка проектов в левой панели
};

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
                    + '_rowid" class="left-menu-item finsproject_list_row"><input type="button" class="left-menu-link finsproject_list_row" projnum="'
                    + value["id"].toString() + '" value="' + value["name"] + '"/></li>';
            });
            $("#projectlistpanel").html(strProjectListContext);
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
