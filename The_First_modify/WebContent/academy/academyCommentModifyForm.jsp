<%@page import="java.util.Date"%>
<%@page import="any_community.vo.CommunityBean"%>
<%@page import="coding_free.vo.CodingFreeBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${sessionScope.nickname==null }">
<c:choose>
	<c:when test="${sessionScope.sId != null }">
    <script type="text/javascript">
		alert("이메일 인증 받으세요.");
		location.href="NewLoginForm.me"
	</script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
		alert("로그인 해주세요");
		location.href="emailSendConfirm.jsp"
		</script>
	</c:otherwise>
</c:choose>
</c:if>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Do you have any Questions?</title>
<script src="js/jquery-3.4.1.js"> </script>
<script type="text/javascript">
// ----------- article. today. post_num

// 게시글 수정
function modify_article(num){  
	if (confirm("수정하시겠습니까?") == true){    //확인
		location.href="AcademyModifyForm.ac?num="+num;
	}else{
			return false;
		}
}

// 게시글 삭제
function delete_article(post_num){  
	if (confirm("삭제하시겠습니까?") == true){    //확인
		location.href="AcademyDeletePro.ac?&post_num="+post_num;
	}else{
			return false;
		}
}

// 댓글 삭제
function delete_comment(comment_num){
	if (confirm("댓글 삭제하시겠습니까?") == true){    
		location.href="AcademyCommentDeletePro.ac?post_num=${article.num }&nickname=${sessionScope.nickname }&comment_num="+comment_num;
	}else{  
		return false;
	}
}

// 댓글 수정
function modify_comment(comment_num){
	if (confirm("댓글 수정하시겠습니까?") == true){  
		location.href="AcademyCommentModifyForm.ac?post_num=${article.num }&comment_num="+comment_num;
	}else{   
		return false;
	}
}

// 댓글 리스트 출력
$(document).ready(function() {
	getComment(1);
});

		function getComment(nowPage){
			$.ajax({		
    			url: "AcademyCommentList.ac", // 요청 url
                type: "POST", // post 방식
                data: {
                	post_num : ${post_num }, // board_no의 값을 넘겨줌
                	nowPage : nowPage
                },
                success : function (json){
                	json = json.replace(/\n/gi,"\\r\\n"); // 개행문자 대체
                	$("#commentList").text(""); // 댓글리스트 영역 초기화
                	var obj = JSON.parse(json); // service 클래스로 부터 전달된 문자열 파싱
                	var cmmntList = obj.replyList; // replyList는 전달된 json의 키값을 의미
                	var output = ""; // 댓글 목록을 누적하여 보여주기 위한 변수
                	var comment_num = "";
                	var comment_nick = "";

        			paging(nowPage);
        			
                	output += "<form action='AcademyCommentModifyPro.ac' method='post'>";
                	
        			output += "<input type='hidden' name='comment_num' value='${modify_num }'>";
        			output += "<input type='hidden' name='post_num' value='${post_num }'>";
                	
        			output += "<div><input type='text' class='bd_color' name='nickname' value=${comment.nickname } readonly>";
        			output += "<span class='w3-right'><input type='submit' class='btn btn-success btn-sm' value='댓글수정'></span></div>";
        			output += "<textarea class='form-control' id='exampleTextarea' rows='3' name='comment'>${comment.comment }</textarea></form>";
        			
					for (var i = 0; i < cmmntList.length; i++) { // 반복문을 통해 output에 누적 
						for (var j = 0; j < cmmntList[i].length; j++) {                	 
	 	                    var cmmnt = cmmntList[i][j];
	 	                    if(j === 0){
	 	                    	comment_num = cmmnt.comment_num;
	 	                    	output += " No." + comment_num +"&nbsp;&nbsp;&nbsp";
	 	                    }else  if(j === 1){
	 	     					output += "<span class='w3-right'> <i class=' fa fa-calendar'></i>" + cmmnt.reply_date +"</span>";
	 	                    }else  if(j === 2){
	 	                    		comment_nick = cmmnt.nickname;
	 	                    		output += "<i class='fa fa-user'></i> <a href='#'> " + cmmnt.nickname +"</a>&nbsp;&nbsp;&nbsp";
	 	                    		
	 	                    		var session_nick = '${sessionScope.nickname }';
			                    	 if(session_nick===comment_nick){
			      						output += "<class='w3-right'><input type='button' class='btn btn-secondary btn-sm' value='댓글수정' onclick='modify_comment("+comment_num+");'></button>"; 
			      						output += "<input type='button' class='btn btn-secondary btn-sm' value='댓글삭제' onclick='delete_comment("+comment_num+");'>";
			      					}
	 	                    }else  if(j === 3){
	 	                    	output += "<div class='w3-border right-box_padding cmmnt_scroll'>"+cmmnt.comment; 
	 	                    	output += "</div>";
	 	                    }
	 	                    
	   	                 	}
						
	   	                }
						output += "<div class='dataTables_paginate paging_simple_numbers pageList' id='dataTable_paginate'''>";
	  	              	$("#commentList").html(output); // commentList 영역에 output 출력

	   	             }
			});
		}
		
 		// 댓글 페이징
		function paging(nowPage){
			$.ajax({		
				url: "AcademyCommentPagingDetail.ac?nowPage="+nowPage, // 요청 url
	            type: "POST", // post 방식
	            data: {
	            	post_num : ${article.num } 
	            },
	            success : function (page){
	            	var page_obj = JSON.parse(page); 
	            	var pageInfo = page_obj.pageInfo;
             		var output="";
             		var nowPage, cmmnt_page, cmmnt_maxPage, cmmnt_startPage, cmmnt_endPage, cmmnt_count;
             		
	            	for(var i=0; i<pageInfo.length; i++){
	            		var pageVar = pageInfo[i];
	            		if(i==0){
	            			nowPage=pageVar.cmmnt_page;
	            		}else if(i==1){
	            			cmmnt_maxPage=pageVar.cmmnt_maxPage;
	            		}else if(i==2){
	            			cmmnt_startPage=pageVar.cmmnt_startPage;
	            		}else if(i==3){
	            			cmmnt_endPage=pageVar.cmmnt_endPage;
	            		}else if(i==4){
	            			cmmnt_count=pageVar.cmmnt_count;
	            		}
	            	}
	            	
	            	output += "<div> <ul class='pagination pagination-sm'>";
	            	if(nowPage<= 1) {
	            		output += "<li class='page-item disabled'> <a class='page-link'>&laquo;</a></li>";
        			}else{
        				var sub = parseInt(nowPage)-1;
        				output += " <li class='page-item active'><a class='page-link' href='javascript:getComment("+sub+")'>&laquo;</a>&nbsp  </li>";
        			}
	            	for(var j = cmmnt_startPage; j <= cmmnt_endPage; j++) {
            		    if(j ==nowPage){
            		    	output +="<li class='page-item active'><a class='page-link'>"+j+"</a></li>"
            			} else {
            				output += "<li class='page-item'><a class='page-link' href='javascript:getComment("+j+")'>"+j+"</a>&nbsp";
            			}
	            	}
	            	if(nowPage >= cmmnt_maxPage) {
	            		output += "<li class='page-item disabled'> <a class='page-link'>&raquo;</a></li>";
	    			} else { 
	    				var add = parseInt(nowPage)+1;
	    				output += " <li class='page-item active'><a class='page-link' href='javascript:getComment("+add+")'>&raquo;</a>&nbsp  </li>";
	    				output +="</ul></div>"
	    			}
	            	
	            	$(".pageList").html(output);
	            }
			});
		}
