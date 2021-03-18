package admin.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import member.dao.MemberDAO;

public class MemberDeleteProService {

	public boolean removeMember(String id) {
		System.out.println("MemberDeleteProService - removeMember()");
		
		boolean isRemoveSuccess = false;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		int deleteCount = memberDAO.deleteMember(id);
		
		// deleteCount 가 0보다 크면 commit 수행 및 isRemoveSuccess 를 true 로 변경
		// 아니면 rollback 수행
		if(deleteCount > 0) {
			commit(con);
			isRemoveSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isRemoveSuccess;
	}
}
