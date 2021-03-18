package any_community.dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import any_community.vo.AnyCommentBean;

public class CommentDAO {

	private CommentDAO() {}
	private static CommentDAO instance = new CommentDAO();
	public static CommentDAO getInstance() {
		return instance;
	}
	
	Connection con = null;
	
	public void setConnection(Connection con) {
		this.con = con;
	}

	
	// 댓글작성
	public int writeComment(AnyCommentBean anyCommentBean) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int insertCount = 0;
		int num = 0;
		
		try {
			String comment ="";
			if(anyCommentBean.getComment().contains("\r\n")) {
				comment = anyCommentBean.getComment().replace("\r\n", "<br>");
			}else {
				comment = anyCommentBean.getComment();
			}
			
			String sql = "SELECT max(comment_num) as mnum FROM any_comment";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				num = rs.getInt("mnum") + 1;
			}
			
		    sql = "INSERT INTO any_comment VALUES (?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, anyCommentBean.getPost_num());
			pstmt.setString(3, anyCommentBean.getNickname());
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
	
	// 댓글 목록 가져오기
	public ArrayList<AnyCommentBean> getCommentList(int post_num, int cmmnt_page, int cmmnt_limit) {
		ArrayList<AnyCommentBean> list = new ArrayList<AnyCommentBean>();
		int startRow = (cmmnt_page - 1) * cmmnt_limit;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT *,time(date) AS time FROM any_comment where post_num = ? LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, post_num);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, cmmnt_limit);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				AnyCommentBean anyCommentBean = new AnyCommentBean();
				anyCommentBean.setComment_num(rs.getInt("comment_num"));
				anyCommentBean.setPost_num(rs.getInt("post_num"));
				anyCommentBean.setNickname(rs.getString("nickname"));
				anyCommentBean.setComment(rs.getString("comment"));
				anyCommentBean.setDate(rs.getDate("date"));
				anyCommentBean.setTime(rs.getString("time"));
				list.add(anyCommentBean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}

		return list;
	}

	// 댓글 삭제
	public int deleteComment(int comment_num) {
		int deleteCount = 0 ;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "DELETE FROM any_comment WHERE comment_num=?";
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


	// 댓글 페이징을 위한 Count
	public int selectCommentListCount(int post_num) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT COUNT(*) FROM any_comment WHERE post_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, post_num);
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


	// 게시글에 대한 댓글 전체 삭제
	public int deleteAllComment(int num) {
		int deleteCount = 0 ;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "DELETE FROM any_comment WHERE post_num=?";
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
	public AnyCommentBean getComment(int post_num, int modify_num) {
		AnyCommentBean comment = new AnyCommentBean();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM any_comment WHERE post_num = ? AND comment_num = ?";
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
	public int updateComment(AnyCommentBean anyCommentBean) {
		PreparedStatement pstmt = null;
		int updateCount = 0;
		

		try {
			String comment ="";
			if(anyCommentBean.getComment().contains("\r\n")) {
				comment = anyCommentBean.getComment().replace("\r\n", "<br>");
			}else {
				comment = anyCommentBean.getComment();
			}
			
			String sql = "UPDATE any_comment SET comment = ?, date = now() WHERE comment_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, comment);
			pstmt.setInt(2, anyCommentBean.getComment_num());
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}
	
	
}
