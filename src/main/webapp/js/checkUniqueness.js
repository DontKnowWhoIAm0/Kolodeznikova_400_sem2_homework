function checkFieldValueUniqueness(fieldId, errorId, url) {

    $(document).on("input", fieldId, function() {

        const value = $(this).val().trim();

        if (value === "") {
            $(errorId).text("");
            $("#signup-button").prop("disabled", false);
            return;
        }

        $.get(url, { [$(this).attr("name")]: value }, function(response) {

            if (response === "exists") {
                if (fieldId === "#login") {
                    $(errorId).text("Пользователь с таким логином уже существует");
                } else {
                    $(errorId).text("Пользователь с таким никнеймом уже существует");
                }
                $("#signup-button").prop("disabled", true);
            } else {
                $(errorId).text("");
                $("#signup-button").prop("disabled", false);
            }
        });
    });
}

$(document).ready(function() {
    const contextPath = window.contextPath;
    checkFieldValueUniqueness("#login", "#login-error", contextPath + '/ajax/checkLogin');
    checkFieldValueUniqueness("#nickname", "#nickname-error", contextPath + '/ajax/checkNickname');
});
