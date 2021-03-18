package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class AllDAO {
	private AllDAO() {};
	private static AllDAO instance = new AllDAO();
	public static AllDAO getInstance() {
		return instance;
	};
	Connection con;

	public void setConnection(Connection con) { //외부에서 받은 Connection 객체를 전달받아 저장
		this.con = con;
	}
	
	public Date getToday() {
		PreparedStatement pstmt = null;
		Date today = null;
		ResultSet rs = null;

		try {
			String sql = "select Date(now()) AS today";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				today = rs.getDate("today");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs);
			 close(pstmt);
		}
		return today;
	}
}
