const result1 = [
  "Литургия (с выниманием частиц)",
  "Литургия (без вынимания частиц)",
  "Молебен святителю Николаю (по четвергам)",
  "Молебен перед иконой Божией Матери \"Неупиваемая чаша\" (по пятницам)",
  "Молебен перед иконой Божией Матери \"Всецарица\" (в первое воскресенье месяца)",
  "Молебен перед иконой \"Лобзание Иисуса Христа Иудою\" (по субботам)"
]

const result2 = [
  "Литургия (с выниманием частиц)",
  "Литургия (без вынимания частиц)"
]

const result5 = [
  "Литургия (с выниманием частиц)",
  "Литургия (без вынимания частиц)",
  "Панихида"
]

const items = [
  [result1, result2, result1, result1],
  [result5, result2, result5, result5]
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
                         trimText(names)

        var result = $('input:text[name=orderid]')
        result.val(resultText)
        result.text(resultText)

        console.log(resultText)
    }
 }

 buildThirdList = function() {
    var firstListVal = $('input:radio[name=first_list]:checked').val()
    var secondListVal = $('input:radio[name=second_list]:checked').val()
    var contentForGeneratedDynamically = items[firstListVal][secondListVal]
    var generatedDynamically = $('#generatedDynamically')
    generatedDynamically.empty()

    var ul = $('<ul style="list-style: none; border: 5px solid black"></ul>');
    generatedDynamically.append($(ul))

    $.each(contentForGeneratedDynamically, function(index, listItem) {
        if (index == 0) {
            ul.append($('<li><label><input type="radio" name="third_list" data-validation-engine="validate[required]" value="'
                + index + '" checked="checked" data-prompt-position="topRight">' + listItem + '</label></li>'))
        } else {
            ul.append($('<li><label><input type="radio" name="third_list" data-validation-engine="validate[required]" value="'
                + index + '" data-prompt-position="topRight">' + listItem + '</label></li>'))
        }
    });

    $('input:radio[name=third_list]').change(buildResult)
}

$(document).ready(function(){
    $("input:text[name=orderid]").hide();

    $('input:radio(name=first_list name=second_list)').change(buildThirdList)

    $("form").validationEngine('attach', {showOneMessage: true, showArrowOnRadioAndCheckbox: true, scroll: true})

    $("form").submit(buildResult)

    buildThirdList()
});