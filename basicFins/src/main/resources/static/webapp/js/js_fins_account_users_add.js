/*
    Created by Ivribin
*/

//Функция при загрузки страницы
function StartPage() {
    doAjaxGetUserCache("RoProject");
    doAjaxGetSubUserList();

};


//Ajax получение списка дочерних пользаков + заполнение таблицы
function doAjaxGetSubUserList() {
    SpinnerOn("doAjaxGetSubUserList");
    $.ajax({
        url : 'GetSubUserList',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({

        }),
        success: function (data) {
            $("#sub_user_table_body").html(JsonToTableBody("app_user","id",["id","email","last_name","first_name"],data.text));
            SpinnerOff("doAjaxGetSubUserList");
        }
    });
};