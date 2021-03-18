package any_community.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import any_community.dao.CommentDAO;
import any_community.vo.AnyCommentBean;

public class CommentModifyService {

	public AnyCommentBean getComment(int post_num, int modify_num) {
		System.out.println("CommentModifyService - getComment");
		
		Connection con = getConnection();
		CommentDAO cdao = CommentDAO.getInstance();
		cdao.setConnection(con);

		AnyCommentBean comment = new AnyCommentBean();
		
		comment = cdao.getComment(post_num, modify_num);
		
		close(con);
		
		return comment;
	}

	public static boolean updateComment(AnyCommentBean anyCommentBean) {
		System.out.println("CommentModifyService - updateComment");
		boolean isUpdateSuccess = false;
		int updateCount = 0;
		
		Connection con = getConnection();
		CommentDAO cdao = CommentDAO.getInstance();
		cdao.setConnection(con);

		AnyCommentBean comment = new AnyCommentBean();
		
		updateCount = cdao.updateComment(anyCommentBean);
		
		if (updateCount > 0) {
			commit(con);
			isUpdateSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isUpdateSuccess;
	}

}
