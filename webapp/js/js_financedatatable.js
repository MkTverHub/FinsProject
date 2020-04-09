
//Submit Form
function submitFinsDataForm() {
    $("#id_financedataform").submit();
}

$(function(){
    $('#financetable').on('click', '.fincrowlink', function(){
        var strFinsRecordId = "";
        var strFinsBlockFlg = "";
        var strFinsOpertype = "";//Тип транзакции
        var strOperDt = "";
        var strFinsAmount = "";
        var strFinsDetail = "";
        var strPaymentAccIn = "";
        var strPaymentAccOut = "";
        var strFinsArticle = "";
        var strProjectId = "";
        var strFinsContrAgent = "";
        var strFinsRequisites = "";

        strFinsRecordId = $(this).find('.fieldid').html();
        strFinsBlockFlg = $(this).find('.fieldlockflg').html();
        strFinsOpertype = $(this).find('.fieldfinsopertype').html();
        strOperDt = $(this).find('.fieldoperdate').html();
        strFinsAmount = $(this).find('.fieldamount').html();
        strFinsDetail = $(this).find('.fielddetail').html();

        strPaymentAccIn = $(this).find('.fieldpayaccin').html();
        strPaymentAccOut = $(this).find('.fieldpayaccout').html();
        strFinsArticle = $(this).find('.fieldfinsarticle').html();
        strProjectId = $(this).find('.fieldprojectid').html();
        strFinsContrAgent = $(this).find('.fieldfinscontragent').html();
        strFinsRequisites = $(this).find('.fieldrequisites').html();

        $('#finsedittypeid').attr('value','1');//1-Изменить, 2-создать, 3-удалить
        $('#recordid').attr('value',strFinsRecordId);
        $('#fieldlockflgid').attr('value',strFinsBlockFlg);
        $('#finsopertypeid').attr('value',strFinsOpertype);
        $('#fieldoperdateid').attr('value',strOperDt);
        $('#fieldamountid').attr('value',strFinsAmount);
        $('#fielddetailid').attr('value',strFinsDetail);
        $('#paymentaccinid').attr('value',strPaymentAccIn);
        $('#paymentaccoutid').attr('value',strPaymentAccOut);
        $('#finsarticleid').attr('value',strFinsArticle);
        $('#projectidid').attr('value',strProjectId);
        $('#finscontragentid').attr('value',strFinsContrAgent);
        $('#requisitesid').attr('value',strFinsRequisites);

        $('#finsedittypeid').val('1');//1-Изменить, 2-создать, 3-удалить
        $('#recordid').val(strFinsRecordId);
        $('#fieldlockflgid').val(strFinsBlockFlg);
        $('#finsopertypeid').val(strFinsOpertype);
        $('#fieldoperdateid').val(strOperDt);
        $('#fieldamountid').val(strFinsAmount);
        $('#fielddetailid').val(strFinsDetail);
        $('#paymentaccinid').val(strPaymentAccIn);
        $('#paymentaccoutid').val(strPaymentAccOut);
        $('#finsarticleid').val(strFinsArticle);
        $('#projectidid').val(strProjectId);
        $('#finscontragentid').val(strFinsContrAgent);
        $('#requisitesid').val(strFinsRequisites);
    });
});

/*
 //Заполнение поля при выборе выпадающего списка
 $("#selfinsedittype").bind('change focus', function(){
 var variableX = $(this).val();//1-Изменить, 2-создать, 3-удалить
 $('#finsedittypeid').val(variableX);
 //Если выбрано "Создать", чистим форму
 if(variableX == '2'){
 ClearFinsForm();
 }
 });
 */

//Нажатие на "Приход"
$("#arrivalbt").on('click', function(){
    ClearFinsForm();
    $('#finsedittypeid').attr('value','2');//1-Изменить, 2-создать, 3-удалить
    $('#finsedittypeid').val('2');//1-Изменить, 2-создать, 3-удалить
    $('#finsopertypeid').val('1');
    $('#divpaymentaccinid').removeClass('hidden-element');
    $('#divpaymentaccoutid').addClass('hidden-element');
});

//Нажатие на "Расход"
$("#expensebt").on('click', function(){
    ClearFinsForm();
    $('#finsedittypeid').attr('value','2');//1-Изменить, 2-создать, 3-удалить
    $('#finsedittypeid').val('2');//1-Изменить, 2-создать, 3-удалить
    $('#finsopertypeid').val('2');
    $('#divpaymentaccinid').addClass('hidden-element');
    $('#divpaymentaccoutid').removeClass('hidden-element');
});

//Нажатие на "Перевод"
$("#transferbt").on('click', function(){
    ClearFinsForm();
    $('#finsedittypeid').attr('value','2');//1-Изменить, 2-создать, 3-удалить
    $('#finsedittypeid').val('2');//1-Изменить, 2-создать, 3-удалить
    $('#finsopertypeid').val('3');
    $('#divpaymentaccinid').removeClass('hidden-element');
    $('#divpaymentaccoutid').removeClass('hidden-element');
});

//Нажатие на имя проекта в спискепанель слева
$(".finsproject_list_row").on('click', function(){
    $('#field_selected_fproj_num').attr('value',$(this).attr('projnum'));
    $("#id_setfinsproject").submit();

});

//Выбор значения выпадающего списка контрагента в финс форме
/*
Вынес на основную страницу к Ajax функции
$("#contr_agent_select_field").change( function(){
    $('#finscontragentid').attr('value',$('#contr_agent_select_field').val());

});
*/

function ClearFinsForm (){
    $('#recordid').attr('value','');
    $('#fieldlockflgid').attr('value','');
    $('#finsopertypeid').attr('value','');
    $('#fieldoperdateid').attr('value','');
    $('#fieldamountid').attr('value','');
    $('#fielddetailid').attr('value','');
    $('#paymentaccinid').attr('value','');
    $('#paymentaccoutid').attr('value','');
    $('#finsarticleid').attr('value','');
    $('#projectidid').attr('value','');
    $('#finscontragentid').attr('value','');
    $('#requisitesid').attr('value','');

    $('#recordid').val('');
    $('#fieldlockflgid').val('');
    $('#finsopertypeid').val('');
    $('#fieldoperdateid').val('');
    $('#fieldamountid').val('');
    $('#fielddetailid').val('');
    $('#paymentaccinid').val('');
    $('#paymentaccoutid').val('');
    $('#finsarticleid').val('');
    $('#projectidid').val('');
    $('#finscontragentid').val('');
    $('#requisitesid').val('');
};



