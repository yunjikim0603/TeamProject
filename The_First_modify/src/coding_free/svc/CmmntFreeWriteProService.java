package coding_free.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import coding_free.dao.CodingFreeDAO;
import coding_free.vo.CodingFreeBean;
import coding_free.vo.CodingFreeCommentBean;

public class CmmntFreeWriteProService {

	public boolean writeArticle(CodingFreeBean cb) {
		System.out.println("CodingFreeWriteService");
		boolean isWriteSuccess = false;

		Connection con = getConnection();
		CodingFreeDAO cdao = CodingFreeDAO.getInstance();
		cdao.setConnection(con);
		
		int insertCount = cdao.writeArticle(cb);
		
		if(insertCount > 0) {
			commit(con);
			isWriteSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isWriteSuccess;
	}

	public boolean writeComment(CodingFreeCommentBean codingFreeCommentBean) {
		boolean isSuccess = false;
		Connection con = getConnection();
		CodingFreeDAO cdao = CodingFreeDAO.getInstance();
		cdao.setConnection(con);
		
		int insertCount = cdao.writeComment(codingFreeCommentBean);
		
		if(insertCount > 0) {
			commit(con);
			isSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		
		return isSuccess;
	}

}
