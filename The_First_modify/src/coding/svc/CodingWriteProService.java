package coding.svc;

import java.sql.Connection;

import coding.dao.CodingDAO;
import coding.vo.CodingBean;
import static db.JdbcUtil.*;

public class CodingWriteProService {

	public boolean insertArticle(CodingBean codingBean) {
		boolean isWriteSuccess = false; 

		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);

		int insertCount = codingDAO.insertCodingArticle(codingBean);
		if(insertCount>0) {
			commit(con);
			isWriteSuccess=true;
		}else {
			rollback(con);
		}

		close(con);
		return isWriteSuccess;
	}
}
