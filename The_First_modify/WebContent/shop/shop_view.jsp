<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${sessionScope.nickname==null }">
    <script type="text/javascript">
		alert("로그인 해주세요");
		location.href="LoginForm.me"
	</script>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SHOP</title>
<style type="text/css">

.prd_detail_box {
     padding: 80px 0 0; 
	margin-left: 20%
	
    }
    
 .left_area {
    float: left;
    width: 550px;
    margin-top: 5px;
    }
    
.prd_img {
    position: relative;
    width: 550px;
    height: 300px;
     text-align: center; 
    margin-bottom: 50px;
    }
    
.right_area {
    width: 425px;
    float: left;
    
    }
    
.prd_info_box {
    position: relative;
    width: 100%;
    }
    
.prd_name {
    font-size: 26px;
    color: #000;
    line-height: 32px;
    word-break: break-all;
    }
    
 .prd_price {
 	color: orange;
    font-weight: bold;
    font-size: 25px;  
    }
    
.prd_info{
	width: 300px;
}  
 
  .prd_btn {
    overflow: hidden;
    margin: 10px 0 100px 0;    
    }  
    
	
</style>


</head>


<body>

	<!-- header page -->
		<jsp:include page="../inc/link.jsp"/>	
		<jsp:include page="../inc/top.jsp"/>
		<jsp:include page="../inc/green.jsp"/>
	<!-- header page -->


	
	<div class="prd_detail_box">
		<div class="left_area">
			<div class="prd_img">
				<img src="admin/productUpload/${shop.product_image }"/>	
			</div>	
		</div>
		<div class="right_area">
			<div class="prd_info_box">
				<p class="prd_name">${shop.product_name }</p>
				<p class="prd_price">
					<c:set var="price" value="${shop.price }"/>
					<fmt:formatNumber type="number" maxFractionDigits="3" value="${price}"/> CP 
				</p>
				<p class="prd_info">${shop.product_info }</p>
			</div>
			
			<form action="ShopPayment.shop" method="post">
			<input type="hidden" name="price" value="${shop.price }">
			<input type="hidden" name="barcode" value="${shop.barcode_image }">
			<input type="hidden" name="stock" value="${shop.stock }">
			<input type="hidden" name="purchase_count" value="${shop.purchase_count }">
			<input type="hidden" name="product_cod" value="${shop.product_cod }">
			
			<div class="prd_btn">
				<input type="submit" value="교환" class="bs_btn btn-info" onclick="return confirm('${shop.price} 포인트가 차감됩니다. 교환하시겠습니까?');">
				<a href="ShopList.shop"><input type="button" value="목록" class="bs_btn btn-info"></a>	
			</div>
			
			</form>
		</div>
	</div>
				
				


		<!-- footer page -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- footer page -->
		
	</body>



</html>

















