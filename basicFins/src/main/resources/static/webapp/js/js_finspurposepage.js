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
                $("#plate_body").replaceWith(JsonToPlateList(data.text));
                SpinnerOff("GetPurposeList");
            }
        });
    }catch (e) {
        SpinnerOff("GetPurposeList");
        console.log("Error doAjaxGetPurposeList: " + e);
    }
};

//Ajax формы операции DB lov (Создать/Обновить/Удалить)
function doAjaxPurposeDBOperation() {
    SpinnerOn("doAjaxPurposeDBOperation");
    try {
        var strDBOperation = $('#purpose_db_action').attr('value');//update/insert/delete
        var strPurposeId = '';
        if($('#purpose_id').attr('value') == null){strPurposeId = '';}else{strPurposeId = $('#purpose_id').attr('value');}
        var strPurposeName = $('#purpose_name').val();
        var strPurposeDescription = $('#purpose_description').val();
        var strPurposeProfit = $('#purpose_profit').val();
        var strPurposeExpense = $('#purpose_expense').val();

        $.ajax({
            url : 'OperationPurpose',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data : ({
                DBOperation: strDBOperation,
                PurposeParRowId: '',
                PurposeId: strPurposeId,
                PurposeName: strPurposeName,
                PurposeDescription: strPurposeDescription,
                PurposeExpense: strPurposeExpense,
                PurposeProfit: strPurposeProfit
            }),
            success: function (data) {
                //doAjaxGetLovList();
                SpinnerOff("doAjaxPurposeDBOperation");
            }
        });

    }catch (e) {
        SpinnerOff("doAjaxPurposeDBOperation");
        console.log("Error doAjaxPurposeDBOperation: " + e);
    }
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
//Выделить плитку
function SelectPlatePurpose(varId) {
    console.log(varId);
}

//Событие нажатия на кнопку "Создать" lov
function InsertPurpose(){
    UnSetROForm();
    //CleanLovForm();
    $('#purpose_db_action').attr('value','insert');//update/insert/delete
};
//Событие нажатия на кнопку "Удалить" lov
function DeleteLOV(){
    //$('#lov_db_action').attr('value','delete');
    //doAjaxLovDBOperation();
    //CleanLovForm();
    //SetROForm();
};
//Событие нажатия на кнопку "Сохранить" lov
function SavePurpose(){
    doAjaxPurposeDBOperation();
    //CleanLovForm();
    //SetROForm();
};
//Событие нажатия на кнопку "Отменить" lov
function ResetLOV(){
    //CleanLovForm();
    //SetROForm();
};

//Событие нажатия на плитку события
$(function(){

    $("#main_palate_container").on("click", ".card-body", function () {
        UnSetROForm()
        $('#purpose_db_action').attr('value',"update");
        $('#purpose_name').val($(this).find('.plate_name_field_class').attr('value'));
        $('#purpose_description').val($(this).find('.plate_description_field_class').attr('value'));
        $('#purpose_profit').val($(this).find('.plate_profit_field_class').attr('value'));
        $('#purpose_expense').val($(this).find('.plate_expense_field_class').attr('value'));
    });

});

//Событие выбора значения выпадающего "Характеристики"
$(function(){
    /*
    $("#lov_options_list").change( function(){
        var strSelectValue = $('#lov_options_list').val();
        $('#lov_options_list').attr('select_value',strSelectValue);
    });

     */
});

//Событие выбора значения выпадающего "Тип"
$(function(){
    /*
    $("#lov_type_list").change( function(){
        var strSelectValue = $('#lov_type_list').val();
        $('#lov_type_list').attr('select_value',strSelectValue);
    });

     */
});

//-------------Функции обработки-----------------
//Функция установки выбранного значения
function SetActiveSelect(ListSelector,SelectedVal){
    /*
    $(ListSelector).find('[selected]').prop('selected', false);//Сбросить текущее активное значение
    $(ListSelector).find('[value = "' + SelectedVal + '"]').prop('selected', true);
    $(ListSelector).attr('select_value',SelectedVal);
     */
}
//Функция сброса списка
function ResetPickList(ListSelector){
    /*
    $(ListSelector).find('[selected]').prop('selected', false);//Сбросить текущее активное значение
    $(ListSelector).find('[selected]').attr('select_value', null);
     */
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
    $('#purpose_name').attr('readonly', false);
    $('#purpose_description').attr('readonly', false);
    $('#purpose_profit').attr('readonly', false);
    $('#purpose_expense').attr('readonly', false);
    $('#purpose_name').attr('disabled', false);
    $('#purpose_description').attr('disabled', false);
    $('#purpose_profit').attr('disabled', false);
    $('#purpose_expense').attr('disabled', false);
}



//Сформировать плитки целей
function JsonToPlateList(strJsonContext){
    try {
        var strBody = "";
        var strPlate = "";
        var obj = $.parseJSON(strJsonContext);
        $.each(obj, function (index, value) {
            strPlate = strPlate +"<div class=\"col-md-4\"><div class=\"card border py-4\"><div class=\"card-body\"><div class=\"d-flex d-lg-flex d-md-block align-items-center mb-1\"><div><div class=\"mb-3\">"+"<h4 class=\"text-muted font-weight-normal mb-2 w-100 plate_id_field_class\" id=\"plate_id_id_"+ value["id"]+ "\" value=\"" + value["id"] +"\">"+"<h4 class=\"text-muted font-weight-normal mb-2 w-100 plate_name_field_class\" id=\"plate_name_id_"+ value["id"]+ "\" value=\"" + value["name"] +"\">";
            strPlate = strPlate + value["name"];
            strPlate = strPlate + "</h4><h6 class=\"text-muted font-weight-normal mb-2 w-100 plate_description_field_class\" id=\"plate_description_id_"+ value["description"]+ "\" value=\"" + value["name"] +"\">";
            strPlate = strPlate + value["description"];
            strPlate = strPlate + "</h6><div class=\"dropdown-divider\"/></div><div class=\"mb-1\"><h5 class=\"text-muted font-weight-normal mb-1 w-100 text-truncate\">Возможный приход</h5><div class=\"d-inline-flex align-items-center\"><h2 class=\"mb-1 font-weight-medium sum-color-green plate_profit_field_class\" id=\"plate_profit_id_"+ value["id"]+ "\" value=\"" + value["profit"] +"\">";
            strPlate = strPlate + value["profit"];
            strPlate = strPlate + "</h2></div></div><div class=\"mb-2\"><h5 class=\"text-muted font-weight-normal mb-1 w-100 text-truncate\">Возможный расход</h5><div class=\"d-inline-flex align-items-center\"><h2 class=\"mb-1 font-weight-medium sum-color-red plate_expense_field_class\" id=\"plate_expense_id_"+ value["id"]+ "\" value=\"" + value["expense"] +"\">";
            strPlate = strPlate + value["expense"];
            strPlate = strPlate + "</h2></div></div></div><div class=\"ml-auto mt-md-3 mt-lg-0\"><span class=\"opacity-7 text-muted\"><svg xmlns=\"http://www.w3.org/2000/svg\"width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-user-plus\"><path d=\"M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2\"/><circle cx=\"8.5\" cy=\"7\" r=\"4\"/><line x1=\"20\" y1=\"8\" x2=\"20\" y2=\"14\"/><line x1=\"23\" y1=\"11\" x2=\"17\" y2=\"11\"/></svg></span></div></div><div class=\"d-flex d-lg-flex d-md-block align-items-center\">" +
                "<button onclick=\"SelectPlatePurpose('" + value["id"] + "')\" class=\"btn waves-effect waves-light btn-light align-self-center\">Изменить</button></div></div></div></div>";

            strBody = strPlate;
        });
        return strBody;
    }catch (e) {
        console.log("Error JsonToPlateList: " + e);
        return "";
    }
}