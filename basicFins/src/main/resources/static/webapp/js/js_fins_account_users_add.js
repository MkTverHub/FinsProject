/*
    Created by Ivribin
*/

//Функция при загрузки страницы
function StartPage() {
    doAjaxGetUserCache("RoProject");
    doAjaxGetSubUserList();

};


//Ajax получение списка дочерних пользаков + заполнение таблицы + заполнение пиклиста
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
            var strSubUserPickListContext = '<option value="0">Выберете значение</option>';
            $("#sub_user_table_body").html(JsonToTableBody("app_user","id",["id","email","lastName","firstName","middleName","phone","position"],data.text));
            var obj = jQuery.parseJSON(data.text);
            $.each(obj, function (index, value) {
                strSubUserPickListContext = strSubUserPickListContext + '<option value = "' + value['email'] + '">' + value["lastName"] + ' ' + value["firstName"]  + '</option>';
            });
            $("#sub_users_picklist").html(strSubUserPickListContext);
            SpinnerOff("doAjaxGetSubUserList");
        }
    });
};

//Ajax формы операции DB SubUser (Создать/Обновить/Удалить)
function doAjaxSubUserDBOperation() {
    SpinnerOn("doAjaxSubUserDBOperation");
    var strDBOperation = $('#subuser_db_action').attr('value');//update/insert/delete
    var strSubUserId = "";
    if($('#subuser_id').attr('value') == null){strSubUserId = "";}else{strSubUserId=$('#subuser_id').attr('value')};
    var strSubUserFstName = $('#subuser_fst_name').val();
    var strSubUserLstName= $('#subuser_lst_name').val();
    var strSubUserMdlName = $('#subuser_mdl_name').val();
    var strSubUserPhone = $('#subuser_phone').val();
    var strSubUserPosition = $('#subuser_position_list').attr('select_value');
    var strSubUserEmail = $('#subuser_email').val();
    var strSubUserPassword = $('#subuser_password').val();

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
    ClearSubUserForm()
    SetROSubUserForm();
};
//Событие нажатия на кнопку "Удалить" контрагента
function DeleteSubUser(){
    $('#subuser_db_action').attr('value','delete');//update/insert/delete
    doAjaxSubUserDBOperation();
    ClearSubUserForm()
    SetROSubUserForm();
};
//Событие нажатия на кнопку "Создать" контрагента
function InsertSubUser(){
    UnSetROSubUsertForm();
    $('#subuser_db_action').attr('value','insert');//update/insert/delete
};
//Событие нажатия на кнопку "Отменить" контрагента
function ResetSubUser(){
    ClearSubUserForm()
    SetROSubUserForm();
};

//Событие выбора значения выпадающего "Тип"
$(function(){
    $("#subuser_position_list").change( function(){
        $('#subuser_position_list').attr('select_value',$('#subuser_position_list').val());
    });
});

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
        strSubUserMdlName = $(this).find('.middleName_t_cell_class').attr('value');
        strSubUserPhone = $(this).find('.phone_t_cell_class').attr('value');
        strSubUserPosition = $(this).find('.position_t_cell_class').attr('value');
        strSubUserEmail = $(this).find('.email_t_cell_class').attr('value');
        strSubUserPassword = "****";

        $('#subuser_db_action').attr('value',strDBOperation);//update/insert/delete
        $('#subuser_id').attr('value',strSubUserId);
        $('#subuser_fst_name').attr('value',strSubUserFstName);
        $('#subuser_lst_name').attr('value',strSubUserLstName);
        $('#subuser_mdl_name').attr('value',strSubUserMdlName);
        $('#subuser_phone').attr('value',strSubUserPhone);
        $('#subuser_email').attr('value',strSubUserEmail);
        $('#subuser_password').attr('value',strSubUserPassword);


        $('#subuser_fst_name').val(strSubUserFstName);
        $('#subuser_lst_name').val(strSubUserLstName);
        $('#subuser_mdl_name').val(strSubUserMdlName);
        $('#subuser_phone').val(strSubUserPhone);
        SetActiveSelect('#subuser_position_list',strSubUserPosition)
        $('#subuser_email').val(strSubUserEmail);
        $('#subuser_password').val(strSubUserPassword);

    });
});

//Событие выбора значения выпадающего SubUserPickList
$(function(){
    $("#sub_users_picklist").change( function(){
        var strSelectValue = $('#sub_users_picklist').val();
        $('#sub_users_picklist').attr('select_value',strSelectValue);
    });
});

//-------Доп Функции----------------
//Сделать форму SubUser RO
function SetROSubUserForm(){
    $('#subuser_fst_name').attr('readonly', true);
    $('#subuser_lst_name').attr('readonly', true);
    $('#subuser_mdl_name').attr('readonly', true);
    $('#subuser_phone').attr('readonly', true);
    $('#subuser_position_list').attr('disabled', true);
    $('#subuser_email').attr('readonly', true);
    $('#subuser_password').attr('readonly', true);
}

//Сделать форму SubUser not RO
function UnSetROSubUsertForm(){
    $('#subuser_fst_name').attr('readonly', false);
    $('#subuser_lst_name').attr('readonly', false);
    $('#subuser_mdl_name').attr('readonly', false);
    $('#subuser_phone').attr('readonly', false);
    $('#subuser_position_list').attr('disabled', false);
    $('#subuser_email').attr('readonly', false);
    $('#subuser_password').attr('readonly', false);
}

//Чистка формы SubUser
function ClearSubUserForm(){
    $('#subuser_db_action').attr('value','');//update/insert/delete
    $('#subuser_id').attr('value','');
    $('#subuser_fst_name').val('');
    $('#subuser_lst_name').val('');
    $('#subuser_mdl_name').val('');
    $('#subuser_phone').val('');
    $('#subuser_position_list').val('');
    $('#subuser_email').val('');
    $('#subuser_password').val('');
};

//Функция установки выбранного значения
function SetActiveSelect(ListSelector,SelectedVal){
    $(ListSelector).find('[selected]').prop('selected', false);//Сбросить текущее активное значение
    $(ListSelector).find('[value = "' + SelectedVal + '"]').prop('selected', true);
    $(ListSelector).attr('select_value',SelectedVal);
}
//Функция сброса списка
function ResetPickList(ListSelector){
    $(ListSelector).find('[selected]').prop('selected', false);//Сбросить текущее активное значение
    $(ListSelector).find('[selected]').attr('select_value', null);

}