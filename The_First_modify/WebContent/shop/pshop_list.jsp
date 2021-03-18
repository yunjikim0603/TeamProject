<%@page import="shop.vo.ShopBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
						<h2>CASH POINT 충전</h2>
<!-- 						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus placerat enim et urna sagittis, rhoncus euismod erat tincidunt. Donec tincidunt volutpat erat.</p> -->
					</div>
				</div>
				 <div class="">
				 
				
				 
                            <form action="PShopPayment.shop" method="post">
                      
							<input type="hidden" name="name" value="point">
							<input type="hidden" name="nickname" value="${sessionScope.nickname }">
							
								  <div class="card border-info mb-3" style="max-width: 30rem; text-align: center; margin-left: auto; margin-right: auto;">
								  <div class="card-header">충전금액을 선택해 주세요</div>
								  <div class="card-body">
								    
								    <div class="form-group" style="text-align: left; margin-left: 40%;">
								    <div class="custom-control custom-radio">
								      <input type="radio" id="customRadio100" name="price" value="100" class="custom-control-input" >
								      <label class="custom-control-label" for="customRadio100">100 CP</label>
								    </div>
									<div class="custom-control custom-radio">
								      <input type="radio" id="customRadio1" name="price" value="3000" class="custom-control-input">
								      <label class="custom-control-label" for="customRadio1">3,000 CP</label>
								    </div>
								    <div class="custom-control custom-radio">
								      <input type="radio" id="customRadio2" name="price" value="5000" class="custom-control-input" >
								      <label class="custom-control-label" for="customRadio2">5,000 CP</label>
								    </div>
								    <div class="custom-control custom-radio">
								      <input type="radio" id="customRadio3" name="price" value="10000" class="custom-control-input" >
								      <label class="custom-control-label" for="customRadio3">10,000 CP</label>
								    </div>
								     <div class="custom-control custom-radio">
								      <input type="radio" id="customRadio4" name="price" value="20000" class="custom-control-input" >
								      <label class="custom-control-label" for="customRadio4">20,000 CP</label>
								    </div>
								  </div>
								  </div>
								</div>
							
					<div class="text-center">
					<input type="submit" value="결제" class="bs_btn btn-info" onclick="return confirm('결제하시겠습니까?');">
					</div>
					
					</form>	
	
                            </div>
                        </div>
                    </div>



		<!-- footer page -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- footer page -->
		
	</body>

</html>
















