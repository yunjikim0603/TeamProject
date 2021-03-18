package notice.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import notice.dao.NoticeDAO;
import notice.vo.NoticeCommentBean;


public class NoticeCommentModifyService {
	
	public boolean updateComment(NoticeCommentBean noticeCommentBean) {
		System.out.println("noticeCommentModifyService - updateCmmntFree()");
		boolean isSuccess = false;
		int updateCount = 0;
		
		Connection con = getConnection();
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		noticeDAO.setConnection(con);
		
		updateCount = noticeDAO.updateComment(noticeCommentBean);
		
		if (updateCount > 0) {
			commit(con);
			isSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}

}
