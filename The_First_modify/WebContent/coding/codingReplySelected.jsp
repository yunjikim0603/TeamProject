<%@page import="coding.vo.PageInfo"%>
<%@page import="java.util.Date"%>
<%@page import="coding.vo.Coding_refBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="coding.vo.CodingBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:choose>
    <c:when test="${sessionScope.nickname!=null}">
        String nickname = ${sessionScope.nickname };
    </c:when>
    <c:otherwise>
       	<script type="text/javascript">
		alert("로그인 해주세요");
		location.href="LoginForm.me"
	</script>
    </c:otherwise>
</c:choose>
<c:if test="${sessionScope.level==1 }">
    <script type="text/javascript">
		alert("LEVEL 2부터 읽으실 수 있습니다.");
		history.back();
	</script>
</c:if>
<%
	// 전달받은 request 객체에서 데이터 가져오기
	CodingBean article = (CodingBean)request.getAttribute("article");
	ArrayList<Coding_refBean> article_refList = (ArrayList<Coding_refBean>)request.getAttribute("article_refList");
// 	ArrayList<CmmntBean> cmmntList = (ArrayList<CmmntBean>)request.getAttribute("cmmntList");
	String nowPage = (String)request.getAttribute("page");
	Date today = (Date)request.getAttribute("today");
	
	// PageInfo 객체로부터 페이징 정보 가져오기
	PageInfo reply_pageInfo = (PageInfo)request.getAttribute("replyPageInfo");
	
	int isSelected =0;
	if (request.getAttribute("isSelected")!=null){
		isSelected = Integer.parseInt((String)request.getAttribute("isSelected"));
	}
	
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="bootstrap.css">
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="js/jquery-3.4.1.js"> </script>

