package shop.action;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shop.svc.ShopViewService;
import shop.vo.ActionForward;
import shop.vo.ShopBean;



public class ShopViewAction implements Action {
	

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ShopViewAction!");
		ActionForward forward = null;
		
		// 전달받은 product_cod값 가져오기
		String product_cod = request.getParameter("product_cod");
		
		// ShopViewService 클래스의 getShopView() 메서드를 호출하여 상세 정보 가져오기
		// => 파라미터 : product_cod    리턴타입 : ShopBean(shopBean)
		ShopViewService shopViewService = new ShopViewService();
		ShopBean shopBean = shopViewService.getShopView(product_cod);

		
		// ShopBean 객체를 request 객체에 저장
		request.setAttribute("shop", shopBean);
		
		Cookie todayImageCookie = new Cookie("today" + product_cod, URLEncoder.encode(shopBean.getProduct_image(), "UTF-8"));
		
		// => 톰캣 환경에서 쿠키값을 한글로 지정할 때 발생하는 오류 해결책 : UTF-8 로 인코딩
		todayImageCookie.setMaxAge(60 * 60 * 24); // 60초 -> 60분 -> 24시 = 1일
		// response 객체에 쿠키 전달
		response.addCookie(todayImageCookie);
		
		// ActionForward 객체를 사용하여 shop 폴더의 shop_view.jsp 페이지로 이동 설정
		forward = new ActionForward();
		forward.setPath("/shop/shop_view.jsp");
		
		return forward;
	}

}






