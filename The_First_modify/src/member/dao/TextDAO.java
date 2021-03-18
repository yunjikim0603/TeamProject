package member.dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import any_community.vo.CommunityBean;
import member.vo.TextBean;

public class TextDAO {
	private TextDAO() {}
	private static TextDAO instance = new TextDAO();
	public static TextDAO getInstance() {
		return instance;
	}
	
	Connection con = null;
	public void setConnection(Connection con) {
		this.con = con;
	}
	
	// 쪽지 전송
	public int wrtieText(TextBean tb) {
		int insertCount = 0;
		PreparedStatement pstmt = null;

		try {
			String sql = "INSERT INTO text VALUES (null,?,?,?,?,?,null)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tb.getSender());
			pstmt.setString(2, tb.getReceiver());
			pstmt.setString(3, tb.getSubject());
			pstmt.setString(4, tb.getContents());
			pstmt.setBoolean(5, false);
			insertCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return insertCount;
	}

	// 페이징
	public int selectListCount(String receiver) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT COUNT(*) FROM text where receiver = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, receiver);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				listCount = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return listCount;
	}
	
	// 리스트 가져오기
	public ArrayList<TextBean> selectTextList(int page, int limit, String receiver) {
		ArrayList<TextBean> textList = new ArrayList<TextBean>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int startRow = (page - 1) * 10;
		
		try {
			String sql = "SELECT * FROM text WHERE receiver=? ORDER BY idx DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, receiver);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				TextBean tb = new TextBean();
				tb.setIdx(rs.getInt("idx"));
				tb.setSender(rs.getString("sender"));
				tb.setReceiver(rs.getString("receiver"));
				tb.setSubject(rs.getString("subject"));
				tb.setContents(rs.getString("contents"));
				tb.setIsChecked(rs.getBoolean("isChecked"));
				tb.setSend_date(rs.getTimestamp("send_date"));
				textList.add(tb);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return textList;
	}

	// 디테일
	public TextBean selectText(int idx) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		TextBean tb = null;
		
		try {
			String sql = "SELECT * FROM text WHERE idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				tb = new TextBean();
				tb.setIdx(rs.getInt("idx"));
				tb.setSender(rs.getString("sender"));
				tb.setReceiver(rs.getString("receiver"));
				tb.setSubject(rs.getString("subject"));
				tb.setContents(rs.getString("contents"));
				tb.setIsChecked(rs.getBoolean("isChecked"));
				tb.setSend_date(rs.getTimestamp("send_date"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return tb;
	}

	// 체크여부
	public int updateChecked(int idx) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE text SET isChecked=true WHERE idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	// 삭제
	public int deleteText(int idx) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "DELETE FROM text WHERE idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	// 체크여부 확인하기
	public int checkText(String receiver) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		
		try {
			String sql = "SELECT count(idx) FROM text WHERE receiver=? AND isChecked=? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, receiver);
			pstmt.setBoolean(2, false);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				updateCount = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return updateCount;
	}

	public int updateReceiver(String nickname) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE text SET receiver=? WHERE receiver=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			pstmt.setString(2, nickname);
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	public int getTextCount(String nickname) {
		int textCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT count(receiver) FROM text WHERE receiver=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				textCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return textCount;
	}

	
	
}
