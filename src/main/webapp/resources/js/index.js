var choseContactId = null;

function enterToSend(event) {
    if (event.keyCode == 13 && event.shiftKey) {
        return true;
    }

    if (event.keyCode == 13) {
        send();
        return false;
    }

    return true;
}

function send() {
    if (choseContactId != null) {
        var userIdHidden = document.getElementById("userId");
        var historyTextarea = document.getElementById("historyTextarea");
        var contentTextarea = document.getElementById("contentTextarea");
        $.ajax({
            url: "/sendMessage?fromId=" + userIdHidden.value + "&toId=" + choseContactId + "&content=" + contentTextarea.value,
            dataType: "json",
            success: function (data) {
                if (data.code == 200 && data.content == true) {
                    var head = "[ME] " + new Date().toString().substring(0, 24);
                    historyTextarea.value = historyTextarea.value + head + "\n" + contentTextarea.value + "\n\n";
                    historyTextarea.scrollTop = 99999;
                    contentTextarea.value = "";
                } else {
                    alert("Send failed!");
                }
            }
        });
    }
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

function chooseContact(newChoseContactId) {
    var contentTextarea = document.getElementById("contentTextarea");

    if (choseContactId != null) {
        var choseContact = document.getElementById(choseContactId);
        choseContact.draft = contentTextarea.value;
        choseContact.className = "contact";
    }
    var newChoseContact = document.getElementById(newChoseContactId);
    newChoseContact.className = "choseContact";
    choseContactId = newChoseContactId;
    if (newChoseContact.draft == undefined) {
        contentTextarea.value = "";
    } else {
        contentTextarea.value = newChoseContact.draft;
    }

    getHistoryMessages();
}

function readOfflineMessages() {
    var userIdHidden = document.getElementById("userId");
    $.ajax({
        url: "/readOfflineMessages?userId=" + userIdHidden.value + "&contactUserId=" + choseContactId,
        dataType: "json",
        success: function (data) {
            if (data.code == 200 && data.content == true) {
                var messageCount = document.getElementById("messageCount" + choseContactId);
                if (messageCount.innerHTML != "") {
                    messageCount.innerHTML = "";
                }
            }
        }
    });
}

function getHistoryMessages() {
    var userIdHidden = document.getElementById("userId");
    var historyTextarea = document.getElementById("historyTextarea");
    $.ajax({
        url: "/getHistoryMessages?userId=" + userIdHidden.value + "&contactUserId=" + choseContactId,
        dataType: "json",
        success: function (data) {
            if (data.code == 200) {
                var innerHtml = "";
                var messageList = data.content;
                for (var i = 0; i < messageList.length; i++) {
                    if (messageList[i].fromId == userIdHidden.value) {
                        innerHtml += "[ME] ";
                    }
                    var time = new Date(messageList[i].addTime).toString().substring(0, 24);
                    innerHtml = innerHtml + time + "\n";
                    innerHtml = innerHtml + messageList[i].content + "\n\n";
                }
                if (innerHtml != "") {
                    innerHtml += "=== History Above ===\n\n";
                }
                historyTextarea.innerHTML = innerHtml;
                historyTextarea.scrollTop = 99999;
                readOfflineMessages();
            }
        }
    });
}

function clickRequestBox() {
    var requestBoxHint = document.getElementById("requestBoxHint");
    var requestBox = document.getElementById("requestBox");
    requestBoxHint.style.color = "black";
    if (requestBox.open == "true") {
        requestBoxHint.innerHTML = "Click to open";
        requestBox.style.display = "none";
        requestBox.open = "false";
    } else {
        requestBoxHint.innerHTML = "Click to close";
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
                    requestBoxHint.innerHTML = "No request";
                } else {
                    requestBoxHint.innerHTML = requestList.length + " request" + (requestList.length == 1 ?  "" : "s") + ", click to open";
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

function listen() {
    var userIdHidden = document.getElementById("userId");
    var requestBox = document.getElementById("requestBox");
    var requestBoxHint = document.getElementById("requestBoxHint");
    var sockJS = new SockJS('/ws');
    stompClient = Stomp.over(sockJS);
    stompClient.connect({}, function (frame) {
        console.log("Connected: " + frame);

        stompClient.subscribe("/topic/request/" + userIdHidden.value, function (data) {
            var request = JSON.parse(data.body);
            if (requestBox.innerHTML == "There is no request.") {
                requestBox.innerHTML = getRequestLine(request);
            } else {
                requestBox.innerHTML += getRequestLine(request);
            }
            requestBoxHint.style.color = "red";
            requestBoxHint.innerHTML = "New request";
        });

        stompClient.subscribe("/topic/message/" + userIdHidden.value, function (data) {
            var message = JSON.parse(data.body);
            if (message.fromId == choseContactId) {
                var historyTextarea = document.getElementById("historyTextarea");
                var time = new Date(message.addTime).toString().substring(0, 24);
                historyTextarea.value += time + "\n" + message.content + "\n\n";
                historyTextarea.scrollTop = 9999;

                readOfflineMessages();
            } else {
                var messageCount = document.getElementById("messageCount" + message.fromId);
                if (messageCount != null) {
                    if (messageCount.innerHTML == "") {
                        messageCount.innerHTML = " (1)";
                    } else {
                        var count = messageCount.innerHTML.substring(2, messageCount.innerHTML.length -1);
                        messageCount.innerHTML = " (" + (parseInt(count) + 1) + ")";
                    }
                } else {
                    var contacts = document.getElementsByClassName("contacts")[0];
                    contacts.innerHTML += "<div draft='' class='contact' id='" + message.fromId + "' onclick='chooseContact(this.id);'></div>";
                    var contact = document.getElementById(message.fromId);
                    contact.innerHTML = message.fromUserName + "<span class='messageCount' id='messageCount" + message.fromId + "'> (1)</span>"
                }
            }
        });
    });
}

function getOfflineMessageCount() {
    var userIdHidden = document.getElementById("userId");
    $.ajax({
        url: "/getOfflineMessageCount?userId=" + userIdHidden.value,
        dataType: "json",
        success: function (data) {
            if (data.code == 200) {
                var pairList = data.content;
                for (var i = 0; i < pairList.length; i++) {
                    var messageCount = document.getElementById("messageCount" + pairList[i].contactUserId);
                    if (messageCount != null) {
                        var count = pairList[i].count;
                        if (count != 0) {
                            messageCount.innerHTML = " (" + count + ")";
                        }
                    }
                }
            }
        }
    });
}

window.onload = function () {
    var errorHint = document.getElementById("errorHint");
    if (errorHint.innerHTML != "") {
        errorHint.style.display = "block";
    }

    getOfflineMessageCount();
    getRequests();
    listen();
};

window.onbeforeunload = function() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
};
