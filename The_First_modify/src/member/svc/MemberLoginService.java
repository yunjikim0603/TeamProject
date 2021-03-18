package member.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import member.dao.MemberDAO;
import member.vo.MemberBean;

public class MemberLoginService {
	
	public int isLoginMember(MemberBean member) {
		System.out.println("MemberLoginService");
		int loginResult = 0;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		loginResult = memberDAO.selectLoginMember(member);
		
		close(con);
		
		return loginResult;
	}
	
	public boolean isEmailChecked(String id) {
		System.out.println("MemberLoginService - isEmailCheck");
		boolean isEmailChecked = false;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		isEmailChecked = memberDAO.getUserEmailChecked(id);
		
		close(con);
		
		return isEmailChecked;
	}
	
	public String getNickname(String id) {
		System.out.println("MemberLoginService - getNickname");
		String nickname = null;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		nickname = memberDAO.getUserNickname(id);
		
		close(con);
		return nickname;
	}
	
}
