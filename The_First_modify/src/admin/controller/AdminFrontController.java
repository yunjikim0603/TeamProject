package admin.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.action.Action;
import admin.action.MemberDeleteProAction;
import admin.action.MemberListAction;
import admin.action.ProductDeleteProAction;
import admin.action.ProductListAction;
import admin.action.ProductModifyFormAction;
import admin.action.ProductModifyProAction;
import admin.action.ProductRegistProAction;
import admin.vo.ActionForward;

@WebServlet("*.ad")
public class AdminFrontController extends HttpServlet {

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AdminFrontController!");
		
		//post방식 요청에 대한 한글처리
		request.setCharacterEncoding("UTF-8");

		// 요청된 서블릿 주소를 바로 추출
		String command = request.getServletPath();
		System.out.println("command : " + command);
		
		// 공통된 작업 처리를 위한 Action, ActionForward 타입 변수 선언
		Action action = null;
		ActionForward forward = null;
		
		//관리자페이지
		if(command.equals("/AdminList.ad")) {
			forward = new ActionForward();
			forward.setPath("/admin/admin_list.jsp");
			
		//상품목록 및 추가 페이지
		}else if(command.equals("/ProductList.ad")) {
			action = new ProductListAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		//상품 등록
		}else if(command.equals("/ProductRegistForm.ad")) {
			forward = new ActionForward();
			forward.setPath("/admin/product_regist.jsp");

		}else if(command.equals("/ProductRegistPro.ad")) {
			action = new ProductRegistProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}	
			
		//상품 수정		
		}else if(command.equals("/ProductModifyForm.ad")) {
			action = new ProductModifyFormAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if(command.equals("/ProductModifyPro.ad")) {
			action = new ProductModifyProAction();		
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if(command.equals("/ProductDeletePro.ad")) {
			action = new ProductDeleteProAction();						
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		// 회원목록
		} else if(command.equals("/MemberList.ad")) {
			action = new MemberListAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}  else if(command.equals("/MemberDeletePro.ad")) {
			action = new MemberDeleteProAction();	
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
