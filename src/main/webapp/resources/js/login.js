function doSubmit() {
    var form = document.getElementById("form");
    var password = document.getElementById("password");

    password.value = $.md5(password.value);
    form.submit();
}

window.onload = function () {
    var errorHint = document.getElementById("errorHint");
    if (errorHint.innerHTML != "") {
        errorHint.style.display = "block";
    }
};