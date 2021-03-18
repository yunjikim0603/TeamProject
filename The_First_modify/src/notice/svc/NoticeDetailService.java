package notice.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import notice.dao.NoticeDAO;
import notice.vo.NoticeBean;



public class NoticeDetailService {
	
	public NoticeBean getArticle(int num) throws Exception {
		System.out.println("NoticeDetailService - getArticle()");

		Connection con = getConnection();
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		noticeDAO.setConnection(con);
		
		NoticeBean article = null;
		
		// NoticeDAO 클래스의 selectArticle() 메서드 호출하여 DB 작업 수행
		// => 파라미터 : num, 리턴타입 : NoticeBean
		article = noticeDAO.selectArticle(num);
		
		close(con);
		
		return article;
	}
	
	public void plusReadcount(int num) throws Exception {
		// 조회수 1 증가
		Connection con = getConnection();
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		noticeDAO.setConnection(con);
		
		// NoticeDAO 클래스의 updateReadcount() 메서드 호출하여 DB 작업 수행
		// => 파라미터 : num, 리턴타입 : int
		int updateCount = noticeDAO.updateReadcount(num);
		
		// 리턴된 결과(updateCount) 가 0보다 크면 commit, 아니면 rollback 수행 
		if(updateCount > 0) {
			commit(con);
		} else {
			rollback(con);
		}
		
		close(con);
		
	}
	
}











