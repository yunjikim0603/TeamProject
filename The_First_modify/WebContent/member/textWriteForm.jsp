<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Text Write</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="icon" type="image/png" href="Login_v4/images/icons/favicon.ico"/>
	<link rel="stylesheet" type="text/css" href="Login_v4/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="Login_v4/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="Login_v4/fonts/iconic/css/material-design-iconic-font.min.css">
	<link rel="stylesheet" type="text/css" href="Login_v4/vendor/animate/animate.css">
	<link rel="stylesheet" type="text/css" href="Login_v4/vendor/css-hamburgers/hamburgers.min.css">
	<link rel="stylesheet" type="text/css" href="Login_v4/vendor/animsition/css/animsition.min.css">
	<link rel="stylesheet" type="text/css" href="Login_v4/vendor/select2/select2.min.css">
	<link rel="stylesheet" type="text/css" href="Login_v4/vendor/daterangepicker/daterangepicker.css">
	<link rel="stylesheet" type="text/css" href="Login_v4/css/util.css">
	<link rel="stylesheet" type="text/css" href="Login_v4/css/main.css">
</head>
<body>
	<section>
	<div class="limiter">
		<div class="container-login100" style="background-image: url('images/bg-01.jpg');">
			<div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
				
		<form action="TextWrite.te" method="post">
			<span class="login100-form-title p-b-49">
						Send Text
			</span>
					<div class="wrap-input100 validate-input m-b-23" data-validate = "Sender">
						<span class="label-input100">Sender</span>
						<input class="input100" type="text" id="sender" name="sender" value="${sessionScope.nickname }" readonly="readonly">
						<span class="focus-input100" data-symbol="&#xf206;"></span>
					</div>
					
					<div class="wrap-input100 validate-input m-b-23" data-validate = "receiver">
						<span class="label-input100">Receiver</span>
						<input class="input100" type="text" name="receiver" id="receiver" required="required">
						<span class="focus-input100"></span>
					</div>
					
					<div class="wrap-input100 validate-input m-b-23" data-validate = "Subject">
						<span class="label-input100">Subject</span>
						<input class="input100" type="text" name="subject" id="subject" required="required">
						<span class="focus-input100"></span>
					</div>
					
					<div class="wrap-input100 validate-input m-b-23" data-validate = "Content">
						<span class="label-input100">Content</span>
						<textarea class="input100" name="contents" cols="40" rows="30" required="required"></textarea>
						<span class="focus-input100"></span>
					</div>
					
					<div class="text-right p-t-8 p-b-31">
						<a href="#">
							
						</a>
					</div>
					
					<div class="container-login100-form-btn">
						<div class="wrap-login100-form-btn">
							<div class="login100-form-bgbtn"></div>
							<button class="login100-form-btn" type="submit"> SEND </button>
							<button class="login100-form-btn" type="reset"> REWRITE </button>
							<a href="TextList.te?receiver=${sessionScope.nickname }"><button class="login100-form-btn" type="button"> LIST </button></a>
						</div>
					</div>

					<div class="text-right p-t-8 p-b-31">
						<a href="#">
							
						</a>
					</div>
				</form>
			</div>
		</div>
	</div>
			
	</section>
</body>
</html>