<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <script src="/webjars/jquery/3.1.0/jquery.min.js"></script>
    <script src="/resources/js/lib/md5.js"></script>
    <script src="/resources/js/common.js"></script>
    <script src="/resources/js/register.js"></script>
    <link type="text/css" href="/resources/css/main.css" rel="stylesheet"/>
</head>
<body>
<h1>WebSocket IM</h1>
<form id="form" method="post" action="/visit/doRegister">
    USERNAME:<br>
    <input type="text" id="username" name="username" onblur="checkUsername();"/><br><br>
    PASSWORD:<br>
    <input type="password" id="password" name="password"/><br><br>
    CONFIRM:<br><input type="password" id="confirm" name="confirm"/><br><br>
    VERIFY CODE:<br>
    (click to change.)<br>
    <img id="verifyCode" src="/visit/verifyCode" onclick="changeVerifyCode();"/><br>
    <input type="text" name="verifyCode"/><br><br>
    <div id="addContactHint">${errorHint}</div>
    <button onclick="return doSubmit();">Register</button>
    Have an account? <a href="/visit/login">Login >></a><br><br>
</form>
&copy;2016 <a href="http://yangwen.net.cn">ProgrammerRabbit</a>
</body>
</html>