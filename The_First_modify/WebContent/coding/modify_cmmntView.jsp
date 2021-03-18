<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="coding.vo.CmmntBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="coding.vo.PageInfo"%>
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
	ArrayList<CmmntBean> cmmntList = (ArrayList<CmmntBean>)request.getAttribute("cmmntList");
	String nowPage = (String)request.getAttribute("page");
	int post_num = (int)request.getAttribute("post_num");
	Date today = (Date)request.getAttribute("today");
	
	// PageInfo 객체로부터 페이징 정보 가져오기
	PageInfo cmmnt_pageInfo = (PageInfo)request.getAttribute("cmmnt_pageInfo");
	int cmmnt_count = cmmnt_pageInfo.getListCount();
	int cmmnt_nowPage = cmmnt_pageInfo.getPage(); // page 디렉티브의 이름과 중복되므로 nowPage 라는 변수명으로 사용
	int cmmnt_startPage = cmmnt_pageInfo.getStartPage();
	int cmmnt_endPage = cmmnt_pageInfo.getEndPage();
	int cmmnt_maxPage = cmmnt_pageInfo.getMaxPage();

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Do you have any Questions?</title>
<style type="text/css">
#cdiv{
	  border-bottom: 1px dotted #d3d3d3;
}
span {
	display:inline-block; 
	text-align: center;
}
.
</style>
<script src="js/jquery-3.4.1.js"> </script>
<script type="text/javascript">

function insert_comment(){
	alert("코멘트 넣기");
	location.href="CmmntWritePro.code?post_num="+<%=post_num%>;
}

function delete_comment(comment_num){
	alert("코멘트 삭제");
	location.href="CmmntDeletePro.code?post_num="+<%=post_num%>+"&comment_num="+comment_num;
}

function update_heart(comment_num){
// 	alert(comment_num);
	if (confirm("추천하시겠습니까?") == true){    //확인
		updateHeart(comment_num);
	}else{   //취소
		return false;
	}
}

function cancle_heart(comment_num){
// 	alert(comment_num);
	if (confirm("추천을 취소하시겠습니까?") == true){    //확인
		cancleHeart(comment_num);
	}else{   //취소
		return false;
	}
}

$(document).ready(function() {
	getComment();
});

function getComment(){
// 	alert("getComment(false)");
	$.ajax({		
		url: "CmmntHeartList.code?recommender=${sessionScope.nickname}", // 요청 url
        type: "POST", // post 방식
        data: {
        	post_num : <%=post_num%>, // board_no의 값을 넘겨줌
        	cmmnt_page : <%=cmmnt_nowPage%>
//         	reply_page : i
        },
        success : function (list){
        	
         	list = list.replace(/\n/gi,"\\r\\n"); // 개행문자 대체
        	$("#cmmnt").text(""); // 댓글리스트 영역 초기화
        	var obj = JSON.parse(list); // service 클래스로 부터 전달된 문자열 파싱
        	var commentList =obj.commentList; // replyList는 전달된 json의 키값을 의미
        	var output = ""; // 댓글 목록을 누적하여 보여주기 위한 변수
        	var comment_num ="";
        	var heart ="";
//         		paging(i);
//         		i=i-1;
        	for (var i = 0; i < commentList.length; i++) { // 반복문을 통해 output에 누적
//                    output += "<div class='w3-border w3-padding'>";
	                 for (var j = 0; j < commentList[i].length; j++) {
	                    var commentVar = commentList[i][j];
	                    if(j === 0){
	                    	comment_num =commentVar.comment_num;
	                    	output += "<div id='cdiv'><span style='width: 10%'>" + commentVar.comment_num +"</span>";
	                    }else  if(j === 1){
	                    	var comment_nick = commentVar.comment_nickname;
	     					output += "<span style='width: 20%'>" + commentVar.comment_nickname + "</span>";
	                    }else  if(j === 2){
	                    	output += "<span style='width: 20%'>" + commentVar.comment + "</span>";
	                    }else  if(j === 3){
	                    	output += "<span style='width: 20%'>" + commentVar.date + "</span>";
	                    }else  if(j === 4){
	                    	var post_num = parseInt(commentVar.post_num);
						}else  if(j === 5){
	                    	var session_nick = commentVar.session_nick;
	                    	 if(session_nick===comment_nick){
	      						output += "<span><input type='button' class='btn btn-secondary btn-sm' value='수정'></button>"; 
	      						output += "<input type='button' class='btn btn-secondary btn-sm' value='삭제' onclick='delete_comment("+comment_num+");' ></span>";
	      					}
						}else  if(j === 6){
	                    	var heart = parseInt(commentVar.heart);
						}
	                    else  if(j === 7){
	                    	output += "<span>";
	                    	var existNum = parseInt(commentVar.exist);
// 	                    	alert(ss);
							if(existNum>0){
								output += "<button class='btn btn-danger btn-sm w3-round' onclick='cancle_heart("+comment_num+");'>";
		    					output += "<i class='fa fa-heart' style='font-size:16px;color:white'></i>&nbsp;<span class='rec_count'>"+heart+"</span></button></div>";
							}
							else if(heart==0 && existNum==0){
								output += "<button class='btn btn-outline-danger btn-sm w3-round' id='heart_count' onclick='update_heart("+comment_num+");'>";
	    						output += "<i class='fa fa-heart' style='font-size:16px;color:red'></i>&nbsp;<span class='rec_count'>"+heart+"</span></button></div>";
							}else if(heart>0 && existNum==0){
								output += "<button class='btn btn-danger btn-sm w3-round' id='heart_count' onclick='update_heart("+comment_num+");'>";
    							output += "<i class='fa fa-heart' style='font-size:16px;color:white'></i>&nbsp;<span class='rec_count'>"+heart+"</span></button></div>";
								}
							}
	                   
	                    
	                    }
	                 }
        			output += "<br><br><br><br>";
        			output += "<form action='CmmntWritePro.code?post_num="+post_num+"' method='post'>";
        			output += "<div><input type='text' name='nickname' value='"+session_nick+"' readonly></textarea>";
        			output += "<textarea  name='comment' cols='60' rows='3' required=''></textarea></form>";
        			output += "<input type='submit' class='btn btn-success btn-lg' value='등록'></div>";
//         			output += "<span style='width: 20px'><button class='btn btn-success btn-lg2' value='등록' onclick='insert_comment();'></span></div>";
//         			output +="</form>";
//         			<input type="submit" class="btn btn-success btn-lg" value="등록">
		          	$(".cmmnt").html(output); // commentList 영역에 output 출력
	   }
	});
}

function updateHeart(comment_num){
	$.ajax({		
		url: "CmmntUpdateHeart.code?recommender=${sessionScope.nickname}", // 요청 url
        type: "POST", // post 방식
        data: {
//         	recommender: recommender,
        	cmmnt_num: comment_num
        },
        success : function(){
//         	alert("update");
        	getComment();
	   }
	});
}

function cancleHeart(comment_num){
	$.ajax({		
		url: "CmmntDeleteHeart.code?recommender=${sessionScope.nickname}", // 요청 url
        type: "POST", // post 방식
        data: {
//         	recommender: recommender,
        	comment_num: comment_num
        },
        success : function(){
        	getComment();
	   }
	});
}

</script>  


</head>
<body>
<%-- 	<jsp:include page="../inc/top.jsp"/> --%>
	<jsp:include page="../inc/link.jsp"/>
<%-- 	<jsp:include page="../inc/green.jsp"/> --%>

	<!-- 댓글 보여주기 -->
	<section class="cmmnt"> <!-- 코멘트 부분 div 시작 -->
	

	</section>

</body>
</html>