package job_community.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import job_community.dao.JobBoardDAO;

public class JobBoardDeleteProService {

	public boolean removeArticle(int num) {
		System.out.println("JobBoardDeleteService");
		boolean isRemoveSuccess = false;
		
		Connection con = getConnection();
		JobBoardDAO boardDAO = JobBoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		int deleteCount = boardDAO.deleteArticle(num);
		
		if(deleteCount > 0) {
			commit(con);
			isRemoveSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isRemoveSuccess;
	}

}
