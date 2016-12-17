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

function openRequestBox() {
    var requestBoxHint = document.getElementById("requestBoxHint");
    var requestBox = document.getElementById("requestBox");
    if (requestBox.open == "true") {
        requestBoxHint.innerHTML = "Click to open :)";
        requestBox.style.display = "none";
        requestBox.open = "false";
    } else {
        $.ajax({
            url: "/getRequests",
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    var innerHtml = "";
                    var requestList = data.content;
                    for (var i = 0; i < requestList.length; i++) {
                        innerHtml += requestList[i].requestUserName;
                        innerHtml += "&nbsp;<a onclick='acceptRequest(" + requestList[i].id + ");'>ACCEPT</a>&nbsp;|";
                        innerHtml += "&nbsp;<a onclick='rejectRequest(" + requestList[i].id + ");'>REJECT</a><br>";
                    }
                    if (innerHtml == "") {
                        innerHtml = "There is no request";
                    }
                    requestBoxHint.innerHTML = "Click to close :)";
                    requestBox.innerHTML = innerHtml;
                    requestBox.style.display = "block";
                    requestBox.open = "true";
                }
            }
        });
    }
}

function acceptRequest(requestId) {
    var userIdHidden = document.getElementById("userId");
    $.ajax({
        url: "/acceptRequest?userId=" + userIdHidden.value + "&requestId=" + requestId,
        dataType: "json",
        success: function (data) {
            if (data.code == 200 && data.content == true) {
                alert("Accept succeed!");
                location.reload();
            } else {
                alert("Accept failed!");
            }
        }
    });
}

function rejectRequest(requestId) {
    var userIdHidden = document.getElementById("userId");
    $.ajax({
        url: "/rejectRequest?userId=" + userIdHidden.value + "&requestId=" + requestId,
        dataType: "json",
        success: function (data) {
            if (data.code == 200 && data.content == true) {
                alert("Reject succeed!");
                location.reload();
            } else {
                alert("Reject failed!");
            }
        }
    });
}

window.onload = function () {
    var errorHint = document.getElementById("errorHint");
    if (errorHint.innerHTML != "") {
        errorHint.style.display = "block";
    }
};