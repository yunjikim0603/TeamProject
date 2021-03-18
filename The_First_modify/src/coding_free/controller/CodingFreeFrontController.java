package coding_free.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coding_free.action.Action;
import coding_free.action.CmmntDeleteHeartAction;
import coding_free.action.CmmntDeleteProAction;
import coding_free.action.CmmntFreeUpdateHeartAction;
import coding_free.action.CmmntListAction;
import coding_free.action.CmmntModifyFormAction;
import coding_free.action.CmmntModifyProAction;
import coding_free.action.CmmntPagingDetailAction;
import coding_free.action.CmmntWriteProAction;
import coding_free.action.CodingFreeDetailAction;
import coding_free.action.CodingFreeListAction;
import coding_free.action.CodingFreeWriteProAction;
import coding_free.action.CodingFreeDeleteProAction;
import coding_free.action.CodingFreeModifyFormAction;
import coding_free.action.CodingFreeModifyProAction;
import coding_free.vo.ActionForward;

@WebServlet("*.cf")
public class CodingFreeFrontController extends HttpServlet{

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String command = request.getServletPath();
		
		Action action = null;
		ActionForward forward = null;
		System.out.println("command : " + command);
	
		//
		if (command.equals("/CodingFreeList.cf")) {
			action = new CodingFreeListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CodingFreeWriteForm.cf")) {
			forward = new ActionForward();
			forward.setPath("/coding_free/codingFreeWrite.jsp");
		} else if(command.equals("/CodingFreeWritePro.cf")) {
			action = new CodingFreeWriteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CodingFreeDetail.cf")) {
			action = new CodingFreeDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CodingFreeModifyForm.cf")) {
			action = new CodingFreeModifyFormAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CodingFreeModifyPro.cf")) {
			action = new CodingFreeModifyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CodingFreeDeletePro.cf")) {
			action = new CodingFreeDeleteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CodingFreeCommentList.cf")) {
			action = new CmmntListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CmmntWritePro.cf")) {
			action = new CmmntWriteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/CmmntDeletePro.cf")) {
			action = new CmmntDeleteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/CmmntModifyForm.cf")) {
			action = new CmmntModifyFormAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CmmntModifyPro.cf")) {
			action = new CmmntModifyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CmmntFreeUpdateHeart.cf")) {
			action = new CmmntFreeUpdateHeartAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/CmmntDeleteHeart.cf")) {
			action = new CmmntDeleteHeartAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/CmmntPagingDetail.cf")) {
			action = new CmmntPagingDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/CodingFreeFileDown.cf")) {
			forward = new ActionForward();
			forward.setPath("/coding_free/file_down.jsp");
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
