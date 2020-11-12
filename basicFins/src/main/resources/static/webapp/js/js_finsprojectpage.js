/**
 * Created by Admin on 15.03.2020.
 */
//Функция при загрузки страницы
function StartPage() {
    doAjaxGetProjectList();
    doAjaxGetCompanyList();
    ClearProjectForm();
    SetROForm();

};

//-----------Ajax Functions------------

//Ajax Создать/Обновить/Удалить
function doAjaxDellProject() {
    var strFinsProjectOperation = $("#fins_project_operation_type").val();
    var strFinsProjectId = $("#fins_project_record_id").val();
    var strFinsProjectName = $("#fins_project_name").val();
    var strFinsProjectDescription = $("#fins_project_desqription").val();
    $.ajax({
        url : 'OperationFinsProject',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            FinsProjectOperation: strFinsProjectOperation,
            FinsProjectId: strFinsProjectId,
            FinsProjectName: strFinsProjectName,
            FinsProjectDescription : strFinsProjectDescription
        }),
        success: function (data) {
            var strTableData = "";
            var obj = jQuery.parseJSON(data.text);
            $.each(obj, function (index, value) {
                strTableData = strTableData + '<tr id="'+value["id"].toString()+'_rowid" class="finsprojectrowlink"><th class="fieldid">' + value["id"].toString() + '</th><th class="fieldname">' + value["name"] + '</th><th class="fielddescription">' + value["description"] + '</th></tr>';
            });
            $("#finsproject_table_body").html(strTableData);
        }
    });

    $('#fins_project_operation_type').val('');
};

//Ajax получение списка проектов при загрузке страницы
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
            var strTableData = "";//Контекст таблицы
            var strProjectListContext = "";//Контекс левой панели
            var obj = jQuery.parseJSON(data.text);
            $.each(obj, function (index, value) {
                strTableData = strTableData
                    + '<tr id="'+value["id"].toString()
                    + '_rowid" class="finsprojectrowlink"><th class="fieldid">'
                    + value["id"].toString() + '</th><th class="fieldname">'
                    + value["name"] + '</th><th class="fielddescription">'
                    + value["description"] + '</th></tr>';
                strProjectListContext = strProjectListContext
                    + '<li id="' + value["id"].toString()
                    + '_rowid" class="left-menu-item finsproject_list_row"><input type="button" class="left-menu-link finsproject_list_row" projnum="'
                    + value["id"].toString() + '" value="' + value["name"] + '"/></li>';
            });
            $("#finsproject_table_body").html(strTableData);
            $("#projectlistpanel").html(strProjectListContext);
        }
    });

    $('#fins_project_operation_type').val('');
};

//Ajax получение списка Компаний
function doAjaxGetCompanyList() {
    $.ajax({
        url : 'GetCompanyList',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            //
        }),
        success: function (data) {
            JSONStringToCompanyPickList(data.text)
        }
    });
};


//Ajax получения отчета
function doAjaxGetProjectProfit(ProjectId) {
    $.ajax({
        url : 'GetReport',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            ReportName: 'GetProjectProfit',
            ProjectId: ProjectId
        }),
        success: function (data) {
            var obj = jQuery.parseJSON(data.text);
            console.log(data.text);
            $('#project_income_id').val(obj.ProjectIncome);
            $('#project_expense_id').val(obj.ProjectExpense);
        }
    });
};


//Клик по записи
$(function(){
    $('#finsproject_table_body').on('click', '.finsprojectrowlink', function(){
        UnSetROForm();
        strFinsProjectId = $(this).find('.fieldid').html();
        strFinsProjectName = $(this).find('.fieldname').html();
        strFinsProjectDescription = $(this).find('.fielddescription').html();
        $('#fins_project_operation_type').val('edit');
        $('#fins_project_record_id').val(strFinsProjectId);
        $('#fins_project_name').val(strFinsProjectName);
        $('#fins_project_desqription').val(strFinsProjectDescription);
        doAjaxGetProjectProfit(strFinsProjectId);
    });
});

//Отчистка формы
function ClearProjectForm (){
    $('#fins_project_record_id').val('');
    $('#fins_project_name').val('');
    $('#fins_project_desqription').val('');
};

//Кнопка "Удалить"
function deleteFinsProject() {
    $('#fins_project_operation_type').val('delete');
    doAjaxDellProject();
    ClearProjectForm();
    SetROForm();
};

//Кнопка "Создать"
function createFinsProject() {
    UnSetROForm();
    ClearProjectForm();
    $('#fins_project_operation_type').val('new');
};

//Кнопка "Сохранить"
function saveFinsProject() {
    doAjaxDellProject();
    ClearProjectForm();
    UnSetROForm();
};

//Кнопка "Отменить"
function resetFinsProject() {
    ClearProjectForm();
    SetROForm();
};


//Кнопка "Добавить", привязать компанию к проекту
function LinkCompanyToProject() {
    doAjaxCompanytDBOperation($('#company_picklist_id').val(),$("#fins_project_record_id").val());
};

//Событие выбора значения выпадающего компаний
/*
$(function(){
    $("#company_picklist_id").change( function(){
        var strCompanyId = $('#company_picklist_id').val();
        var strProjectId = $("#fins_project_record_id").val();
        $("#fins_project_commany_id").val(strProjectId);
    });
});*/

//Парсинг JSON списка Компаний в выпадающий список
function JSONStringToCompanyPickList(JSONString) {
    var strCompanyId = '';
    var strCompanyName = '';
    var strCompanyPickListContext = '<option value="0">Выберете значение</option>';

    var obj = jQuery.parseJSON(JSONString);
    $.each(obj, function (index, value) {
        if(value['id'] == null){strCompanyId = 'null';} else {strCompanyId = value['id'].toString();}
        if(value['name'] == null){strCompanyName = 'null';} else {strCompanyName = value['name'].toString();}
        strCompanyPickListContext = strCompanyPickListContext + '<option value = "' + strCompanyId + '">' + strCompanyName + '</option>';
    });
    $("#company_picklist_id").html(strCompanyPickListContext);
}

//Сделать форму RO
function SetROForm(){
    $('#fins_project_name').attr('readonly', true);
    $('#fins_project_desqription').attr('readonly', true);
}

//Сделать форму not RO
function UnSetROForm(){
    $('#fins_project_name').attr('readonly', false);
    $('#fins_project_desqription').attr('readonly', false);
}