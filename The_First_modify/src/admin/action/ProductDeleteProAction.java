package admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.svc.ProductDeleteProService;
import admin.vo.ActionForward;

public class ProductDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ProductDeleteProAction");
		
		ActionForward forward = null;
		
		// 파라미터로 전달된 상품코드 (product_cod) 가져와서 변수에 저장
		String product_cod = request.getParameter("product_cod");
		
		ProductDeleteProService productdeleteProService = new ProductDeleteProService();

			// ProductDeleteProService 클래스의 removeProduct() 메서드를 호출하여 삭제 작업 수행
			// => 파라미터 : 상품코드(product_cod)    리턴타입 : boolean(isDeleteSuccess)
			boolean isDeleteSuccess = productdeleteProService.removeProduct(product_cod);
			
			// isDeleteSuccess 가 false 일 경우
			// => 자바스크립트를 사용하여 삭제 실패! 출력 후 이전페이지로 돌아가기
			// isDeleteSuccess 가 true 일 경우
			// => ActionForward 객체를 사용하여 상품목록(ProductList.ad)
			if(!isDeleteSuccess) {
				// 리턴받은 수정 결과(isModifySuccess)가 false 일 경우
				// => 자바스크립트를 사용하여 "글 수정 실패!" 출력 후 이전 페이지로 이동
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('글 삭제 실패!')");
				out.println("history.back()");
				out.println("</script>");
			} else {
				forward = new ActionForward();
				forward.setPath("ProductList.ad");
				forward.setRedirect(true);
			}
			
		
		
		
		return forward;
	}

}
