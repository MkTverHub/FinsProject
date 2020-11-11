/*
    Created by Ivribin
*/

//Функция при загрузки страницы
function StartPage() {
    doAjaxGetLovList();
};


//-----------Ajax Functions------------
//Ajax получение списка Компаний
function doAjaxGetLovList() {
    $.ajax({
        url : 'GetLovList',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            //
        }),
        success: function (data) {
            JSONStringToLovTable(data.text);
        }
    });
};

//Ajax формы операции DB Контакта (Создать/Обновить/Удалить)
function doAjaxLovDBOperation() {
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
            //alert('Ajax: OperationCompany');
            doAjaxGetLovList();
        }
    });
};

//--------Функции заполнения----------------
//Парсинг JSON списка компаний в таблицу
function JSONStringToLovTable(JSONString) {
    var strLovTableContext = '';
    var strLovId = '';
    var strLovVal = '';
    var strLovDescription = '';
    var strLovOptions = '';
    var strLovType = '';
    var obj = jQuery.parseJSON(JSONString);
    $.each(obj, function (index, value) {
        strLovTableContext = strLovTableContext + '<tr class="fincrowlink lovlist_row">';
        if(value['id'] == null){strLovId = 'null';} else {strLovId = value['id'].toString();}
        if(value['text_val'] == null){strLovVal = 'null';} else {strLovVal = value['text_val'].toString();}
        if(value['description'] == null){strLovDescription = 'null';} else {strLovDescription = value['description'].toString();}
        if(value['options'] == null){strLovOptions = 'null';} else {strLovOptions = value['options'].toString();}
        if(value['type'] == null){strLovType = 'null';} else {strLovType = value['type'].toString();}
        strLovTableContext = strLovTableContext + '<th class="lov_id_row" value="' + strLovId + '">' + strLovId + '</th>' + '<th class="lov_value_row" value="' + strLovVal + '">' + strLovVal + '</th>' + '<th class="lov_description_row" value="' + strLovDescription + '">' + strLovDescription + '</th>' + '<th class="lov_options_row" value="' + strLovOptions + '">' + strLovOptions + '</th>' + '<th class="lov_type_row" value="' + strLovType + '">' + strLovType + '</th>' + '</tr>';
    });
    $("#finslov_table_body").html(strLovTableContext);
};

//Чистка формы Lov
function CleanLovForm() {
    $('#lov_db_action').attr('value','');
    $('#lov_record_id').attr('value','');
    $('#lov_value').val('');
    $('#lov_description').val('');
    ResetPickList('#lov_options_list');
    ResetPickList('#lov_type_list');
};


//-------------Функции событий-----------------
//Событие нажатия на кнопку "Создать" компанию
function InsertLOV(){
    $('#lov_db_action').attr('value','insert');//update/insert/delete
};
//Событие нажатия на кнопку "Удалить" компанию
function DeleteLOV(){
    $('#lov_db_action').attr('value','delete');
    doAjaxLovDBOperation();
    CleanLovForm();
};
//Событие нажатия на кнопку "Сохранить" компанию
function SaveLOV(){
    doAjaxLovDBOperation();
    CleanLovForm();
};

//Событие нажатия на строку компании
$(function(){
    $("#finslov_table_body").on("click", ".lovlist_row", function () {
        $('#lov_db_action').attr('value',"update");
        $('#lov_record_id').attr('value',$(this).find('.lov_id_row').attr('value'));
        $('#lov_value').val($(this).find('.lov_value_row').attr('value'));
        $('#lov_description').val($(this).find('.lov_description_row').attr('value'));
        SetActiveSelect('#lov_options_list',$(this).find('.lov_options_row').attr('value'));
        SetActiveSelect('#lov_type_list',$(this).find('.lov_type_row').attr('value'));
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
}
//Функция сброса списка
function ResetPickList(ListSelector){
    $(ListSelector).find('[selected]').prop('selected', false);//Сбросить текущее активное значение
    $(ListSelector).find('[selected]').attr('select_value', null);
}