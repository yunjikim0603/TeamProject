package shop.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;


import shop.dao.ShopDAO;
import shop.vo.ShopBean;

public class ShopListService {
	
	public ArrayList<ShopBean> getShopList() throws Exception{
		System.out.println("ShopListService - getShopList()");
		
		Connection con = getConnection();
		ShopDAO shopDAO = ShopDAO.getInstance();
		shopDAO.setConnection(con);
		
		// DB 작업을 위한 DAO 객체의 메서드 호출
		// => ShopDAO 객체의 selectShopList() 메서드를 호출하여 상품 목록 가져오기
		ArrayList<ShopBean> shopList = shopDAO.selectShopList();
		
		close(con);
		
		return shopList;
	}
}



















