package academy_community.svc;

import static academy_community.db.JdbcUtil.*;

import java.sql.Connection;

import academy_community.dao.AcademyCommentDAO;
import academy_community.dao.AcademyDAO;
import academy_community.vo.AcademyBean;
import academy_community.vo.AcademyCommentBean;

public class AcademyDetailService {

	public AcademyBean getArticle(int num) {
		Connection con = getConnection();
		AcademyDAO boardDAO = AcademyDAO.getInstance();
		boardDAO.setConnection(con);
		
		// BoardDAO 클래스의 selectArticle() 메서드 호출하여 DB 작업 수행
		// => 파라미터 : board_num, 리턴타입 : BoardBean
		AcademyBean article = boardDAO.selectArticle(num);
		
		// BoardDAO 클래스의 updateReadcount() 메서드 호출하여 DB 작업 수행
		// => 파라미터 : board_num, 리턴타입 : int
		int updateCount = boardDAO.updateReadcount(num);
		
		// 리턴된 결과(updateCount) 가 0보다 크면 commit, 아니면 rollback 수행 
			if(updateCount > 0) {
				commit(con);
			} else {
				rollback(con);
			}
		
		close(con);
		
		return article;
	}
	public void plusReadcount(int num) throws Exception {
		// 조회수 1 증가
		Connection con = getConnection();
		AcademyDAO boardDAO = AcademyDAO.getInstance();
		boardDAO.setConnection(con);
		
		// BoardDAO 클래스의 updateReadcount() 메서드 호출하여 DB 작업 수행
		// => 파라미터 : board_num, 리턴타입 : int
		int updateCount = boardDAO.updateReadcount(num);
		
		// 리턴된 결과(updateCount) 가 0보다 크면 commit, 아니면 rollback 수행 
		if(updateCount > 0) {
			commit(con);
		} else {
			rollback(con);
		}
		
		close(con);
		
	}


	
}
