package coding.svc;

import java.sql.Connection;

import coding.dao.CodingDAO;
import coding.vo.CodingBean;
import static db.JdbcUtil.*;

public class CodingModifyProService {

//	public boolean isArticleWriter(int board_num, String board_pass) throws Exception {
//		// 글 번호에 해당하는 게시물에 저장된 패스워드와 입력받은 패스워드를 비교하여 일치여부 리턴
//
//		boolean isArticleWriter = false;
//		
//		Connection con = getConnetion();
//		CodingDAO codingDAO = CodingDAO.getInstance();
//		codingDAO.setConnection(con);
//		
////		isArticleWriter = codingDAO.isArticleWriter(num);
//		
//		close(con);
//		
//		return isArticleWriter;
//	}
	
	
	public boolean modifyArticle(CodingBean article) {
		boolean isModifySuccess = false;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		int updateCount = codingDAO.updateArticle(article);
		
		if(updateCount > 0) {
			commit(con);
			isModifySuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isModifySuccess;
	}
	

}
