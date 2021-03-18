package member.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import member.dao.MemberDAO;

public class MemberDeleteService {

	public boolean deleteMember(String id) {
		System.out.println("MemberDeleteService");
		boolean isDeleteSuccess = false;
		int deleteResult = 0;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		deleteResult = memberDAO.deleteMember(id);
		
		if (deleteResult > 0) {
			commit(con);
			isDeleteSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isDeleteSuccess;
	}

}
