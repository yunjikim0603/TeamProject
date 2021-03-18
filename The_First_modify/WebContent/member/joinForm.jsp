<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${sessionScope.sId != null}">
	<script>
		alert("이미 로그인된 회원입니다");
	</script>
</c:if>

<!DOCTYPE html>
<html>
<head>
	<title>Sign Up</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
	<link rel="icon" type="image/png" href="ContactFrom_v4/images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="ContactFrom_v4/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="ContactFrom_v4/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="ContactFrom_v4/vendor/animate/animate.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="ContactFrom_v4/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="ContactFrom_v4/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="ContactFrom_v4/vendor/select2/select2.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="ContactFrom_v4/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="ContactFrom_v4/css/util.css">
	<link rel="stylesheet" type="text/css" href="ContactFrom_v4/css/main.css">
<!--===============================================================================================-->
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
$(function () {
	var submitId = false;
	var submitNickname = false;
	var pwDupChkResult = false;
	$('#pwDupChkResult_ture').hide();
	$('#pwDupChkResult_false').hide();
	
	// 아이디 중복체크
	$('#idDup').click(function() {
		var idValue = $('#id').val();
		if (idValue == "") {
			alert("아이디 입력하세요");
			$('#id').focus();
			return;
		} 
		
		$.ajax({
			url: "idDupCheck.me?id=" + idValue,
			type: "GET",
			success: function(count) {
				if (count == 1) {
					alert('중복된 ID입니다');
					$('#id').focus();
				} else {
					alert('사용가능한 ID입니다');
					submitId = true;
				}
			}
		});
	});
	
	// 닉네임 중복체크
	$('#nicknameDup').click(function() {
		var nicknameValue = $('#nickname').val();
		if (nicknameValue == "") {
			alert("Nickname 입력하세요");
			$('#nickname').focus();
			return;
		}
		$.ajax({
			url: "nicknameDupCheck.me?nickname=" + nicknameValue,
			type: "GET",
			success: function(count) {
				if (count == 1) {
					alert('중복된 Nickname입니다 ');
					$('#nickname').focus();
				} else {
					alert('사용가능한 Nickname입니다');
					submitNickname = true;
				}
			}
		});
	});

	// pw 일치여부 
	$('input').keyup(function() {
		var pw1 = $('#password').val();
		var pw2 = $('#pwCheck').val();
		
		if (pw1 != "" || pw2 != "") {
			if (pw1 !== pw2) {
				$('#pwDupChkResult_ture').hide();
				$('#pwDupChkResult_false').show();
			} else {
				$('#pwDupChkResult_ture').show();
				$('#pwDupChkResult_false').hide();
				pwDupChkResult = true;
			}
		}
		
	});
	
	// 중복체크 했는지 유무 판단 
	$('#submit').click(function () {
			if (idRegex == false) {
				alert('아이디는 4~12자리 영문,숫자 조합');
				return false;
			} else if (passRegex == false) {
				alert('패스워드는 8~16자리 영문,숫자 조합');
				return false;
			} if (submitId == false) {
				alert('아이디 중복체크를 해주세요');
				return false;
			} else if (submitNickname == false) {
				alert('닉네임 중복체크를 해주세요');
				return false;
			} else if (pwDupChkResult == false) {
				alert('패스워드 일치여부 확인해주세요');
				return false;
			} else if (idRegex == true && passRegex == true && submitId == true 
					&& submitNickname == true && pwDupChkResult == true) {
				$("#form").submit();
			}
		});
		
});

	var idRegex = false;
	var passRegex = false;
	
 // 아이디 정규표현식 확인
 // 4~12자리 영문,숫자 조합
	function checkId(id) {
		const regex = /^[A-Za-z0-9][A-Za-z0-9]{3,11}$/;
		const element = document.querySelector('#checkIdResult');
		
		if (regex.exec(id.value)) {
			idRegex = true;
			element.innerHTML = "적합";
		} else {
			element.innerHTML = "부적합";
		}
	}
 
 // 비밀번호 정규표현식 확인 
 // 8~16자리 영문,숫자 조합
 	function checkPassword(password) {
	 	const lengthRegex = /^[A-Za-z0-9]{8,16}$/;
	 	const englishCaseRegex = /[A-Za-z]/;
	 	const digitRegex = /[0-9]/;
	 	
	 	var element = document.querySelector('#checkPasswordResult');
	 	if (lengthRegex.exec(password.value) && englishCaseRegex.exec(password.value) 
	 			&& digitRegex.exec(password.value)) {
			passRegex = true;
			element.innerHTML = "적합한 Password";
		} else {
			element.innerHTML = "부적합한 Password";
		}
 	}
