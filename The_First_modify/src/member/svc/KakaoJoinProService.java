package member.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import member.dao.KakaoDAO;
import member.dao.MemberDAO;
import member.vo.MemberBean;

public class KakaoJoinProService {

	public boolean joinMember(MemberBean member) {
		System.out.println("KakaoJoinProService");

		boolean isJoinSuccess = false;
		
		Connection con = getConnection();
		KakaoDAO kakaoDAO = KakaoDAO.getInstance();
		kakaoDAO.setConnection(con);
			
		int insertCount = kakaoDAO.insertKakao(member);
		
		if(insertCount > 0) {
			commit(con);
			isJoinSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		System.out.println(insertCount);
		System.out.println(isJoinSuccess);
		return isJoinSuccess;
	}

	public static MemberBean getMember(String id) {
		System.out.println("KakaoJoinProService - getMember!");
		Connection con = getConnection();
		KakaoDAO kakaoDAO = KakaoDAO.getInstance();
		kakaoDAO.setConnection(con);
		
		close(con);
		
		return kakaoDAO.getMember(id);
	}

}
