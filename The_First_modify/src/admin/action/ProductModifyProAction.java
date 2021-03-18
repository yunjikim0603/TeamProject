package admin.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import admin.svc.ProductModifyProService;
import admin.vo.ActionForward;
import shop.svc.ShopViewService;
import shop.vo.ShopBean;

public class ProductModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ProductModifyProAction");
		
		ActionForward forward = null;
		
		// 파라미터로 전달된 상품코드 (product_cod) 가져와서 변수에 저장
		String product_cod = request.getParameter("product_cod");

		System.out.println(product_cod);
		
		String saveFolder = "/admin/productUpload"; 
		System.out.println("11");
		
		ServletContext context2 = request.getServletContext();
		System.out.println("22");
		
		String realFolder = context2.getRealPath(saveFolder);
		System.out.println("333");
		
		int fileSize = 1024*1024*10; //10Mbyte
		
		
		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		System.out.println("444");
		
		
		//MultipartRequest 객체로부터 전달 된 파라미터들 가져오기
//		String buyer_id =multi.getParameter("buyer_id");
		String product_name =multi.getParameter("product_name");
		int price =Integer.parseInt(multi.getParameter("price"));
		int stock =Integer.parseInt(multi.getParameter("stock"));
//		String product_image = multi.getOriginalFileName((String)multi.getFileNames().nextElement());
		String product_info =multi.getParameter("product_info");
		int purchase_count =Integer.parseInt(multi.getParameter("purchase_count"));
//		int qty = Integer.parseInt(multi.getParameter("qty"));
		String barcode_image = multi.getOriginalFileName((String)multi.getFileNames().nextElement());
		System.out.println("555");
		
		
		
//		System.out.println(buyer_id);
		System.out.println(product_cod);
		System.out.println(product_name);
		System.out.println(price);
		System.out.println(stock);
//		System.out.println(product_image);
		System.out.println(product_info);
		System.out.println(purchase_count);
//		System.out.println(qty);
		System.out.println(barcode_image);
		
		
		ShopViewService shopViewService = new ShopViewService();
		ShopBean shopBean = shopViewService.getShopView(product_cod);
		
		//변경된 이미지가 null이면 기존이미지로 
//		if(product_image == null) {
//			product_image = shopBean.getProduct_image();
//		}
		
		if(barcode_image == null) {
			barcode_image = shopBean.getBarcode_image();
		}
		
		
			ShopBean product = new ShopBean();
			product.setProduct_cod(product_cod);
//			product.setProduct_name(request.getParameter("product_name"));
//			product.setPrice(Integer.parseInt(request.getParameter("price")));
//			product.setStock(Integer.parseInt(request.getParameter("stock")));
//			product.setPurchase_count(Integer.parseInt(request.getParameter("purchase_count")));
//			product.setBuyer_id(buyer_id);
			product.setProduct_name(product_name);
			product.setPrice(price);
			product.setStock(stock);
//			product.setProduct_image(product_image);
			product.setBarcode_image(barcode_image);
//			product.setQty(qty);
			product.setProduct_info(product_info);
			product.setPurchase_count(purchase_count);
			
			
			// ShopBean 객체에 수정된 게시물 내용 저장 후
			// ProductModifyProService 클래스의 modifyProduct() 메서드를 호출
			// => 파라미터 : product   리턴타입 : boolean(isModifySuccess)
			ProductModifyProService productModifyProService = new ProductModifyProService();
			boolean isModifySuccess = productModifyProService.modifyProduct(product);
			
			if(!isModifySuccess) {
				// 리턴받은 수정 결과(isModifySuccess)가 false 일 경우
				// => 자바스크립트를 사용하여 "글 수정 실패!" 출력 후 이전 페이지로 이동
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('글 수정 실패!')");
				out.println("history.back()");
				out.println("</script>");
			} else {
				// 리턴받은 수정 결과(isModifySuccess)가 true 일 경우
				// => ActionForward 객체를 사용하여 수정된 게시물로 이동(Redirect 방식)
				//    (URL : NoticeDetail.no?num=x&page=y)
				forward = new ActionForward();
				forward.setPath("ProductList.ad");
				forward.setRedirect(true);
			}
			
			

		
		return forward;
	}

}
