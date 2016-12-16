function checkUsername() {
    var username = document.getElementById("username");
    $.ajax({
        url: "/visit/isUsernameRegistered?username=" + username.value,
        dataType: "json",
        success: function(data) {
            if (data.code == 200 && data.content == true) {
                var errorHint = document.getElementById("errorHint");
                errorHint.innerHTML = "\"" + username.value + "\" is already registered!<br><br>";
                errorHint.style.display = "block";
                username.value = "";
            }
        }
    });
}

function doSubmit() {
    var form = document.getElementById("form");
    var username = document.getElementById("username");
    var password = document.getElementById("password");
    var confirm = document.getElementById("confirm");
    var errorHint = document.getElementById("errorHint");
    if (username.value.trim() == "") {
        showErrorHint("USERNAME shouldn't be empty!<br><br>");
        return false;
    }
    if (password.value.trim() == "") {
        showErrorHint("PASSWORD shouldn't be empty!<br><br>");
        return false;
    }
    if (username.value.length > 20) {
        showErrorHint("USERNAME should be 1-20 long!<br><br>");
        return false;
    }
    if (password.value != confirm.value) {
        showErrorHint("PASSWORD and CONFIRM don't match!<br><br>");
        password.value = "";
        confirm.value = "";
        return false;
    }
    password.value = $.md5(password.value);
    confirm.value = $.md5(password.value);
    form.submit();
    return true;
}

function showErrorHint(text) {
    var errorHint = document.getElementById("errorHint");
    errorHint.innerHTML = text;
    errorHint.style.display = "block";
}

window.onload = function () {
    var errorHint = document.getElementById("errorHint");
    if (errorHint.innerHTML != "") {
        errorHint.style.display = "block";
    }
};