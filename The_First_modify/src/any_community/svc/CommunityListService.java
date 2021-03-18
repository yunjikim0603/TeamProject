package any_community.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import any_community.dao.CommunityDAO;
import any_community.vo.CommunityBean;

public class CommunityListService {

	public int getListCount() {
		System.out.println("CommunityListService - getListCount");
		int listCount = 0; 
		
		Connection con = getConnection();
		CommunityDAO cdao = CommunityDAO.getInstance();
		cdao.setConnection(con);
		
		listCount = cdao.selectListCount();
		
		close(con);
		
		return listCount;
	}
	
	public ArrayList<CommunityBean> getArticleList(int page, int limit) {
		System.out.println("CommunityListService - getArticleList");
		ArrayList<CommunityBean> articleList = null;
		
		Connection con = getConnection();
		CommunityDAO cdao = CommunityDAO.getInstance();
		cdao.setConnection(con);
		
		articleList = cdao.selectArticleList(page, limit);
		
		close(con);
		
		return articleList;
	}
}
