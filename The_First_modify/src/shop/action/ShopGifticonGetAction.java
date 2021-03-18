package shop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shop.vo.ActionForward;


public class ShopGifticonGetAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ShopGifticonGetAction");
		
		ActionForward forward = null;
		
		String barcode = request.getParameter("barcode");
		
		request.setAttribute("barcode", barcode);
		
		forward = new ActionForward(); 
		forward.setPath("/shop/shop_gifticon.jsp"); //성공시 메인으로 이동
		
		return forward;
	}

}
