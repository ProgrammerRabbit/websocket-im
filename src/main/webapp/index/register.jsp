<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <script src="<c:url value='/webjars/jquery/3.1.0/jquery.min.js'/>"></script>
    <script src="<c:url value='/index/js/md5.js'/>"></script>
    <script>
        function changeVerifyCode() {
            var verifyCode = document.getElementById("verifyCode");
            verifyCode.src = "<c:url value='/verifyCode'/>" + "?time=" + new Date().getTime();
        }

        function checkUsername() {
            var username = document.getElementById("username");
            $.ajax({
                url: "<c:url value='/isUsernameRegistered'/>" + "?username=" + username.value,
                dataType: "json",
                success: function(data) {
                    if (data.code == 200 && data.content == true) {
                        var error = document.getElementById("error");
                        error.innerHTML = "\"" + username.value + "\" is already registered!<br><br>";
                        error.style.display = "block";
                        username.value = "";
                    }
                }
            });
        }

        function doSubmit() {
            var form = document.getElementById("form");
            var password = document.getElementById("password");
            var confirm = document.getElementById("confirm");
            var error = document.getElementById("error");
            if (password.value != confirm.value) {
                error.innerHTML = "PASSWORD and CONFIRM don't match!";
                password.value = "";
                confirm.value = "";
                return;
            }
            password.value = $.md5(password.value);
            confirm.value = $.md5(password.value);
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
<form id="form" method="post" action="<c:url value='/register'/>">
    USERNAME:<br><input type="text" id="username" name="username" onblur="checkUsername();"/><br><br>
    PASSWORD:<br><input type="password" id="password" name="password"/><br><br>
    CONFIRM:<br><input type="password" id="confirm" name="confirm"/><br><br>
    VERIFY CODE:<br>(click to change.)<br><img id="verifyCode" src="<c:url value='/verifyCode'/>" onclick="changeVerifyCode();"/><br>
    <input type="text" name="verifyCode"/><br><br>
    <div id="error">${requestScope.r_error}</div>
    <button onclick="doSubmit();">Register</button>
    Have an account? <a href="<c:url value='/index/login.jsp'/>">Login >></a><br><br>
</form>
&copy;2016 <a href="http://yangwen.net.cn">ProgrammerRabbit</a>
</body>
</html>