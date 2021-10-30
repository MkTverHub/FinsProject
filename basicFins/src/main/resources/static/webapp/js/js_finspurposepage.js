/*
    Created by Ivribin
*/

//Функция при загрузки страницы
function StartPage() {
    doAjaxGetUserCache("FinsLOVEditor");//Получение списка проектов в левой панели
    doAjaxGetPurposeList();
    //SetROForm();
};


//-----------Ajax Functions------------
//Ajax получение списка Lov + заполнение таблицы
function doAjaxGetPurposeList() {
    SpinnerOn("GetPurposeList");
    try {
        $.ajax({
            url : 'GetPurposeList',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data : ({
                //
            }),
            success: function (data) {
                console.log("doAjaxGetPurposeList: " + data.text);
                //JSONStringToLovTable(data.text);
                //$("#finslov_table_body").html(JsonToTableBody("lov","id",["id","text_val","description","options","type"],data.text));
                SpinnerOff("GetPurposeList");
            }
        });
    }catch (e) {
        SpinnerOff("GetPurposeList");
        console.log("Error doAjaxGetPurposeList: " + e);
    }
};

//Ajax формы операции DB lov (Создать/Обновить/Удалить)
function doAjaxLovDBOperation() {
    SpinnerOn("doAjaxLovDBOperation");
    var strDBOperation = $('#lov_db_action').attr('value');//update/insert/delete
    var strLovId = '';
    if($('#lov_record_id').attr('value') == null){strLovId = '';}else{strLovId = $('#lov_record_id').attr('value');}
    var strLovVal = $('#lov_value').val();
    var strLovDescription = $('#lov_description').val();
    var strLovOptions = $('#lov_options_list').attr('select_value');
    var strLovType = $('#lov_type_list').attr('select_value');

    $.ajax({
        url : 'OperationLov',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            DBOperation: strDBOperation,
            LovId: strLovId,
            LovValue: strLovVal,
            LovDescription: strLovDescription,
            LovOptions: strLovOptions,
            LovType: strLovType
        }),
        success: function (data) {
            doAjaxGetLovList();
            SpinnerOff("doAjaxLovDBOperation");
        }
    });
};

//Чистка формы Lov
function CleanLovForm() {
    $('#lov_db_action').attr('value','');
    $('#lov_record_id').attr('value','');
    $('#lov_value').val('');
    $('#lov_description').val('');
    ResetPickList('#lov_options_list');
    ResetPickList('#lov_type_list');
    $('#lov_options_list option:contains("Выберете значение")').prop('selected', true);
    $('#lov_type_list option:contains("Выберете значение")').prop('selected', true);
};


//-------------Функции событий-----------------
//Событие нажатия на кнопку "Создать" lov
function InsertLOV(){
    UnSetROForm();
    CleanLovForm();
    $('#lov_db_action').attr('value','insert');//update/insert/delete
};
//Событие нажатия на кнопку "Удалить" lov
function DeleteLOV(){
    $('#lov_db_action').attr('value','delete');
    doAjaxLovDBOperation();
    CleanLovForm();
    SetROForm();
};
//Событие нажатия на кнопку "Сохранить" lov
function SaveLOV(){
    doAjaxLovDBOperation();
    CleanLovForm();
    SetROForm();
};
//Событие нажатия на кнопку "Отменить" lov
function ResetLOV(){
    CleanLovForm();
    SetROForm();
};

//Событие нажатия на строку lov
$(function(){
    $("#finslov_table_body").on("click", ".lov_t_row_class", function () {
        console.log("Click");
        UnSetROForm();
        $('#lov_db_action').attr('value',"update");
        $('#lov_record_id').attr('value',$(this).find('.id_t_cell_class').attr('value'));
        $('#lov_value').val($(this).find('.text_val_t_cell_class').attr('value'));
        $('#lov_description').val($(this).find('.description_t_cell_class').attr('value'));
        SetActiveSelect('#lov_options_list',$(this).find('.options_t_cell_class').attr('value'));
        SetActiveSelect('#lov_type_list',$(this).find('.type_t_cell_class').attr('value'));

    });
});

//Событие выбора значения выпадающего "Характеристики"
$(function(){
    $("#lov_options_list").change( function(){
        var strSelectValue = $('#lov_options_list').val();
        $('#lov_options_list').attr('select_value',strSelectValue);
    });
});

//Событие выбора значения выпадающего "Тип"
$(function(){
    $("#lov_type_list").change( function(){
        var strSelectValue = $('#lov_type_list').val();
        $('#lov_type_list').attr('select_value',strSelectValue);
    });
});

//-------------Функции обработки-----------------
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

//Сделать форму RO
function SetROForm(){
    $('#lov_value').attr('readonly', true);
    $('#lov_description').attr('readonly', true);
    $('#lov_options_list').attr('disabled', true);
    $('#lov_type_list').attr('disabled', true);
}
//Сделать форму not RO
function UnSetROForm(){
    $('#lov_value').attr('readonly', false);
    $('#lov_description').attr('readonly', false);
    $('#lov_options_list').attr('disabled', false);
    $('#lov_type_list').attr('disabled', false);
}