package coding.svc;

import java.sql.Connection;

import coding.dao.CodingDAO;
import coding.vo.CodingBean;
import coding.vo.Coding_refBean;

import static db.JdbcUtil.*;

public class CodingReplyDetailService {

	public Coding_refBean getArticle_ref(int num) {
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		Coding_refBean article_ref = codingDAO.selectArticle_ref(num);
		
		close(con);
		
		return article_ref;
	}

	public Coding_refBean getArticle_ref(int num, int ref_num) {
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		Coding_refBean article_ref = codingDAO.selectArticle_ref(num,ref_num);
		
		close(con);
		
		return article_ref;
	}


}
