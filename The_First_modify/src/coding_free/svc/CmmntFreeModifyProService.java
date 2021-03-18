package coding_free.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.*;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import coding_free.dao.CodingFreeDAO;
import coding_free.vo.CodingFreeCommentBean;

public class CmmntFreeModifyProService {

	public boolean modifyCmmnt(CodingFreeCommentBean codingFreeCommentBean) {
		int updateCount = 0;
		boolean isModifySuccess = false;
		
		Connection con = getConnection();
		CodingFreeDAO cfDAO = CodingFreeDAO.getInstance();
		cfDAO.setConnection(con);
		
		updateCount = cfDAO.updateCmmnt(codingFreeCommentBean);
		
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
