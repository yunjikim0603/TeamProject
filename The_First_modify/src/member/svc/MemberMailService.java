package member.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import member.dao.MemberDAO;

public class MemberMailService {

	public String getMemberEmail(String id) {
		System.out.println("MemberMailService - getMemberEmail");

		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		String email = memberDAO.getUserEmail(id);
		
		close(con);
		
		return email;
	}
	
	public boolean getUserEmailChecked(String id) {
		System.out.println("MemberMailService - getUserEmailChecked");

		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		boolean emailCheked = memberDAO.getUserEmailChecked(id);
		
		close(con);
		
		return emailCheked;
	}
	
	public void setUserEmailChecked(String id) {
		System.out.println("MemberMailService - setUserEmailChecked");

		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		boolean isSetEmailChecked = memberDAO.setUserEmailChecked(id);
		
		if (isSetEmailChecked) {
			commit(con);
		} else {
			rollback(con);
		}
		
		close(con);
	}
}
