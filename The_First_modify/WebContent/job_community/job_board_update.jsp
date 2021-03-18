<%@page import="job_community.vo.JobBoardBean"%>
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
<style type="text/css">
.mg10 {
	margin-top: 10% !important;
}

.mg5 {
	margin-top: 3% !important;
}
</style>
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
						<h2> </h2>
					</div>
				</div>
		</div>
		

			<div class="gtco-container">
			<div class="center-box row">
							<!-- 게시물 목록 가져오기 -->
			<form action="JobBoardModifyPro.job?num=${article.num }" method="post" enctype="multipart/form-data" class="code_wf">
					<div class="form-group">					
				    <label class="control-label" for="readOnlyInput">글쓴이</label>
				    <input class="form-control" id="nickname" name="nickname" type="text" value="${sessionScope.nickname }" readonly></div>
						
						<div class="form-group">
							  <label class="control-label" for="subject">제목</label>
							  <input class="form-control form-control-lg" type="text" placeholder="제목" id="subject" name="subject" value="${article.subject }">
						</div>
						
						<div class="form-group mg10 ">
								<label class="control-label" for="file">파일첨부</label>
							     <input type="file" class="form-control-file text-right" name="file" >
							</div>
							<c:if test="${article.file != null}">
									<article class="w3-border w3-large w3-padding article_content">
									<img src="./job_community/images/${article.file }" width=800px >	<br><br><br>
								</article>
								</c:if>

							<div class="form-group mg3"><label class="control-label" for="content">내용</label>
		  					<textarea class="form-contrdiv" name="content"  id="content" cols="100" rows="12" required>${article.content }</textarea></div>
				
								<div class="wsr" >
									<input type="reset"  class="btn btn-outline-secondary btn-lg" value="다시쓰기" />
									<input type="submit" class="btn btn-secondary btn-lg" value="등록">&nbsp;&nbsp;
									
								</div>
								</form>
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
		
		
	<!-- jQuery -->
	<script src="js/jquery.min.js"></script>
	<!-- jQuery Easing -->
	<script src="js/jquery.easing.1.3.js"></script>
	<!-- Bootstrap -->
	<script src="js/bootstrap.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <script src="js/scripts.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
    <script src="js/chart-area-demo.js"></script>
    <script src="js/chart-bar-demo.js"></script>
    <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js" crossorigin="anonymous"></script>
    <script src="js/datatables-demo.js"></script>
		
	<!-- Waypoints -->
	<script src="js/jquery.waypoints.min.js"></script>
	<!-- Carousel -->
	<script src="js/owl.carousel.min.js"></script>
	<!-- Magnific Popup -->
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/magnific-popup-options.js"></script>
	<!-- Main -->
	<script src="js/main.js"></script>
	
	</body>
</html>