package coding_free.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import coding_free.dao.CodingFreeDAO;

public class CodingFreeCommentHeartService {

	public boolean insertHeart(int cmmnt_num, String recommender, String nickname) {
		boolean isSuccess = false;
		System.out.println("CodingFreeCommentHeartService        insertHeart");
		Connection con = getConnection();
		CodingFreeDAO codingFreeDAO = CodingFreeDAO.getInstance();
		codingFreeDAO.setConnection(con);
		
		int success = codingFreeDAO.insertFreeHeart( cmmnt_num, recommender, nickname);
		
		if(success>0) {
			System.out.println("insertHeart===========성공");
			commit(con);
			isSuccess=true;
		}else {
			System.out.println("insertHeart===========실패");
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}

	public boolean updateHeartCount(int cmmnt_num) {
		boolean isSuccess = false;
		System.out.println("CodingFreeCommentHeartService        updateHeartCount");
		Connection con = getConnection();
		CodingFreeDAO codingFreeDAO = CodingFreeDAO.getInstance();
		codingFreeDAO.setConnection(con);
		
		int success = codingFreeDAO.updateFreeHeartCount(cmmnt_num);
		
		if(success>0) {
			System.out.println("updateHeartCount===========성공");
			commit(con);
			isSuccess=true;
		}else {
			System.out.println("updateHeartCount===========실패");
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
		
	}
	
	
	public ArrayList<Integer> checkRecommender(String recommender) {
		ArrayList<Integer> numList = null;
		
		Connection con = getConnection();
		CodingFreeDAO codingFreeDAO = CodingFreeDAO.getInstance();
		codingFreeDAO.setConnection(con);
		
		numList = codingFreeDAO.checkFreeRecommender(recommender);
		
		close(con);
		
		return numList;
	}

	public boolean deleteHeart(int cmmnt_num, String recommender) {
	boolean isSuccess = false;
		
	Connection con = getConnection();
	CodingFreeDAO codingFreeDAO = CodingFreeDAO.getInstance();
	codingFreeDAO.setConnection(con);
		
		int success = codingFreeDAO.deleteFreeHeart(cmmnt_num,recommender);
		
		if(success>0) {
			commit(con);
			isSuccess=true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}

	public int selectFreeHeartCount(String nickname) {
		int free_heart = 0;
		
		
		Connection con = getConnection();
		CodingFreeDAO codingFreeDAO = CodingFreeDAO.getInstance();
		codingFreeDAO.setConnection(con);
		
		free_heart = codingFreeDAO.selectFreeHeartCount(nickname);
		
		close(con);
		return free_heart;
		
		
	}


}
