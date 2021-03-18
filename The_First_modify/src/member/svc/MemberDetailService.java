package member.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;

import member.dao.MemberDAO;
import member.vo.MemberBean;

public class MemberDetailService {

	public MemberBean getMember(String nickname) {
		System.out.println("MemberDetailService");

		MemberBean mb = null;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
			
		mb = memberDAO.getMember(nickname);
		
		close(con);
		
		return mb;
	}
	
	public int getHearts(String nickname) {
		System.out.println("MemberDetailService");

		int hearts = 0;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
			
		hearts = memberDAO.getHearts(nickname);
		
		close(con);
		
		return hearts;
	}
}
