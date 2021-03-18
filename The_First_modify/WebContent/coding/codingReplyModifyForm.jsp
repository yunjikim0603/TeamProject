<%@page import="coding.vo.PageInfo"%>
<%@page import="java.util.Date"%>
<%@page import="coding.vo.Coding_refBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="coding.vo.CodingBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${sessionScope.nickname==null }">
    <script type="text/javascript">
		alert("로그인 해주세요");
		location.href="LoginForm.me"
	</script>
</c:if>
<%	
	// 전달받은 request 객체에서 데이터 가져오기
	CodingBean article = (CodingBean)request.getAttribute("article");
// 	ArrayList<CmmntBean> cmmntList = (ArrayList<CmmntBean>)request.getAttribute("cmmntList");
	Date today = (Date)request.getAttribute("today");
	int post_num = (int)request.getAttribute("post_num");
	// PageInfo 객체로부터 페이징 정보 가져오기
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="js/jquery-3.4.1.js"> </script>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

</head>
<body>
		<!-- header page -->
		<jsp:include page="../inc/link.jsp"/>
		<jsp:include page="../inc/top.jsp"/>
		<jsp:include page="../inc/green.jsp"/>
		<!-- header page -->

<section class="gtco-section">

<div class="gtco-container">

	<div class="w3-article">
	 <div class="left-box"> 
			<div class="w3-border w3-padding">
				<i class="fa fa-user"></i> <a href="#">${article.nickname }</a>
				<i class='fas fa-coins' style="float: right!important;">${article.CP }</i>
				<br>
				<i class="fa fa-calendar">
					<c:if test="${article.date==today}">
							<td>${article.time}</td>
					</c:if>
					<c:if test="${article.date<today}">
							<td id="date">${article.date}</td>
					</c:if>
				</i>
				<div class="w3-right">
					<!-- 조회수 --><span><i class="fa fa-eye"></i>${article.readcount }</span>&nbsp;
					<!-- 댓글수 --><i class="fa fa-commenting-o"  onclick="javascript:openCmmnt();"></i>&nbsp;<span class="reply_count"></span>
				</div>
			</div>
			<div class="w3-border w3-padding">
				No.${article.num }&nbsp;&nbsp;&nbsp; <span class="w3-center w3-xlarge w3-text-blue">${article.subject }</span>
			</div>
<%-- 			<article class="w3-border w3-large w3-padding">${article.content }</article> --%>
				<c:if test="${article.file != null}">
							<article class="w3-border w3-large w3-padding article_content">
					<img src="./codingUpload/${article.file }" width=800px >	<br><br><br>
					${article.content } <br><br>
				</article>
					</c:if>
			<div class="w3-border w3-padding">첨부파일: <a href="CodingFileDown.code?file_name=${article.file }" target="blank">${article.file }</a></div><br>
<!-- 			<div class="check" > 체크</div> -->
			
			<c:if test="${article.nickname == sessionScope.nickname }">
			<div>
			    <button type="button" class="btn btn-outline-secondary" onclick="modify_article(${article.num})">글수정</button>&nbsp;&nbsp;
			     <button type="button" class="btn btn-outline-secondary" onclick="delete_article(${article.num})">글삭제</button>
			</div>
			</c:if>
		</div>
		

<div class="w3-article" >
	 <div class="right-box " > 
				<form action="CodingReplyModifyPro.code?post_num=${post_num }&ref_num=${ref_num }&isSelected=${isSelected }" method="post" enctype="multipart/form-data" name="boardform" class="code_rwf">
					<div class="form-group">					
				    <label class="control-label" for="readOnlyInput">글쓴이</label>
				    <input class="rform-control" id="nickname" name="nickname" type="text" value="${sessionScope.nickname }" readonly=""></div>
						
						<div class="form-group">
							  <label class="control-label"" for="subject">제목</label>
							  <input class="rform-control form-control-lg" type="text" placeholder="제목" id="subject" name="subject" value="${ref.subject }">
						</div>

							<div class="form-group mg10 ">
						<label class="control-label" for="file">파일첨부</label> 
						<input type="file" class="form-control-file text-right" name="file">
					</div>
					<c:if test="${ref.file != null}">
						<article class="w3-border w3-large w3-padding article_content">
							<img src="./codingUpload/${ref.file }" width=800px> <br><br><br>
						</article>
					</c:if>
					<div class="form-group mg3">
						<label class="control-label" for="content">내용</label>
						<textarea class="form-contrdiv" name="content" id="content" cols="86" rows="12" required="required">
							${ref.content }
						</textarea>
					</div>
								<div class="wsr" >
									<input type="reset"  class="btn btn-outline-danger btn-lg" value="다시쓰기" />
									<input type="submit" class="btn btn-danger btn-lg" value="등록">&nbsp;&nbsp;
									
								</div>
								</form>
				  </div>
                            </div>
                           
</div>


	
	</div>
	
	<div>

	 
<!-- 	 <a href="javascript:getReplyListCount()">답글보기</a> -->
<!-- 	 	<input type=button value="답글보기" onclick="getReply()"> -->
	</div>
		
</div>

<section id="replyPage"> </section>	
</div>
	</div>
	
	</section>
	

	
	
	
	<!-- header page -->
		<jsp:include page="../inc/bottom.jsp"/>
	<!-- header page -->
	
	
</body>
</html>