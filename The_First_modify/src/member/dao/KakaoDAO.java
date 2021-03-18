package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.xml.internal.ws.Closeable;

import member.vo.MemberBean;
import member.vo.SHA256;

import static db.JdbcUtil.*;

public class KakaoDAO {
	private KakaoDAO() {}
	private static KakaoDAO instance = new KakaoDAO();
	public static KakaoDAO getInstance() {
		return instance;
	}
	
	Connection con = null;
	public void setConnection(Connection con) {
		this.con = con;
	}
	
	public int insertKakao(MemberBean member) {
		int insertCount = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "INSERT INTO member VALUES(?,?,?,?,?,?,?,?,?,now(),0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getNickname());
			pstmt.setString(4, member.getEmail());
			pstmt.setInt(5, 0); // CP
			pstmt.setInt(6, 0); // LP
			pstmt.setInt(7, 1); // level
			pstmt.setString(8, "test"); // email 인증
			pstmt.setInt(9, 1); // false
			insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return insertCount;
	}
	
	public int kakaoLogin(MemberBean member) {
		int loginResult = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT password FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(1).equals(member.getPassword())) {
					loginResult = 1;
				}else {
					loginResult = -1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return loginResult;
	}

	
	public MemberBean getMember(String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberBean mb = null;
		try {
			String sql = "SELECT * FROM member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println("kakaoGetmember work??");
				mb = new MemberBean();
				mb.setId(rs.getString("id"));
				mb.setNickname(rs.getString("nickname"));
				mb.setEmail(rs.getString("email"));
				mb.setCp(rs.getInt("cp"));
				mb.setLp(rs.getInt("lp"));
				mb.setLevel(rs.getInt("level"));
				mb.setDate(rs.getDate("date"));
			}
			
		} catch (SQLException e) {
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		System.out.println("KakaoDAO - getMember");
		
		return mb;
	}


}
