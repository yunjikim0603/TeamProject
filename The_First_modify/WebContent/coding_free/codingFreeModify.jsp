<%@page import="coding.vo.PageInfo"%>
<%@page import="coding.vo.CodingBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
.mg10{
	margin-top: 10% !important;
}
.mg5{
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
			<form action="CodingFreeModifyPro.cf?num=${article.num }" method="post" enctype="multipart/form-data" name="boardform" class="code_wf">
					<div class="form-group">					
				    <label class="control-label" for="readOnlyInput">글쓴이</label>
				    <input class="form-control" id="nickname" name="nickname" type="text" value="${sessionScope.nickname }" readonly=""></div>
						
						<div class="form-group">
							  <label class="control-label" for="subject">제목</label>
							  <input class="form-control form-control-lg" type="text" placeholder="제목" id="subject" name="subject" value="${article.subject }">
						</div>
						
<!-- 							  <span class="w3-left"><label class="control-label" for="isPublic">금액</label></span> -->
							 
<!-- 									<div class="input-group-append"> -->
<!-- 								        <span class="input-group-text" id="">Upload</span> -->
<!-- 								      </div> -->
							<div class="form-group mg10 ">
								<label class="control-label" for="file">파일첨부</label>
							     <input type="file" class="form-control-file text-right" name="file" >
							</div>
							<c:if test="${article.file != null}">
									<article class="w3-border w3-large w3-padding article_content">
							<img src="./coding_free/images/${article.file }" width=800px >	<br><br><br>
								</article>
								</c:if>
					
														
							<div class="form-group mg3"><label class="control-label" for="content">내용</label>
		  					<textarea class="form-contrdiv" name="content" id="content" cols="100" rows="12" required>${article.content }</textarea></div>
				
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

	</body>
</html>

