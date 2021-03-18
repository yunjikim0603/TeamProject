package job_community.svc;

import java.sql.Connection;

import job_community.dao.JobBoardDAO;

import static db.JdbcUtil.*;

import job_community.vo.JobBoardBean;

public class JobBoardWriteProService {

	public boolean insertArticle(JobBoardBean jobBoardBean) {
		System.out.println("JobBoardWriteService");
		boolean isWriteSuccess = false;
		
		Connection con = getConnection();
		JobBoardDAO jobBoardDAO = JobBoardDAO.getInstance();
		jobBoardDAO.setConnection(con);
		
		int insertCount = jobBoardDAO.insertArticle(jobBoardBean);
		
		if(insertCount > 0) {
			commit(con);
			isWriteSuccess = true;
			
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isWriteSuccess;
	}

}
