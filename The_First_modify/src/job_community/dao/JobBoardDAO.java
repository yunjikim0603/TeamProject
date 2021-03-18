package job_community.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static db.JdbcUtil.*;

import job_community.vo.JobBoardBean;
import job_community.vo.JobCommentBean;

public class JobBoardDAO {
	
	private JobBoardDAO() {}
	
	
	public static JobBoardDAO instance = new JobBoardDAO();
	
	public static JobBoardDAO getInstance() {
		return instance;
	}
	// ----------------------------------------------------
	
	Connection con;

	// 외부로부터 Connection 객체를 전달받아 저장할 setConnection 메서드
	public void setConnection(Connection con) {
		this.con = con;
	}

	// 글쓰기
	public int insertArticle(JobBoardBean jobBoardBean) {
		int insertCount = 0;
		System.out.println("JobBOardDAO insertArticle");
		
		PreparedStatement pstmt = null;
		String content = "";
		
		if(jobBoardBean.getContent().contains("\r\n")) {
			content = jobBoardBean.getContent().replace("\r\n", "<br>");
		}else {
			content = jobBoardBean.getContent();
		}
		try {
			String sql = "INSERT INTO job_community VALUES (null, ?, ?, ?, ?, ?, now());";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, jobBoardBean.getNickname());
			pstmt.setString(2, jobBoardBean.getSubject());
			pstmt.setString(3, content);
			pstmt.setString(4, jobBoardBean.getFile());
			pstmt.setInt(5, 0);
			
			insertCount = pstmt.executeUpdate();
			System.out.println(insertCount);
			System.out.println(jobBoardBean.getNickname());
			System.out.println(jobBoardBean.getSubject());
			System.out.println(jobBoardBean.getContent());
			System.out.println(jobBoardBean.getFile());
			
		} catch (SQLException e) {
			System.out.println("insertArticle() 에러" + e.getMessage());
		}finally {
			close(pstmt);
		}
		
