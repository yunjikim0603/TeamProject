package job_community.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import job_community.dao.JobBoardDAO;
import job_community.vo.JobCommentBean;

public class JobCommentListService {

	public ArrayList<JobCommentBean> getCommentList(int post_num, int cmmnt_page, int cmmnt_limit) {
		ArrayList<JobCommentBean> cmmntList = null;
		
		Connection con = getConnection();
		JobBoardDAO jobBoardDAO = JobBoardDAO.getInstance();
		jobBoardDAO.setConnection(con);
		
		cmmntList = jobBoardDAO.getCommentList(post_num, cmmnt_page, cmmnt_limit);
		
		close(con);
		
		return cmmntList;
	}

	public int getCommentListCount(int post_num) {
		int commentListCount = 0;
		Connection con = getConnection();
		JobBoardDAO jobBoardDAO = JobBoardDAO.getInstance();
		jobBoardDAO.setConnection(con);
		
		commentListCount = jobBoardDAO.selectCommentListCount(post_num);
		
		close(con);
		
		return commentListCount;
	}

	public JobCommentBean getComment(int post_num, int comment_num) {
		JobCommentBean cmmnt = null;
		
		Connection con = getConnection();
		JobBoardDAO jobBoardDAO = JobBoardDAO.getInstance();
		jobBoardDAO.setConnection(con);
		
		cmmnt = jobBoardDAO.getComment(post_num, comment_num);
		
		close(con);
		
		return cmmnt;
	}

}
