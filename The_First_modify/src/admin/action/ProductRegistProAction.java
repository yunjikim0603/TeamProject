package admin.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import admin.svc.ProductRegistProService;
import admin.vo.ActionForward;
import shop.vo.ShopBean;

public class ProductRegistProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ProductRegistProAction!");
		ActionForward forward = null;
		ShopBean shopBean = null;
		
		String saveFolder = "/admin/productUpload";
		ServletContext context = request.getServletContext();
		String realFolder = context.getRealPath(saveFolder);
		int fileSize = 1024 * 1024 * 5;
		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8", 
				new DefaultFileRenamePolicy()); 
		
		shopBean = new ShopBean();
		// 주의사항! request.getParameter() 메서드 대신 multi.getParameter() 메서드 사용 필수!
		shopBean.setProduct_cod(multi.getParameter("product_cod"));
		shopBean.setProduct_name(multi.getParameter("product_name"));
		shopBean.setPrice(Integer.parseInt(multi.getParameter("price")));
		shopBean.setStock(Integer.parseInt(multi.getParameter("stock")));
		shopBean.setProduct_info(multi.getParameter("product_info"));
		shopBean.setProduct_image(multi.getOriginalFileName((String)multi.getFileNames().nextElement()));
		shopBean.setBarcode_image(multi.getOriginalFileName((String)multi.getFileNames().nextElement()));
		
		ProductRegistProService productRegistProService = new ProductRegistProService();
		//상품 등록 작업을 위한 registProduct() 메서드 호출
		// => 파라미터 : 상품 정보(ShopBean 객체), 리턴값 : 게시물 등록 성공 여부(boolean)
		boolean isRegistSuccess = productRegistProService.registProduct(shopBean);
				
		System.out.println("isRegistSuccess = " + isRegistSuccess);
		
		
		// 글쓰기 성공 여부에 따른 후속 처리(이동 작업)
		// => 성공 시 : ActionForward 객체를 생성하여 상품목록(ProductList.ad) 페이지 요청(Redirect 방식)
		if(!isRegistSuccess) { // 등록 실패
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); 
			out.println("<script>"); // 자바스크립트 실행을 위한 <script> 시작 태그
			out.println("alert('상품 등록 실패!')"); // alert dialog 출력
			out.println("history.back()"); // 또는 out.println("history.go(-1)");  // 이전 페이지로 돌아가기
			out.println("</script>"); // 자바스크립트 종료 위한 <script> 끝 태그
		} else { // 등록 성공
			forward = new ActionForward(); // ActionForward 객체 생성
			forward.setPath("ProductList.ad"); // 서블릿 주소 지정
			forward.setRedirect(true); // Redirect 방식(true) 지정
		}
		
		// ActionForward 객체 리턴
		return forward; 
	}

}

















