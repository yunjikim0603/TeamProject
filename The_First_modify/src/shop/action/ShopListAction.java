package shop.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shop.svc.ShopListService;
import shop.vo.ActionForward;
import shop.vo.ShopBean;


public class ShopListAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ShopListAction");
		
		ActionForward forward = null;
		
		// ShopListService 인스턴스 생성 및 getShopList() 메서드를 호출하여 상품 목록 가져오기
		// => 파라미터 : X     리턴타입 : ArrayList<ShopBean> (shopList)
		ShopListService shopListService = new ShopListService();
		ArrayList<ShopBean> shopList = shopListService.getShopList();
		
		// request 객체의 setAttribute() 메서드를 호출하여 객체 저장(shopList)
		request.setAttribute("shopList", shopList);
		
		// ActionForward 객체를 생성하여 shop 폴더 내의 shop_list.jsp 페이지 이동 설정
		forward = new ActionForward();
		forward.setPath("/shop/shop_list.jsp");
		
		return forward;
	}

}

















