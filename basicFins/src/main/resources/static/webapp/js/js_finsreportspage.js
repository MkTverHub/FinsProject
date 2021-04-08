/**
 * Created by Admin on 28.07.2020.
 */
//Функция при загрузки страницы
function StartPage() {
    doAjaxGetUserCache("Reports");
    doAjaxGetProjectProfit();
    doAjaxGetYearProfitList();
    //Report4_BuildChart(["2016", "2017", "2018", "2019"],["10", "25", "55", "120"],"Продажи товаров за период");
};

//-------------Функции событий-----------------

//-----------Ajax Functions------------

//Ajax получения отчета
function doAjaxGetYearProfitList() {
    //SpinnerOn("doAjaxGetYearProfitList");
    $.ajax({
        url : 'GetReport',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            ReportName: 'GetYearProfitList'
        }),
        success: function (data) {
            console.log(data.text);
            var arr_lable = [];
            var arr_value = [];
            var obj = jQuery.parseJSON(data.text);
            if(data.text != null) {
                $.each(obj, function (index, value) {
                    arr_lable.push(value["rep_lable"]);
                    arr_value.push(value["rep_value"]);
                });
                Report4_BuildChart(arr_lable,arr_value,"Продажи товаров за период")
            }
            //SpinnerOff("doAjaxGetYearProfitList");
        }
    });
};


//Ajax получения отчета
function doAjaxGetProjectProfit() {
    SpinnerOn("doAjaxGetProjectProfit");
    try {
        $.ajax({
            url: 'GetReport',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: ({
                ReportName: 'GetProjectProfit'
            }),
            success: function (data) {
                var obj = jQuery.parseJSON(data.text);
                $('#projectprofitid').html(obj.ProjectProfit);
                $('#projectprofitid_sub').html(obj.ProjectIncome);
                $('#projectexpenseid_sub').html(obj.ProjectExpense);
                $('#projectsaldoid_sub').html(obj.ProjectProfit);
                SpinnerOff("doAjaxGetProjectProfit");
            }
        });
    }catch (e) {
        console.log("Error doAjaxGetProjectProfit: " + e);
        SpinnerOff("doAjaxGetProjectProfit");
        return "";
    }
};




//-----------Конструкторы графиков-----------
//4й график (Сколько было продаж за год)
function Report4_BuildChart(labels, values, chartTitle) {
    var ctx = document.getElementById("myChart4").getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels, // Наши метки
            datasets: [{
                label: chartTitle, // Название рядов
                data: values, // Значения
                backgroundColor: [ // Задаем произвольные цвета
                    'rgba(97, 116, 213, 1)',
                    'rgba(95, 118, 232, 1)',
                    'rgba(118, 139, 244, 1)',
                    'rgba(115, 133, 223, 1)',
                    'rgba(177, 189, 250, 1)',
                    'rgba(199, 208, 255, 1)'
                ],
                borderColor: [ // Добавляем произвольный цвет обводки
                    'rgba(97, 116, 213, 1)',
                    'rgba(97, 116, 213, 1)',
                    'rgba(97, 116, 213, 1)',
                    'rgba(97, 116, 213, 1)',
                    'rgba(97, 116, 213, 1)',
                    'rgba(97, 116, 213, 1)'
                ],
                borderWidth: 1 // Задаем ширину обводки секций
            }]
        }
    });
    return myChart;
}
