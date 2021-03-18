package academy_community.dao;

import static academy_community.db.JdbcUtil.*;
import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import academy_community.vo.AcademyCommentBean;
import any_community.vo.AnyCommentBean;
import coding_free.vo.CodingFreeCommentBean;

public class AcademyCommentDAO {
	private AcademyCommentDAO() {}
	
	private static AcademyCommentDAO instance = new AcademyCommentDAO();

	public static AcademyCommentDAO getInstance() {
		return instance;
	}
	// ---------------------------------------------------
	static Connection con = null;

	public void setConnection(Connection con) {
		this.con = con;
	}

	// 댓글 작성
	public int insertComment(AcademyCommentBean commentBean) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int insertCount = 0;
		int num = 0;
		
		try {
			String comment ="";
			if(commentBean.getComment().contains("\r\n")) {
				comment = commentBean.getComment().replace("\r\n", "<br>");
			}else {
				comment = commentBean.getComment();
			}
			String sql = "SELECT max(comment_num) as mnum FROM academy_comment";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				num = rs.getInt("mnum") + 1;
			}
			
		    sql = "INSERT INTO academy_comment VALUES (?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, commentBean.getPost_num());
			pstmt.setString(3, commentBean.getNickname());
			pstmt.setString(4, comment);
			
			insertCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
		}

		return insertCount;
	}

	// 댓글 리스트 출력
	public ArrayList<AcademyCommentBean> getCommentList(int post_num, int cmmnt_page, int cmmnt_limit) {
		ArrayList<AcademyCommentBean> list = new ArrayList<AcademyCommentBean>();
		int startRow = (cmmnt_page - 1) * cmmnt_limit;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT *, time(date) AS time FROM academy_comment WHERE post_num = ? LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, post_num);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, cmmnt_limit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AcademyCommentBean academyCommentBean = new AcademyCommentBean();
				academyCommentBean.setComment_num(rs.getInt("comment_num"));
				academyCommentBean.setPost_num(rs.getInt("post_num"));
				academyCommentBean.setNickname(rs.getString("nickname"));
				academyCommentBean.setComment(rs.getString("comment"));
				academyCommentBean.setDate(rs.getDate("date"));
				academyCommentBean.setTime(rs.getString("time"));
				list.add(academyCommentBean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}

		return list;
	}

	// 댓글 카운팅
	public int getCommentListCount(int num) {
		int commentListCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// ORDER BY comment_num
			String sql = "SELECT count(*) FROM academy_comment where post_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				commentListCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return commentListCount;
	}

	// 댓글 삭제
	public int deleteComment(int comment_num) {
		int deleteCount = 0 ;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "DELETE FROM academy_comment WHERE comment_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, comment_num);
			deleteCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return deleteCount;
	}

	// 댓글 전체 삭제
	public int deleteAllComment(int num) {
		int deleteCount = 0 ;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "DELETE FROM academy_comment WHERE post_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			deleteCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return deleteCount;
	}

	// 댓글 가져오기
	public AcademyCommentBean getComment(int post_num, int modify_num) {
		AcademyCommentBean comment = new AcademyCommentBean();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM academy_comment WHERE post_num = ? AND comment_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, post_num);
			pstmt.setInt(2, modify_num);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				comment.setComment_num(rs.getInt("comment_num"));
				comment.setPost_num(rs.getInt("post_num"));
				comment.setNickname(rs.getString("nickname"));
				comment.setComment(rs.getString("comment"));
				comment.setDate(rs.getDate("date"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}

		return comment;
	}

	// 댓글 수정
	public int updateComment(AcademyCommentBean academyCommentBean) {
		PreparedStatement pstmt = null;
		int updateCount = 0;
		
		try {
			String comment ="";
			if(academyCommentBean.getComment().contains("\r\n")) {
				comment = academyCommentBean.getComment().replace("\r\n", "<br>");
			}else {
				comment = academyCommentBean.getComment();
			}
			
			String sql = "UPDATE academy_comment SET comment = ?, date = now() WHERE comment_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, comment);
			pstmt.setInt(2, academyCommentBean.getComment_num());
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}

}
