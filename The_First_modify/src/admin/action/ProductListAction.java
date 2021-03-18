package admin.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.svc.ProductListService;
import admin.vo.ActionForward;
import shop.vo.ShopBean;

public class ProductListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ProductListAction!");
		
		ActionForward forward = null;
		
		// ProductListService 인스턴스 생성 후 상품 목록 가져오기
		ProductListService productListService = new ProductListService();

		// ProductListService 객체의 getProductList() 메서드를 호출하여 게시물 목록 가져오기
		// => 파라미터로 현재 페이지(page) 와 게시물 수(limit) 를 전달
		// => ArrayList<ShopBean> 타입 객체 리턴
		ArrayList<ShopBean> shopList = null;
		shopList = productListService.getListProduct();
		

		// request 객체의 setAttribute() 게시물 목록 저장
		request.setAttribute("shopList", shopList);
		
		
		// ActionForward 객체를 생성하여 admin 폴더의 admin_list.jsp 페이지로 이동(Dispatch 방식)
		forward = new ActionForward();
		forward.setPath("/admin/product_list.jsp");
		// forward.setRedirect(false) 는 생략 가능(기본값)
		
		return forward;
	}

}



















