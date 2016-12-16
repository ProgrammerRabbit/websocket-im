<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Index</title>
</head>
<body>
<h1>WebSocket IM</h1>
Hi, ${user.username}! | <a href="/logout">Logout</a><br><br>
CONTACTS:<br>
<#list user.contacts as contact>
    ${contact.username}<br>
</#list>
<br>
&copy;2016 <a href="http://yangwen.net.cn">ProgrammerRabbit</a>
</body>
</html>