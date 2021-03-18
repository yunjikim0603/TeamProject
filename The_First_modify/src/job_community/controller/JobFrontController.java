package job_community.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import job_community.action.Action;
import job_community.action.JobBoardDeleteProAction;
import job_community.action.JobBoardDetailAction;
import job_community.action.JobBoardListAction;
import job_community.action.JobBoardModifyFormAction;
import job_community.action.JobBoardModifyProAction;
import job_community.action.JobBoardWriteProAction;
import job_community.action.JobCommentDeleteProAction;
import job_community.action.JobCommentListAction;
import job_community.action.JobCommentModifyFormAction;
import job_community.action.JobCommentModifyProAction;
import job_community.action.JobCommentPagingAction;
import job_community.action.JobCommentWriteProAction;
import job_community.vo.ActionForward;

@WebServlet("*.job")
public class JobFrontController extends HttpServlet {

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//post방식 요청에 대한 한글처리
		request.setCharacterEncoding("UTF-8");

		String command = request.getServletPath();
		Action action = null;
		ActionForward forward = null;
		
		if(command.equals("/JobBoardWriteForm.job")) {
			forward = new ActionForward();
			forward.setPath("/job_community/job_board_write.jsp");
			
		} else if(command.equals("/JobBoardWritePro.job")) {
			action = new JobBoardWriteProAction();
			
			try {
				forward = new ActionForward();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/JobBoardList.job")) {
			action = new JobBoardListAction();
			System.out.println("/JobBoardList.job");
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/JobBoardDetail.job")) {
			action = new JobBoardDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/JobBoardModifyForm.job")) {
			action = new JobBoardModifyFormAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/JobBoardModifyPro.job")) {
			action = new JobBoardModifyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/JobBoardDeletePro.job")) {
			action = new JobBoardDeleteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/JobCommentWritePro.job")) {
			action = new JobCommentWriteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/JobCommentList.job")) {
			action = new JobCommentListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/JobCommentPaging.job")) {
			action = new JobCommentPagingAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/JobCommentModifyForm.job")) {
			action = new JobCommentModifyFormAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/JobCommentModifyPro.job")) {
			action = new JobCommentModifyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/JobCommentDeletePro.job")) {
			action = new JobCommentDeleteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
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