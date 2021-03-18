package member.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import member.dao.MemberDAO;
import member.vo.MemberBean;

public class MemberUpdateProService {

	public boolean updateMember(MemberBean mb) {
		System.out.println("MemberUpdateProService");
		boolean isUpdateSuccess = false;
		int updateCount = 0;

		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		updateCount = memberDAO.updateMember(mb);
		
		if (updateCount > 0) {
			commit(con);
			isUpdateSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isUpdateSuccess;
	}
	
	//dain
	public boolean updateMemberCP(String nickname, int CP) {
		boolean isUpdateSuccess = false;
		int updateCount = 0;

		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		updateCount = memberDAO.updateMemberCP(nickname, CP);
		
		if (updateCount > 0) {
			commit(con);
			isUpdateSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isUpdateSuccess;
	}
	
	public boolean updateArticleCP(String nickname, int article_CP) {
		System.out.println("MemberDetailService - updateCP");
		boolean isSuccess =false;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
			
		int updateNum = memberDAO.updateCP(nickname, article_CP);
		
		if(updateNum>0) {
			commit(con);
			isSuccess = true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}
	

	public int selectCP(String nickname) {
		int userCP = 0;
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);

		userCP = memberDAO.selectCP(nickname);

		close(con);
		return userCP;
	}

	public boolean updateArticleLP(String nickname) {
		boolean isSuccess =false;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
			
		int updateNum = memberDAO.updateArticleLP(nickname);
		
		if(updateNum>0) {
			commit(con);
			isSuccess = true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}
	
	public boolean updateCommentLP(String nickname) {
		boolean isSuccess =false;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
			
		int updateNum = memberDAO.updateCommentLP(nickname);
		
		if(updateNum>0) {
			commit(con);
			isSuccess = true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}
	
	public boolean minusArticletLP(String nickname) {
		boolean isSuccess =false;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
			
		int updateNum = memberDAO.minusArticletLP(nickname);
		
		if(updateNum>0) {
			commit(con);
			isSuccess = true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}

	public boolean minusCommentLP(String nickname) {
		boolean isSuccess =false;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
			
		int updateNum = memberDAO.minusCommentLP(nickname);
		
		if(updateNum>0) {
			commit(con);
			isSuccess = true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}
	

	public boolean updateMemberHeart(String nickname, int hearts) {
		boolean isSuccess =false;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
			
		int updateNum = memberDAO.updateMemberHeart(nickname, hearts);
		
		if(updateNum>0) {
			commit(con);
			isSuccess = true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}
}
