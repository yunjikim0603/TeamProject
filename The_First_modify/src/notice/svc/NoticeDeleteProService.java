package notice.svc;

import static db.JdbcUtil.*;
import java.sql.Connection;

import notice.dao.NoticeDAO;


public class NoticeDeleteProService {

	public boolean isArticleWriter(int num) {
//		System.out.println("NoticeDeleteProService - isArticleWriter()");
		boolean isArticleWriter = false;
		
		Connection con = getConnection();
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		noticeDAO.setConnection(con);
		// NoticeDAO 클래스의 isArticleWriter() 메서드를 호출
		// 파라미터 : num    리턴타입 : boolean(isArticleWriter)
		isArticleWriter = noticeDAO.isArticleWriter(num);
		
		close(con);
		
		return isArticleWriter;
	}

	public boolean removeArticle(int num) {
		boolean isRemoveSuccess = false;
		
		Connection con = getConnection();
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		noticeDAO.setConnection(con);
		
		// NoticeDAO 클래스의 deleteArticle() 메서드 호출하여 게시물 삭제
		// => 파라미터 : 글번호(num)   리턴타입 : int(deleteCount)
		int deleteCount = noticeDAO.deleteArticle(num);
		
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





















