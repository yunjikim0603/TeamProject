package member.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import member.dao.TextDAO;
import member.vo.TextBean;

public class TextViewService {

	public TextBean getText(int idx) {
		System.out.println("TextViewService - getText");
		
		Connection con = getConnection();
		TextDAO textDAO = TextDAO.getInstance();
		textDAO.setConnection(con);
		
		TextBean text = null;
		
		text = textDAO.selectText(idx);
		
		close(con);

		return text;
	}

	public void updateChecked(int idx) {
		System.out.println("TextViewService - setChecked");
		
		Connection con = getConnection();
		TextDAO textDAO = TextDAO.getInstance();
		textDAO.setConnection(con);
		
		int updateCount = textDAO.updateChecked(idx);

		if(updateCount > 0) {
			commit(con);
		} else {
			rollback(con);
		}
		
		close(con);
	}

}
