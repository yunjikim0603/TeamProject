package any_community.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import any_community.dao.CommunityDAO;
import any_community.vo.CommunityBean;

public class CommunityModifyService {

	public boolean modifyArticle(CommunityBean article) {
		System.out.println("CommunityModifyService - modifyArticle");
		boolean isModifySuccess = false;
		int updateCount = 0;
		
		Connection con = getConnection();
		CommunityDAO cdao = CommunityDAO.getInstance();
		cdao.setConnection(con);
		
		updateCount = cdao.updateArticle(article);
		
		if (updateCount > 0) {
			commit(con);
			isModifySuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isModifySuccess;
	}

}
