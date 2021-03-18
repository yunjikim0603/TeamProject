package any_community.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import any_community.dao.CommunityDAO;
import any_community.vo.CommunityBean;

public class CommunityDetailService {

	public CommunityBean getArticle(int num) {
		System.out.println("CommunityDetailService - getArticle");

		Connection con = getConnection();
		CommunityDAO cdao = CommunityDAO.getInstance();
		cdao.setConnection(con);
		
		CommunityBean article = null;
		
		article = cdao.selectArticle(num);
		
		System.out.println("--------------------------");
		System.out.println("닉네임 넘어오나 ??");
		System.out.println(article.getNickname());
		
		close(con);
		
		return article;
	}

	public static void plusReadcount(int num) {
		System.out.println("CommunityDetailService - plusReadcount");
	
		Connection con = getConnection();
		CommunityDAO cdao = CommunityDAO.getInstance();
		cdao.setConnection(con);
		
		int updateCount = cdao.updateReadcount(num);
		
		if(updateCount > 0) {
			commit(con);
		} else {
			rollback(con);
		}
		
		close(con);
	}
}
