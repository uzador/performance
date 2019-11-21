$(document).ready(function(){
    const months = ["ЯНВАРЯ", "ФЕВРАЛЯ", "МАРТА","АПРЕЛЯ", "МАЯ", "ИЮНЯ", "ИЮЛЯ", "АВГУСТА", "СЕНТЯБРЯ", "ОКТЯБРЯ", "НОЯБРЬ", "ДЕКАБРЯ"];

    var id = 1
    var weekCount = 2
    var url = 'http://typicon.online/Schedule/get/' + id + '/' + weekCount

    $.ajax({
        url: url,
       	type: 'GET',
    }).done(function(jsonResp) {
//        var schedule = JSON.stringify(data, null, '  ')
        var schedule = JSON.stringify(jsonResp)

        jsonResp.schedule.forEach(function(value) {
            console.log(value.Value.Name.Text);

            value.Value.Days.forEach(function(day) {
                var wDate = new Date(day.Date)
                var wDateFormatted = wDate.getDate() + "-" + months[wDate.getMonth()] + "-" + wDate.getFullYear()

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
        console.log("NO! " + JSON.stringify(jsonResp, null, '  '));
    });
}); 