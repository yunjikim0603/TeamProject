package academy_community.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import academy_community.dao.AcademyCommentDAO;
import academy_community.vo.AcademyCommentBean;

public class AcademyCommentModifyService {

	public AcademyCommentBean getComment(int post_num, int modify_num) {
		System.out.println("AcademyCommentModifyService - getComment");
		
		Connection con = getConnection();
		AcademyCommentDAO cdao = AcademyCommentDAO.getInstance();
		cdao.setConnection(con);

		AcademyCommentBean comment = new AcademyCommentBean();
		
		comment = cdao.getComment(post_num, modify_num);
		
		close(con);
		
		return comment;
	}

	public static boolean updateComment(AcademyCommentBean academyCommentBean) {
		System.out.println("AcademyCommentModifyService - updateComment");
		boolean isUpdateSuccess = false;
		int updateCount = 0;
		
		Connection con = getConnection();
		AcademyCommentDAO cdao = AcademyCommentDAO.getInstance();
		cdao.setConnection(con);
		
		AcademyCommentBean comment = new AcademyCommentBean();
		
		updateCount = cdao.updateComment(academyCommentBean);
		
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
