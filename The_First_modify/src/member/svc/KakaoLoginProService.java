package member.svc;

import java.sql.Connection;

import static db.JdbcUtil.*;

import member.dao.KakaoDAO;
import member.vo.MemberBean;

public class KakaoLoginProService {

	public int isLoginMember(MemberBean member) {
		System.out.println("KakaoLoginService - isLoginMember");
		int loginResult = 0;
		
		Connection con = getConnection();
		KakaoDAO kakaoDAO = KakaoDAO.getInstance();
		kakaoDAO.setConnection(con);
		
		loginResult = kakaoDAO.kakaoLogin(member);
		
		close(con);
		
		return loginResult;
	}

}