</script>

</head>
<body>
<!-- 	<a href="./main.all"><img alt="home" src="./images/home.png" style="width:40px; float:right; margin-right:20px; margin-top: 20px;"></a> -->
	<div class="container-contact100">
		<div class="wrap-contact100">
			<form class="contact100-form validate-form" action="JoinPro.me" method="post" id="form">
				<span class="contact100-form-title">
					Welcome!
				</span>
				<div class="wrap-input100 validate-input" data-validate="Name is required">
					<span class="label-input100">ID</span>
					<input class="input100" type="text" name="id" id="id" required="required" placeholder="4~12자리 영문,숫자 조합" onkeyup="checkId(this)">
					<span id="checkIdResult"></span>
					<div class="wrap-contact100-form-btn" style="width:120px;">
						<div class="contact100-form-bgbtn"></div>
						<button class="contact100-form-btn" id="idDup">
							<span>
								Dup.check
							</span>
						</button>
					</div>
				</div>
				
				<div class="wrap-input100 validate-input" data-validate="Name is required">
					<span class="label-input100">Nickname</span>
					<input class="input100" type="text" name="nickname" id="nickname" required="required" placeholder="Enter nickname">
					<div class="wrap-contact100-form-btn" style="width:120px;">
						<div class="contact100-form-bgbtn"></div>
						<button class="contact100-form-btn" id="nicknameDup">
							<span>
								Dup.check
							</span>
						</button>
					</div>
				</div>
				
				<div class="wrap-input100 validate-input">
					<span class="label-input100">Password</span>
					<input class="input100" type="password" name="password" id="password" required="required" placeholder="8~16자리 영문,숫자 조합" onkeyup="checkPassword(this)">
					<span id="checkPasswordResult"></span>
				</div>
				
				<div class="wrap-input100 validate-input">
					<span class="label-input100">Confirm Password </span>
					<input class="input100" type="password" name="passwordCheck" id="pwCheck" required="required" placeholder="8~16자리 영문,숫자 조합">
						<span id="pwDupChkResult_ture">Password 일치</span>
						<span id="pwDupChkResult_false">Password 불일치</span>
				</div>

				<div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">
					<span class="label-input100">Email</span>
					<input class="input100" type="text" name="email" id="email" required="required" placeholder="Enter Email addess">
					<span class="focus-input100"></span>
				</div>

				<div class="container-contact100-form-btn">
					<div class="wrap-contact100-form-btn">
						<div class="contact100-form-bgbtn"></div>
						<button class="contact100-form-btn" type="submit" id="submit">
							<span>
								Submit
							</span>
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>



	<div id="dropDownSelect1"></div>

<!--===============================================================================================-->
	<script src="ContactFrom_v4/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="ContactFrom_v4/vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
	<script src="ContactFrom_v4/vendor/bootstrap/js/popper.js"></script>
	<script src="ContactFrom_v4/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="ContactFrom_v4/vendor/select2/select2.min.js"></script>
	<script>
		$(".selection-2").select2({
			minimumResultsForSearch: 20,
			dropdownParent: $('#dropDownSelect1')
		});
	</script>
<!--===============================================================================================-->
	<script src="ContactFrom_v4/vendor/daterangepicker/moment.min.js"></script>
	<script src="ContactFrom_v4/vendor/daterangepicker/daterangepicker.js"></script>
<!--===============================================================================================-->
	<script src="ContactFrom_v4/vendor/countdowntime/countdowntime.js"></script>
<!--===============================================================================================-->
	<script src="js/main.js"></script>

	<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-23581568-13"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-23581568-13');
</script>

</body>
</html>
