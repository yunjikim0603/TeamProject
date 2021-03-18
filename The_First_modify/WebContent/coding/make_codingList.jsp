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

<%
	// Action 클래스에서 request 객체의 setAttibute() 메서드로 저장되어 전달된 객체 가져오기(Object 타입이므로 형변환 필요)
	ArrayList<CodingBean> articleList = (ArrayList<CodingBean>)request.getAttribute("articleList");
// 	PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
// 	String nowPage = request.getParameter("page");
	Date today = (Date)request.getAttribute("today");

	// PageInfo 객체로부터 페이징 정보 가져오기
// 	int listCount = pageInfo.getListCount();
// 	int nowPage = pageInfo.getPage(); // page 디렉티브의 이름과 중복되므로 nowPage 라는 변수명으로 사용
// 	int startPage = pageInfo.getStartPage();
// 	int endPage = pageInfo.getEndPage();
// 	int maxPage = pageInfo.getMaxPage();

%> 
<head>
<script type="text/javascript">
function private_article() {
	alert("작성자만 읽을 수 있는 글입니다.");
}
</script>
        <link href="css/styles_table.css" rel="stylesheet" />
        <link href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css" rel="stylesheet" crossorigin="anonymous" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/js/all.min.js" crossorigin="anonymous"></script>

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
						<h2>Charged Coding Q&A</h2>
						
					</div>
				</div>
				 <div class="">
                            <div class="card-header"><i class="fas fa-table mr-1"></i>Charged Coding Q&A</div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th >글번호</th>
                                                 <th>CP</th>
                                                <th>글제목</th>
                                                <th>작성자</th>
                                                <th>작성일</th>
                                                <th>조회수</th>
                                            </tr>
                                        </thead>

                                        <tbody>
<!-- 게시물 목록 가져오기 -->
      <c:forEach var="article" items="${articleList }">
      		<c:if test="${article.CP!=0 }">
				<tr style="background-color: #A1E2DF">
			</c:if>
			<td align="center">${article.num }</td>
			<c:choose>
			<c:when test="${article.CP==0 }">
				<td align="center">채택완료</td>
			</c:when>
			<c:otherwise>
					<td align="center">${article.CP }</td>
			</c:otherwise>
			</c:choose>
					<c:choose>
			    <c:when test="${article.isPublic eq 0 && !(article.nickname eq sessionScope.nickname)}">
			        	<td align="center"  onclick="private_article()"><i class="fas fa-lock" style="color:red;"></i>${article.subject }</td>
			    </c:when>
			    <c:when test="${article.isPublic eq 0 && article.nickname eq sessionScope.nickname  }">
			   			<td align="center" ><i class="fas fa-lock" style="color:red;"></i><a href='<c:url value="CodingDetail.code?post_num=${article.num}"/>'>${article.subject }</a></td>
			    </c:when>
			    <c:otherwise>
			      <td>
						<a href='<c:url value="CodingDetail.code?post_num=${article.num}"/>'>${article.subject }</a>
					</td>
			    </c:otherwise>
			</c:choose>
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
								    <button type="button" class="bs_btn btn-info" onclick="location.href='CodingWriteForm.code?nickname=${sessionScope.nickname }'">글쓰기</button>
								</c:if>
                           	
                            </div>
                            </div>
                        </div>
                    </div>


		
		<!-- END .gtco-services -->

		<!-- footer page -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- footer page -->
	</body>
</html>

