/**
 * Created by IVRibin on 20.03.2020.
 */
//Submit Form
/*
function submitFinsDataForm() {
    $("#id_financedataform").submit();
}
*/

//Выбор строки в таблице контрагентов и заполнение формы
$(function(){
    $('#tb_contragentlist').on('click', '.contragentlist_row', function(){
        //alert('puk');
        $('#field_selected_contragent_num').attr('value',$(this).attr('row_num'));
        $('#selected_cntragnt_id').attr('value',$(this).find('.cntr_id').attr('value'));
        $('#selected_cntragnt_name').attr('value',$(this).find('.cntr_name').attr('value'));
        $('#selected_cntragnt_desqription').attr('value',$(this).find('.cntr_description').attr('value'));
        $('#selected_cntragnt_phone').attr('value',$(this).find('.cntr_phone_num').attr('value'));
        $('#selected_cntragnt_mail').attr('value',$(this).find('.cntr_email_addr').attr('value'));

        $('#cntragnt_action').attr('value','update');
        $('#cntragnt_id').attr('value',$(this).find('.cntr_id').attr('value'));
        $('#cntragnt_name').attr('value',$(this).find('.cntr_name').attr('value'));
        $('#cntragnt_desqription').attr('value',$(this).find('.cntr_description').attr('value'));
        $('#cntragnt_phone').attr('value',$(this).find('.cntr_phone_num').attr('value'));
        $('#cntragnt_mail').attr('value',$(this).find('.cntr_email_addr').attr('value'));

        $("#id_selected_contragent_form").submit();//Сабмит формы, для пересчета дочерних таблиц

    });
});

//Нажатие кнопки "Создать" новый Контрагент
$("#id_CreateContragent").on('click', function(){
    ClearContragentForm();
    $('#cntragnt_action').attr('value','new');
});

//Нажатие кнопки "Удалить" Контрагент
$("#id_DellContragent").on('click', function(){
    $('#cntragnt_action').attr('value','delete');
    $('#cntragnt_action').attr('value','delete');
    $("#id_contragentform").submit();
});


function ClearContragentForm (){
    $('#cntragnt_id').attr('value','');
    $('#cntragnt_name').attr('value','');
    $('#cntragnt_desqription').attr('value','');
    $('#cntragnt_phone').attr('value','');
    $('#cntragnt_mail').attr('value','');

    $('#cntragnt_id').val('');
    $('#cntragnt_name').val('');
    $('#cntragnt_desqription').val('');
    $('#cntragnt_phone').val('');
    $('#cntragnt_mail').val('');
};

//Выбор строки в таблице реквизитов и заполнение формы
$(function(){
    $('#tb_cntragnt_requisits').on('click', '.requisitslist_row', function(){
        //$('#field_selected_contragent_num').attr('value',$(this).attr('row_num'));

        $('#requisit_action').attr('value','update');
        $('#requisit_id').attr('value',$(this).find('.cntr_req_id').attr('value'));
        $('#requisit_name').attr('value',$(this).find('.cntr_req_name').attr('value'));
        $('#requisit_ReqDescription').attr('value',$(this).find('.cntr_req_description').attr('value'));
        $('#requisit_ReqINN').attr('value',$(this).find('.cntr_req_inn').attr('value'));
        $('#requisit_ReqKPP').attr('value',$(this).find('.cntr_req_kpp').attr('value'));
        $('#requisit_ReqFinsAcc').attr('value',$(this).find('.cntr_req_acc').attr('value'));
        $('#requisit_ReqBIK').attr('value',$(this).find('.cntr_req_bik').attr('value'));
        $('#requisit_ReqBankName').attr('value',$(this).find('.cntr_req_bank_name').attr('value'));
        $('#requisit_ReqCrspAcc').attr('value',$(this).find('.cntr_req_crsp_acc').attr('value'));
        $('#requisit_ReqAddrIndex').attr('value',$(this).find('.cntr_req_addr_index').attr('value'));
        $('#requisit_ReqAddrCity').attr('value',$(this).find('.cntr_req_addr_city').attr('value'));
        $('#requisit_ReqAddrString').attr('value',$(this).find('.cntr_req_addr_string').attr('value'));
        $('#requisit_ReqPhoneNum').attr('value',$(this).find('.cntr_req_phone_num').attr('value'));
        $('#requisit_ReqEmail').attr('value',$(this).find('.cntr_req_email_addr').attr('value'));
        //$('#requisit_ReqWebSite').attr('value',$(this).find('.').attr('value'));
    });
});

//Нажатие кнопки "Создать" новый Реквизит
$("#id_CreateRequisit").on('click', function(){
    ClearRequisitForm();
    $('#requisit_action').attr('value','new');
});

//Нажатие кнопки "Удалить" Реквизит
$("#id_DellRequisit").on('click', function(){
    $('#requisit_action').attr('value','delete');
    $("#id_requisits_form").submit();
});

function ClearRequisitForm (){
    $('#requisit_id').val('');
    $('#requisit_name').val('');
    $('#requisit_ReqDescription').val('');
    $('#requisit_ReqINN').val('');
    $('#requisit_ReqKPP').val('');
    $('#requisit_ReqFinsAcc').val('');
    $('#requisit_ReqBIK').val('');
    $('#requisit_ReqBankName').val('');
    $('#requisit_ReqCrspAcc').val('');
    $('#requisit_ReqAddrIndex').val('');
    $('#requisit_ReqAddrCity').val('');
    $('#requisit_ReqAddrString').val('');
    $('#requisit_ReqPhoneNum').val('');
    $('#requisit_ReqEmail').val('');

    $('#requisit_id').attr('value','');
    $('#requisit_name').attr('value','');
    $('#requisit_ReqDescription').attr('value','');
    $('#requisit_ReqINN').attr('value','');
    $('#requisit_ReqKPP').attr('value','');
    $('#requisit_ReqFinsAcc').attr('value','');
    $('#requisit_ReqBIK').attr('value','');
    $('#requisit_ReqBankName').attr('value','');
    $('#requisit_ReqCrspAcc').attr('value','');
    $('#requisit_ReqAddrIndex').attr('value','');
    $('#requisit_ReqAddrCity').attr('value','');
    $('#requisit_ReqAddrString').attr('value','');
    $('#requisit_ReqPhoneNum').attr('value','');
    $('#requisit_ReqEmail').attr('value','');
    //$('#requisit_ReqWebSite').attr('value',$(this).find('.').attr('value'));
};