package admin.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import shop.dao.ShopDAO;


public class ProductDeleteProService {

	public boolean removeProduct(String product_cod) {
		System.out.println("ProductDeleteProService - removeArticle()");
		
		boolean isRemoveSuccess = false;
		
		Connection con = getConnection();
		ShopDAO shopDAO = ShopDAO.getInstance();
		shopDAO.setConnection(con);
		
		// ShopDAO 클래스의 deleteProduct() 메서드 호출하여 게시물 삭제
		// => 파라미터 : 상품코드(product_cod)   리턴타입 : int(deleteCount)
		int deleteCount = shopDAO.deleteProduct(product_cod);
		
		// deleteCount 가 0보다 크면 commit 수행 및 isRemoveSuccess 를 true 로 변경
		// 아니면 rollback 수행
		if(deleteCount > 0) {
			commit(con);
			isRemoveSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isRemoveSuccess;
	}
	
	
}
