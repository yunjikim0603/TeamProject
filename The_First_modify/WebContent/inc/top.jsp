<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%
// 세션값 가져오기 String id에 저장
String id = (String)session.getAttribute("sId");

%>  

<title>Do you have any Questions?</title>

<c:choose>
    <c:when test="${! empty sessionScope.nickname}">
    <c:if test="${sessionScope.sId eq 'admin'}">
    	<header class="text-right" style="color:#945bf1; margin-right:20px;">
		<i class="fa fa-cube" style="font-size:26px; color: #3ac1bc"></i><b>${sessionScope.nickname}</b> 님이 로그인하셨습니다 | <a href="AdminList.ad">관리자페이지</a> | <a href="Mypage.me">마이페이지</a>	| <a href=" Logout.me">로그아웃</a>
		</header>
    </c:if>
    <c:if test="${! (sessionScope.sId eq 'admin')}">
        <header class="text-right" style="color:#945bf1; margin-right:20px;">
        <c:if test="${sessionScope.level==1 }">
   					<i class="fa fa-pagelines" style="font-size:26px; color: green"></i><b>${sessionScope.nickname}</b> 님이 로그인하셨습니다 | <a href="Mypage.me">마이페이지</a>	| <a href=" Logout.me">로그아웃</a>
		</c:if>
		<c:if test="${sessionScope.level==2 }">
   			<i class="fa fa-pencil" style="font-size:26px; color: brown"></i><b>${sessionScope.nickname}</b>님이 로그인하셨습니다 | <a href="Mypage.me">마이페이지</a>	| <a href=" Logout.me">로그아웃</a>
		</c:if>
		<c:if test="${sessionScope.level==3 }">
   			<i class="fa fa-star" style="font-size:26px; color: gold"></i><b>${sessionScope.nickname}</b>님이 로그인하셨습니다 | <a href="Mypage.me">마이페이지</a>	| <a href=" Logout.me">로그아웃</a>
		</c:if>
	</header>
	</c:if>
    </c:when>
    <c:otherwise>
        <header class="text-right" style="color:#945bf1; margin-right:20px;">
			<a href=" LoginForm.me">로그인</a> | 	<a href=" JoinForm.me">회원가입</a>
		</header>
    </c:otherwise>
</c:choose>
