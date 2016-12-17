<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Index</title>
    <script src="/webjars/jquery/3.1.0/jquery.min.js"></script>
    <script src="/resources/js/common.js"></script>
    <script src="/resources/js/index.js"></script>
    <link type="text/css" href="/resources/css/main.css" rel="stylesheet"/>
</head>
<body>
<h1>WebSocket IM</h1>
<input id="userId" type="hidden" value="${user.id}"/>
Hi, ${user.username}! |
<a href="/logout">Logout</a><br><br>
<button onclick="clickRequestBox();">RequestBox</button>
&nbsp;<span id="requestBoxHint"></span><br><br>
<div id="requestBox" open="none"></div><br>
CONTACTS:<br>
<#list user.contacts as contact>
    ${contact.username}<br>
</#list><br>
<input id="contact" type="text"/>
<button onclick="addContact();">ADD CONTACT</button>
<div id="errorHint"></div><br><br>
<textarea id="historyTextarea" rows="15" cols="40" disabled="disabled"></textarea><br>
<textarea id="contentTextarea" rows="3" cols="40"></textarea>
<button onclick="send();">Send</button><br><br>
&copy;2016 <a href="http://yangwen.net.cn">ProgrammerRabbit</a>
</body>
</html>