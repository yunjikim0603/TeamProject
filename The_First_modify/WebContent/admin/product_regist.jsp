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
						<h2>상품 등록 </h2>
					</div>
				</div>

	<section id="registForm">
		<header>
<!-- 			<h2>상품 등록</h2> -->
		</header>
		<form action="ProductRegistPro.ad" method="post" enctype="multipart/form-data" name="registForm">
			<table class="table">
				<tr>
					<th class="td_left"><label for="product_cod">상품코드</label></th>
					<td class="td_right"><input type="text" name="product_cod" id="product_cod" required="required"/></td>
				</tr>
				<tr>
					<th class="td_left"><label for="product_name">상품명</label></th>
					<td class="td_right"><input type="text" name="product_name" id="product_name" required="required"/></td>
				</tr>
				<tr>
					<th class="td_left"><label for="price">가격</label></th>
					<td class="td_right"><input type="text" name="price" id="price" required="required"/></td>
				</tr>
				<tr>
					<th class="td_left"><label for="stock">상품재고량</label></th>
					<td class="td_right"><input type="text" name="stock" id="stock" required="required"/></td>
				</tr>
				<tr>
					<th class="td_left"><label for="product_info">상품설명</label></th>
					<td class="td_right" >
						<textarea rows="13" cols="40" name="product_info" id="product_info" required="required"></textarea>
					</td>
				</tr>
				<tr>
					<th class="td_left"><label for="product_image">상품이미지</label></th>
					<td class="td_right"><input type="file" name="product_image" id="product_image" required="required"/></td>
				</tr>
				<tr>
					<th class="td_left"><label for="barcode_image">바코드이미지</label></th>
					<td class="td_right"><input type="file" name="barcode_image" id="barcode_image" required="required"/></td>
				</tr>
				<tr>
					<td colspan="2" id="commandCell">
						<input type="submit" value="등록" class="bs_btn btn-info" onclick="return confirm('상품을 등록하시겠습니까?');"/>
<!-- 						<input type="reset" value="다시작성" class="bs_btn btn-info"/> -->
						<input type="button" value="목록" class="bs_btn btn-info" onclick="location.href='ProductList.ad'"/>
					</td>
			</table>
		</form>
	</section>
	
	                     
                  </div>
                </div>
            



		<!-- footer page -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- footer page -->
		
	</body>


</html>
