		return insertCount;
	}
	
	// ============ 글 목록 조회 ============= 
		public int selectListCount() {

			int listCount = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				String sql = "SELECT COUNT(*) FROM job_community";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
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

		// 게시물 목록 조회 후 리턴
		public ArrayList<JobBoardBean> selectArticleList() {
			ArrayList<JobBoardBean> articleList = new ArrayList<JobBoardBean>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			
			try {
				String sql = "SELECT *, time(date) AS time FROM job_community ORDER BY num DESC";
				pstmt = con.prepareStatement(sql);
	            rs = pstmt.executeQuery();
	            
	            // ResultSet 객체 내의 모든 레코드를 각각 레코드별로 BoardBean 에 담아서 ArrayList 객체에 저장
	            // => 패스워드 제외
	            while(rs.next()) {
	                JobBoardBean boardBean = new JobBoardBean();
	                boardBean.setNum(rs.getInt("num"));
	                boardBean.setNickname(rs.getString("nickname"));
	                boardBean.setSubject(rs.getString("subject"));
	                boardBean.setContent(rs.getString("content"));
	                boardBean.setReadcount(rs.getInt("readcount"));
	                boardBean.setFile(rs.getString("file"));
	                boardBean.setDate(rs.getDate("date"));
	                boardBean.setTime(rs.getString("time"));
	                articleList.add(boardBean);
	            }
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}
			
			return articleList;
		}

		// ============ 게시물 상세 내용 조회 ============
		public JobBoardBean selectArticle(int num) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			System.out.println(num);
			JobBoardBean article = null;
			
			// 글번호(num)에 해당하는 게시물 정보 조회
			try {
				String sql = "SELECT *, time(date) AS time FROM job_community WHERE num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num);
				rs = pstmt.executeQuery();
				
				// 게시물이 존재할 경우 BoardBean 객체에 저장
				if(rs.next()) {
					article = new JobBoardBean();
					article.setNum(rs.getInt("num"));
					article.setNickname(rs.getString("nickname"));
					article.setSubject(rs.getString("subject"));
					article.setContent(rs.getString("content"));
	                article.setReadcount(rs.getInt("readcount"));
	                article.setFile(rs.getString("file"));
	                article.setDate(rs.getDate("date"));
	                article.setTime(rs.getString("time"));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}
			
			return article;
		}

		public int updateReadcount(int num) {
			PreparedStatement pstmt = null;
			int updateCount = 0;
			
			try {
				String sql = "UPDATE job_community SET readcount=readcount+1 WHERE num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num);
				updateCount = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}
			
			return updateCount;
		}

		
		public int updateArticle(JobBoardBean article) {
			int updateCount = 0;
			PreparedStatement pstmt = null;
			
			String content = "";
			if(article.getContent().contains("\r\n")) {
				content = article.getContent().replace("\r\n", "<br>");
			}else {
				content = article.getContent();
			}
			
			try {
				String sql = "UPDATE job_community SET subject=?, content=?, file=?, date=now() WHERE num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, article.getSubject());
				pstmt.setString(2, content);
				pstmt.setString(3, article.getFile());
				pstmt.setInt(4, article.getNum());
				
				updateCount = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}
			
			return updateCount;
		}

		
		// ========== 글 삭제 ===========
		// => 삭제를 위한 패스워드 확인은 글 수정의 isArticleWriter() 메서드 함께 사용
		public int deleteArticle(int num) {
			int deleteCount = 0;
			PreparedStatement pstmt = null;
			
			try {
				String sql = "DELETE FROM job_community WHERE num=?";
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
		
		
		//댓글
		public int writeComment(JobCommentBean jobCommentBean) {
			PreparedStatement pstmt = null;
			int insertCount = 0;
			
			try {
//				String sql = "SELECT max(comment_num) as mnum FROM job_comment";
//				pstmt = con.prepareStatement(sql);
//				rs = pstmt.executeQuery();
//				
//				if (rs.next()) {
//					num = rs.getInt("mnum") + 1;
//				}
				String comment ="";
				if(jobCommentBean.getComment().contains("\r\n")) {
					comment = jobCommentBean.getComment().replace("\r\n", "<br>");
				}else {
					comment = jobCommentBean.getComment();
				}
				
				String sql = "INSERT INTO job_comment VALUES (null,?,?,?,now())";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, jobCommentBean.getPost_num());
				pstmt.setString(2, jobCommentBean.getNickname());
				pstmt.setString(3, comment);
				
				insertCount = pstmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}

			return insertCount;
		}
		
		// 댓글 목록 가져오기
		public ArrayList<JobCommentBean> getCommentList(int post_num, int page, int limit) {
			ArrayList<JobCommentBean> list = new ArrayList<JobCommentBean>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			int startRow = (page - 1) * 3;
			
			try {
				// ORDER BY comment_num
				String sql = "SELECT *, time(date) AS time FROM job_comment where post_num = ? ORDER BY comment_num DESC LIMIT ?,?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, post_num);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, limit);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					JobCommentBean jobCommentBean = new JobCommentBean();
					jobCommentBean.setComment_num(rs.getInt("comment_num"));
					jobCommentBean.setPost_num(rs.getInt("post_num"));
					jobCommentBean.setNickname(rs.getString("nickname"));
					jobCommentBean.setComment(rs.getString("comment"));
					jobCommentBean.setDate(rs.getDate("date"));
					jobCommentBean.setTime(rs.getString("time"));
					list.add(jobCommentBean);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}

			return list;
		}
		
		public int selectCommentListCount(int post_num) {
			int listCount = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				String sql = "SELECT COUNT(*) FROM job_comment WHERE post_num=?";
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
		
		public ArrayList<Integer> checkCommentNumList(int post_num) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList<Integer> numList = new ArrayList<Integer>();
			int num=0;
			
			try {
				String sql = "select comment_num from job_comment where post_num=?";
//				String sql = "SELECT recommender FROM coding_charge_comment WHERE recommender= ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, post_num);
//				pstmt.setInt(2, comment_num);
	            rs = pstmt.executeQuery();
	            
	            // ResultSet 객체 내의 모든 레코드를 각각 레코드별로 BoardBean 에 담아서 ArrayList 객체에 저장
	            // => 패스워드 제외
	            while(rs.next()) {
	            	num = rs.getInt("comment_num");
	            	numList.add(num);
	            }
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}
			
			return numList;
		}

		public JobCommentBean getComment(int post_num, int modify_num) {
			JobCommentBean cmmnt = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				// ORDER BY comment_num
				String sql = "SELECT *, time(date) AS time FROM job_comment WHERE post_num = ? AND comment_num = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, post_num);
				pstmt.setInt(2, modify_num);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					cmmnt = new JobCommentBean();
					cmmnt.setComment_num(rs.getInt("comment_num"));
					cmmnt.setPost_num(rs.getInt("post_num"));
					cmmnt.setNickname(rs.getString("nickname"));
					cmmnt.setComment(rs.getString("comment"));
//					cmmnt.setDate(rs.getDate("date"));
//					cmmnt.setTime(rs.getString("time"));
//					cmmnt.setHeart(rs.getInt("heart"));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}

			return cmmnt;
		}
		public int updateCmmntJob(JobCommentBean jobCommentBean) {
			int updateCount = 0;
			PreparedStatement pstmt = null;
			
			try {
				String comment ="";
				if(jobCommentBean.getComment().contains("\r\n")) {
					comment = jobCommentBean.getComment().replace("\r\n", "<br>");
				}else {
					comment = jobCommentBean.getComment();
				}
				String sql = "UPDATE job_comment SET comment=?, date=now() WHERE comment_num=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, comment);
					pstmt.setInt(2, jobCommentBean.getComment_num());
					
					updateCount = pstmt.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}
			
			return updateCount;
		}
		
		public int deleteComment(int comment_num) {
			PreparedStatement pstmt = null;
			int deleteCount = 0;
			
			try {
				
				String sql = "DELETE FROM job_comment WHERE comment_num=?";
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

		public int deleteCommentAll(int num) {
			PreparedStatement pstmt = null;
			int deleteCount = 0;
			
			try {
				
				String sql = "DELETE FROM job_comment WHERE post_num=?";
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
	
	
}
