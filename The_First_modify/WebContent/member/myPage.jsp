<%@page import="member.vo.MemberBean"%>
<%@page import="member.dao.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%
	String nickname = (String)session.getAttribute("nickname");
	MemberDAO mdao = MemberDAO.getInstance();
	MemberBean mb = mdao.getMember(nickname);
%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head>
    <!-- Required meta tags-->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Colorlib Templates">
    <meta name="author" content="Colorlib">
    <meta name="keywords" content="Colorlib Templates">

    <!-- Title Page-->
    <title>My Page</title>

    <!-- Icons font CSS-->
    <link href="regform/vendor/mdi-font/css/material-design-iconic-font.min.css" rel="stylesheet" media="all">
    <link href="regform/vendor/font-awesome-4.7/css/font-awesome.min.css" rel="stylesheet" media="all">
    <!-- Font special for pages-->
    <link href="regform/https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i" rel="stylesheet">

    <!-- Vendor CSS-->
    <link href="regform/vendor/select2/select2.min.css" rel="stylesheet" media="all">
    <link href="regform/vendor/datepicker/daterangepicker.css" rel="stylesheet" media="all">

    <!-- Main CSS-->
    <link href="regform/css/main.css" rel="stylesheet" media="all">
    
<script src="js/jquery-3.4.1.js"> </script>
<script type="text/javascript">
// 회원 삭제
function delete_member() {
	if (confirm("회원탈퇴하시겠습니까 ?") == true) {
		location.href="MemberDelete.me?id=${sessionScope.sId }";
	} else {
		return false;
	}
}
</script>
</head>

<body>
	<a href="./main.all"><img alt="home" src="./images/home.png" style="width:40px; float:right; margin-right:20px; margin-top: 20px;"></a>
    <div class="page-wrapper bg-gra-03 p-t-45 p-b-50">
        <div class="wrapper wrapper--w790">
            <div class="card card-5">
                <div class="card-heading">
                    <h2 class="title">My Information</h2>
                </div>
                <div class="card-body">
                    <form method="POST" action="UpdateForm.me" id="form">
                        <div class="form-row m-b-55">
                            <div class="name">ID</div>
                            <div class="value">
                                <div class="row row-space">
                                    <div class="col-2">
                                        <div class="input-group-desc">
                                            <input class="input--style-5" type="text" name="id" value="<%=mb.getId()%>" readonly="readonly">
                                        </div>
                                    </div>
                                    
                                </div>
                            </div>
                        </div>
                        <div class="form-row m-b-55">
                            <div class="name">Nickname</div>
                            <div class="value">
                                <div class="row row-space">
                                    <div class="col-2">
                                        <div class="input-group-desc">
                                            <input class="input--style-5" type="text" name="nickname" value="<%=mb.getNickname()%>" readonly="readonly">
                                        </div>
                                    </div>
                                    
                                </div>
                            </div>
                        </div>
                        <div class="form-row m-b-55">
                            <div class="name">Email</div>
                            <div class="value">
                                <div class="row row-space">
                                    <div class="col-2">
                                        <div class="input-group">
                                            <input class="input--style-5" type="email" name="email" value="<%=mb.getEmail()%>" readonly="readonly">
                                        </div>
                                    </div>
                                    
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-row m-b-55">
                            <div class="name">CP</div>
                            <div class="value">
                                <div class="row row-refine">
                                    <div class="col-3">
                                        <div class="input-group-desc">
                                            <input class="input--style-5" type="text" name="cp" value="<%=mb.getCp()%>" readonly="readonly">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-row m-b-55">
                            <div class="name">LP</div>
                            <div class="value">
                                <div class="row row-refine">
                                    <div class="col-3">
                                        <div class="input-group-desc">
                                            <input class="input--style-5" type="text" name="lp" value="<%=mb.getLp()%>" readonly="readonly">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                          <div class="form-row m-b-55">
                            <div class="name">Hearts</div>
                            <div class="value">
                                <div class="row row-refine">
                                    <div class="col-3">
                                        <div class="input-group-desc">
                                            <input class="input--style-5" type="text" name="lp" value="<%=mb.getHearts()%>" readonly="readonly">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-row m-b-55">
                            <div class="name">Level</div>
                            <div class="value">
                                <div class="row row-refine">
                                    <div class="col-3">
                                        <div class="input-group-desc">
                                            <input class="input--style-5" type="text" name="level" value="<%=mb.getLevel()%>" readonly="readonly">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-row m-b-55">
                            <div class="name">Date</div>
                            <div class="value">
                                <div class="row row-space">
                                    <div class="col-2">
                                        <div class="input-group-desc">
                                            <input class="input--style-5" type="text" name="date" value="<%=mb.getDate()%>" readonly="readonly">
                                        </div>
                                    </div>
                                    
                                </div>
                            </div>
                        </div>
                       
                        <div style="text-align:center;">
                            <button class="btn btn--radius-2 btn--red" type="submit">수   정 </button>
                            <button class="btn btn--radius-2 btn--red" type="button" onclick="location.href='TextList.te?receiver=<%=mb.getNickname() %>'" >쪽 지 함</button>
                            <!-- 쪽지함 클릭시 페이지 이동안하고 그대로 UpdateForm에 머무름 / 삭제시에도 Main으로 가지않고 updateForm에 머무름 -->
                            <button class="btn btn--radius-2 btn--red" type="button" onclick="delete_member()">탈   퇴 </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Jquery JS-->
    <script src="regform/vendor/jquery/jquery.min.js"></script>
    <!-- Vendor JS-->
    <script src="regform/vendor/select2/select2.min.js"></script>
    <script src="regform/vendor/datepicker/moment.min.js"></script>
    <script src="regform/vendor/datepicker/daterangepicker.js"></script>

    <!-- Main JS-->
    <script src="js/global.js"></script>

</body><!-- This templates was made by Colorlib (https://colorlib.com) -->

</html>
<!-- end document-->