/**
 * Created by Admin on 15.03.2020.
 */


//-----------Ajax Functions------------

//Ajax Создать/Оюновить/Удалить
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

    //Чистим форму
    ClearFinsForm();
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

    //Чистим форму
    ClearFinsForm();
    $('#fins_project_operation_type').val('');
};


//Клик по записи
$(function(){
    $('#finsproject_table_body').on('click', '.finsprojectrowlink', function(){
        //alert('puk');
        strFinsProjectId = $(this).find('.fieldid').html();
        strFinsProjectName = $(this).find('.fieldname').html();
        strFinsProjectDescription = $(this).find('.fielddescription').html();
        $('#fins_project_operation_type').val('edit');
        $('#fins_project_record_id').val(strFinsProjectId);
        $('#fins_project_name').val(strFinsProjectName);
        $('#fins_project_desqription').val(strFinsProjectDescription);

    });
});

//Отчистка формы
function ClearFinsForm (){
    //$('#fins_project_operation_type').val('');
    $('#fins_project_record_id').val('');
    $('#fins_project_name').val('');
    $('#fins_project_desqription').val('');
};

//Кнопка "Удалить"
function deleteFinsProject() {
    $('#fins_project_operation_type').val('delete');
    doAjaxDellProject();
};

//Кнопка "Создать"
function createFinsProject() {
    $('#fins_project_operation_type').val('new');
    ClearFinsForm();
};

//Кнопка "Сохранить"
function saveFinsProject() {
    doAjaxDellProject();
};