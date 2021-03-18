<%@page import="shop.vo.ShopBean"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="member.dao.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
     
<c:if test="${sessionScope.nickname==null }">
    <script type="text/javascript">
		alert("로그인 해주세요");
		location.href="LoginForm.me"
	</script>
</c:if>

<c:if test="${!(sessionScope.sId eq 'admin') }">
    <script type="text/javascript">
		alert("접근 권한이 없습니다.");
		history.back();
	</script>
</c:if>	


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>

<style type="text/css">
      .table { 
       border-collapse: collapse;
      border-top: 3px solid #168;
  		 }   
    .table th {
      color: #168 !important;
      background: #00C1C4;
      text-align: center;
    }
    .table th, .table td {
      padding: 10px;
      border: 1px solid #ddd;
    }
    .table th:first-child, .table td:first-child {
      border-left: 0;
    }
    .table th:last-child, .table td:last-child {
      border-right: 0;
    }
    .table tr td:first-child{
      text-align: center;
    }
    .table caption{caption-side: bottom; display: none;}
        
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
						<h2>상품목록 및 추가</h2>
<!-- 						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus placerat enim et urna sagittis, rhoncus euismod erat tincidunt. Donec tincidunt volutpat erat.</p> -->
					</div>
				</div>
				 <div class="">
				 
		<section id="listForm">
	<c:if test="${shopList != null }"> 
<!-- 		<h2>상품목록</h2> -->
<!-- 		<form method="post" id="listForm"> -->
			<table class="table">
				<tr class="table-info">
					<th>상품코드</th>
					<th>상품명</th>
					<th>가격</th>
					<th>상품재고량</th>
					<th>상품판매량</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
				
				<c:forEach var="product" items="${shopList}" varStatus="status">

				<tr class="tr_list">
					<td>
						${product.product_cod }
					</td>
					<td>
						${product.product_name }
					</td>
					<td>
						${product.price }
					</td>
					<td>
						${product.stock }
					</td>
					<td>
						${product.purchase_count }
					</td>
<%-- 					<td><a href="ProductModifyForm.ad?product_cod="${product.product_cod}><b>수정</b></a> --%>
					
					<td><a href="ProductModifyForm.ad?product_cod=${product.product_cod }"><b>수정</b></a></td>
					<td><a href="ProductDeletePro.ad?product_cod=${product.product_cod}" onclick="return confirm('정말로 삭제하시겠습니까?');"><b>삭제</b></a></td>
				</tr>
				</c:forEach>
			</table>
			</c:if>	
<!-- 		</form> -->




	<c:if test="${cartList = null }">
		<section class="div_empty">상품 정보가 없습니다. </section>
	</c:if>	
	
	<div class="text-center">
		<a href="ProductRegistForm.ad"><input type="button" value="상품등록" class="bs_btn btn-info"></a>
	</div>
	
	</section>
                           
                            </div>
                        </div>
                    </div>



		<!-- footer page -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- footer page -->
                            
                           

	</body>



</html>