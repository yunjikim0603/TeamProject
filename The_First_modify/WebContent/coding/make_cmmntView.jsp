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
	location.href="CmmntWritePro.code?post_num=${post_num }";
}

function delete_comment(comment_num){
	if (confirm("댓글 삭제하시겠습니까?") == true){    //확인
		location.href="CmmntDeletePro.code?nickname=${sessionScope.nickname }&post_num=${post_num }&comment_num="+comment_num;
	}else{   //취소
		return false;
	}
}

function modify_comment(comment_num){
	if (confirm("댓글 수정하시겠습니까?") == true){    //확인
		location.href="CmmntModifyForm.code?post_num=${post_num }&comment_num="+comment_num;
	}else{   //취소
		return false;
	}
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
        	post_num : ${post_num }
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
			if(commentList==""){
				output = "댓글이 없습니다. 댓글을 등록해주세요.";
			}else{
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
						}else  if(j === 6){
	                    	var heart = parseInt(commentVar.heart);
						}
	                    else  if(j === 7){
	                    	output += "<span>";
	                    	var existNum = parseInt(commentVar.exist);
// 	                    	alert(ss);
							if(existNum>0){
								output += "<button class='btn btn-danger btn-sm w3-round' onclick='cancle_heart("+comment_num+");'>";
		    					output += "<i class='fa fa-heart' style='font-size:16px;color:white'></i>&nbsp;<span class='rec_count'>"+heart+"</span></button>";
							}
							else if(heart==0 && existNum==0){
								output += "<button class='btn btn-outline-danger btn-sm w3-round' id='heart_count' onclick='update_heart("+comment_num+");'>";
	    						output += "<i class='fa fa-heart' style='font-size:16px;color:red'></i>&nbsp;<span class='rec_count'>"+heart+"</span></button>";
							}else if(heart>0 && existNum==0){
								output += "<button class='btn btn-danger btn-sm w3-round' id='heart_count' onclick='update_heart("+comment_num+");'>";
    							output += "<i class='fa fa-heart' style='font-size:16px;color:white'></i>&nbsp;<span class='rec_count'>"+heart+"</span></button>";
								}
							}
	                    }
	                 if(session_nick===comment_nick){
   						output += "<span><input type='button' class='btn btn-secondary btn-sm' value='수정' onclick='modify_comment("+comment_num+");'></button>"; 
   						output += "<input type='button' class='btn btn-secondary btn-sm' value='삭제' onclick='delete_comment("+comment_num+");' ></span></div>";
   					}else{
   						output += "</div>";
   					}
	                 }
			}
        			output += "<br><br><br><br>";
        			output += "<form action='CmmntWritePro.code?post_num=${post_num }' method='post'>";
        			output += "<div><input type='text' name='nickname' value='${sessionScope.nickname }' readonly></div>";
        			output += "<textarea  name='comment' cols='60' rows='3' required=''></textarea>";
        			output += "<input type='submit' class='btn btn-success btn-lg' value='등록'></div></form>";
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

	<input type="hidden" name="post_num" value="${post_num }" />
	<!-- 댓글 보여주기 -->
	<section class="cmmnt"> <!-- 코멘트 부분 div 시작 -->
	

	</section>

</body>
</html>