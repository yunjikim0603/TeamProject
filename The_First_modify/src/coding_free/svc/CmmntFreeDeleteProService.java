package coding_free.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import coding_free.dao.CodingFreeDAO;

import static db.JdbcUtil.*;

public class CmmntFreeDeleteProService {

	public boolean deleteCmmnt(int comment_num) {
		boolean isDeleteSuccess = false;
		
		Connection con = getConnection();
		CodingFreeDAO cfDAO = CodingFreeDAO.getInstance();
		cfDAO.setConnection(con);
		
		// BoardDAO 클래스의 deleteArticle() 메서드 호출하여 게시물 삭제
		// => 파라미터 : 글번호(board_num)   리턴타입 : int(deleteCount)
		int deleteCount = cfDAO.deleteCmmnt(comment_num);
		
		// deleteCount 가 0보다 크면 commit 수행 및 isRemoveSuccess 를 true 로 변경
		// 아니면 rollback 수행
		if(deleteCount > 0) {
			System.out.println("삭제됐나여");
			commit(con);
			isDeleteSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isDeleteSuccess;
	}
	
	public boolean deleteCmmntHeart(int comment_num) {
		boolean isDeleteSuccess = false;
		
		Connection con = getConnection();
		CodingFreeDAO cfDAO = CodingFreeDAO.getInstance();
		cfDAO.setConnection(con);
		
		// BoardDAO 클래스의 deleteArticle() 메서드 호출하여 게시물 삭제
		// => 파라미터 : 글번호(board_num)   리턴타입 : int(deleteCount)
		int deleteCount = cfDAO.deleteFreeCmmntHeart(comment_num);
		
		// deleteCount 가 0보다 크면 commit 수행 및 isRemoveSuccess 를 true 로 변경
		// 아니면 rollback 수행
		if(deleteCount >= 0) {
			System.out.println("삭제됐나여");
			commit(con);
			isDeleteSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isDeleteSuccess;
	}

}
