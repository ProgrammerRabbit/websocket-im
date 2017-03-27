var stompClient = null;
var userId = null;
var chosenContactId = null;

window.onload = function () {
    var userIdHidden = document.getElementById("userId");
    userId = userIdHidden.value;

    var addContactHint = document.getElementById("addContactHint");
    addContactHint.innerHTML = "";

    getOfflineMessageCount();

    getRequests();

    listenWebSocketMessage();
};

window.onbeforeunload = function () {
    if (stompClient != null) {
        stompClient.disconnect();
    }
};

function getOfflineMessageCount() {
    $.ajax({
        url: "/getOfflineMessageCount",
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

function getRequests() {
    var requests = document.getElementById("requests");

    $.ajax({
        url: "/getRequests",
        dataType: "json",
        success: function (data) {
            if (data.code == 200) {
                var innerHtml = "";
                var requestList = data.content;
                for (var i = 0; i < requestList.length; i++) {
                    innerHtml += buildRequestLine(requestList[i]);
                }
                if (innerHtml == "") {
                    requests.innerHTML = "There is no request.";
                } else {
                    requests.innerHTML = innerHtml;
                }
            }
        }
    });
}

function listenWebSocketMessage() {
    var sockJS = new SockJS('/ws');
    stompClient = Stomp.over(sockJS);
    stompClient.connect({}, function () {
        var requests = document.getElementById("requests");
        var historyTextarea = document.getElementById("historyTextarea");
        var contacts = document.getElementById("contacts");

        stompClient.subscribe("/topic/request/" + userId, function (data) {
            var request = JSON.parse(data.body);
            if (requests.innerHTML == "There is no request.") {
                requests.innerHTML = buildRequestLine(request);
            } else {
                requests.innerHTML += buildRequestLine(request);
            }
        });

        stompClient.subscribe("/topic/message/" + userId, function (data) {
            var message = JSON.parse(data.body);
            if (message.fromId == chosenContactId) {
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
                        var count = messageCount.innerHTML.substring(2, messageCount.innerHTML.length - 1);
                        messageCount.innerHTML = " (" + (parseInt(count) + 1) + ")";
                    }
                } else {
                    updateSession();

                    contacts.innerHTML += "<div draft='' class='contact' id='" + message.fromId + "' onclick='chooseContact(this.id);'></div>";
                    var contact = document.getElementById(message.fromId);
                    contact.innerHTML = message.fromUserName + "<span class='messageCount' id='messageCount" + message.fromId + "'> (1)</span>"
                }
            }
        });
    });
}

function buildRequestLine(request) {
    return "<div id='request" + request.id + "'>"
        + "<a onclick='acceptRequest(" + request.id + ");'>ACCEPT</a>&nbsp;|&nbsp;"
        + "<a onclick='rejectRequest(" + request.id + ");'>REJECT</a>&nbsp;"
        + request.requestUserName + "<br></div>";
}

function chooseContact(newChoseContactId) {
    var contentTextarea = document.getElementById("contentTextarea");
    var choseContact = document.getElementById(chosenContactId);
    var newChosenContact = document.getElementById(newChoseContactId);

    if (chosenContactId != null) {
        choseContact.draft = contentTextarea.value;
        choseContact.className = "contact";
    }

    contentTextarea.value = (newChosenContact.draft == undefined) ? "" : newChosenContact.draft;
    newChosenContact.className = "chosenContact";
    chosenContactId = newChoseContactId;

    getHistoryMessages();
}

function getHistoryMessages() {
    var historyTextarea = document.getElementById("historyTextarea");

    $.ajax({
        url: "/getHistoryMessages?contactUserId=" + chosenContactId,
        dataType: "json",
        success: function (data) {
            if (data.code == 200) {
                var history = "";
                var messageList = data.content;
                for (var i = 0; i < messageList.length; i++) {
                    if (messageList[i].fromId == userId) {
                        history += "[ME] ";
                    }
                    var time = new Date(messageList[i].addTime).toString().substring(0, 24);
                    history += time + "\n" + messageList[i].content + "\n\n";
                }
                if (history != "") {
                    history += "=== History Above ===\n\n";
                }
                historyTextarea.value = history;
                historyTextarea.scrollTop = 99999;

                readOfflineMessages();
            }
        }
    });
}

function readOfflineMessages() {
    $.ajax({
        url: "/readOfflineMessages?contactUserId=" + chosenContactId,
        dataType: "json",
        success: function (data) {
            if (data.code == 200 && data.content == true) {
                var messageCount = document.getElementById("messageCount" + chosenContactId);
                if (messageCount.innerHTML != "") {
                    messageCount.innerHTML = "";
                }
            }
        }
    });
}

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
    var historyTextarea = document.getElementById("historyTextarea");
    var contentTextarea = document.getElementById("contentTextarea");

    if (chosenContactId != null && contentTextarea.value != "") {
        $.ajax({
            url: "/sendMessage?toId=" + chosenContactId + "&content=" + contentTextarea.value,
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
    var addContactHint = document.getElementById("addContactHint");
    var addContactInput = document.getElementById("addContactInput");
    $.ajax({
        url: "/addContact?username=" + addContactInput.value,
        dataType: "json",
        success: function (data) {
            if (data.code == 200 && data.content == true) {
                addContactHint.innerHTML = "";
                alert("Request sent succeed!");
            } else {
                addContactHint.innerHTML = data.message;
            }
            addContactInput.value = "";
        }
    });
}

function acceptRequest(requestId) {
    var request = document.getElementById("request" + requestId);
    var contacts = document.getElementById("contacts");

    $.ajax({
        url: "/acceptRequest?requestId=" + requestId,
        dataType: "json",
        success: function (data) {
            if (data.code == 200 && data.content != null) {
                alert("Accept succeed!");

                updateSession();

                contacts.innerHTML += "<div draft='' class='contact' id='" + data.content.requestUserId + "' onclick='chooseContact(this.id);'></div>";
                var contact = document.getElementById(data.content.requestUserId);
                contact.innerHTML = data.content.requestUserName + "<span class='messageCount' id='messageCount" + data.content.requestUserId + "'></span>"

                request.style.display = "none";
            } else {
                alert("Accept failed!");
            }
        }
    });
}

function rejectRequest(requestId) {
    var request = document.getElementById("request" + requestId);

    $.ajax({
        url: "/rejectRequest?requestId=" + requestId,
        dataType: "json",
        success: function (data) {
            if (data.code == 200 && data.content == true) {
                alert("Reject succeed!");

                request.style.display = "none";
            } else {
                alert("Reject failed!");
            }
        }
    });
}

function updateSession() {
    $.ajax({
        url: "/updateSession"
    });
}
