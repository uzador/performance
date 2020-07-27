const result1 = [
  "Литургия (с выниманием частиц) - <em class='text-warning'>10 руб./имя</em>",
  "Молебен святителю Николаю (по четвергам) - <em class='text-warning'>10 руб./имя</em>",
  "Молебен перед иконой Божией Матери \"Неупиваемая чаша\" (по пятницам) - <em class='text-warning'>10 руб./имя</em>",
  "Молебен перед иконой Божией Матери \"Всецарица\" (в первое воскресенье месяца) - <em class='text-warning'>10 руб./имя</em>",
  "Молебен перед иконой \"Лобзание Иисуса Христа Иудою\" (по субботам) - <em class='text-warning'>10 руб./имя</em>"
]

const result2 = [
  "Литургия (с выниманием частиц) - <em class='text-warning'>400 руб./имя</em>",
  "Литургия (без вынимания частиц) - <em class='text-warning'>100 руб./имя</em>"
]

const result4 = [
  "Литургия (с выниманием частиц) - <em class='text-warning'>10 руб./имя</em>",
  "Панихида - <em class='text-warning'>10 руб./имя</em>"
]

const result5 = [
  "Литургия (без вынимания частиц) - <em class='text-warning'>250 руб./имя</em>"
]

const result6 = [
  "Литургия (без вынимания частиц) - <em class='text-warning'>250 руб./имя</em>",
  "Молебен святителю Николаю (по четвергам) - <em class='text-warning'>150 руб./имя</em>",
  "Молебен перед иконой Божией Матери \"Неупиваемая чаша\" (по пятницам) - <em class='text-warning'>150 руб./имя</em>",
  "Молебен перед иконой Божией Матери \"Всецарица\" (в первое воскресенье месяца) - <em class='text-warning'>150 руб./имя</em>",
  "Молебен перед иконой \"Лобзание Иисуса Христа Иудою\" (по субботам) - <em class='text-warning'>150 руб./имя</em>"
]

const result7 = [
  "Литургия (без вынимания частиц) - <em class='text-warning'>500 руб./имя</em>",
  "Молебен святителю Николаю (по четвергам) - <em class='text-warning'>300 руб./имя</em>",
  "Молебен перед иконой Божией Матери \"Неупиваемая чаша\" (по пятницам) - <em class='text-warning'>300 руб./имя</em>",
  "Молебен перед иконой Божией Матери \"Всецарица\" (в первое воскресенье месяца) - <em class='text-warning'>300 руб./имя</em>",
  "Молебен перед иконой \"Лобзание Иисуса Христа Иудою\" (по субботам) - <em class='text-warning'>300 руб./имя</em>"
]

const result8 = [
  "Литургия (без вынимания частиц) - <em class='text-warning'>500 руб./имя</em>"
]

const items = [
  [result1, result2, result6, result7],
  [result4, result2, result5, result8]
]

$(window).on('pageshow', function(){
    if (window.performance && window.performance.navigation.type == window.performance.navigation.TYPE_BACK_FORWARD) {
        location.reload();
    }
});

function isEmpty(str) {
    return (!str.trim() || 0 === str.length);
}

function trimText(str) {
    if (isEmpty(str)) {
        return str
    } else {
        return str.trim().replace(/\s\s+/g, ' ')
    }
}

buildResult = function() {
    var names = $('textarea[name=names]').val()

    if (!isEmpty(names)) {
        var firstListChoiceText = $('input:radio[name=first_list]:checked').closest('label').text()
        var secondListChoiceText = $('input:radio[name=second_list]:checked').closest('label').text()
        var thirdListChoiceText = $('input:radio[name=third_list]:checked').closest('label').text()
        var resultText = trimText(firstListChoiceText) + " " +
                         trimText(secondListChoiceText) + " " +
                         trimText(thirdListChoiceText) + " " +
                         names

        var result = $('input:text[name=orderid]')
        result.val(resultText)

        console.log(resultText)
    }
 }

 buildThirdList = function() {
    var firstListVal = $('input:radio[name=first_list]:checked').val()
    var secondListVal = $('input:radio[name=second_list]:checked').val()
    var contentForGeneratedDynamically = items[firstListVal][secondListVal]
    var generatedDynamically = $('#generatedDynamically')
    generatedDynamically.empty()
    var genDynamicGroup = $('<div class="form-group">').appendTo(generatedDynamically);
    var genDynamicList = $('<div class="btn-group-vertical btn-group-toggle" role="group" data-toggle="buttons">').appendTo(genDynamicGroup)

    $.each(contentForGeneratedDynamically, function(index, listItem) {
        if (index == 0) {
            genDynamicList.append($('<label class="btn btn-outline-secondary active"><input type="radio" name="third_list" value="'
                + index + '" autocomplete="off" checked>' + listItem + '</label>'))
        } else {
            genDynamicList.append($('<label class="btn btn-outline-secondary"><input type="radio" name="third_list" value="'
                + index + ' autocomplete="off"">' + listItem + '</label>'))
        }
    });

    $('input:radio[name=third_list]').change(buildResult)
}

$(document).ready(function(){
    $("input:text[name=orderid]").hide();
    $("input:text[name=pstype]").hide();

    $('input:radio(name=first_list name=second_list)').change(buildThirdList)

    $("form").validationEngine('attach', {showOneMessage: true, showArrowOnRadioAndCheckbox: true, scroll: true})

    $("form").submit(buildResult)

    buildThirdList()
})



