<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<%
	// Action 클래스에서 request 객체의 setAttibute() 메서드로 저장되어 전달된 객체 가져오기(Object 타입이므로 형변환 필요)
	Date today = (Date)request.getAttribute("today");
%>  
<!DOCTYPE HTML>
<!--
	Cube by FreeHTML5.co
	Twitter: http://twitter.com/gettemplateco
	URL: http://freehtml5.co
-->
<html>
	<head>
		<style type="text/css">
    		.channel-chat {
    		position: fixed;
     		bottom: 0;
     		z-index: 99999;
     		} 
   		</style>
	</head>
	<body>
	<!-- 카카오톡 채팅 -->
   		<a id="channel-chat-button" href="#" onclick="void chatChannel();" class="channel-chat">
  		<img src="images/consult_small_yellow_pc.png" style="width: 104px"/></a>

 		<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
   		<script type="text/javascript">
        	// input your appkey
     		Kakao.init('c089c8172def97eb00c07217cae17495')
     		function chatChannel() {
       		Kakao.Channel.chat({
         	channelPublicId: '_CZgxnxb',
       			})
     		}
	   	</script> 
		<!-- header page -->
		<jsp:include page="inc/link.jsp"/>
		<jsp:include page="inc/top.jsp"/>
		<jsp:include page="inc/green.jsp"/>
		<!-- header page -->


<!-- 		<div class="gtco-services gtco-section"> -->
<!-- 			<div class="gtco-container"> -->
				
<!-- 				<div class="row row-pb-md"> -->
<!-- 					<div class="col-md-4 col-sm-4 service-wrap"> -->
<!-- 						<div class="service"> -->
<!-- 							<h3><i class="ti-pie-chart"></i> 취업 정보 </h3> -->
<!-- 						</div> -->
						
<!-- 					</div> -->
<!-- 					<div class="col-md-4 col-sm-4 service-wrap"> -->
<!-- 						<div class="service animate-change"> -->
<!-- 							<h3><i class="ti-ruler-pencil"></i> Design</h3> -->
<!-- 							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima quidem quae, assumenda dolores ad pariatur! Deleniti debitis, voluptatem! Omnis enim magnam perspiciatis, natus. Ipsum distinctio, voluptatibus vero laboriosam sequi! Officia fuga quam eveniet quo similique!</p> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="col-md-4 col-sm-4 service-wrap"> -->
<!-- 						<div class="service"> -->
<!-- 							<h3><i class="ti-settings"></i> Development</h3> -->
<!-- 							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta aperiam, maiores officia alias accusantium fugiat, doloremque voluptas, voluptatem dolore vel animi praesentium saepe unde consequuntur, eum asperiores odit aliquam error nulla impedit repellendus. Nesciunt, delectus.</p> -->
<!-- 						</div> -->
<!-- 					</div> -->

<!-- 					<div class="clearfix visible-md-block visible-sm-block"></div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<!-- END .gtco-services -->

		<div class="gtco-section gtco-products">
			<div class="gtco-container">
				<div class="row row-pb-sm">
					<div class="col-md-8 col-md-offset-2 gtco-heading text-center">
						<h2>Ask any coding questions</h2>
						<p>Choose the color you want.</p>
					</div>
				</div>
				<div class="row row-pb-md">
					<div class="col-md-4 col-sm-4">
						<a href='<c:url value="/JobBoardList.job"/>' class="gtco-item two-row bg-img animate-box" style="background-image: url(images/img_1.jpg)">
							<div class="overlay">
								<i class="ti-arrow-top-right"></i>
								<div class="copy">
									<h3>Employment information</h3>
									<p>취업 정보 바로가기</p>
								</div>
							</div>
							<img src="images/img_1.jpg" class="hidden" alt="Free HTML5 Website Template by FreeHTML5.co">
						</a>
						<a href='<c:url value="/AcademyList.ac"/>' class="gtco-item two-row bg-img animate-box" style="background-image: url(images/img_2.jpg)">
							<div class="overlay">
								<i class="ti-arrow-top-right"></i>
								<div class="copy">
									<h3>Recommendation for academy</h3>
									<p>학원 추천 바로가기</p>
								</div>
							</div>
							<img src="images/img_2.jpg" class="hidden" alt="Free HTML5 Website Template by FreeHTML5.co">
						</a>
					</div>
					<div class="col-md-4 col-sm-4">
						<a href='<c:url value="/CodingList.code"/>' class="gtco-item one-row bg-img animate-box" style="background-image: url(images/img_md_1.jpg)">
							<div class="overlay">
								<i class="ti-arrow-top-right"></i>
								<div class="copy">
									<h3>Charged Coding Q&A</h3>
									<p>코딩 질문(Charged) 바로가기</p>
								</div>
							</div>
							<img src="images/img_md_1.jpg" class="hidden" alt="Free HTML5 Website Template by FreeHTML5.co">
						</a>
					</div>
					<div class="col-md-4 col-sm-4">
						<a href='<c:url value="/CodingFreeList.cf"/>' class="gtco-item two-row bg-img animate-box" style="background-image: url(images/img_3.jpg)">
							<div class="overlay">
								<i class="ti-arrow-top-right"></i>
								<div class="copy">
									<h3>Free Coding Q&A</h3>
									<p>코딩 질문(Free) 바로가기</p>
								</div>
							</div>
							<img src="images/img_3.jpg" class="hidden" alt="Free HTML5 Website Template by FreeHTML5.co">
						</a>
						<a href='<c:url value="/CommunityList.any"/>' class="gtco-item two-row bg-img animate-box" style="background-image: url(images/img_4.jpg)">
							<div class="overlay">
								<i class="ti-arrow-top-right"></i>
								<div class="copy">
									<h3>Free Board</h3>
									<p>자유 게시판 바로가기</p>
								</div>
							</div>
							<img src="images/img_4.jpg" class="hidden" alt="Free HTML5 Website Template by FreeHTML5.co">
						</a>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 text-center">
					<p><a href='<c:url value="/ShopMain.shop"/>' class="btn btn-special">Visit Shop</a></p>
					</div>
				</div>
			</div>
		</div>
		<!-- END .gtco-products -->
		

		<!-- footer page -->
		<jsp:include page="inc/bottom.jsp"/>
		<!-- footer page -->


	<div class="gototop js-top">
		<a href="#" class="js-gotop"><i class="icon-arrow-up"></i></a>
	</div>

	</body>
</html>

