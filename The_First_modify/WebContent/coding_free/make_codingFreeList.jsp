<%@page import="java.util.Date"%>
<%@page import="coding_free.vo.PageInfo"%>
<%@page import="coding_free.vo.CodingFreeBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:if test="${sessionScope.nickname==null }">
<c:choose>
	<c:when test="${sessionScope.sId != null }">
    <script type="text/javascript">
		alert("이메일 인증 받으세요.");
		location.href="emailSendConfirm.jsp"
	</script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
		alert("로그인 해주세요");
		location.href="LoginForm.me"
		</script>
	</c:otherwise>
</c:choose>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Do you have any Questions?</title>

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
						<h2>Free Coding Q&A</h2>
						
					</div>
				</div>
				 <div class="">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>Free Coding Q&A</div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th >글번호</th>
                                                <th>글제목</th>
                                                <th>작성자</th>
                                                <th>작성일</th>
                                                <th>조회수</th>
                                            </tr>
                                        </thead>

                                        <tbody>
<!-- 게시물 목록 가져오기 -->
      <c:forEach var="article" items="${articleList }">
      		<tr>
			<td align="center">${article.num }</td>
			   			<td align="center" ><a href='<c:url value="CodingFreeDetail.cf?post_num=${article.num}"/>'>${article.subject }</a></td>
			    
					<td >${article.nickname}</td>
					<c:if test="${article.date==today}">
							<td>${article.time}</td>
					</c:if>
					<c:if test="${article.date<today}">
							<td id="date">${article.date}</td>
					</c:if>
					<td >${article.readcount}</td>
				</tr>
    	</c:forEach>
	 </table>
                                </div>
                            </div>
                            <div class="text-right">
                            <c:if test="${sessionScope.level>=2 }">
								    <button type="button" class="bs_btn btn-info" onclick="location.href='CodingFreeWriteForm.cf'">글쓰기</button>
								</c:if>
                            	
                            </div>
                            </div>
                        </div>
                    </div>


		
		<!-- END .gtco-services -->

		<!-- footer page -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- footer page -->

	<div class="gototop js-top">
		<a href="#" class="js-gotop"><i class="icon-arrow-up"></i></a>
		
	</div>

	</body>
</html>