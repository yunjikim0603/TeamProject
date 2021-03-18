package coding.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import coding.dao.CodingDAO;

public class CodingReplySelectedService {

	public boolean replySelected(int post_num, int ref_num, int CP) {
		boolean isSuccess =false;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		int updateCount = codingDAO.updateReplySelected(post_num, ref_num, CP);
		if(updateCount>0) {
			commit(con);
			isSuccess=true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}

	public String getNickname(int post_num, int ref_num) {
		String nickname = null;
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		nickname = codingDAO.getNickname(post_num, ref_num);
		
		
		close(con);
		
		
		return nickname;
	}
	
	public int getSelectedRef_num(int post_num) {
		int selectedRef_num=0;
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		selectedRef_num = codingDAO.getSelectedRef_num(post_num);
		
		close(con);
		
		return selectedRef_num;
	}

	public boolean updateIsPublic(int pNum, int post_num) {
		boolean isSuccess =false;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		int updateCount = codingDAO.updateIsPublic(pNum ,post_num);
		if(updateCount>0) {
			commit(con);
			isSuccess=true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}

	public boolean codingMinusCP(int post_num) {
		boolean isSuccess =false;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		int updateCount = codingDAO.minusCP(post_num);
		if(updateCount>0) {
			commit(con);
			isSuccess=true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}
	
	

}
