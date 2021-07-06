/*
    Created by Ivribin
*/
function StartPage() {
    doAjaxGetUserCache();
    doAjaxGetUserInfo();
}

//-----------Ajax Functions------------
//Ajax сведений пользователя
function doAjaxGetUserInfo() {
    SpinnerOn("doAjaxGetUserInfo");
    try {
        $.ajax({
            url: 'GetUserInfo',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                //
            }),
            success: function (data) {
                console.log(data.text);
                SetUserForm(data.text);
                SpinnerOff("doAjaxGetUserInfo");
            }
        });
    }catch (e) {
        SpinnerOff("doAjaxGetUserInfo");
        console.log("Error doAjaxGetUserInfo: " + data.text);
    }
};

//--------Функции заполнения------------
function SetUserForm(JSONString) {
    try {
        var obj = jQuery.parseJSON(JSONString);
        $('#lst_name_field').val(obj.lastName);
        $('#fst_name_field').val(obj.firstName);
        $('#mdl_name_field').val(obj.middleName);
        $('#phone_field').val(obj.phone);
        $('#pay_status_field').val(obj.access_status);
        $('#pay_end_date_field').val(obj.access_dt);
    }catch (e) {
        console.log("Error SetUserForm: " + data.text);
    }
};