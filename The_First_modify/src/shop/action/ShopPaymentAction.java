package shop.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.vo.MemberBean;
import shop.svc.ShopPaymentSuccessService;
import shop.vo.ActionForward;
import shop.vo.ShopBean;

public class ShopPaymentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ShopPaymentAction");
		
		HttpSession session = request.getSession();
		String nickname = (String)session.getAttribute("nickname");
		
		int cp = Integer.parseInt(request.getParameter("price"));
		
		String barcode = request.getParameter("barcode");
		int stock = Integer.parseInt(request.getParameter("stock"));
		int purchase_count = Integer.parseInt(request.getParameter("purchase_count"));
		String product_cod = request.getParameter("product_cod");
		
		ShopPaymentSuccessService shopPaymentSuccessService = new ShopPaymentSuccessService();
		
		//MemberBean mb 객체에 저장
		MemberBean mb = new MemberBean();
		mb.setNickname(nickname);
		mb.setCp(cp);
		
		//ShopBean sb 객체에 저장
		ShopBean sb = new ShopBean();
		sb.setStock(stock);
		sb.setPurchase_count(purchase_count);
		sb.setProduct_cod(product_cod);
		
		boolean isUpdateSuccess = shopPaymentSuccessService.deductPoint(mb,sb);
		
		if(!isUpdateSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('포인트가 부족합니다.')");
			out.println("location.href='ShopList.shop'");
			out.println("</script>");
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('교환 완료! 기프티콘은 이메일로 발송되었습니다.')");
			out.println("location.href='ShopGifticonSend.shop?barcode=" + barcode + "'");//이메일 발송 
			out.println("</script>");
		}

		return null;
	}

}
		
		
