<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
function textCheck() {
	$.ajax({
		url: "/The_First/TextCheck.te",
       		type: "POST",
       	    data: {
			receiver : '${sessionScope.nickname}'
       	 	},
        	success: function (count) {
				if(count > 0) {
				window.open("/The_First/member/textCheck.jsp");
				} 
       		 }
		});
}
// textCheck();
// if(${sessionScope.nickname} != null) {
// 	setInterval(textCheck(),60000);
// }

if(${sessionScope.nickname } != null) {
	StartTextCheck();
}

// 시작
function StartTextCheck() {
   	var textId = setInterval(textCheck, 60000);
}

// 중지
function StopTextCheck() {
	clearInterval(textId);
}

// alert('${sessionScope.nickname}');
</script>
</head>
<body>
	<h1>읽지않은 쪽지가 있습니다.</h1>
	<a href="/The_First/TextList.te?receiver=${sessionScope.nickname}" onclick="StopTextCheck();"> 
	확인하러 가기 </a>
</body>
</html>