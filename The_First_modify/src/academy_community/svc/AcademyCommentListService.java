package academy_community.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import academy_community.dao.AcademyCommentDAO;
import academy_community.vo.AcademyCommentBean;

public class AcademyCommentListService {

	public ArrayList<AcademyCommentBean> getCommentList(int post_num, int cmmnt_page, int cmmnt_limit) {
		System.out.println("AcademyCommentListService - getCommentList");

		ArrayList<AcademyCommentBean> list = new ArrayList<AcademyCommentBean>();
		
		Connection con = getConnection();
		AcademyCommentDAO academyCommentDAO = AcademyCommentDAO.getInstance();
		academyCommentDAO.setConnection(con);
		
		list = academyCommentDAO.getCommentList(post_num,cmmnt_page,cmmnt_limit);
		
		close(con);
		
		return list;
	}

	public int getCommentListCount(int num) {
		int commentListCount = 0;
		
		Connection con = getConnection();
		AcademyCommentDAO academyCommentDAO = AcademyCommentDAO.getInstance();
		academyCommentDAO.setConnection(con);
		
		commentListCount = academyCommentDAO.getCommentListCount(num);
		
		close(con);
		
		return commentListCount;
	}

}
