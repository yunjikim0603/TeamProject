<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>textList</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="icon" type="image/png" href="table/images/icons/favicon.ico"/>
	<link rel="stylesheet" type="text/css" href="table/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="table/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="table/vendor/animate/animate.css">
	<link rel="stylesheet" type="text/css" href="table/vendor/select2/select2.min.css">
	<link rel="stylesheet" type="text/css" href="table/vendor/perfect-scrollbar/perfect-scrollbar.css">
	<link rel="stylesheet" type="text/css" href="table/css/util.css">
	<link rel="stylesheet" type="text/css" href="table/css/main.css">
	<style>
		.stBtn{
			width: 150px;
			height: 50px;
			background-color: #E2E2E2;
			border-radius: 10px;
			font-size: 25px;
			position: fixed;
			right: 50%;
			bottom: 2%;
			margin-top: 10px;
		}
		
		.pageList{
			position: fixed;
			right: 50%;
			bottom: 8%;
			font-size: 18px;
			padding-bottom: 5px
		}
	
	</style>
</head>
<body>
	<div class="">
		<div class="card-header">
			<i class="fas fa-table mr-1"></i> 쪽지함
		</div>
	</div>
		
		<div class="limiter">
			<div class="wrap-table100">
				<div class="table">
					<div class="row header">
						<div class="cell" style="width: 30%">
							Sender
						</div>
						<div class="cell" style="width: 50%">
							Subject
						</div>
						<div class="cell" style="width: 20%">
							Send Date
						</div>
					</div>
						<c:if test="${textList ne null }">
							<c:forEach var="tl" items="${textList }">
								<c:if test="${tl.isChecked eq false}">
									<div class="row">
										<div class="cell" data-title="Sender">
											${tl.sender }
										</div>
										
										<div class="cell" data-title="Subject">
											<a href="TextView.te?idx=${tl.idx }&page=${pageInfo.page}">
													${tl.subject } </a>
										</div>
										
										<div class="cell" data-title="Sender">
											${tl.send_date }
										</div>
									</div>
									
								</c:if>
								
								<c:if test="${tl.isChecked eq true}">
									<div class="row" style="background-color: #E2E2E2" >
										<div class="cell" data-title="Sender">
											${tl.sender }
										</div>
										
										<div class="cell" data-title="Subject">
											<a href="TextView.te?idx=${tl.idx }&page=${pageInfo.page}">
													${tl.subject } </a>
										</div>
										
										<div class="cell" data-title="Sender">
											${tl.send_date }
										</div>
									</div>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${textList eq null }">
							<div class="row" id="emptyArea">
								<div class="cell">
								등록된 글이 없습니다.
<!-- 							<section id="emptyArea">등록된 글이 없습니다.</section> -->
								</div>
							</div>
						</c:if>
					</div>
				</div>
				
			</div>
			<div class="pageList">
			<c:if test="${textList ne null }">
				<c:choose>
					<c:when test="${pageInfo.page le 1}">
						[이전]&nbsp;
				</c:when>
					<c:otherwise>
						<a href="TextList.te?page=${pageInfo.page - 1}&receiver=${sessionScope.nickname}">[이전]</a> &nbsp;
				</c:otherwise>
				</c:choose>

				<c:forEach var="pi" begin="${pageInfo.startPage }"
					end="${pageInfo.endPage }" step="1">
					<c:choose>
						<c:when test="${pi eq pageInfo.page }">
								[${pi}]
					</c:when>
						<c:otherwise>
									<a href="TextList.te?page=${pi}&receiver=${sessionScope.nickname}">[${pi }] </a>&nbsp;
					</c:otherwise>
					</c:choose>
				</c:forEach>

				<c:choose>
					<c:when test="${pageInfo.page ge pageInfo.maxPage}">
							&nbsp;[다음]
					</c:when>
			
					<c:otherwise>
						<a href="TextList.te?page=${pageInfo.page + 1}&receiver=${sessionScope.nickname}">
						&nbsp;[다음] </a>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
		
		<input class="stBtn" type="button" value="Send Text" onclick="location.href='TextWriteForm.te'"/>
				
</body>
</html>