package coding_free.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import coding_free.dao.CodingFreeDAO;
import coding_free.vo.CodingFreeBean;

public class CodingFreeListService {

	public int getListCount() {
		System.out.println("CodingFreeListService - getListCount");
		int listCount = 0; 
		
		Connection con = getConnection();
		CodingFreeDAO cdao = CodingFreeDAO.getInstance();
		cdao.setConnection(con);
		
		listCount = cdao.selectListCount();
		
		close(con);
		
		return listCount;
	}
	
	public ArrayList<CodingFreeBean> getArticleList(int page, int limit) {
		System.out.println("CommunityListService - getArticleList");
		ArrayList<CodingFreeBean> articleList = null;
		
		Connection con = getConnection();
		CodingFreeDAO cdao = CodingFreeDAO.getInstance();
		cdao.setConnection(con);
		
		articleList = cdao.selectArticleList(page, limit);
		
		close(con);
		
		return articleList;
	}
}
