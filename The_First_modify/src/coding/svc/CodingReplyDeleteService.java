package coding.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import coding.dao.CodingDAO;
import static db.JdbcUtil.*;

public class CodingReplyDeleteService {

	public boolean deleteReply(int post_num) {
		boolean isDeleteSuccess = false;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		int deleteCount = codingDAO.deleteReply(post_num);
		
		if(deleteCount >= 0) {
			commit(con);
			isDeleteSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isDeleteSuccess;
	}
	
	public boolean deleteReply(int post_num, int reply_num) {
		boolean isDeleteSuccess = false;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		int deleteCount = codingDAO.deleteReply(post_num, reply_num);
		
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
