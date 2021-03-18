package notice.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import notice.dao.NoticeDAO;
import notice.vo.NoticeCommentBean;

public class NoticeCommentListService {

	public List<NoticeCommentBean> getCommentList(int post_num, int cmmnt_page, int cmmnt_limit) {
		System.out.println("getCommentList");
		
		List<NoticeCommentBean> list = new ArrayList<NoticeCommentBean>();
		
		Connection con = getConnection();
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		noticeDAO.setConnection(con);
		
		list = noticeDAO.getCommentList(post_num, cmmnt_page, 3);
		
		close(con);
		
		return list;
	}
	
	
	public int getCommentListCount(int num) {
		int commentListCount = 0;
		
		Connection con = getConnection();
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		noticeDAO.setConnection(con);
		
		commentListCount = noticeDAO.getCommentListCount(num);
		
		close(con);
		
		return commentListCount;
	}

	public NoticeCommentBean getComment(int post_num, int modify_num) {
		NoticeCommentBean cmmnt = new NoticeCommentBean();
		
		Connection con = getConnection();
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		noticeDAO.setConnection(con);
		
		cmmnt = noticeDAO.getComment(post_num, modify_num);
		
		close(con);
		
		return cmmnt;
	}

}
