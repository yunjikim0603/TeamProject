package coding_free.dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import coding.vo.CmmntBean;
import coding_free.vo.CodingFreeBean;
import coding_free.vo.CodingFreeCommentBean;

public class CodingFreeDAO {

	private CodingFreeDAO() {
	}

	private static CodingFreeDAO instance = new CodingFreeDAO();

	public static CodingFreeDAO getInstance() {
		return instance;
	}

	Connection con = null;

	public void setConnection(Connection con) {
		this.con = con;
	}

	// 글쓰기
	public int writeArticle(CodingFreeBean cb) {
		int insertCount = 0;
		PreparedStatement pstmt = null;

		try {
			String sql = "INSERT INTO coding_free VALUES (null,?,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cb.getNickname());
			pstmt.setString(2, cb.getSubject());
			pstmt.setString(3, cb.getContent());
			pstmt.setInt(4, 0);
			pstmt.setString(5, cb.getFile());
			insertCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return insertCount;
	}

	// 페이징
	public int selectListCount() {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT COUNT(*) FROM coding_free";
			pstmt = con.prepareStatement(sql);
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
	public ArrayList<CodingFreeBean> selectArticleList(int page, int limit) {
		ArrayList<CodingFreeBean> articleList = new ArrayList<CodingFreeBean>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int startRow = (page - 1) * 10;
		
		try {
			String sql = "SELECT *, time(date) AS time FROM coding_free ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CodingFreeBean cb = new CodingFreeBean();
				cb.setNum(rs.getInt("num"));
				cb.setNickname(rs.getString("nickname"));
				cb.setSubject(rs.getString("subject"));
				cb.setContent(rs.getString("content"));
				cb.setReadcount(rs.getInt("readcount"));
				cb.setFile(rs.getString("file"));
				cb.setDate(rs.getDate("date"));
				cb.setTime(rs.getString("time"));
				articleList.add(cb);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return articleList;
	}

	// 글 상세보기
	public CodingFreeBean selectArticle(int num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		CodingFreeBean cb = null;
		
		try {
			String sql = "SELECT *, time(date) AS time FROM coding_free WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				cb = new CodingFreeBean();
				cb.setNum(rs.getInt("num"));
				cb.setNickname(rs.getString("nickname"));
				cb.setSubject(rs.getString("subject"));
				cb.setContent(rs.getString("content"));
				cb.setReadcount(rs.getInt("readcount"));
				cb.setFile(rs.getString("file"));
				cb.setDate(rs.getDate("date"));
				cb.setTime(rs.getString("time"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return cb;
	}

	// 글 수정
	public int updateArticle(CodingFreeBean article) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE coding_free SET subject=?, content=?, file=?, date=now() where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getSubject());
			pstmt.setString(2, article.getContent());
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

	// 글 삭제
	public int deleteArticle(int num) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "DELETE FROM coding_free WHERE num=?";
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

	// 조회수 증가
	public int updateReadcount(int num) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE coding_free SET readcount=readcount+1 WHERE num=?";
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
	
	//댓글 dain
	
	public int writeComment(CodingFreeCommentBean codingFreeCommentBean) {
		PreparedStatement pstmt = null;
		int insertCount = 0;
		
		try {
//			String sql = "SELECT max(comment_num) as mnum FROM coding_free_comment";
//			pstmt = con.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			
//			if (rs.next()) {
//				num = rs.getInt("mnum") + 1;
//			}
			String comment ="";
			if(codingFreeCommentBean.getComment().contains("\r\n")) {
				comment = codingFreeCommentBean.getComment().replace("\r\n", "<br>");
			}else {
				comment = codingFreeCommentBean.getComment();
			}
			
			String sql = "INSERT INTO coding_free_comment VALUES (null,?,?,?,now(),0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, codingFreeCommentBean.getPost_num());
			pstmt.setString(2, codingFreeCommentBean.getNickname());
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
	public ArrayList<CodingFreeCommentBean> getCommentList(int post_num, int page, int limit) {
		ArrayList<CodingFreeCommentBean> list = new ArrayList<CodingFreeCommentBean>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int startRow = (page - 1) * 3;
		
		try {
			// ORDER BY comment_num
			String sql = "SELECT *, time(date) AS time FROM coding_free_comment where post_num = ? ORDER BY comment_num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, post_num);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CodingFreeCommentBean codingFreeCommentBean = new CodingFreeCommentBean();
				codingFreeCommentBean.setComment_num(rs.getInt("comment_num"));
				codingFreeCommentBean.setPost_num(rs.getInt("post_num"));
				codingFreeCommentBean.setNickname(rs.getString("nickname"));
				codingFreeCommentBean.setComment(rs.getString("comment"));
				codingFreeCommentBean.setDate(rs.getDate("date"));
				codingFreeCommentBean.setTime(rs.getString("time"));
				codingFreeCommentBean.setHeart(rs.getInt("heart"));
				list.add(codingFreeCommentBean);
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
			String sql = "SELECT COUNT(*) FROM coding_free_comment WHERE post_num=?";
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
			String sql = "select comment_num from coding_free_comment where post_num=?";
//			String sql = "SELECT recommender FROM coding_charge_comment WHERE recommender= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, post_num);
//			pstmt.setInt(2, comment_num);
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

	public CodingFreeCommentBean getComment(int post_num, int modify_num) {
		CodingFreeCommentBean cmmnt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// ORDER BY comment_num
			String sql = "SELECT *, time(date) AS time FROM coding_free_comment WHERE post_num = ? AND comment_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, post_num);
			pstmt.setInt(2, modify_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cmmnt = new CodingFreeCommentBean();
				cmmnt.setComment_num(rs.getInt("comment_num"));
				cmmnt.setPost_num(rs.getInt("post_num"));
				cmmnt.setNickname(rs.getString("nickname"));
				cmmnt.setComment(rs.getString("comment"));
//				cmmnt.setDate(rs.getDate("date"));
//				cmmnt.setTime(rs.getString("time"));
//				cmmnt.setHeart(rs.getInt("heart"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return cmmnt;
	}
	public int updateCmmntFree(CodingFreeCommentBean codingFreeCommentBean) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		
		try {
			
			String comment ="";
			if(codingFreeCommentBean.getComment().contains("\r\n")) {
				comment = codingFreeCommentBean.getComment().replace("\r\n", "<br>");
			}else {
				comment = codingFreeCommentBean.getComment();
			}
			
			String sql = "UPDATE coding_free_comment SET comment=?, date=now() WHERE comment_num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, comment);
				pstmt.setInt(2, codingFreeCommentBean.getComment_num());
				
				updateCount = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}
	
	//하트
	public int updateCmmnt(CodingFreeCommentBean codingFreeCommentBean) {
		int updateCount = 0;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		
		try {
			String sql = "select count(recommender) AS hearts from free_heart where comment_num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, codingFreeCommentBean.getComment_num());
	            rs = pstmt.executeQuery();
            if(rs.next()) {
            	int hearts = rs.getInt("hearts");
				sql = "UPDATE coding_free_comment SET comment=?, date=now(), heart=? WHERE post_num=? AND comment_num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, codingFreeCommentBean.getComment());
				pstmt.setInt(2, hearts);
				pstmt.setInt(3, codingFreeCommentBean.getPost_num());
				pstmt.setInt(4, codingFreeCommentBean.getComment_num());
				
				updateCount = pstmt.executeUpdate();
            } 
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}
	
	public int deleteCmmnt(int comment_num) {
		int deleteCount = 0 ;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM coding_free_comment WHERE comment_num=?";
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
	
	//FreeHeart 프리하트
		public int insertFreeHeart(int cmmnt_num, String recommender, String nickname) {
			PreparedStatement pstmt = null;
			int insertCount = 0;
			
			try {
				String sql = "INSERT INTO free_heart values(?,?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, nickname);
				pstmt.setInt(2, cmmnt_num);
				pstmt.setString(3, recommender);
				
				insertCount = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}

			return insertCount;
	}
		
		public int updateFreeHeartCount(int cmmnt_num) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int updateCount = 0;
			int hearts = 0;
			
			try {
				String sql = "select count(recommender) AS hearts from free_heart where comment_num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cmmnt_num);
	            rs = pstmt.executeQuery();
	            if(rs.next()) {
	            	hearts = rs.getInt("hearts");
	            	sql = "UPDATE coding_free_comment SET heart=? WHERE comment_num=?";
	    			pstmt = con.prepareStatement(sql);
	    			pstmt.setInt(1, hearts);
	    			pstmt.setInt(2, cmmnt_num);
	    			updateCount = pstmt.executeUpdate();
	    			
	            }
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}

			return updateCount;
		}


		public int deleteFreeHeart(int cmmnt_num, String recommender) {
			PreparedStatement pstmt = null;
			int deleteCount = 0;
			
			try {
				String sql = "DELETE FROM free_heart WHERE comment_num=? AND recommender=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cmmnt_num);
				pstmt.setString(2, recommender);
				deleteCount = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}

			return deleteCount;
		}
		
		public int deleteFreeCmmntHeart(int comment_num) {
			PreparedStatement pstmt = null;
			int deleteCount = 0;
			
			try {
				String sql = "DELETE FROM free_heart WHERE comment_num=?";
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
		public ArrayList<Integer> checkFreeRecommender(String recommender) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList<Integer> recList = new ArrayList<Integer>();
			int num=0;
			
			try {
				
				String sql = "SELECT fh.comment_num AS num FROM coding_free_comment AS fc JOIN free_heart as fh ON fc.comment_num=fh.comment_num AND fh.recommender=?"; 
//				String sql = "SELECT recommender FROM coding_charge_comment WHERE recommender= ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, recommender);
//				pstmt.setInt(2, comment_num);
	            rs = pstmt.executeQuery();
	            
	            // ResultSet 객체 내의 모든 레코드를 각각 레코드별로 BoardBean 에 담아서 ArrayList 객체에 저장
	            // => 패스워드 제외
	            while(rs.next()) {
	            	num = rs.getInt("num");
	            	recList.add(num);
	            }
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}
			
			return recList;
		}

		public String getNickname(int comment_num) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String nickname="";
			
			try {
				// ORDER BY comment_num
				String sql = "SELECT nickname FROM coding_free_comment WHERE comment_num = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, comment_num);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					nickname = rs.getString(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}

			return nickname;
		}

		
		public int selectFreeHeartCount(String nickname) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int hearts = 0;
			
			try {
				String sql = "select count(nickname) AS hearts from free_heart where nickname=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, nickname);
	            rs = pstmt.executeQuery();
	            while(rs.next()) {
	            	hearts = rs.getInt("hearts");
	            }
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}

			return hearts;
		}
		


	

	
	
	
}
