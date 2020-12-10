/**
 * Created by Admin on 28.07.2020.
 */
//Функция при загрузки страницы
function StartPage() {
    //alert('StartPage');
    doAjaxGetProjectList();//Получение списка проектов в левой панели
    BuildChart(["2016", "2017", "2018", "2019"],["10", "25", "55", "120"],"Продажи товаров за период");
    doAjaxGetYearProfitList('22');
};

//-----------Ajax Functions------------
//Ajax получение списка проектов в левой панели
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
            var strProjectListContext = "";
            var obj = jQuery.parseJSON(data.text);
            $.each(obj, function (index, value) {
                strProjectListContext = strProjectListContext
                    + '<li id="' + value["id"].toString()
                    + '_rowid" class="left-menu-item finsproject_list_row"><input type="button" class="left-menu-link finsproject_list_row" projnum="'
                    + value["id"].toString() + '" value="' + value["name"] + '"/></li>';
            });
            $("#projectlistpanel").html(strProjectListContext);
        }
    });
};
//Ajax получения отчета
function doAjaxGetYearProfitList(ProjectId) {
    $.ajax({
        url : 'GetReport',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            ReportName: 'GetYearProfitList',
            ProjectId: ProjectId
        }),
        success: function (data) {
            console.log(data.text);
        }
    });
};


//-----------Конструкторы графиков-----------
//4й график
function BuildChart(labels, values, chartTitle) {
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
