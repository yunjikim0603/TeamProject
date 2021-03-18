package coding.svc;

import java.sql.Connection;

import coding.dao.CodingDAO;
import coding.vo.CodingBean;
import coding.vo.Coding_refBean;

import static db.JdbcUtil.*;

public class CodingDetailService {

	public CodingBean getArticle(int num) {
		System.out.println("CodingDetailService - getArticle");
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		CodingBean article = null;
		
		article = codingDAO.selectArticle(num);
		
		close(con);
		return article;
	}

	public boolean updateReadcount(int num) {
		boolean isUpdated = false;
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		// BoardDAO 클래스의 updateReadcount() 메서드 호출하여 DB 작업 수행
		// => 파라미터 : board_num, 리턴타입 : int
		int updateCount = codingDAO.updateReadcount(num);
		
		// 리턴된 결과(updateCount) 가 0보다 크면 commit, 아니면 rollback 수행 
		if(updateCount > 0) {
			commit(con);
			isUpdated = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isUpdated;
	}

	public Coding_refBean getArticle_ref(int num) {
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		Coding_refBean article_ref = codingDAO.selectArticle_ref(num);
		
		close(con);
		
		return article_ref;
	}

}
