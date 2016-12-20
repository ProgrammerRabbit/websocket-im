<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Index</title>
    <script src="/webjars/jquery/3.1.0/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/1.0.2/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
    <script src="/resources/js/index.js"></script>
    <link type="text/css" href="/resources/css/main.css" rel="stylesheet"/>
</head>
<body>
<h1>WebSocket IM</h1>
<input id="userId" type="hidden" value="${user.id}"/>
Hi, ${user.username}!&nbsp;|&nbsp;<a href="/logout">Logout</a>
<hr>
<table>
    <tr>
        <td valign="top">
            CONTACTS:
            <div id="contacts">
            <#list user.contacts as contact>
                <div draft="" class="contact" id="${contact.id}" onclick="chooseContact(this.id);">
                ${contact.username}<span class="messageCount" id="messageCount${contact.id}"></span>
                </div>
            </#list>
            </div>
        </td>
        <td rowspan="2">
            <textarea id="historyTextarea" rows="15" cols="40" disabled="disabled"></textarea><br>
            <table class="sendHint">
                <tr>
                    <td>
                        <strong>Enter</strong> to send, <strong>Shift+Enter</strong> to break line.
                    </td>
                    <td align="right">
                        <button onclick="send();">Send</button>
                    </td>
                </tr>
            </table>
            <textarea id="contentTextarea" rows="3" cols="40" onkeypress="return enterToSend(event);"></textarea>
        </td>
        <td valign="top" rowspan="2">
            REQUESTS:
            <div id="requests"></div>
        </td>
    </tr>
    <tr>
        <td valign="bottom">
            <div class="error" id="addContactHint"></div>
            <input id="addContactInput" type="text"/>
            <button onclick="addContact();">ADD CONTACT</button>
        </td>
    </tr>
</table>
<br><br>
&copy;2016 <a href="http://yangwen.net.cn">ProgrammerRabbit</a>
</body>
</html>