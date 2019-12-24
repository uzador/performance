$(document).ready(function(){

    const MONTHS = ['ЯНВАРЯ', 'ФЕВРАЛЯ', 'МАРТА', 'АПРЕЛЯ', 'МАЯ', 'ИЮНЯ',
     'ИЮЛЯ', 'АВГУСТА', 'СЕНТЯБРЯ', 'ОКТЯБРЯ', 'НОЯБРЯ', 'ДЕКАБРЯ',]
    const weekCount = 2

    const url = 'http://typicon.online/Schedule/json?id=berluki&weekscount=' + weekCount

    $.ajax({
        url: url,
       	type: 'GET',
    }).done(function(jsonResp) {
        $("#schedule").text("Расписание").wrapInner("<h1></h1>")

        jsonResp.schedule.forEach(function(value) {
            $("#schedule").append("<div><h2>" + value.Name.Text + "</h2></div>")

            value.Days.forEach(function(day) {
                const wDate = new Date(day.Date)
                const wDateFormatted = wDate.getDate() + "-" + MONTHS[wDate.getMonth()] + "-" + wDate.getFullYear()

                $("#schedule").append("<div><h3>" + wDateFormatted + "</h3></div>")
                $("#schedule").append("<div>" + day.Name.Text + "</div>")
                $("#schedule").append("<div>Знак службы: " + day.SignName.Text + "</div>")

                day.Worships.forEach(function(worship) {
                    $("#schedule").append("<div>" + worship.Time + " " + worship.Name.Text.Text + "</div>")
                });
            })
        });

//	    $("#schedule").css({"white-space": "pre-wrap"}).text(schedule);
    }).fail(function(jsonResp) {
        console.log("Ошибка: " + JSON.stringify(jsonResp, null, '  '))
    });
}); 