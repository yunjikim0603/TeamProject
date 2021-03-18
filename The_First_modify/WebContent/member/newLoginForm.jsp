<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>로그인</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script>
	window.onunload = function () { 
	    window.opener.location.reload(); 
	}
</script>
</head>
<body class="w3-black">
	<div class="w3-main">
		<h3 class="w3-center">로그인</h3>
		<div>
			<div class="w3-container w3-panel w3-border w3-margin">
				<div>
					<h5>아이디 로그인</h5>
				</div>
				<form action="/The_First/NewLoginAction.me" method="post">
					<input class="w3-input w3-border" type="text" name="id" id="id" required="" placeholder="아이디" autofocus>
					<input class="w3-input w3-border" type="password" name="password" id="passwd" required="" placeholder="비밀번호">
					<input class="w3-button w3-border" type="submit" value="로그인">
					<input class="w3-button w3-border" type="button" value="ID/PW 찾기" onclick="location.href=''">
				</form>
			</div>
		</div>
	</div>
</body>
</html>