</script>

</head>
<body>

	<!-- header page -->
	<jsp:include page="../inc/link.jsp" />
	<jsp:include page="../inc/top.jsp" />
	<jsp:include page="../inc/green.jsp" />
	<!-- header page -->

	<section class="gtco-section">

		<div class="gtco-container">

			<div class="w3-article">
				<div class="left-box">
					<div class="w3-border w3-padding">
						<i class="fa fa-user"></i> <a href="#">${article.nickname }</a> <br>
						<i class="fa fa-calendar"> <c:if test="${article.date==today}">
								<td>${article.time}</td>
							</c:if> <c:if test="${article.date<today}">
								<td id="date">${article.date}</td>
							</c:if>
						</i>
						<div class="w3-right">
							<span><i class="fa fa-eye"></i>${article.readcount }</span> &nbsp;
						</div>
					</div>
					
					<div class="w3-border w3-padding">
						No.${article.num } &nbsp;&nbsp;&nbsp; 
						<span class="w3-center w3-xlarge w3-text-blue">${article.subject }</span>
					</div>
					
					<article class="w3-border w3-large w3-padding article_content_free">
						${article.content } <br><br> 
						<label class="control-label" for="readOnlyInput">학원명</label>
						<input class="form-control" id="academy_name" type="text"
							value="${article.academy_name }" readonly="readonly">
						<label class="control-label" for="readOnlyInput">학원 주소</label> 
						<input class="form-control" id="address" type="text"
							value="${article.address }" readonly="readonly"><br>
						<br>
						<div id="map" style="margin: 0 auto; width: 450px; height: 350px;"></div>
						<br>
						<br>
					</article>

				<!-- 게시글 수정 삭제 -->
				<c:if test="${article.nickname == sessionScope.nickname }">
					<div class="w3-padding">
						<button type="button" class="btn btn-outline-secondary"
							onclick="modify_article(${article.num})">글수정</button>
						<button type="button" class="btn btn-outline-secondary"
							onclick="delete_article(${article.num})">글삭제</button>
					</div>
				</c:if>
				</div>

				<!-- 댓글 출력 -->
				<div class="w3-article" id="cmmnt_article">
					<div class="right-box " id="commentList">
					</div>
				</div>
			</div>
		</div>

	</section>

<!-- 카카오 지도 api -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=99e4787a897671e1cd06a99afee54577&libraries=services"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  

// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

// 주소로 좌표를 검색합니다
geocoder.addressSearch(document.getElementById("address").value , function(result, status) {

    // 정상적으로 검색이 완료됐으면 
     if (status === kakao.maps.services.Status.OK) {

        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        // 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });

        // 인포윈도우로 장소에 대한 설명을 표시합니다
        var infowindow = new kakao.maps.InfoWindow({
            content: '<div style="width:180px;text-align:center;padding:6px 0;    font-size: initial;">'+document.getElementById("academy_name").value+'</div>'			       	
        });
        infowindow.open(map, marker);

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
    } 
}); 	
</script>

	<!-- header page -->
	<jsp:include page="../inc/bottom.jsp" />
	<!-- header page -->

</body>
</html>