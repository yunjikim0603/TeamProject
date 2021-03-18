package coding_free.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import coding_free.dao.CodingFreeDAO;
import coding_free.vo.CodingFreeCommentBean;

public class CodingFreeCommentListService {
	
	public ArrayList<CodingFreeCommentBean> getCommentList(int post_num, int page, int limit) {
		System.out.println("CodingFreeCommentListService");
		ArrayList<CodingFreeCommentBean> list = new ArrayList<CodingFreeCommentBean>();
		
		Connection con = getConnection();
		CodingFreeDAO cdao = CodingFreeDAO.getInstance();
		cdao.setConnection(con);
		
		list = cdao.getCommentList(post_num, page, limit);
		
		close(con);
		
		return list;
		
	}

	public CodingFreeCommentBean getComment(int post_num, int modify_num) {
		CodingFreeCommentBean cmmnt = new CodingFreeCommentBean();
		
		Connection con = getConnection();
		CodingFreeDAO cdao = CodingFreeDAO.getInstance();
		cdao.setConnection(con);
		
		cmmnt = cdao.getComment(post_num, modify_num);
		
		close(con);
		
		return cmmnt;
	}
	
	public String getNickname(int comment_num) {
		
		Connection con = getConnection();
		CodingFreeDAO cdao = CodingFreeDAO.getInstance();
		cdao.setConnection(con);
		
		String nickname = cdao.getNickname(comment_num);
		
		close(con);
		
		return nickname;
	}

	public int getCommentListCount(int post_num) {
		int cmmntListCount = 0;
		
		Connection con = getConnection();
		CodingFreeDAO cdao = CodingFreeDAO.getInstance();
		cdao.setConnection(con);
		
		cmmntListCount = cdao.selectCommentListCount(post_num);
		
		close(con);
		
		return cmmntListCount;
	}

	
	public ArrayList<Integer> checkCommentNumList(int num) {
		ArrayList<Integer> numList = null;
		
		Connection con = getConnection();
		CodingFreeDAO cdao = CodingFreeDAO.getInstance();
		cdao.setConnection(con);
		
		numList = cdao.checkCommentNumList(num);
		
		
		close(con);
		
		return numList;
	}

}
