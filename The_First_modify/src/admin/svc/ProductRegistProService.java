package admin.svc;

import static db.JdbcUtil.*;
import java.sql.Connection;

import shop.dao.ShopDAO;
import shop.vo.ShopBean;


public class ProductRegistProService {

	public boolean registProduct(ShopBean shopBean) {
		System.out.println("ProductRegistProService!");
		
		Connection con = getConnection();
		ShopDAO shopDAO = ShopDAO.getInstance();
		shopDAO.setConnection(con);
		
		// => 글쓰기 작업을 위해 ShopDAO 객체의 insertProduct() 메서드 호출
		//    => 파라미터 : ShopBean 객체, 리턴값 : int타입 글 등록 성공에 대한 결과값
		int insertCount = shopDAO.insertProduct(shopBean);
		
		boolean isRegistSuccess = false; // 글 등록 성공 여부를 리턴하기 위한 변수
		
		// insertCount 가 0보다 크면 성공, 아니면 실패
		if(insertCount > 0) {
			// 글쓰기 성공 시 commit 작업 수행 및 isWriteSuccess 변수값을 true 로 변경
			commit(con);
			
//			// 글쓰기 성공 후 참조글번호(board_re_ref) 업데이트를 위한 작업 수행
//			boardDAO.updateBoard_re_ref(boardBean);
//			commit(con);
			
			isRegistSuccess = true;
		} else {
			// 글쓰기 실패 시 rollback 작업 수행
			rollback(con);
		}
		
		// 5. Connection 객체 반환하기
		close(con);
		
		
		// 6. 글쓰기 성공 여부 리턴
		return isRegistSuccess;
	}

}














