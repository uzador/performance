$(document).ready(function(){
    const months = ["ЯНВАРЯ", "ФЕВРАЛЯ", "МАРТА","АПРЕЛЯ", "МАЯ", "ИЮНЯ", "ИЮЛЯ", "АВГУСТА", "СЕНТЯБРЯ", "ОКТЯБРЯ", "НОЯБРЬ", "ДЕКАБРЯ"];

    const id = 1
    const weekCount = 2
    const url = 'http://typicon.online/Schedule/get/' + id + '/' + weekCount

    $.ajax({
        url: url,
       	type: 'GET',
    }).done(function(jsonResp) {
        const schedule = JSON.stringify(jsonResp, null, '  ')

        jsonResp.schedule.forEach(function(value) {
            console.log(value.Value.Name.Text);

            value.Value.Days.forEach(function(day) {
                const wDate = new Date(day.Date)
                const wDateFormatted = wDate.getDate() + "-" + months[wDate.getMonth()] + "-" + wDate.getFullYear()

                console.log("  " + wDateFormatted);
                console.log("  " + day.Name.Text);
                console.log("  " + "Знак службы: " + day.SignName.Text);

                day.Worships.forEach(function(worship) {
                    console.log("    " + worship.Time + " " + worship.Name.Text.Text);
                });
            })
        });

	    $("#schedule").css({"white-space": "pre-wrap"}).text(schedule);
    }).fail(function(jsonResp) {
        $("#schedule").text("Ошибка при загрузки расписания: " + JSON.stringify(jsonResp, null, '  '));
    });
}); 