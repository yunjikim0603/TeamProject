package shop.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shop.action.ShopGifticonGetAction;
import shop.action.ShopGifticonSendAction;
import shop.action.ShopListAction;
import shop.action.ShopPaymentAction;
import shop.action.Action;
import shop.action.PShopPaymentSuccessAction;
import shop.action.ShopViewAction;
import shop.vo.ActionForward;

@WebServlet("*.shop")
public class ShopFrontController extends HttpServlet {
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ShopFrontController!");
		
		//post방식 요청에 대한 한글처리
		request.setCharacterEncoding("UTF-8");
		
		// 요청된 서블릿 주소를 바로 추출
		String command = request.getServletPath();
		System.out.println("command : " + command);
		
		// 공통된 작업 처리를 위한 Action, ActionForward 타입 변수 선언
		Action action = null;
		ActionForward forward = null;
		
		//shop 메인
		if(command.equals("/ShopMain.shop")) {
			forward = new ActionForward();
			forward.setPath("/shop/shop_main.jsp");
			
//----------------------포인트샵--------------------
			
		//포인트충전 목록
		}else if(command.equals("/PShopList.shop")) {
			forward = new ActionForward();
			forward.setPath("/shop/pshop_list.jsp");
			
		//포인트 결제 API
		} else if(command.equals("/PShopPayment.shop")) {
			forward = new ActionForward();
			forward.setPath("/shop/pshop_payment.jsp");
		
		//포인트 결제완료
		} else if(command.equals("/PShopPaymentSuccess.shop")) {
			action = new PShopPaymentSuccessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
//----------------기프티콘샵----------------------
			
		//기프티콘 상품 목록
		} else if(command.equals("/ShopList.shop")) {
			action = new ShopListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}	
			
		//상품 상세정보 보기
		} else if(command.equals("/ShopView.shop")) {
			action = new ShopViewAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		//포인트로 교환하기
		} else if(command.equals("/ShopPayment.shop")) {
			action = new ShopPaymentAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}		
		
		//기프티콘 이메일로 전송
		} else if(command.equals("/ShopGifticonSend.shop")) {
			action = new ShopGifticonSendAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
		//이메일 기프티콘 링크
		} else if(command.equals("/ShopGifticonGet.shop")) {
			action = new ShopGifticonGetAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
				
		

			
		
		if(forward != null) {
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
