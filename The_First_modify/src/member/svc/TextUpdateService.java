package member.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import member.dao.TextDAO;

public class TextUpdateService {

	public boolean updateReceiver(String nickname) {
		System.out.println("TextUpdateService - updateReceiver");
		boolean isTextUpdate = false;
		int updateCount = 0;
		
		Connection con = getConnection();
		TextDAO textDAO = TextDAO.getInstance();
		textDAO.setConnection(con);
		
		updateCount = textDAO.updateReceiver(nickname);
		
		if(updateCount > 0) {
			commit(con);
			isTextUpdate = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isTextUpdate;
	}

	public int getTextCount(String nickname) {
		System.out.println("TextUpdateService - getTextCount");
		int textCount = 0;
		
		Connection con = getConnection();
		TextDAO textDAO = TextDAO.getInstance();
		textDAO.setConnection(con);
		
		textCount = textDAO.getTextCount(nickname);
		
		close(con);
		
		return textCount;
	}

}
