package academy_community.svc;

import static academy_community.db.JdbcUtil.*;

import java.sql.Connection;

import academy_community.dao.AcademyDAO;

public class AcademyDeleteProService {

	public boolean removeArticle(int num) {
		boolean isRemoveSuccess = false;
		
		Connection con = getConnection();
		AcademyDAO boardDAO = AcademyDAO.getInstance();
		boardDAO.setConnection(con);
		
		// BoardDAO 클래스의 deleteArticle() 메서드 호출하여 게시물 삭제
		// => 파라미터 : 글번호(board_num)   리턴타입 : int(deleteCount)
		int deleteCount = boardDAO.deleteArticle(num);
	
		
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