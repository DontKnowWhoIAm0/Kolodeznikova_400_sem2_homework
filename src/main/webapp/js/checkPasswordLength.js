function checkPasswordLength(passwordFieldId, errorId, minLength, submitButtonId) {

    $(document).on("input", passwordFieldId, function() {

        const value = $(this).val();

        if (value.length < minLength) {
            $(errorId).text(`Пароль должен быть минимум ${minLength} символов`);
            $(submitButtonId).prop("disabled", true);
        } else {
            $(errorId).text("");
            $(submitButtonId).prop("disabled", false);
        }
    });

    $(passwordFieldId).closest("form").on("submit", function(e) {
        const value = $(passwordFieldId).val();
        if (value.length < minLength) {
            e.preventDefault();
            $(errorId).text(`Пароль должен быть минимум ${minLength} символов`);
            $(submitButtonId).prop("disabled", true);
        }
    });
}

$(document).ready(function() {
    checkPasswordLength("#password", "#password-error", 8, "#signup-button");
});