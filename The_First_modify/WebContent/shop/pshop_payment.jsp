<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8" />


<script src="http://code.jquery.com/jquery-1.12.4.min.js" ></script>
    <script src="http://service.iamport.kr/js/iamport.payment-1.1.5.js"></script>
    <script>
    (function() {
        var IMP = window.IMP;
        var code = "imp70906041";  // FIXME: 가맹점 식별코드
        IMP.init(code);
// 결제요청
<%
request.setCharacterEncoding("utf-8");

String name= request.getParameter("name");
String price = request.getParameter("price");
String nickname = request.getParameter("nickname");

System.out.println(name);
System.out.println(price);
System.out.println(nickname);


%>

        IMP.request_pay({

    	  pg : 'html5_inicis', // pg 사 선택
          pay_method : 'card',
          merchant_uid : 'merchant_' + new Date().getTime(),
          name : '<%=name%>',
          amount : <%=price%>,
          buyer_email : '', //한글사용불가
          buyer_name : '<%=nickname%>'

        }, function(rsp) {
            if ( rsp.success ) {
                var msg = '결제가 완료되었습니다.';
                location.href= "PShopPaymentSuccess.shop?price=<%=price%>"
                
                	
            }
            else {
                var msg = '결제에 실패하였습니다. 관리자에게 문의하세요. 에러내용 : ' + rsp.error_msg
                location.href= "Main.all"
            }
            alert(msg);
        });
    })();
    </script>
    
</head>
<body>
</body>
</html>
