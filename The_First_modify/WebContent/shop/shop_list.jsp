<%@page import="shop.vo.ShopBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<title>SHOP</title>
<style type="text/css">

#productList {
    width: 100%;
    float: left;
    }
  
  
  .productList_Row {
    float: left;
    width: 100%;
    display: block;
    margin: 70px 20%; 
    }
    
   .productList_Row .productList_UnderLine{
    display: block;
    height: 1.5px;
    width: 100%;
    background-color: lightgray;
    margin-top: 340px;
    
    }
    
   .productList_Product {
    float: left;
    display: inline;
    width: 255px;
    padding: 0 30px;
    position: relative;
    }
    
    .productList_Product .product_Name { 
     clear: both; 
     text-align: center; 
     font-size: 18px; 
     font-weight: bold; 
     color: gray; 
     } 
     
     .productList_Product .product_Price {
     font-size: 18px;
     color: orange;
     font-weight: bold;
     margin-top: 5px;
     text-align: center; 
    }
    
    .productList_Product img {
    width: 100%;
    height: 195px;
    
    .productList_Product_Text {
    margin-top: 10px;
    }
    
    
    

        
</style>
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
					
					<div>
					
					<h2>기프티콘 교환</h2>
					</div>
						
<!-- 						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus placerat enim et urna sagittis, rhoncus euismod erat tincidunt. Donec tincidunt volutpat erat.</p> -->
					</div>
				</div>
				 <div class="">
				 
				 
				 	
	<c:if test="${shopList != null }"> 
	
	
	
<!-- 	<table> -->
<!-- 		<tr> -->
<%-- 			<c:forEach var="shop" items="${shopList}" varStatus="status"> --%>
<!-- 				<td> -->
<%-- 					<a href="ShopView.shop?product_cod=${shop.product_cod}"> --%>
<%-- 					<img src="admin/productUpload/${shop.product_image }" id="productImage" /> --%>
<!-- 					</a><br> -->
<%-- 					${shop.product_name }<br> --%>
<%-- 					<c:set var="price" value="${shop.price }"/> --%>
<%-- 					<fmt:formatNumber type="number" maxFractionDigits="3" value="${price}" />CP --%>
<!-- 				</td> -->
<%-- 			<c:if test="${((status.index + 1) mod 4) == 0 }"> --%>
<!-- 				<ul> -->
<!-- 				</ul> -->
<%-- 			</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 		</tr> -->
	
	
<!-- 	</table> -->
	
	
	
				
			
			<div id="productList">
				<ul class="productList_Row">
				<c:forEach var="shop" items="${shopList}" varStatus="status">
				
					<li class="productList_Product">
						<a href="ShopView.shop?product_cod=${shop.product_cod}">
						<img src="admin/productUpload/${shop.product_image }" id="productImage" /></a>
						<p class="product_Name">${shop.product_name }</p>
						<p class="product_Price"><c:set var="price" value="${shop.price }"/>
						<fmt:formatNumber type="number" maxFractionDigits="3" value="${price}" /> CP</p>
					</li>
				<c:if test="${((status.index + 1) mod 4) == 0 }">
				
<!-- 					<div class="productList_UnderLine"></div> -->
					<ul class="productList_Row"></ul>
				
				</c:if>
				</c:forEach>
				
				</ul>
			</div>
	
	
	
	</c:if>	
	<c:if test="${shopList == null }">
		<div class="div_empty">상품 목록이 없습니다.</div>
	</c:if>


				 
				 
		
                           
                            </div>
                        </div>
                    </div>



		<!-- footer page -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- footer page -->
		
	</body>


</html>










