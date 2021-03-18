package notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.action.Action;
import notice.action.CommentPagingDetailAction;
import notice.action.NoticeCommentDeleteProAction;
import notice.action.NoticeCommentListAction;
import notice.action.NoticeCommentModifyFormAction;
import notice.action.NoticeCommentModifyProAction;
import notice.action.NoticeCommentWriteProAction;
import notice.action.NoticeDeleteProAction;
import notice.action.NoticeDetailAction;
import notice.action.NoticeListAction;
import notice.action.NoticeModifyFormAction;
import notice.action.NoticeModifyProAction;
import notice.action.NoticeWriteProAction;
import notice.vo.ActionForward;

@WebServlet("*.no")
public class NoticeFrontController extends HttpServlet {

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("NoticeFrontController!");
		
		//post방식 요청에 대한 한글처리
		request.setCharacterEncoding("UTF-8");

		// 요청된 서블릿 주소를 바로 추출
		String command = request.getServletPath();
		System.out.println("command : " + command);
		
		// 공통된 작업 처리를 위한 Action, ActionForward 타입 변수 선언
		Action action = null;
		ActionForward forward = null;
		
		//글 작성
		if(command.equals("/NoticeWriteForm.no")) {
			forward = new ActionForward();
			forward.setPath("/notice/notice_write.jsp");
			
		}else if(command.equals("/NoticeWritePro.no")) {
			action = new NoticeWriteProAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		//글 목록
		}else if(command.equals("/NoticeList.no")) {
			action = new NoticeListAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		//글 상세보기	
		}else if(command.equals("/NoticeDetail.no")) {
			action = new NoticeDetailAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//글 수정	
		} else if(command.equals("/NoticeModifyForm.no")) {
			action = new NoticeModifyFormAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if(command.equals("/NoticeModifyPro.no")) {
			action = new NoticeModifyProAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		//글 삭제
		} else if(command.equals("/NoticeDeletePro.no")) {
			action = new NoticeDeleteProAction();

			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		//첨부파일 다운	
		} else if(command.equals("/NoticeFileDown.no")) {
			forward = new ActionForward();
			forward.setPath("/notice/file_down.jsp");
			
//		==================댓글==================
		
		//댓글 작성
		} else if(command.equals("/NoticeCommentWritePro.no")) {
			action = new NoticeCommentWriteProAction();
				
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		//댓글 목록
		}else if(command.equals("/NoticeCommentList.no")) {
			action = new NoticeCommentListAction();
				
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		//댓글 페이징
		} else if(command.equals("/CommentPagingDetail.no")) {
			action = new CommentPagingDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		//댓글 수정
		}else if(command.equals("/NoticeCommentModifyForm.no")) {
			action = new NoticeCommentModifyFormAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if(command.equals("/NoticeCommentModifyPro.no")) {
			action = new NoticeCommentModifyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		//댓글 삭제
		}else if(command.equals("/NoticeCommentDeletePro.no")) {
			action = new NoticeCommentDeleteProAction();
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
