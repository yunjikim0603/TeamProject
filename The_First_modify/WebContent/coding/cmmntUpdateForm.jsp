<%@page import="coding.vo.CmmntBean"%>
<%@page import="coding.vo.Coding_refBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="coding.vo.CodingBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//
	String id = null;
	// 로그인이 되지 않은 상태일 경우 로그인 페이지로 강제 이동 처리
	/*
	if(session.getAttribute("id") == null) {
		out.println("<script>");
	    out.println("alert('로그인이 필요한 서비스입니다!')");
	    out.println("location.href='LoginForm.me'");
	    out.println("</script>");
	} else { // 로그인 된 상태일 경우 세션 ID 가져오기
		id = (String)session.getAttribute("id");
	}
	*/
	// 전달받은 request 객체에서 데이터 가져오기
	CodingBean article = (CodingBean)request.getAttribute("article");
	ArrayList<Coding_refBean> article_refList = (ArrayList<Coding_refBean>)request.getAttribute("article_refList");
	ArrayList<CmmntBean> cmmntList = (ArrayList<CmmntBean>)request.getAttribute("cmmntList");
	String nowPage = (String)request.getAttribute("page");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<style type="text/css">
	#articleForm {
		width: 500px;
		height: 500px;
		border: 1px solid red;
		margin: auto;
	}
	
	#article_refForm {
		width: 500px;
		height: 500px;
		border: 1px solid blue;
		margin: auto;
	}
	
	#cmmntForm {
		width: 500px;
		height: 500px;
		border: 1px solid yellow;
		margin: auto;
	}
	
	h2 {
		text-align: center;
	}
	
	#basicInfoArea {
		height: 40px;
		text-align: center;
	}
	
	#articleContentArea {
		background: orange;
		margin-top: 20px;
		height: 350px;
		text-align: center;
		overflow: auto; /* 지정 영역 크기 이상일 경우 자동으로 스크롤바 생성*/
	}
	
	#article_refContentArea {
		background: skyblue;
		margin-top: 20px;
		height: 350px;
		text-align: center;
		overflow: auto; /* 지정 영역 크기 이상일 경우 자동으로 스크롤바 생성*/
	}
	
	#commandList {
		matgin: auto;
		width: 500;
		text-align: center;
	}
	header {
		text-align: right;
	}
</style>
</head>
<body>
	<header>
		<!-- 세션ID(sId) 가 없을 경우 로그인(LoginForm.me), 회원가입(JoinForm.me) 링크 표시 -->
		<!-- 세션ID(sId) 가 있을 경우 회원ID, 로그아웃(Logout.me)링크 표시 -->
		<%if(id == null) {%>
			<a href="LoginForm.me">로그인</a> | <a href="JoinForm.me">회원가입</a>
		<%} else { %>
			<%=id %>님 | <a href="Logout.me">로그아웃</a>
		<%} %>
	</header>
	
	
	<!-- 댓글 보여주기 -->
	<div class="cmmntForm"> <!-- 코멘트 부분 div 시작 -->
	<%if(cmmntList != null) {%> 
		<%for(int i=0; i<cmmntList.size(); i++){ %>		
			<table>
				<tr>
					<td style="width: 20%" id="nickname"><%=cmmntList.get(i).getNickname() %></td>
					<td style="width: 20%" id="comment" > <%=cmmntList.get(i).getComment()%></td>
					<td style="width: 20%" id="date"><%=cmmntList.get(i).getDate()%></td>  <!-- 아니면 날짜 출력 -->
				</tr>
			</table>
		<!-- 댓글쓰기 -->
		<br><br><br><br><br>

	
		<form action="CmmntUpdatePro.code?post_num=<%=cmmntList.get(i).getPost_num()%>&comment_num=<%=cmmntList.get(i).getComment_num()%>" method="post">
			<table id="notice">
			<tr><td><input type="text" size="4" name=id value="<%=cmmntList.get(i).getNickname()%>"  readonly ></td>
			<td><textarea name=comment cols="50"><%=cmmntList.get(i).getComment()%></textarea></td>
			<td><input type="submit" value="댓글수정"  class="btn" ></td></tr>
			</table>
		</form>
		</div>  <!-- 코멘트 부분 div 끝-->
		<br><br>
<% }
}
%>

	

	
</body>
</html>

