package any_community.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import any_community.dao.CommunityDAO;

public class CommunityDeleteProService {

	public boolean deleteArticle(int num) {
		System.out.println("CommunityDeleteProService");
		boolean isDeleteSuccess = false;
		int updateCount = 0;

		Connection con = getConnection();
		CommunityDAO cdao = CommunityDAO.getInstance();
		cdao.setConnection(con);
		
		updateCount = cdao.deleteArticle(num);
		
		if (updateCount > 0) {
			commit(con);
			isDeleteSuccess = true;
		} else {
			rollback(con);
		}

		close(con);
		return isDeleteSuccess;
	}

}
