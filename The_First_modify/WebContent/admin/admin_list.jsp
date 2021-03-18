<%@page import="java.io.PrintWriter"%>
<%@page import="member.dao.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<c:if test="${sessionScope.nickname==null }">
    <script type="text/javascript">
		alert("로그인 해주세요");
		location.href="LoginForm.me"
	</script>
</c:if>

<c:if test="${!(sessionScope.sId eq 'admin') }">
    <script type="text/javascript">
		alert("접근 권한이 없습니다.");
		history.back();
	</script>
</c:if>	

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">

	div.adminList {
		 width: 100%;
	   	 height: 300px;
	   	 text-align: center;
	   	 margin: 0 auto;
	   	 
	}
	
	div.adminList_Inner {
		float: left;
	    margin: 100px;
	    
	}
	
	#Contents {
    width: 1020px;
    height: 100%;
    margin: 0 auto;
    

</style>
<title>관리자페이지</title>
</head>

<body>

	<!-- header page -->
		<jsp:include page="../inc/link.jsp"/>	
		<jsp:include page="../inc/top.jsp"/>
		<jsp:include page="../inc/green.jsp"/>
	<!-- header page -->

		<div class="gtco-section">
			<div class="gtco-container">
				<div class="row">
					<div class="col-md-8 col-md-offset-2 gtco-heading text-center">
						<h2>관리자 메뉴</h2>
					</div>
				</div>
				
			<div id="Contents">
				 <div class="adminList">
				 
				 	<div class="adminList_Inner">
					<a href="MemberList.ad"><img src="./images/memberList.png" alt="MemberList" ><h2>회원목록 </h2></a>
					</div>
					
					<div class="adminList_Inner">
					<a href="ProductList.ad"><img src="./images/productList.png" alt="ProductList"><h2>상품목록 및 추가</h2></a>
					</div>
					
				</div>
			</div>
                            
                           
                  </div>
                </div>
            



		<!-- footer page -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- footer page -->
		
	</body>


</html>