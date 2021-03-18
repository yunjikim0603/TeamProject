<%@page import="member.dao.MemberDAO"%>
<%@page import="member.vo.SHA256"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
	request.setCharacterEncoding("UTF-8");
	String code = request.getParameter("code");
	MemberDAO mdao = MemberDAO.getInstance();
// 	MemberDAO mdao = new MemberDAO();
	String id = null;
	
	if(session.getAttribute("id") != null) {
		id = (String) session.getAttribute("id");
	}
	
	if(id == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 해주세요.');");
		script.println("location.href = 'login.jsp'");
		script.println("</script>");
		script.close();
		return;
	}
	
	String email = mdao.getUserEmail(id);
	
	boolean rightCode = (new SHA256().getSHA256(email).equals(code)) ? true : false;
	
	if(rightCode == true) {
		mdao.setUserEmailChecked(id);
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('인증에 성공했습니다.');");
		script.println("location.href = '../main/main.jsp'");
		script.println("</script>");
		script.close();		
		return;
	} else {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('유효하지 않은 코드입니다.');");
		script.println("location.href = '../main/main.jsp'");
		script.println("</script>");
		script.close();		
		return;
	}
%>