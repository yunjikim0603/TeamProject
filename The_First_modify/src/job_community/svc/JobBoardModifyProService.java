package job_community.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import job_community.dao.JobBoardDAO;
import job_community.vo.JobBoardBean;

public class JobBoardModifyProService {

	public boolean modifyArticle(JobBoardBean article) throws Exception {
		System.out.println("JobModifyProService");
		int updateCount = 0;
		boolean isModifySuccess = false;
		
		Connection con = getConnection();
		JobBoardDAO boardDAO = JobBoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		updateCount = boardDAO.updateArticle(article);
		
		if(updateCount > 0) {
			commit(con);
			isModifySuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isModifySuccess;
	}
}
