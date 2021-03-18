package coding.svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.ArrayList;

import coding.dao.CodingDAO;
import coding.vo.CmmntBean;

public class CmmntListService {

	public int getCommentListCount(int post_num) {
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		int listCount = codingDAO.selectCommentListCount(post_num);

		close(con);
		
		return listCount;
	}
															//post_num,cmmnt_page,cmmnt_limit
	public ArrayList<CmmntBean> getCmmntList(int post_num, int cmmnt_page, int cmmnt_limit) {
		ArrayList<CmmntBean> cmmntList = null;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		cmmntList = codingDAO.selectCmmntList(post_num, cmmnt_page, cmmnt_limit);
		
		
		close(con);
		
		return cmmntList;
	}
	
	public CmmntBean getCmmnt(int post_num, int comment_num) {
		CmmntBean cmmnt = null;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		cmmnt = codingDAO.selectCmmnt(post_num, comment_num);
		
		
		close(con);
		
		return cmmnt;
	}
	
	public ArrayList<Integer> checkCommentNumList(int num) {
		ArrayList<Integer> c_numList = null;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		c_numList = codingDAO.checkCommentNumList(num); 
		
		
		close(con);
		
		return c_numList;
	}
	public String getNickname(int cmmnt_num) {
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		String nickname = codingDAO.getNickname(cmmnt_num); 
		
		
		close(con);
		
		return nickname;
	}

	

}
