document.addEventListener("DOMContentLoaded", function(event){

    const MONTHS = ['ЯНВАРЯ', 'ФЕВРАЛЯ', 'МАРТА', 'АПРЕЛЯ', 'МАЯ', 'ИЮНЯ', 'ИЮЛЯ', 'АВГУСТА', 'СЕНТЯБРЯ', 'ОКТЯБРЯ',
     'НОЯБРЯ', 'ДЕКАБРЯ',]

    const id = 'berluki'
    const weekCount = 2

    const url = 'http://typicon.online/Schedule/json?id=' + id + '&weekscount=' + weekCount

    var xhr = new XMLHttpRequest()

    xhr.onreadystatechange = function() {
        if(xhr.readyState < 4) {
            return;
        }

        if(xhr.status !== 200) {
            alert("Ошибка: " + xhr.status + ' => ' + xhr.statusText)

            return;
        }

        if(xhr.readyState === 4) {
            success(xhr.responseText);
        }
    };

    success = function(response) {
        const scheduleDiv = document.getElementById("schedule");

        const jsonResp = JSON.parse(response);

        jsonResp.schedule.forEach(function(value) {
            const nameDiv = scheduleDiv.appendChild(document.createElement('div'))
            nameDiv.setAttribute('id', 'sched_sedmica_name')
            const h2nameDiv = nameDiv.appendChild(document.createElement('h4'))
            h2nameDiv.innerHTML = value.Name.Text

            value.Days.forEach(function(day) {
                const wDate = new Date(day.Date)
                const wDateFormatted = wDate.getDate() + "-" + MONTHS[wDate.getMonth()] + "-" + wDate.getFullYear()

                const dayDateDiv = scheduleDiv.appendChild(document.createElement('div'))
                dayDateDiv.setAttribute('id', 'sched_date')
                dayDateDiv.innerHTML = wDateFormatted

                const dayNameDiv = scheduleDiv.appendChild(document.createElement('div'))
                dayNameDiv.setAttribute('id', 'sched_day_name')
                dayNameDiv.innerHTML = day.Name.Text

                const daySignDiv = scheduleDiv.appendChild(document.createElement('div'))
                daySignDiv.setAttribute('id', 'sched_day_sign')
                daySignDiv.innerHTML = 'Знак службы: ' + day.SignName.Text

                day.Worships.forEach(function(worship) {
                    const worshipDiv = scheduleDiv.appendChild(document.createElement('div'))
                    worshipDiv.setAttribute('id', 'sched_day_worship')
                    worshipDiv.innerHTML = worship.Time + " " + worship.Name.Text.Text
                });
            })
        });
    }

    xhr.open('GET', url, true)
    xhr.send()
});