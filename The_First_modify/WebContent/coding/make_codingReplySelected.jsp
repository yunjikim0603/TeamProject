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
<c:if test="${sessionScope.level==1 }">
    <script type="text/javascript">
		alert("LEVEL 2부터 읽으실 수 있습니다.");
		history.back();
	</script>
</c:if>
<%	
CodingBean article = (CodingBean)request.getAttribute("article");
ArrayList<Coding_refBean> article_refList = (ArrayList<Coding_refBean>)request.getAttribute("article_refList");
//	ArrayList<CmmntBean> cmmntList = (ArrayList<CmmntBean>)request.getAttribute("cmmntList");
String nowPage = (String)request.getAttribute("page");
Date today = (Date)request.getAttribute("today");

// PageInfo 객체로부터 페이징 정보 가져오기
PageInfo reply_pageInfo = (PageInfo)request.getAttribute("replyPageInfo");

int isSelected =0;
if (request.getAttribute("isSelected")!=null){
	isSelected = (int)request.getAttribute("isSelected");
}
	
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Do you have any Questions?</title>

<script src="js/jquery-3.4.1.js"> </script>
<script type="text/javascript">
function openCmmnt(){  
    window.open("CmmntList.code?post_num="+<%=article.getNum()%>, "Commnets", "resizable=no, width=730px, height=550px");
    
}

$(document).ready(function() {
	getReply(1);
});

function getReply(i){
	$.ajax({		
		url: "CodingReplyDetail.code?s_nick=${sessionScope.nickname }", // 요청 url
        type: "POST", // post 방식
        data: {
        	post_num : <%=article.getNum()%> // board_no의 값을 넘겨줌
//         	reply_page : i
        },
        success : function (json){
        	json = json.replace(/\n/gi,"\\r\\n"); // 개행문자 대체
        	$("#replyList").text(""); // 댓글리스트 영역 초기화
        	var obj = JSON.parse(json); // service 클래스로 부터 전달된 문자열 파싱
        	var replyList =obj.replyList; // replyList는 전달된 json의 키값을 의미
        	var output = ""; // 댓글 목록을 누적하여 보여주기 위한 변수
        	var CP ="";
        	var ref_num = "";
        	var nickname ="";
//         	alert(json);
//         	var reply = replyList[0][0];
//         	alert(reply.nickname);
//         	output += "<i class='fa fa-user'></i>&nbsp;&nbsp;" + reply.nickname;
//         		alert("i값은 " + i);
        		paging(i);
        		i=i-1;
        		
//         	for (var i = 0; i < replyList.length; i++) { // 반복문을 통해 output에 누적
                   output += "<div class='w3-border w3-padding'>";

	                 for (var j = 0; j < replyList[i].length; j++) {
	                    var reply = replyList[i][j];
	                    if(j === 0){
	                    	nickname =reply.nickname;
	                    	output += "<i class='fa fa-user'></i> <a href='#'> " + reply.nickname +"</a><br>";
//	                    output += "<div class='w3-right'><span>채택하기</span> </div><br>";
	                    }else  if(j === 1){
	     					output += "<i class='fa fa-calendar'></i>" + reply.reply_date+"</div>";
	                    }else  if(j === 2){
	                    	CP = reply.ref_CP;
	                    }else  if(j === 3){
 	                    	isWriter = reply.isWriter;
 	                    }else  if(j === 4){
	                    	output += "<div class='w3-border w3-padding'> No."+(i+1)+"&nbsp;&nbsp;&nbsp";
	                    	ref_num = reply.ref_num;
	                    	if(${isSelected }==reply.ref_num){
	                    		output += "<i class='fas fa-coins' style='float: right!important;color:#DBBF41; font-size: 24px;''>"+ CP +"</i>";
	                   		}
	                    }else  if(j === 5){
	                    	output += "<span class='w3-center w3-xlarge w3-text-blue'>"+reply.subject+"</span></div>";
	                    }else  if(j === 6){
	 	                    	output += "</div><article class='w3-border w3-large w3-padding reply_content'>"
	 	                    	file_name = reply.file;
	 	                    	if(reply.file!="null"){
	 	                    		output += "<img src='./codingUpload/"+reply.file+"' width=800px ><br><br><br>";
	 	                    	}
	 	                }else  if(j === 7){
	                   		output += reply.content+"<br><br></article>";
	                    }else  if(j === 8){
 	                    	if(file_name!="null"){
 	                    		output += "<div class='w3-border w3-padding'>첨부파일: <a href='CodingFileDown.code?file_name='"+reply.file+" target='blank'>"+reply.file+"</a></div>";
 	                    	}else{
 	                    		output += "<div class='w3-border w3-padding'>첨부파일 없어용</div>";
 	                    	}
 	                    	output += "<br>";
 	                    	var s_nick = '${sessionScope.nickname }'
 	                    	if(s_nick === nickname){
 	                    		output += "<div>";
 	                    		output += "<button type='button' class='btn btn-outline-secondary' onclick='modify_ref("+ref_num+")'>글수정</button>&nbsp;&nbsp;";
 	                    		output += "<button type='button' class='btn btn-outline-secondary' onclick='delete_ref("+ref_num+")'>글삭제</button>";
 	           			 		output += "</div>";
 	                    	}
 	                    	output += "<div class='dataTables_paginate paging_simple_numbers pageList' id='dataTable_paginate'''>"
 	                    }
	                    
	                 }
	              	$("#replyList").html(output); // replyList 영역에 output 출력
	             }
	});
}
			
