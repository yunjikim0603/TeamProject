package job_community.svc;

import static db.JdbcUtil.*;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;

import job_community.dao.JobBoardDAO;
import job_community.vo.JobCommentBean;

public class JobCommentWriteProService {

	public boolean writeComment(JobCommentBean jobCommentBean) {
		boolean isWriteSuccess = false;
		Connection con = getConnection();
		JobBoardDAO jobBoardDAO = JobBoardDAO.getInstance();
		jobBoardDAO.setConnection(con);
		
		int success = jobBoardDAO.writeComment(jobCommentBean);
		if(success>0) {
			isWriteSuccess=true;
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		return isWriteSuccess;
	}

}
