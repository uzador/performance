$(document).ready(function(){
    var id = 1
    var weekCount = 1
    var url = 'http://typicon.online/Schedule/get/' + id + '/' + weekCount;

    $.ajax({
        url: url,
       	type: 'GET',
    }).done(function(data) {
        var schedule = JSON.stringify(data, null, '  ')
	    console.log("OK! " + schedule);
	    $("#schedule").css({"white-space": "pre-wrap"}).text(schedule);
    }).fail(function(data) {
        console.log("NO! " + JSON.stringify(data, null, '  '));
    });
}); 