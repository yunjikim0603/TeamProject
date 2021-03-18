package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.action.Action;
import member.action.MemberDeleteAction;
import member.action.MemberJoinProAction;
import member.action.MemberLoginProAction;
import member.action.MemberLogoutAction;
import member.action.MemberMailCheckAction;
import member.action.MemberMailSendAction;
import member.action.MemberUpdateFormAction;
import member.action.MemberUpdateProAction;
import member.action.NewLoginAction;
import member.action.idDupCheckAction;
import member.action.nicknameDupCheckAction;
import member.vo.ActionForward;


@WebServlet("*.me")
public class MemberFrontController extends HttpServlet {

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String command = request.getServletPath();
		
		Action action = null;
		ActionForward forward = null;
		
		System.out.println("command = " + command);
		
		//
		if(command.equals("/LoginForm.me")) {
			forward = new ActionForward();
			forward.setPath("/member/loginForm.jsp");
		} else if(command.equals("/LoginPro.me")) {
			action = new MemberLoginProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/JoinForm.me")) {
			forward = new ActionForward();
			forward.setPath("/member/joinForm.jsp");
		} else if(command.equals("/JoinPro.me")) {
			action = new MemberJoinProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/Logout.me")) {
			action = new MemberLogoutAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/Mypage.me")) {
			forward = new ActionForward();
			forward.setPath("/member/myPage.jsp");
		} else if (command.equals("/UpdateForm.me")) {
			action = new MemberUpdateFormAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/UpdatePro.me")) {
			action = new MemberUpdateProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/MemberDelete.me")) {
			action = new MemberDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/MemberMailSendAction.me")) {
			action = new MemberMailSendAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/MemberMailCheckAction.me")) {
			action = new MemberMailCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/idDupCheck.me")) {
			action = new idDupCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/nicknameDupCheck.me")) {
			action = new nicknameDupCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 새로운 로그인창
		else if(command.equals("/NewLoginForm.me")) {
			forward = new ActionForward();
			forward.setPath("/member/newLoginForm.jsp");
		} else if (command.equals("/NewLoginAction.me")) {
			action = new NewLoginAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		// 이동 설정
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else { 
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}else {
			System.out.println("ActionForward 객체 값이 null 입니다.");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}

}
