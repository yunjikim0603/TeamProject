package coding_free.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import coding_free.dao.CodingFreeDAO;
import coding_free.vo.CodingFreeBean;

public class CodingFreeDetailService {

	public CodingFreeBean getArticle(int num) {
		System.out.println("CodingFreeDetailService");

		Connection con = getConnection();
		CodingFreeDAO cdao = CodingFreeDAO.getInstance();
		cdao.setConnection(con);
		
		CodingFreeBean article = null;
		
		article = cdao.selectArticle(num);
		
		close(con);
		
		return article;
	}

	public static void plusReadcount(int num) {
		System.out.println("CodingFreeDetailService - plusReadcount");
	
		Connection con = getConnection();
		CodingFreeDAO cdao = CodingFreeDAO.getInstance();
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
