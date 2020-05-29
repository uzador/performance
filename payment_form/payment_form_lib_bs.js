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

//const snippets: any[] = [
//    { text: "const", displayText: "const declarations" },
//    { text: "let", displayText: "let declarations" },
//    { text: "var", displayText: "var declarations" }
//];

//function snippet(): void {
//    CodeMirror.showHint(
//      codemirror,
//      function (): any {
//        const cursor = codemirror.getCursor();
//        const token = codemirror.getTokenAt(cursor);
//        const start: number = token.start;
//        const end: number = cursor.ch;
//        const line: number = cursor.line;
//        const currentWord: string = token.string;
//
//        const list: any[] = snippets.filter(function (item): boolean {
//          return item.text.indexOf(currentWord) >= 0;
//        });
//
//        return {
//          list: list.length ? list : snippets,
//          from: CodeMirror.Pos(line, start),
//          to: CodeMirror.Pos(line, end)
//        };
//      },
//      { completeSingle: false }
//    );
//}

//snippet = function() {
//    CodeMirror.showHint(
//          codemirror,
//          function () {
//            const cursor = codemirror.getCursor();
//            const token = codemirror.getTokenAt(cursor);
//            const start: number = token.start;
//            const end: number = cursor.ch;
//            const line: number = cursor.line;
//            const currentWord: string = token.string;
//
//            const list: any[] = snippets.filter(function (item): boolean {
//              return item.text.indexOf(currentWord) >= 0;
//            });
//
//            return {
//              list: list.length ? list : snippets,
//              from: CodeMirror.Pos(line, start),
//              to: CodeMirror.Pos(line, end)
//            };
//          },
//          { completeSingle: false }
//        );
//}

$(document).ready(function(){
    $("input:text[name=orderid]").hide();
    $("input:text[name=pstype]").hide();

    $('input:radio(name=first_list name=second_list)').change(buildThirdList)

    $("form").validationEngine('attach', {showOneMessage: true, showArrowOnRadioAndCheckbox: true, scroll: true})

    $("form").submit(buildResult)

    buildThirdList()

//    var namesEditor = CodeMirror.fromTextArea(document.getElementById("names"), {
//        lineNumbers: true,
//        extraKeys: {
//            "Ctrl-Space": "autocomplete"
//        }
//    });

//                        const list: any[] = snippets.filter(function (item): boolean {
//                          return item.text.indexOf(currentWord) >= 0;
//                        });


//    namesEditor.on("change", function(cm) {
//        alert("changed")
//    })

//    namesEditor.on('cursorActivity', function(){
//    	var options = {
//            hint: function() {
//                return {
//                from: namesEditor.getDoc().getCursor(),
//                  to: namesEditor.getDoc().getCursor(),
//                list: ['hello', 'yes', 'did']
//                }
//            }
//        };
//
//       namesEditor.showHint(options);
//    });
})

/*
(function () {
  const codemirror = CodeMirror(document.body, {
    value:
      "// CodeMirror Addon hint/show-hint.js sample.\n// Snippets are Ctrl-E or Cmd-E.",
    mode: "text/javascript",
    lineNumbers: true,
    styleActiveLine: true,
    theme: "solarized dark"
  });

  codemirror.setOption("extraKeys", {
    "Cmd-E": function () {
      snippet();
    },
    "Ctrl-E": function () {
      snippet();
    }
  });

  const snippets: any[] = [
    { text: "const", displayText: "const declarations" },
    { text: "let", displayText: "let declarations" },
    { text: "var", displayText: "var declarations" }
  ];

  function snippet(): void {
    CodeMirror.showHint(
      codemirror,
      function (): any {
        const cursor = codemirror.getCursor();
        const token = codemirror.getTokenAt(cursor);
        const start: number = token.start;
        const end: number = cursor.ch;
        const line: number = cursor.line;
        const currentWord: string = token.string;

        const list: any[] = snippets.filter(function (item): boolean {
          return item.text.indexOf(currentWord) >= 0;
        });

        return {
          list: list.length ? list : snippets,
          from: CodeMirror.Pos(line, start),
          to: CodeMirror.Pos(line, end)
        };
      },
      { completeSingle: false }
    );
  }
})();
*/




