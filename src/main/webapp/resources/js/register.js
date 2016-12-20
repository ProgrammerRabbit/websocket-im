function checkUsername() {
    isUsernameValid();

    var username = document.getElementById("username");

    $.ajax({
        url: "/visit/isUsernameRegistered?username=" + username.value,
        dataType: "json",
        success: function(data) {
            if (data.code == 200 && data.content == true) {
                showErrorHint("\"" + username.value + "\" is already registered!<br><br>");
                username.value = "";
            }
        }
    });
}

function isUsernameValid() {
    var username = document.getElementById("username");
    var errorHint = document.getElementById("errorHint");

    if (username.value.indexOf(" ") != -1 || username.value.indexOf("<") != -1) {
        showErrorHint("USERNAME shouldn't contains whitespace or '&lt;'!<br><br>");
        return false;
    }

    if (username.value.trim() == "") {
        showErrorHint("USERNAME shouldn't be empty!<br><br>");
        username.value = "";
        return false;
    }

    if (username.value.length > 12) {
        showErrorHint("USERNAME should be 1-12 long!<br><br>");
        return false;
    }

    errorHint.innerHTML = "";
    errorHint.style.display = "none";
    return true;
}

function doSubmit() {
    if (!isUsernameValid()) {
        return false;
    }

    var form = document.getElementById("form");
    var password = document.getElementById("password");
    var confirm = document.getElementById("confirm");
    var errorHint = document.getElementById("errorHint");

    if (password.value.trim() == "") {
        showErrorHint("PASSWORD shouldn't be empty!<br><br>");
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

window.onload = function () {
    var errorHint = document.getElementById("errorHint");
    if (errorHint.innerHTML != "") {
        errorHint.style.display = "block";
    }
};