<%@page import="shop.vo.ShopBean"%>
<%@page import="notice.vo.NoticeBean"%>
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
	    width: 40% !important;
	    margin-left: auto;
	    margin-right: auto;
       border-collapse: collapse;
      border-top: 3px solid #168;
  		 }   
    .table th {
      color: #168 !important;
      background: #bee5eb;
      text-align: center;
      font-weight: 300 !important;
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
						<h2>상품 수정</h2>
<!-- 						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus placerat enim et urna sagittis, rhoncus euismod erat tincidunt. Donec tincidunt volutpat erat.</p> -->
					</div>
				</div>
				 <div class="">
				 
				
				 	<section id="writeForm">
<!-- 		<h2>상품 수정</h2> -->
		<form action="ProductModifyPro.ad?product_cod=${shopBean.product_cod }" method="post" name="boardForm" enctype="multipart/form-data">
		
			<input type="hidden" name="product_cod" id="product_cod" value="${shopBean.product_cod }" />
			<table class="table">
			
				<tr>
					<th class="td_left"><label for="product_cod">상품코드</label></th>
					<td class="td_right"><input type="text" name="product_cod" id="product_cod" value="${shopBean.product_cod }" readonly/></td>
				</tr>
				<tr>
					<th class="td_left"><label for="product_name">상품명</label></th>
					<td class="td_right">
						<input type="text" name="product_name" id="product_name" value="${shopBean.product_name }" required />
					</td>
				</tr>
				<tr>
					<th class="td_left"><label for="price">가격</label></th>
					<td class="td_right">
						<input type="text" name="price" id="price" value="${shopBean.price }" required/>
					</td>
				</tr>
				<tr>
					<th class="td_left"><label for="stock">상품재고량</label></th>
					<td class="td_right">
						<input type="text" name="stock" id="stock" value="${shopBean.stock }" required />
					</td>
				</tr>
				<tr>
					<th class="td_left"><label for="purchase_count">상품구매량</label></th>
					<td class="td_right">
						<input type="text" name="purchase_count" id="purchase_count" value="${shopBean.purchase_count }" required />
					</td>
				</tr>
				<tr>
					<th class="td_left"><label for="product_info">상품설명</label></th>
					<td class="td_right" >
						<textarea rows="13" cols="40" name="product_info" id="product_info" required>${shopBean.product_info }</textarea>
					</td>
				</tr>
<!-- 				<tr> -->
<!-- 					<th class="td_left"><label for="product_image">상품이미지</label></th> -->
<!-- 					<td class="td_right"><input type="file" name="product_image" id="product_image" readonly/> -->
<%-- 					<img src="./admin/productUpload/${shopBean.product_image }" width=400px> --%>
<!-- 					</td> -->
					
<!-- 				</tr> -->
				<tr>
					<th class="td_left"><label for="barcode_image">바코드이미지</label></th>
					<td class="td_right"><input type="file" name="barcode_image" id="barcode_image"/>
					<img src="./admin/productUpload/${shopBean.barcode_image }" width=400px>
					</td>
				</tr>
				
			</table>
			<div style="text-align: center;">
				<input type="submit" value="수정" class="bs_btn btn-info" onclick="return confirm('상품을 수정하시겠습니까?');"/>&nbsp;&nbsp;
				<input type="button" value="취소" class="bs_btn btn-info" onclick="history.back()" />
			</div>
		</form>	
	</section>
                            
                           
                            </div>
                        </div>
                    </div>



		<!-- footer page -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- footer page -->
		
	</body>



</html>