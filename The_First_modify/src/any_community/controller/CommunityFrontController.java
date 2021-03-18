package any_community.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import any_community.action.Action;
import any_community.action.CommunityDeleteProAction;
import any_community.action.CommunityDetailAction;
import any_community.action.CommunityListAction;
import any_community.action.CommunityModifyFormAction;
import any_community.action.CommunityModifyProAction;
import any_community.action.CommunityWriteProAction;
import any_community.action.NewLoginFormAction;
import any_community.vo.ActionForward;

@WebServlet("*.any")
public class CommunityFrontController extends HttpServlet{

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String command = request.getServletPath();
		
		Action action = null;
		ActionForward forward = null;
		System.out.println("command : " + command);
	
		//
		if (command.equals("/CommunityList.any")) {
			action = new CommunityListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CommunityWriteForm.any")) {
			forward = new ActionForward();
			forward.setPath("/any_community/communityWrite.jsp");
		} else if(command.equals("/CommunityWritePro.any")) {
			action = new CommunityWriteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CommunityDetail.any")) {
			action = new CommunityDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CommunityModifyForm.any")) {
			action = new CommunityModifyFormAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CommunityModifyPro.any")) {
			action = new CommunityModifyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CommunityDeletePro.any")) {
			action = new CommunityDeleteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		//
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
