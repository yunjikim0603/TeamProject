package coding.svc;

import java.sql.Connection;

import coding.dao.CodingDAO;
import coding.vo.CmmntBean;
import static db.JdbcUtil.*;

public class CmmntWriteProService {

	public boolean writeCmmnt(CmmntBean cmmntBean) {
		boolean isWriteSuccess = false;
		
		Connection con = getConnection();
		CodingDAO codingDAO = CodingDAO.getInstance();
		codingDAO.setConnection(con);
		
		int insertCount = codingDAO.insertCmmnt(cmmntBean);
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
