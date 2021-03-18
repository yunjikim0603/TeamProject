package coding.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.*;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import coding.dao.CodingDAO;
import coding.vo.CmmntBean;

public class CmmntModifyProService {

	public boolean modifyCmmnt(CmmntBean cmmntBean) {
		int updateCount = 0;
		boolean isModifySuccess = false;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		updateCount = codingDAO.updateCmmnt(cmmntBean);
		
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
