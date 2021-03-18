package any_community.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import any_community.dao.CommentDAO;
import any_community.vo.AnyCommentBean;

public class CommentWriteService {

	public boolean writeComment(AnyCommentBean anyCommentBean) {
		System.out.println("CommentWriteService");
		boolean isWriteSuccess = false;

		Connection con = getConnection();
		CommentDAO cdao = CommentDAO.getInstance();
		cdao.setConnection(con);

		int insertCount = cdao.writeComment(anyCommentBean);

		if (insertCount > 0) {
			commit(con);
			isWriteSuccess = true;
		} else {
			rollback(con);
		}

		close(con);

		return isWriteSuccess;
	}

}
