package admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.vo.ActionForward;
import shop.svc.ShopViewService;
import shop.vo.ShopBean;

public class ProductModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
				// 원본 게시물 내용을 가져와서 수정폼에 함께 출력
				System.out.println("ProductModifyFormAction!");
				
				// 파라미터로 전달된 상품코드(product_cod) 가져와서 변수에 저장
				String product_cod = request.getParameter("product_cod");
				
				// 원본 게시물을 가져오기 위해 별도의 Service 클래스를 새로 정의하지 않고
				// 기존에 정의된 ShopViewService 클래스의 getShopView() 메서드를 호출하여 원본 상품 내용 가져오기
				ShopBean shopBean = null;
				ShopViewService shopViewService = new ShopViewService();
				shopBean = shopViewService.getShopView(product_cod);
				
				System.out.println(shopBean.getProduct_cod());
				
				// 원본 게시물과 페이지번호를 request 객체에 저장
				request.setAttribute("shopBean", shopBean);
				
				// ActionForward 객체를 사용하여 admin 폴더 내의 product_modify.jsp 페이지 이동 설정
				ActionForward forward = new ActionForward();
				forward.setPath("/admin/product_modify.jsp"); // Dispatch 방식으로 이동
					
		return forward;
	}

}