<script type="text/javascript">
function openCmmnt(){  
    window.open("CmmntList.code?post_num="+<%=article.getNum()%>, "", width=800, height=700);
    
}
		$(document).ready(function() {
			alert("?");
			getReply(1);
// 			paging(1);
		});
		
		function getReply(i){
			$.ajax({		
    			url: "pre_CodingReplyDetail.code", // 요청 url
                type: "POST", // post 방식
                data: {
                	post_num : <%=article.getNum()%> // board_no의 값을 넘겨줌
//                 	reply_page : i
                },
                success : function (json){
                	json = json.replace(/\n/gi,"\\r\\n"); // 개행문자 대체
                	$("#replyList").text(""); // 댓글리스트 영역 초기화
                	var obj = JSON.parse(json); // service 클래스로 부터 전달된 문자열 파싱
                	var replyList =obj.replyList; // replyList는 전달된 json의 키값을 의미
                	var output = ""; // 댓글 목록을 누적하여 보여주기 위한 변수
                	var page_num ="";
                	var page_addr="";
//                 	alert(json);
//                 	var reply = replyList[0][0];
//                 	alert(reply.nickname);
//                 	output += "<i class='fa fa-user'></i>&nbsp;&nbsp;" + reply.nickname;
//                 		alert("i값은 " + i);
                		paging(i);
                		i=i-1;
                		
//                 	for (var i = 0; i < replyList.length; i++) { // 반복문을 통해 output에 누적
   	                    output += "<div class='w3-border w3-padding'>";

	   	                 for (var j = 0; j < replyList[i].length; j++) {
	 	                    var reply = replyList[i][j];
	 	                    if(j === 0){
	 	                    	output += "<i class='fa fa-user'></i> <a href='#'> " + reply.nickname +"</a>";
	 	                    	
// 	 	                    output += "<div class='w3-right'><span>채택하기</span> </div><br>";
	 	                    }else  if(j === 1){
	 	     					output += "<i class='fa fa-calendar'></i>" + reply.reply_date;
	 	                    }else  if(j === 2){
	 	     					output += "<div class='w3-right'><span><i class='fa fa-eye'></i>"+reply.readcount+"</span>&nbsp;";
	 	     					output += "<i class='fa fa-heart' style='color:red'></i>&nbsp;<span class='rec_count'></span>";
	 	                    	output += "<i class='fa fa-commenting-o'></i>&nbsp;<span class='reply_count'></span></div></div>";
	 	                    }else  if(j === 3){
	 	                    	output += "<div class='w3-border w3-padding'> No."+reply.ref_num+"&nbsp;&nbsp;&nbsp";
	 	                    	if(<%=isSelected%>==reply.ref_num){
	 	                    	output += "<div class='w3-right'><i class='fas fa-check' style='font-size:24px;color:green' onclick='javascript:selected("+reply.ref_num+");'></i></div><br>";
	 	                   			 }
	 	                    }else  if(j === 4){
	 	                    	output += "<span class='w3-center w3-xlarge w3-text-blue'>"+reply.subject+"</span></div>";
	 	                    }else  if(j === 5){
	 	                    	if(reply.file!=""){
	 	                    		output += "<article class='w3-border w3-large w3-padding reply_content'><img src='./codingUpload/"+reply.file+"' width=800px ><br><br><br>";
	 	                    	}
	 	                    }else  if(j === 6){
	 	                   		output += reply.content+"<br><br></article>";
	 	                    }else  if(j === 7){
	 	                    	output += "<div class='w3-border w3-padding'>첨부파일: <a href='CodingFileDown.code?file_name='"+reply.file+" target='blank'>"+reply.file+"</a></div><br>";
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
				url: "CodingReplyDetail.code?nowPage="+nowPage, // 요청 url
	            type: "POST", // post 방식
	            data: {
	            	post_num : <%=article.getNum()%> // board_no의 값을 넘겨줌
// 	            	reply_page : nowPage
	            },
	            success : function (page){
// 	            	alert(page);
// 	            page = page.replace(/\n/gi,"\\r\\n"); // 개행문자 대체
// 	            	$("#check").text(""); // 댓글리스트 영역 초기화
	            	var page_obj = JSON.parse(page); // service 클래스로 부터 전달된 문자열 파싱
// 	            	alert("pageArr: " + page_obj);
	            	var pageInfo =page_obj.pageInfo;
//              		alert(pageInfo.length);
             		var output="";
             		var nowPage, reply_page, reply_maxPage, reply_startPage, reply_endPage, reply_count;
             		
// 	            alert(pageVal.reply_count);
	            	for(var i=0; i<pageInfo.length; i++){
	            		var pageVar = pageInfo[i];
	            		if(i==0){
// 	            			reply_page=pageVar.reply_page;
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
	            	output += "<div> <ul class='pagination pagination-lg'>";
	            	if(nowPage<= 1) {
	            		output += "<li class='page-item disabled'> <a class='page-link' href='#'>&laquo;</a></li>";
//         				output += "[이전]&nbsp";
        			}else{
        				var sub = parseInt(nowPage)-1;
        				output += " <li class='page-item active'><a class='page-link' href='javascript:getReply("+sub+")'>&laquo;</a>&nbsp  </li>";
        			}
	            	for(var j = reply_startPage; j <= reply_endPage; j++) {
            		    if(j ==nowPage){
            		    	output +="<li class='page-item active'><a class='page-link' href='#'>"+j+"</a></li>"
            			} else {
            				output += "<li class='page-item'><a class='page-link' href='javascript:getReply("+j+")'>"+j+"</a>&nbsp";
//             				nowPage=j;
            			}
	            	}
	            	if(nowPage >= reply_maxPage) {
// 	            		output += "&nbsp[다음]";
	            		output += "<li class='page-item disabled'> <a class='page-link' href='#'>&raquo;</a></li>";
	    			} else { 
	    				var add = parseInt(nowPage)+1;
	    				output += " <li class='page-item active'><a class='page-link' href='javascript:getReply("+add+")'>&raquo;</a>&nbsp  </li>";
	    				output +="</ul></div>"
// 	    				output += "<a href='javascript:getReply("+add+")'>&nbsp[다음]</a>";
	    			}

	            	
	            	$(".pageList").html(output);
	            }
 // 	                	$(".check").append(pageVal.reply_count);
// 	            	   alert(pageV.reply_count);

// 				}
			});
		}

</script>  


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
				<i class="fa fa-user"></i> <a href="#"> <%=article.getNickname() %></a><br>
				<i class="fa fa-calendar">
					<%if(article.getDate().compareTo(today)==0){ //날짜가 같으면 %> 
						<td style="width: 20%" id="date"><%=article.getTime()%> <!-- 시간출력 --></td>
					<%}else{ %>
						<td style="width: 20%" id="date"><%=article.getDate()%></td>
					<%} %>
				</i>
				<div class="w3-right">
					<!-- 조회수 --><span><i class="fa fa-eye"></i><%=article.getReadcount() %></span>&nbsp;
					<!-- 추천수 --><i class="fa fa-heart" style="color:red"></i>&nbsp;<span class="rec_count"></span>
					<!-- 댓글수 --><i class="fa fa-commenting-o"  onclick="javascript:openCmmnt();"></i>&nbsp;<span class="reply_count"></span>
				</div>
			</div>
			<div class="w3-border w3-padding">
				No.<%=article.getNum() %> &nbsp;&nbsp;&nbsp; <span class="w3-center w3-xlarge w3-text-blue"><%=article.getSubject() %></span>
			</div>
<%-- 			<article class="w3-border w3-large w3-padding">${article.content }</article> --%>
			<%if(article.getFile() != null) { %>
				<article class="w3-border w3-large w3-padding article_content">
					<img src="./codingUpload/<%=article.getFile()%>" width=800px >	<br><br><br>
					<%=article.getContent() %> <br><br>
				</article>
			<%}%>
			<div class="w3-border w3-padding">첨부파일: <a href="CodingFileDown.code?file_name=<%=article.getFile()%>" target="blank"><%=article.getFile() %></a></div><br>
<!-- 			<div class="check" > 체크</div> -->
		</div>	
		
		
<%if(article_refList != null) {%> 
<div class="w3-article" id="reply_article">
	 <div class="right-box " id="replyList"> 
	 <!-- 페이지 목록 버튼 표시 -->
	<!-- 이전 페이지 또는 다음 페이지가 존재할 경우에만 해당 하이퍼링크 활성화 -->
	
	</div>
	
	<div>

	<%
	}else {%>
	<section id="emptyArea">등록된 글이 없습니다.</section>
<%} %>
	 
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