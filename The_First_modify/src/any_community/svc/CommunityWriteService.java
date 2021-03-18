package any_community.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import any_community.dao.CommunityDAO;
import any_community.vo.CommunityBean;

public class CommunityWriteService {

	public boolean writeArticle(CommunityBean cb) {
		System.out.println("CommunityWriteService");
		boolean isWriteSuccess = false;

		Connection con = getConnection();
		CommunityDAO cdao = CommunityDAO.getInstance();
		cdao.setConnection(con);
		
		int insertCount = cdao.writeArticle(cb);
		
		if(insertCount > 0) {
			commit(con);
			isWriteSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isWriteSuccess;
	}

}
