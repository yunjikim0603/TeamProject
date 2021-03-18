package notice.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import notice.dao.NoticeDAO;
import notice.vo.NoticeBean;


public class NoticeModifyProService {
	
	public boolean isArticleWriter(int num) throws Exception {
		// 글 번호에 해당하는 게시물에 저장된 패스워드와 입력받은 패스워드를 비교하여 일치여부 리턴
		System.out.println("NoticeModifyProService - isArticleWriter()");

		boolean isArticleWriter = true;

		return isArticleWriter;
	}
	

	public boolean modifyArticle(NoticeBean article) throws Exception {
		int updateCount = 0;
		boolean isModifySuccess = false;
		
		Connection con = getConnection();
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		noticeDAO.setConnection(con);
		
		// NoticeDAO 클래스의 updateArticle() 메서드를 호출하여 글 수정
		// => 파라미터 : NoticeBean    리턴타입 : int(updateCount)
		updateCount = noticeDAO.updateArticle(article);
		
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











