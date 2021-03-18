<%@page import="academy_community.vo.AcademyBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
AcademyBean article = (AcademyBean)request.getAttribute("article");
String nowPage = (String)request.getAttribute("page");
String address = request.getParameter("addr");
String aname = request.getParameter("aname");
%>
<html>
<head>
<style>
	#map {text-align:center}
</style>
</head>
<body>
		
		<div id="map" style="width:450px;height:350px;"></div><br>
		학원주소 : <input type="text" id="address" style="border: 0px;" value="<%=address %>"><br>
		학원명 : <input type="text" id="academy_name" style="border: 0px;" value="<%=aname%>"><br>
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
            content: '<div style="width:150px;text-align:center;padding:6px 0;">'+document.getElementById("academy_name").value+'</div>'			       	
        });
        infowindow.open(map, marker);

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
    } 
});    
</script>
		
</body>
</html>