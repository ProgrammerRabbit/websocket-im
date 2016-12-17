function send() {
    var historyTextarea = document.getElementById("historyTextarea");
    var contentTextarea = document.getElementById("contentTextarea");
    var head = "[ME] " + new Date().toString().substring(0, 24);
    historyTextarea.value = historyTextarea.value + head + "\n" + contentTextarea.value + "\n";
    historyTextarea.scrollTop = 99999;
    contentTextarea.value = "";
}

function addContact() {
    var contact = document.getElementById("contact");
    var errorHint = document.getElementById("errorHint");
    $.ajax({
        url: "/addContact?username=" + contact.value,
        dataType: "json",
        success: function(data) {
            if (data.code == 200) {
                if (data.content == false) {
                    showErrorHint(data.message);
                } else {
                    errorHint.style.display = "none";
                    alert("Request sent succeed!");
                }
            } else {
                showErrorHint(data.message);
            }
            contact.value = "";
        }
    });
}

window.onload = function () {
    var errorHint = document.getElementById("errorHint");
    if (errorHint.innerHTML != "") {
        errorHint.style.display = "block";
    }
};