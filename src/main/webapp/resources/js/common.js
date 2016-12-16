function changeVerifyCode() {
    var verifyCode = document.getElementById("verifyCode");
    verifyCode.src = "/visit/verifyCode?time=" + new Date().getTime();
}
