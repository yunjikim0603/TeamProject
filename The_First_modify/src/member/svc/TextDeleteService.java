package member.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import member.dao.TextDAO;

public class TextDeleteService {

	public boolean deleteText(int idx) {
		System.out.println("TextDeleteService - deleteText");
		boolean isDeleteSuccess = false;
		
		Connection con = getConnection();
		TextDAO textDAO = TextDAO.getInstance();
		textDAO.setConnection(con);
		
		int updateCount = textDAO.deleteText(idx);

		if(updateCount > 0) {
			commit(con);
			isDeleteSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);

		return isDeleteSuccess;
	}

}
