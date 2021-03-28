/**
 * Created by Admin on 15.03.2020.
 */
//Функция при загрузки страницы
function StartPage() {
    doAjaxGetUserCache();
    doAjaxGetProjectList();
    doAjaxGetCompanyList();
    ClearProjectForm();
    SetROForm();

};

//-----------Ajax Functions------------

//Ajax Создать/Обновить/Удалить
function doAjaxDellProject() {
    SpinnerOn("doAjaxDellProject");
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
            doAjaxGetProjectList();
            SpinnerOff("doAjaxDellProject");
        }
    });
    $('#fins_project_operation_type').val('');
};

//Ajax получение списка проектов + заполнение таблицы
function doAjaxGetProjectList() {
    SpinnerOn("doAjaxGetProjectList");
    $.ajax({
        url : 'GetFinsProjectList',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({

        }),
        success: function (data) {
            $("#finsproject_table_body").html(JsonToTableBody("project","id",["id","name","description"],data.text));
            SpinnerOff("doAjaxGetProjectList");
        }
    });
};

//Ajax получение списка Компаний
function doAjaxGetCompanyList() {
    SpinnerOn("doAjaxGetCompanyList");
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
            JSONStringToCompanyPickList(data.text);
            SpinnerOff("doAjaxGetCompanyList");
        }
    });
};


//Ajax получения отчета
function doAjaxGetProjectProfit(ProjectId) {
    SpinnerOn("doAjaxGetProjectProfit");
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
            SpinnerOff("doAjaxGetProjectProfit");
        }
    });
};

//Ajax приявязки компании
function doAjaxCompanytDBOperation(CompanyId,ProjectId) {
    SpinnerOn("doAjaxCompanytDBOperation");
    var strDBOperation = 'set_project';
    var strCompanyId = CompanyId;
    var strProjectId = ProjectId;

    $.ajax({
        url : 'OperationCompany',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            DBOperation: strDBOperation,
            CompanyId: strCompanyId,
            CompanyName: '',
            CompanyFullName: '',
            CompanyINN: '',
            CompanyKPP: '',
            CompanyFinsAcc: '',
            CompanyProjectId: strProjectId
        }),
        success: function (data) {
            SpinnerOff("doAjaxCompanytDBOperation");
        }
    });
};


//Клик по записи
$(function(){
    $('#finsproject_table_body').on('click', '.project_t_row_class', function(){
        UnSetROForm();
        strFinsProjectId = $(this).find('.id_t_cell_class').html();
        strFinsProjectName = $(this).find('.name_t_cell_class').html();
        strFinsProjectDescription = $(this).find('.description_t_cell_class').html();
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

