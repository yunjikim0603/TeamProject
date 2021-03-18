package coding.svc;

import java.sql.Connection;
import java.util.ArrayList;

import coding.dao.CodingDAO;

import static db.JdbcUtil.*;

public class CodingDeleteProService {

	public boolean removeArticle(int num) {
		boolean isDeleteSuccess =false;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		// BoardDAO 클래스의 deleteArticle() 메서드 호출하여 게시물 삭제
		// => 파라미터 : 글번호(board_num)   리턴타입 : int(deleteCount)
		int deleteCount = codingDAO.deleteArticle(num);
		
		// deleteCount 가 0보다 크면 commit 수행 및 isRemoveSuccess 를 true 로 변경
		// 아니면 rollback 수행
		if(deleteCount > 0) {
			commit(con);
			isDeleteSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isDeleteSuccess;
	}


}
