package member.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;

import member.dao.MemberDAO;

public class DupCheckService {

	public int idChk(String id) {
		System.out.println("IdDupCheckService - idChk");
		int count = 0;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		count = memberDAO.idChk(id);
		
		close(con);
		
		return count;
	}

	public int nicknameChk(String nickname) {
		System.out.println("IdDupCheckService - nicknameChk");
		int count = 0;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		count = memberDAO.nicknameChk(nickname);
		
		close(con);
		
		return count;
	}

}
