package admin.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import shop.dao.ShopDAO;
import shop.vo.ShopBean;



public class ProductModifyProService {

//상품수정
	public boolean modifyProduct(ShopBean product) throws Exception {
		System.out.println("ProductModifyProService - modifyProduct()");
		
		int updateCount = 0;
		boolean isModifySuccess = false;
		
		Connection con = getConnection();
		ShopDAO shopDAO = ShopDAO.getInstance();
		shopDAO.setConnection(con);
		
		// ShopDAO 클래스의 updateProduct() 메서드를 호출하여 글 수정
		// => 파라미터 : ShopBean    리턴타입 : int(updateCount)
		updateCount = shopDAO.updateProduct(product);
		
		// updateCount 가 0보다 크면 commit 을 수행하고, isModifySuccess 를 true 로 변경 
		// 아니면 rollback 수행
		if(updateCount > 0) {
			commit(con);
			isModifySuccess = true;
		} else {
			rollback(con);
		}
		
		
		close(con);
		
		return isModifySuccess;
	}
	
}











