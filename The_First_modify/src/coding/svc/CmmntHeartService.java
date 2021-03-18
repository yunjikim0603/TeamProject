package coding.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import any_community.dao.CommunityDAO;
import coding.dao.CodingDAO;

public class CmmntHeartService {

	public boolean insertHeart(int cmmnt_num, String recommender, String nickname) {
		boolean isSuccess = false;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		int success = codingDAO.insertChargeHeart(cmmnt_num, recommender, nickname);
		
		if(success>0) {
			commit(con);
			isSuccess=true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}

	public boolean updateHeartCount(int cmmnt_num) {
		boolean isSuccess = false;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		int success = codingDAO.updateChargeHeartCount(cmmnt_num);
		
		if(success>0) {
			commit(con);
			isSuccess=true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
		
	}
	
	
	public ArrayList<Integer> checkRecommender(String recommender) {
		ArrayList<Integer> numList = null;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		numList = codingDAO.checkChargeRecommender(recommender);
		
		close(con);
		
		return numList;
	}

	public boolean deleteHeart(int cmmnt_num, String recommender) {
	boolean isSuccess = false;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		int success = codingDAO.deleteChargeHeart(cmmnt_num,recommender);
		
		if(success>0) {
			commit(con);
			isSuccess=true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}

	public int selectChargeHeartCount(String nickname) {
		
		int charge_heart=0;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		charge_heart = codingDAO.selectChargeHeartCount(nickname);
		
		close(con);
		return charge_heart;
	}


}
