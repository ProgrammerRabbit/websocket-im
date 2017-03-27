<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <script src="/webjars/jquery/3.1.0/jquery.min.js"></script>
    <script src="/resources/js/lib/md5.js"></script>
    <script src="/resources/js/common.js"></script>
    <script src="/resources/js/login.js"></script>
    <link type="text/css" href="/resources/css/main.css" rel="stylesheet"/>
</head>
<body>
<h1>WebSocket IM</h1>
<form id="form" method="post" action="/visit/doLogin">
    USERNAME:<br>
    <input type="text" name="username"/><br><br>
    PASSWORD:<br>
    <input type="password" id="password" name="password"/><br><br>
    VERIFY CODE:<br>
    (click to change.)<br>
    <img id="verifyCode" src="/visit/verifyCode" onclick="changeVerifyCode();"/><br>
    <input type="text" name="verifyCode"/><br><br>
    <div id="errorHint">${errorHint}</div>
    <button onclick="doSubmit();">Login</button>
    Don't have an account? <a href="/visit/register">Register >></a><br><br>
</form>
&copy;2016 <a href="http://yangwen.net.cn">ProgrammerRabbit</a>
</body>
</html>