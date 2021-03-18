package any_community.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import any_community.dao.CommentDAO;

public class CommentDeleteProService {

	public boolean deleteComment(int comment_num) {
		System.out.println("CommentDeleteProService - deleteComment");
		boolean isDeleteSuccess = false;

		Connection con = getConnection();
		CommentDAO cdao = CommentDAO.getInstance();
		cdao.setConnection(con);
		
		int deleteCount = cdao.deleteComment(comment_num);

		if(deleteCount > 0) {
			commit(con);
			isDeleteSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isDeleteSuccess;
	}

	public boolean deleteAllComment(int num) {
		System.out.println("CommentDeleteProService - deleteComment");
		boolean isDeleteSuccess = false;
		
		Connection con = getConnection();
		CommentDAO cdao = CommentDAO.getInstance();
		cdao.setConnection(con);
		
		int deleteCount = cdao.deleteAllComment(num);
	
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
