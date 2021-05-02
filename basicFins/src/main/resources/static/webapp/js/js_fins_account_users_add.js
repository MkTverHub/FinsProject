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
            console.log(data.text);
            $("#sub_user_table_body").html(JsonToTableBody("app_user","id",["id","email","lastName","firstName"],data.text));
            SpinnerOff("doAjaxGetSubUserList");
        }
    });
};

//Ajax формы операции DB SubUser (Создать/Обновить/Удалить)
function doAjaxSubUserDBOperation() {
    SpinnerOn("doAjaxSubUserDBOperation");
    var strDBOperation = $('#subuser_db_action').attr('value');//update/insert/delete
    var strSubUserId = $('#subuser_id').attr('value');
    var strSubUserFstName = $('#subuser_fst_name').attr('value');
    var strSubUserLstName= $('#subuser_lst_name').attr('value');
    var strSubUserMdlName = $('#subuser_mdl_name').attr('value');
    var strSubUserPhone = $('#subuser_phone').attr('value');
    var strSubUserPosition = $('#subuser_position').attr('select_value');
    var strSubUserEmail = $('#subuser_email').attr('value');
    var strSubUserPassword = $('#subuser_password').attr('value');

    $.ajax({
        url : 'OperationSubUser',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            DBOperation: strDBOperation,
            SubUserId: strSubUserId,
            SubUserFstName: strSubUserFstName,
            SubUserLstName: strSubUserLstName,
            SubUserMdlName: strSubUserMdlName,
            SubUserPhone: strSubUserPhone,
            SubUserPosition:strSubUserPosition,
            SubUserEmail:strSubUserEmail,
            SubUserPassword:strSubUserPassword
        }),
        success: function (data) {
            doAjaxGetSubUserList();//Обновить таблицу SubUser
            SpinnerOff("doAjaxSubUserDBOperation");
        }
    });
};


//-------------Функции событий-----------------
//Событие нажатия на кнопку "Сохранить" контрагента
function SaveSubUser(){
    doAjaxSubUserDBOperation();
    SetROSubUserForm();
};
//Событие нажатия на кнопку "Удалить" контрагента
function DeleteSubUser(){
    $('#cntragnt_db_action').attr('value','delete');//update/insert/delete
    doAjaxSubUserDBOperation();
    SetROSubUserForm();
};
//Событие нажатия на кнопку "Создать" контрагента
function InsertSubUser(){
    UnSetROSubUsertForm();
    $('#cntragnt_db_action').attr('value','insert');//update/insert/delete
};
//Событие нажатия на кнопку "Отменить" контрагента
function ResetSubUser(){
    SetROSubUserForm();
};


//Событие нажатия на строку SubUser
$(function(){
    var strDBOperation = 'update';//update/insert/delete
    var strSubUserId = '';
    var strSubUserFstName = '';
    var strSubUserLstName= '';
    var strSubUserMdlName = '';
    var strSubUserPhone = '';
    var strSubUserPosition = '';
    var strSubUserEmail = '';
    var strSubUserPassword = '';



    $("#sub_user_table_body").on("click", ".app_user_t_row_class", function () {
        UnSetROSubUsertForm();
        strSubUserId = $(this).find('.id_t_cell_class').attr('value');
        strSubUserFstName = $(this).find('.firstName_t_cell_class').attr('value');
        strSubUserLstName = $(this).find('.lastName_t_cell_class').attr('value');
        strSubUserMdlName = "";
        strSubUserPhone = "";
        strSubUserPosition = "";
        strSubUserEmail = $(this).find('.email_t_cell_class').attr('value');
        strSubUserPassword = "****";

        $('#subuser_db_action').attr('value',strDBOperation);//update/insert/delete
        $('#subuser_id').attr('value',strSubUserId);
        $('#subuser_fst_name').attr('value',strSubUserFstName);
        $('#subuser_lst_name').attr('value',strSubUserLstName);
        $('#subuser_mdl_name').attr('value',strSubUserMdlName);
        $('#subuser_phone').attr('value',strSubUserPhone);
        $('#subuser_position').attr('value',strSubUserPosition);
        $('#subuser_email').attr('value',strSubUserEmail);
        $('#subuser_password').attr('value',strSubUserPassword);


        $('#subuser_fst_name').val(strSubUserFstName);
        $('#subuser_lst_name').val(strSubUserLstName);
        $('#subuser_mdl_name').val(strSubUserMdlName);
        $('#subuser_phone').val(strSubUserPhone);
        $('#subuser_position').val(strSubUserPosition);
        $('#subuser_email').val(strSubUserEmail);
        $('#subuser_password').val(strSubUserPassword);

    });
});

//-------Доп Функции----------------
//Сделать форму SubUser RO
function SetROSubUserForm(){
    $('#subuser_fst_name').attr('readonly', true);
    $('#subuser_lst_name').attr('readonly', true);
    $('#subuser_mdl_name').attr('readonly', true);
    $('#subuser_phone').attr('readonly', true);
    $('#subuser_position').attr('readonly', true);
    $('#subuser_email').attr('readonly', true);
    $('#subuser_password').attr('readonly', true);
}

//Сделать форму SubUser not RO
function UnSetROSubUsertForm(){
    $('#subuser_fst_name').attr('readonly', false);
    $('#subuser_lst_name').attr('readonly', false);
    $('#subuser_mdl_name').attr('readonly', false);
    $('#subuser_phone').attr('readonly', false);
    $('#subuser_position').attr('readonly', false);
    $('#subuser_email').attr('readonly', false);
    $('#subuser_password').attr('readonly', false);
}