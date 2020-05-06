/**
 * Created by Admin on 15.03.2020.
 */
//Ajax
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
            //Получаем из контрола контекст таблицы
            $("#finsproject_table_body").html(data.text);
            //$("#contr_agent_requisits_list").html(data.text);
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