<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>textView</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
	<style>
	
	
		h1{
			font-family: 'Noto Sans KR', sans-serif;
			text-align: center;
			font-size: 25px;
			font-style: inherit;
		}
	
		table{/* border:1px solid red; */
		width: 800px;
 			margin-left: auto; margin-right: auto;
 			 margin-top: 30px; margin-bottom: 50px;
 			font-size: 18px;
 			font-family: 'Noto Sans KR', sans-serif;
		}
		
		th, td{
		border : 1px solid #E2E2E2;
		text-align: center;
		}
		
		.th-total{
			background-color: #6c7ae0;
			height: 50px;
		}
		
		.th-ltop{
			width: 200px;
			background-color: #6c7ae0;
			height: 50px;
			border-top-left-radius: 15px;
		}
		
		.td-rtop{
		
			border-top-right-radius: 15px;
		}
		
		.td-rb{
			border-bottom-right-radius: 15px;
		}
		
		.th-lb{
			border-bottom-left-radius: 15px;
			height: 400px;
			background-color: #6c7ae0;
			
		}
		
		.td-1 {
			width: 600px;
		}
			
		
		.table-edit {
			border-radius: 10px;
		}
		
		#commandList {/* border:1px solid red; */
		width: 800px;
		margin-left: auto; margin-right: auto;
		 margin-top: 30px; margin-bottom: 50px;
		}
		.btn1 {
			width: 90px;
			height: 40px;
			background-color: #E2E2E2;
			border-radius: 10px;
			font-size: 15px;
			font-family: 'Noto Sans KR', sans-serif;
			float: right;
 			}
			
		.btn2 {
			width: 90px;
			height: 40px;
			background-color: #E2E2E2;
			border-radius: 10px;
			font-size: 15px;
			font-family: 'Noto Sans KR', sans-serif;
			float: right;
			margin-right: 5px;
			}
	</style>
</head>
<body>
	<section>
		<h1>쪽지 내용 상세보기</h1>
		<table class="table-edit">
			<tr>
				<th class="th-ltop">보낸사람</th>
				<td class="td-rtop">${text.sender}</td>
			</tr>
			<tr>
				<th class="th-total">보낸날짜</th>
				<td class="td-1">${text.send_date}</td>
			</tr>
			<tr>
				<th class="th-total">제목</th>
				<td>${text.subject}</td>
			</tr>
			<tr>
			 	<th class="th-lb">내용</th>
				<td class="td-rb">${text.contents}</td>
			</tr>
		</table>
	</section>

	<section id="commandList">
		<a href="TextDelete.te?idx=${text.idx }&page=${page}&receiver=${sessionScope.nickname }">
			<input class="btn1" type="button" value="삭제"></a> 
		<a href="TextList.te?receiver=${sessionScope.nickname }"> 
		<input class="btn2" type="button" value="목록"></a>
	</section>
</body>
</html>