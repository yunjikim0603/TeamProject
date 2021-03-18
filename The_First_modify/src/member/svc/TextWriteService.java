package member.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import member.dao.TextDAO;
import member.vo.TextBean;

public class TextWriteService {

	public boolean writeText(TextBean tb) {
		System.out.println("TextWriteService - writeText");
		boolean isWriteSuccess = false;
		
		Connection con = getConnection();
		TextDAO textDAO = TextDAO.getInstance();
		textDAO.setConnection(con);

		int insertCount = textDAO.wrtieText(tb);
		
		if (insertCount > 0) {
			commit(con);
			isWriteSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isWriteSuccess;
	}

	
	
}
