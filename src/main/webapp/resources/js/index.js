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

function clickRequestBox() {
    var requestBoxHint = document.getElementById("requestBoxHint");
    var requestBox = document.getElementById("requestBox");
    if (requestBox.open == "true") {
        requestBoxHint.innerHTML = "Click to open :)";
        requestBox.style.display = "none";
        requestBox.open = "false";
    } else {
        requestBoxHint.innerHTML = "Click to close :)";
        requestBox.style.display = "block";
        requestBox.open = "true";
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

function getRequests() {
    var requestBox = document.getElementById("requestBox");
    var requestBoxHint = document.getElementById("requestBoxHint");
    $.ajax({
        url: "/getRequests",
        dataType: "json",
        success: function (data) {
            if (data.code == 200) {
                var innerHtml = "";
                var requestList = data.content;
                for (var i = 0; i < requestList.length; i++) {
                    innerHtml += getRequestLine(requestList[i]);
                }
                if (innerHtml == "") {
                    innerHtml = "There is no request.";
                    requestBoxHint.innerHTML = "No request :)";
                } else {
                    requestBoxHint.innerHTML = requestList.length + " request" + (requestList.length == 1 ?  "" : "s") + ", click to open :)";
                }
                requestBox.innerHTML = innerHtml;
            }
        }
    });
}

function getRequestLine(request) {
    var innerHtml = "";
    innerHtml += request.requestUserName;
    innerHtml += "&nbsp;<a onclick='acceptRequest(" + request.id + ");'>ACCEPT</a>&nbsp;|";
    innerHtml += "&nbsp;<a onclick='rejectRequest(" + request.id + ");'>REJECT</a><br>";
    return innerHtml;
}

var stompClient = null;

function listenRequest() {
    var userIdHidden = document.getElementById("userIdHidden");
    var requestBox = document.getElementById("requestBox");
    var requestBoxHint = document.getElementById("requestBoxHint");
    var sockJS = new SockJS();
    stompClient = Stomp.over(sockJS);
    stompClient.connect({}, function (frame) {
        console.log("Connected: " + frame);
        stompClient.subscribe("/topic/request/" + userIdHidden.value, function (request) {
            if (requestBox.innerHTML == "There is no request.") {
                requestBox.innerHTML = getRequestLine(request);
            } else {
                requestBox.innerHTML += getRequestLine(request);
            }
            requestBoxHint.innerHTML = "New request :)";
        })
    })
}

window.onload = function () {
    var errorHint = document.getElementById("errorHint");
    if (errorHint.innerHTML != "") {
        errorHint.style.display = "block";
    }

    getRequests();
    listenRequest();
};