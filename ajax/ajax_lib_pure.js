document.addEventListener("DOMContentLoaded", function(event){

    const MONTHS = ['ЯНВАРЯ', 'ФЕВРАЛЯ', 'МАРТА', 'АПРЕЛЯ', 'МАЯ', 'ИЮНЯ', 'ИЮЛЯ', 'АВГУСТА', 'СЕНТЯБРЯ', 'ОКТЯБРЯ',
     'НОЯБРЯ', 'ДЕКАБРЯ']
     const SCHED_ERROR = "Элемент <div id=schedule></div> должен существовать в теле документа, чтобы загрузить расписание"

    args = getScriptArgs()

    const id = args['id']
    const weeks = args['weeks']

    const url = 'http://typicon.online/Schedule/json?id=' + id + '&weekscount=' + weeks

    const xhr = new XMLHttpRequest()

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
        let scheduleDiv = document.getElementById("schedule");

        if (scheduleDiv == null) {
            const script = document.scripts[document.scripts.length - 1]
            if (script.parentElement.localName == 'head') {
                console.error(SCHED_ERROR)
                alert(SCHED_ERROR)

                return
            }

            scheduleDiv = document.createElement('schedule')
            script.parentElement.insertBefore(scheduleDiv, script)
        }

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
                })
            })
        })
    }

    xhr.open('GET', url, true)
    xhr.send()

    function getScriptArgs() {
        const script_id = document.getElementById('schedule_script_id')
        const query = script_id.src.replace(/^[^\?]+\??/,'')
        const vars = query.split("&")
        const args = {}
            for (var i=0; i < vars.length; i++) {
                var pair = vars[i].split("=")
                args[pair[0]] = decodeURI(pair[1]).replace(/\+/g, ' ')
             }

        return args
    }
})