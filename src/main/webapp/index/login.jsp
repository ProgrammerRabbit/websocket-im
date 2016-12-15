<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <script src="<c:url value='/webjars/jquery/3.1.0/jquery.min.js'/>"></script>
    <script src="<c:url value='/index/js/md5.js'/>"></script>
    <script>
        function changeVerifyCode() {
            var verifyCode = document.getElementById("verifyCode");
            verifyCode.src = "<c:url value='/verifyCode'/>" + "?time=" + new Date().getTime();
        }

        function doSubmit() {
            var password = document.getElementById("password");
            var form = document.getElementById("form");
            password.value = $.md5(password.value);
            form.submit();
        }

        window.onload = function () {
            var error = document.getElementById("error");
            if (error.innerHTML != "") {
                error.style.display = "block";
            }
        }
    </script>
    <style>
        #error {
            color: red;
            display: none;
        }
    </style>
</head>
<body>
<h1>WebSocket IM</h1>
<form id="form" method="post" action="<c:url value='/login'/>">
    USERNAME:<br><input type="text" name="username"/><br><br>
    PASSWORD:<br><input type="password" id="password" name="password"/><br><br>
    VERIFY CODE:<br>(click to change.)<br><img id="verifyCode" src="<c:url value='/verifyCode'/>" onclick="changeVerifyCode();"/><br>
    <input type="text" name="verifyCode"/><br><br>
    <div id="error">${requestScope.r_error}</div>
    <button onclick="doSubmit();">Login</button>
    Don't have an account? <a href="<c:url value='/index/register.jsp'/>">Register >></a><br><br>
</form>
&copy;2016 <a href="http://yangwen.net.cn">ProgrammerRabbit</a>
</body>
</html>