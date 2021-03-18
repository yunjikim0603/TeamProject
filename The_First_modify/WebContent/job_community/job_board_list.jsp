<%@page import="job_community.vo.PageInfo"%>
<%@page import="job_community.vo.JobBoardBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link href="jobboardcss.css" rel="stylesheet">
</head>
<body>
	<!-- header page -->
	<jsp:include page="../inc/link.jsp" />
	<jsp:include page="../inc/top.jsp" />
	<jsp:include page="../inc/green.jsp" />
	<!-- header page -->


	<div class="gtco-section">
		<div class="gtco-container">
			<div class="text-center">
					<h1>Employment information</h1>
						
			
				<div>
				   <span class="">구인공고를 확인하세요</span>
				</div>
				<br>
			</div>	 
			<div class="jobList">
				 <div style="width: 100%; margin-bottom: 5%;">
				 <table class="table-bordered" >
				 <tr>
				 <c:forEach var="job" items="${jobList }" varStatus="status">
					<td style="width: 25%;">
						<ul>
				   		<li>
				   		<strong class="">${job.name }</strong><br>
				   		<span class="">${job.title }</span><br>
				   		<span class="">
				       		<span>연봉 : ${job.pay }</span><br>
				       		<span>근무지역 : ${job.area }</span><br>
	   				        <span>근로형태 : ${job.worktype }</span><br>
				       		<span>채용마감 : ${job.closedate}</span>
				       		<input type="button" value="상세보기" onclick="location.href='${job.url}'"/>
				   		</span>
				   		</li>
				  		</ul>
				  	</td>
				  <c:if test="${(status.index+1)%4 == 0}">
				  </tr> <tr>
				  </c:if>
				  </c:forEach>
				  </tr>
				  </table>
				 </div>
				</div>
			
			<div class="">
				<div class="card-header">
					<i class="fas fa-table mr-1"></i>Employment information
				</div>
			
				
<!-- 				<div class="card-body"> -->
<!-- 					<div class="table-responsive"> -->
<!-- 						<table class="table table-bordered" id="dataTable" width="100%" -->
<!-- 							cellspacing="0"> -->
<!-- 							<thead> -->
<!-- 								<tr> -->
<!-- 									<th>회사명</th> -->
<!-- 									<th>공고제목</th> -->
<!-- 									<th>근로형태</th> -->
<!-- 									<th>연봉</th> -->
<!-- 									<th>채용시작일</th> -->
<!-- 									<th>채용종료일</th> -->
<!-- 									<th>근무지역</th> -->
<!-- 								</tr> -->
<!-- 							</thead> -->

<!-- 							<tbody> -->
<!-- 								게시물 목록 가져오기 -->
<%-- 								<c:forEach var="job" items="${jobList }"> --%>
<!-- 									<tr> -->
<%-- 										<td align="center">${job.name }</td> --%>
<!-- 										<td align="center"><a -->
<%-- 											href='<c:url value="${job.url}"/>'>${job.title }</a></td> --%>

<%-- 										<td>${job.worktype}</td> --%>
<%-- 										<td>${job.pay }</td> --%>
<%-- 										<td>${job.opendate }</td> --%>
<%-- 										<td>${job.closedate }</td> --%>
<%-- 										<td>${job.area }</td> --%>
<!-- 									</tr> -->
<%-- 								</c:forEach> --%>
<!-- 						</table> -->
<!-- 					</div> -->
<!-- 				</div> -->
				
				<div class="card-body">
					<div class="table-responsive">
						<table class="table table-bordered" id="dataTable" width="100%"
							cellspacing="0">
							<thead>
								<tr>
									<th>글번호</th>
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
										<td align="center"><a
											href='<c:url value="JobBoardDetail.job?num=${article.num}"/>'>${article.subject }</a></td>

										<td>${article.nickname}</td>
										<c:if test="${article.date==today}">
											<td>${article.time }</td>
										</c:if>
										<c:if test="${article.date<today }">
											<td id="date">${article.date }</td>
										</c:if>
										<td>${article.readcount }</td>
									</tr>
								</c:forEach>
						</table>
					</div>
				</div>
				
				<div class="text-right">
					<%--                             <button type="button" class="bs_btn btn-info" onclick="location.href='AcademyWriteForm.ac?nickname=${sessionScope.nickname }'">글쓰기</button> --%>
					<button type="button" class="bs_btn btn-info"
						onclick="location.href='JobBoardWriteForm.job'">글쓰기</button>
				</div>
			</div>
		</div>
	</div>



	<!-- END .gtco-services -->

	<!-- footer page -->
	<jsp:include page="../inc/bottom.jsp" />
	<!-- footer page -->

	<div class="gototop js-top">
		<a href="#" class="js-gotop"><i class="icon-arrow-up"></i></a>

	</div>



</body>
</html>