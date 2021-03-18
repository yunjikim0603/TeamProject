package shop.svc;

import static db.JdbcUtil.*;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;


import member.vo.MemberBean;
import shop.dao.ShopDAO;
import shop.vo.ShopBean;


public class ShopPaymentSuccessService {


	//포인트 구매 후 증가
	public boolean increasePoint(MemberBean shop) {
		System.out.println("ShopPaymentSuccessService - updatePoint()");

		boolean isUpdateSuccess = false;
		int updateCount = 0;
		
		Connection con = getConnection();
		ShopDAO shopDAO = ShopDAO.getInstance();
		shopDAO.setConnection(con);
			
		updateCount = shopDAO.increasePoint(shop);
		
		if(updateCount > 0) {
			commit(con);
			isUpdateSuccess = true;
		}else {
			rollback(con);
		}
		close(con);
		
		return isUpdateSuccess;
		
		
	}

	//기프티콘 구매 후 차감
	public boolean deductPoint(MemberBean mb, ShopBean sb) {
		System.out.println("ShopPaymentSuccessService - deductPoint()");
		
		boolean isUpdateSuccess = false;
		int updateCount = 0;
		
		Connection con = getConnection();
		ShopDAO shopDAO = ShopDAO.getInstance();
		shopDAO.setConnection(con);
		
		updateCount = shopDAO.deductPoint(mb,sb);
		
		if(updateCount > 0) {
			commit(con);
			isUpdateSuccess = true;
		}else {
			rollback(con);
		}
		close(con);
		
		
		return isUpdateSuccess;
		
	}
	
	
	
}
