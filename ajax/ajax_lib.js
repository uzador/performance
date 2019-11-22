$(document).ready(function(){
    const months = ["ЯНВАРЯ", "ФЕВРАЛЯ", "МАРТА","АПРЕЛЯ", "МАЯ", "ИЮНЯ", "ИЮЛЯ", "АВГУСТА", "СЕНТЯБРЯ", "ОКТЯБРЯ", "НОЯБРЬ", "ДЕКАБРЯ"];

    const id = 1
    const weekCount = 2
    const url = 'http://typicon.online/Schedule/get/' + id + '/' + weekCount

    $.ajax({
        url: url,
       	type: 'GET',
       	async: false
    }).done(function(jsonResp) {
//        const schedule = JSON.stringify(jsonResp, null, '  ')>

        $("#schedule").text("Расписание").wrapInner("<h1></h1>");

        jsonResp.schedule.forEach(function(value) {
            $("#schedule").append("<div><h2>" + value.Value.Name.Text + "</h2></div>");
//            console.log(value.Value.Name.Text);

            value.Value.Days.forEach(function(day) {
                const wDate = new Date(day.Date)
                const wDateFormatted = wDate.getDate() + "-" + months[wDate.getMonth()] + "-" + wDate.getFullYear()

                $("#schedule").append("<div><h3>" + wDateFormatted + "</h3></div>");
                $("#schedule").append("<div>" + day.Name.Text + "</div>");
                $("#schedule").append("<div>Знак службы: " + day.SignName.Text + "</div>");
//                console.log("  " + wDateFormatted);
//                console.log("  " + day.Name.Text);
//                console.log("  " + "Знак службы: " + day.SignName.Text);

                day.Worships.forEach(function(worship) {
                    $("#schedule").append("<div>" + worship.Time + " " + worship.Name.Text.Text + "</div>");
//                    console.log("    " + worship.Time + " " + worship.Name.Text.Text);
                });
            })
        });

//	    $("#schedule").css({"white-space": "pre-wrap"}).text(schedule);
    }).fail(function(jsonResp) {
        console.log("Ошибка: " + JSON.stringify(jsonResp, null, '  '));
    });
}); 