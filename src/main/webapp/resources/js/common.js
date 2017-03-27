function changeVerifyCode() {
    var verifyCode = document.getElementById("verifyCode");
    verifyCode.src = "/visit/verifyCode?time=" + new Date().getTime();
}

function showErrorHint(text) {
    var errorHint = document.getElementById("errorHint");
    errorHint.innerHTML = text;
    errorHint.style.display = "block";
}