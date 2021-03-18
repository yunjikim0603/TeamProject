package member.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;

import member.dao.TextDAO;

public class TextCheckService {

	public int checkText(String receiver) {
		System.out.println("TextCheckService - checkText");
		int count = 0;
		
		Connection con = getConnection();
		TextDAO textDAO = TextDAO.getInstance();
		textDAO.setConnection(con);
		
		count = textDAO.checkText(receiver);
		
		close(con);
		
		return count;
	}

}