function paging(nowPage){
	$.ajax({		
		//url: "CodingReplyDetail.code", // 요청 url
		url: "CodingReplyPaging.code?nowPage="+nowPage, // 요청 url
        type: "POST", // post 방식
        data: {
        	post_num : ${article.num } // board_no의 값을 넘겨줌
//         	reply_page : nowPage
        },
        success : function (page){
        	
//         page = page.replace(/\n/gi,"\\r\\n"); // 개행문자 대체
//         	$("#check").text(""); // 댓글리스트 영역 초기화
        	var page_obj = JSON.parse(page); // service 클래스로 부터 전달된 문자열 파싱
//         	alert("pageArr: " + page_obj);
        	var pageInfo =page_obj.pageInfo;
//      		alert(pageInfo.length);
     		var output="";
     		var nowPage, reply_page, reply_maxPage, reply_startPage, reply_endPage, reply_count;
     		
//         alert(pageVal.reply_count);
        	for(var i=0; i<pageInfo.length; i++){
        		var pageVar = pageInfo[i];
        		if(i==0){
//         			reply_page=pageVar.reply_page;
        			nowPage=pageVar.reply_page;
        		}else if(i==1){
        			reply_maxPage=pageVar.reply_maxPage;
        		}else if(i==2){
        			reply_startPage=pageVar.reply_startPage;
        		}else if(i==3){
        			reply_endPage=pageVar.reply_endPage;
        		}else if(i==4){
        			reply_count=pageVar.reply_count;
        		}
        	}
        	output += "<div> <ul class='pagination pagination-sm'>";
        	if(nowPage<= 1) {
        		output += "<li class='page-item disabled'> <a class='page-link' href='#'>&laquo;</a></li>";
// 				output += "[이전]&nbsp";
			}else{
				var sub = parseInt(nowPage)-1;
				output += " <li class='page-item active'><a class='page-link' href='javascript:getReply("+sub+")'>&laquo;</a>&nbsp  </li>";
			}
        	for(var j = reply_startPage; j <= reply_endPage; j++) {
    		    if(j ==nowPage){
    		    	output +="<li class='page-item active'><a class='page-link'>"+j+"</a></li>"
    			} else {
    				output += "<li class='page-item'><a class='page-link' href='javascript:getReply("+j+")'>"+j+"</a>&nbsp";
//     				nowPage=j;
    			}
        	}
        	if(nowPage >= reply_maxPage) {
//         		output += "&nbsp[다음]";
        		output += "<li class='page-item disabled'> <a class='page-link' href='#'>&raquo;</a></li></div>";
			} else { 
				var add = parseInt(nowPage)+1;
				output += " <li class='page-item active'><a class='page-link' href='javascript:getReply("+add+")'>&raquo;</a>&nbsp  </li>";
				output +="</ul></div>"
// 				output += "<a href='javascript:getReply("+add+")'>&nbsp[다음]</a>";
			}

//         	output +="<span style='float: right!important;' ><button type='button' class='btn btn-outline-danger' onclick='write_reply()'></button></span>";
        	
        	$(".pageList").html(output);
        }
// 	                	$(".check").append(pageVal.reply_count);
//         	   alert(pageV.reply_count);

//			}
	});
}

</script>  

<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<style type="text/css">
#empty-box{
	padding: 10% 10%;
	text-align: center;
}
</style>
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
					<i class="fa fa-user"></i>${article.nickname }
				 
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
					<!-- 조회수 --><span><i class="fa fa-eye"></i>&nbsp;${article.readcount }</span>&nbsp;
					<!-- 댓글수 --><i class="fa fa-commenting-o"  onclick="javascript:openCmmnt();">&nbsp;${comment_count }</i>&nbsp;<span class="reply_count"></span>
				</div>
			</div>
			<div class="w3-border w3-padding">
				No.${article.num }&nbsp;&nbsp;&nbsp; <span class="w3-center w3-xlarge w3-text-blue">${article.subject }</span>
			</div>
<%-- 			<article class="w3-border w3-large w3-padding">${article.content }</article> --%>
				
				<article class="w3-border w3-large w3-padding article_content">
					<c:if test="${article.file != null}">
					<img src="./codingUpload/${article.file }" width=800px >	<br><br><br>
						</c:if>
					${article.content } <br><br>
				</article>
				
			<div class="w3-border w3-padding">첨부파일: <a href="CodingFileDown.code?file_name=${article.file }" target="blank">${article.file }</a></div><br>
<!-- 			<div class="check" > 체크</div> -->
			
			<c:if test="${article.nickname == sessionScope.nickname }">
			<div>
			    <button type="button" class="btn btn-outline-secondary" onclick="modify_article(${article.num})">글수정</button>&nbsp;&nbsp;
			     <button type="button" class="btn btn-outline-secondary" onclick="delete_article(${article.num})">글삭제</button>
			</div>
			</c:if>
		</div>

<div class="w3-article" id="reply_article">
	 <div class="right-box " id="replyList"> 
		
	 <!-- 페이지 목록 버튼 표시 -->
	<!-- 이전 페이지 또는 다음 페이지가 존재할 경우에만 해당 하이퍼링크 활성화 -->
	
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