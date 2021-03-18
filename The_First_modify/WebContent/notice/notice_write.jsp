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
<title>공지사항</title>
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

<!-- 			<div class="gtco-container"> -->
			<div class="center-box row" style="padding: 10px 20% 30px 20% !important;">
							<!-- 게시물 목록 가져오기 -->
			<form action="NoticeWritePro.no" method="post" enctype="multipart/form-data" name="boardform" class="code_wf">
					<div class="form-group">					
				    <label class="control-label" for="readOnlyInput">글쓴이</label>
				    <input class="form-control" id="nickname" name="nickname" type="text" value="${sessionScope.nickname }" readonly="readonly"></div>
						
						<div class="form-group">
							  <label class="control-label" for="subject">제목</label>
							  <input class="form-control form-control-lg" type="text" placeholder="제목"  required="required" id="subject" name="subject">
						</div>

							<div class="form-group mg10 ">
								<label class="control-label" for="file">파일첨부</label>
							     <input type="file" class="form-control-file text-right" name="file" >
							</div>
														
							<div class="form-group mg3"><label class="control-label" for="content">내용</label>
		  					<textarea class="form-contrdiv" name="content" id="content" cols="100" rows="12" required></textarea></div>
				
								<div class="wsr" >
									<a href="NoticeList.no"><input type="button" class="btn btn-outline-secondary btn-lg" value="목록"></a>
									<input type="reset"  class="btn btn-outline-secondary btn-lg" value="다시쓰기" />
									<input type="submit" class="btn btn-secondary btn-lg" value="등록">&nbsp;&nbsp;<br>
								</div>
								</form>
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















