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
<title>회원목록</title>

<style type="text/css">
      .table { 
       border-collapse: collapse;
      border-top: 3px solid #168;
  		 }   
    .table th {
      color: #168 !important;
      background: #00C1C4;
      text-align: center;
    }
    .table th, .table td {
      padding: 10px;
      border: 1px solid #ddd;
    }
    .table th:first-child, .table td:first-child {
      border-left: 0;
    }
    .table th:last-child, .table td:last-child {
      border-right: 0;
    }
    .table tr td:first-child{
      text-align: center;
    }
    .table caption{caption-side: bottom; display: none;}
        
    </style>

<script type="text/javascript">
	function deleteMember(id) {
		if(confirm("선택한 회원을 강퇴하시겠습니까?") == true){
			location.href="MemberDeletePro.ad?id="+id;
		} else{
			return;
		}
	}
</script>
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
						<h2>회원목록</h2>
<!-- 						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus placerat enim et urna sagittis, rhoncus euismod erat tincidunt. Donec tincidunt volutpat erat.</p> -->
					</div>
				</div>
				 <div class="">
				 
					<section id="listForm">
	<c:if test="${memberList != null }"> 
<!-- 		<h2>회원목록</h2> -->
<!-- 		<form method="post" id="listForm"> -->
			<table class="table">
<%-- 			<caption>회원목록</caption> --%>
				<tr class="table-info">
					<th>아이디</th>
					<th>비밀번호</th>
					<th>닉네임</th>
					<th>이메일</th>
					<th>CP</th>
					<th>LP</th>
					<th>Level</th>
					<th>이메일 인증</th>
					<th>가입날짜</th>
					<th>삭제</th>
				</tr>
				
				<c:forEach var="member" items="${memberList}" varStatus="status">

				<tr class="tr_list">
					<td>
						${member.id }
					</td>
					<td>
						${member.password }
					</td>
					<td>
						${member.nickname }
					</td>
					<td>
						${member.email }
					</td>
					<td>
						${member.cp }
					</td>
					<td>
						${member.lp }
					</td>
					<td>
						${member.level }
					</td>
					<td>
						<c:set var="check" value="${member.emailChecked}"/>
						<c:choose>
							<c:when test="${check eq true}">
								Y
							</c:when>
							<c:when test="${check eq false}">
								N
							</c:when>
						</c:choose>
					</td>	
					<td>
						${member.date}
					</td>
					
					<c:set var="id" value="${member.id }"/>
							<c:if test="${id ne 'admin'}">
								<td onclick="deleteMember('${member.id}')" style="cursor: pointer; font-weight: bold;">강퇴</td>
							</c:if>
							<c:if test="${id eq 'admin'}">
								<td></td>
							</c:if>
							
							
				</tr>
				</c:forEach>
			</table>
			</c:if>	
<!-- 		</form> -->


	<c:if test="${memberList = null }">
		<section class="div_empty">회원 정보가 없습니다. </section>
	</c:if>	
	
	</section>
                            
                           
                            </div>
                        </div>
                    </div>



		<!-- footer page -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- footer page -->
		
	</body>



</body>
</html>