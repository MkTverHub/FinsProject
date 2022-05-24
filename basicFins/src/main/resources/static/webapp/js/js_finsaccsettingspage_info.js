/*
    Created by Ivribin
*/
function StartPage() {
    doAjaxGetUserCache("Fins_Account_Settings_Info");
    doAjaxGetUserInfo();
}

//-----------Ajax Functions------------
//Ajax сведений пользователя
function doAjaxGetUserInfo() {
    SpinnerOn("doAjaxGetUserInfo");
    try {
        $.ajax({
            url: 'OperationUser',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                DBOperation: "GetUser",
                UserId: "0",
                UserParentId: "0",
                UserFstName: "",
                UserLstName: "",
                UserMdlName: "",
                UserPhone: "",
                UserPosition: "",
                UserEmail: "",
                UserPassword: "",
                UserLocked: "",
                UserAccessDt: "",
                UserAccessStatus: ""
            }),
            success: function (data) {
                SetUserForm(data.text);
                SpinnerOff("doAjaxGetUserInfo");
            }
        });
    }catch (e) {
        SpinnerOff("doAjaxGetUserInfo");
        console.log("Error doAjaxGetUserInfo: " + data.text);
    }
};

//Ajax сохранения пользака
function doAjaxSaveUserInfo() {
    SpinnerOn("doAjaxSaveUserInfo");
    try {
        var strUserFstName =  $('#fst_name_field').val();
        var strUserLstName = $('#lst_name_field').val();
        var strUserMdlName = $('#mdl_name_field').val();
        var strUserPhone =  $('#phone_field').val();
        $.ajax({
            url: 'OperationUser',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                DBOperation: "update",
                UserId: "0",
                UserParentId: "0",
                UserFstName: strUserFstName,
                UserLstName: strUserLstName,
                UserMdlName: strUserMdlName,
                UserPhone: strUserPhone,
                UserPosition: "",
                UserEmail: "",
                UserPassword: "",
                UserLocked: "",
                UserAccessDt: "",
                UserAccessStatus: ""
            }),
            success: function (data) {
                SetUserForm(data.text);
                SpinnerOff("doAjaxSaveUserInfo");
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

//Сделать форму RO
function SetROUserForm(){
    $('#lst_name_field').attr('readonly', true);
    $('#fst_name_field').attr('readonly', true);
    $('#mdl_name_field').attr('readonly', true);
    $('#phone_field').attr('readonly', true);
}

//Сделать форму NO RO
function UnSetROUserForm(){
    $('#lst_name_field').attr('readonly', false);
    $('#fst_name_field').attr('readonly', false);
    $('#mdl_name_field').attr('readonly', false);
    $('#phone_field').attr('readonly', false);
}

//-------Функции событий---------------
//--Кнопка: Изменить----------
function EditUser() {
    UnSetROUserForm();
}

//--Кнопка: Сохранить----------
function SaveUser() {
    doAjaxSaveUserInfo();
    SetROUserForm();
}
