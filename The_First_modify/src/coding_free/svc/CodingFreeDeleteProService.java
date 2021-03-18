package coding_free.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import coding_free.dao.CodingFreeDAO;

public class CodingFreeDeleteProService {

	public boolean deleteArticle(int num) {
		System.out.println("CodingFreeDeleteProService");
		boolean isDeleteSuccess = false;
		int updateCount = 0;

		Connection con = getConnection();
		CodingFreeDAO cdao = CodingFreeDAO.getInstance();
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
