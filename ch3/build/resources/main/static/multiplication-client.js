function updateMultiplication() {

    $.ajax({
        url: "http://localhost:8080/multiplication/random"
    }).then(function (data) {
        // 폼 비우기
        $("#attempt-form").find("input[name='result-attempt']").val("");
        $("#attempt-form").find("input[name='user-alias']").val("");

        // 무작위 문제를 API로 가져오기 추가하기
        $(".multiplication-a").empty().append(data.factorA);
        $(".multiplication-b").empty().append(data.factorB);
    })
}

$(document).ready(function() {

    updateMultiplication();

    $("#attempt-form").submit(function(event) {

        event.preventDefault();

        var a = $(".multiplication-a").text();
        var b = $(".multiplication-b").text();
        var $form = $(this),
          attempt = $form.find("input[name='result-attempt']").val(),
        userAlias = $form.find("input[name='user-alias']").val();

        var data = {user: { alias: userAlias }, multiplication: { factorA: a, factorB: b},
                resultAttempt: attempt };

        $.ajax({
            url: '/results',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(result) {
                if(result.correct) {
                    $(".result-message").empty().append("정답입니다. 축하드려요!");
                } else {
                    $(".result-message").empty().append("오답입니다! 그래도 포기하지 마세요.");
                }
            }
        });

        updateMultiplication();
    });
});