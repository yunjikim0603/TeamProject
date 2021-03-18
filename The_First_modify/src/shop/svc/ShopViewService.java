package shop.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import shop.dao.ShopDAO;
import shop.vo.ShopBean;

public class ShopViewService {
	
	public ShopBean getShopView(String product_cod) throws Exception {
		System.out.println("ShopViewService - getShopView()");
		
		Connection con = getConnection();
		ShopDAO shopDAO = ShopDAO.getInstance();
		shopDAO.setConnection(con);
	
		
		// ShopDAO 의 selectShop() 메서드를 호출하여 상세 정보 가져오기
		// => 파라미터 : product_cod  리턴타입 : ShopBean(shopBean)
		ShopBean shopBean = shopDAO.selectShop(product_cod);
		
		close(con);
		
		
		return shopBean;
	}
	

	
	
}
