package shop.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.vo.MemberBean;
import shop.svc.ShopPaymentSuccessService;
import shop.vo.ActionForward;

public class PShopPaymentSuccessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("PShopPaymentSuccessAction");
		
		HttpSession session= request.getSession();
		
		String nickname = (String)session.getAttribute("nickname"); //세션 닉네임
		int cp = Integer.parseInt(request.getParameter("price"));
			
		
		//MemberBean shop 객체에 저장
		MemberBean shop = new MemberBean();
		shop.setNickname(nickname);
		shop.setCp(cp);
		
		ShopPaymentSuccessService shopPaymentSuccessService = new ShopPaymentSuccessService();
		boolean isUpdateSuccess = shopPaymentSuccessService.increasePoint(shop);
		
		if(!isUpdateSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('오류! 관리자에게 문의하세요.')");
			out.println("location.href='PShopList.shop'");
			out.println("</script>");
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('포인트 충전이 완료되었습니다.')");
			out.println("location.href='ShopMain.shop'");
			out.println("</script>");
		}

		return null;
	}

}
