package coding.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import coding.vo.CmmntBean;
import coding.vo.CodingBean;
import coding.vo.Coding_refBean;

import static db.JdbcUtil.*;

public class CodingDAO {
	private CodingDAO() {};
	private static CodingDAO instance = new CodingDAO();
	public static CodingDAO getInstance() {
		return instance;
	};
	////////////////////////////////////////////////////////
	
	Connection con;

	public void setConnection(Connection con) { //외부에서 받은 Connection 객체를 전달받아 저장
		this.con = con;
	}

	//DB이름: coding_charge

	public int insertCodingArticle(CodingBean codingBean) {
		int insertCount = 0;
		PreparedStatement pstmt = null;
		
//		num | nickname  |	subject   | content  | readcount	| file 	| date |  isPublic 	| password | CP
		String sql = "insert into coding_charge values(null,?,?,?,?,?,now(),1,0,?)";
		try {
			String content = "";
			
			if(codingBean.getContent().contains("\r\n")) {
				content = codingBean.getContent().replace("\r\n", "<br>");
			}else {
				content = codingBean.getContent();
			}
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, codingBean.getNickname());
			pstmt.setString(2, codingBean.getSubject());
			pstmt.setString(3, content);
			pstmt.setInt(4, codingBean.getReadcount() );
			pstmt.setString(5, codingBean.getFile());
//			pstmt.setInt(6, codingBean.getIsPublic());
//			pstmt.setInt(7, 0); //codingBean.getPassword()
			pstmt.setInt(6, codingBean.getCP());

			insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return insertCount;
	}

	
	public int updateReadcount(int num) {
		// 게시물 조회 수 1 증가 후 결과(updateCount) 리턴
		// UPDATE 문을 사용하여 게시물 조회수(readcount) 를 1 증가시킴
		PreparedStatement pstmt = null;
		
		int updateCount = 0;
		
		// board_num 에 해당하는 board_readcount 값을 1 증가
		try {
			String sql = "UPDATE coding_charge SET readcount=readcount+1 WHERE num=?";
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

	public CodingBean selectArticle(int num) {
		CodingBean article = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 글번호(board_num)에 해당하는 게시물 정보 조회
		try {
			String sql = "SELECT *, time(date) AS time FROM coding_charge WHERE num=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			// 게시물이 존재할 경우 BoardBean 객체에 저장
//			if(rs.next()) { //while?
			while(rs.next()) {
				article = new CodingBean();
				article.setNum(rs.getInt("num"));
				article.setNickname(rs.getString("nickname"));
				article.setSubject(rs.getString("subject"));
				article.setContent(rs.getString("content"));
				article.setReadcount(rs.getInt("readcount"));
				article.setFile(rs.getString("file"));
				article.setDate(rs.getDate("date"));
				article.setIsPublic(rs.getInt("isPublic"));
				article.setTime(rs.getString("time"));
				article.setCP(rs.getInt("CP"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return article;
	}
	public int selectListCount() {
		// 총 게시물 수 조회하여 리턴
//		System.out.println("BoardDAO - selectListCount()");
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT COUNT(*) FROM coding_charge";
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
	public ArrayList<CodingBean> selectArticleList() { // int page, int limit
		// 게시물 목록 조회 후 리턴
		ArrayList<CodingBean> articleList = new ArrayList<CodingBean>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			String sql = "SELECT *, time(date) AS time FROM coding_charge";
			String sql = "SELECT *, time(date) AS time FROM coding_charge ORDER BY CP desc, num desc";
			pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()) {
            	CodingBean codingBean = new CodingBean();
            	codingBean.setNum(rs.getInt("num"));
            	codingBean.setNickname(rs.getString("nickname"));
            	codingBean.setSubject(rs.getString("subject"));
            	codingBean.setContent(rs.getString("content"));
            	codingBean.setReadcount(rs.getInt("readcount"));
            	codingBean.setFile(rs.getString("file"));
            	codingBean.setDate(rs.getDate("date"));
            	codingBean.setIsPublic(rs.getInt("isPublic"));
            	codingBean.setPassword(rs.getInt("password"));
            	codingBean.setTime(rs.getString("time"));
            	codingBean.setCP(rs.getInt("CP"));
                
                articleList.add(codingBean);
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return articleList;
	}

	public int updateArticle(CodingBean article) {
		int updateCount = 0;
		String content = "";
		PreparedStatement pstmt = null;
		
		try {
			if(article.getContent().contains("\r\n")) {
				content = article.getContent().replace("\r\n", "<br>");
			}else {
				content = article.getContent();
			}
//			String sql = "UPDATE board SET board_name=?,board_subject=?,board_content=? WHERE board_num=?";
			String sql = "UPDATE coding_charge SET subject=?, content=?, file=?, date=now() WHERE num=?";
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

	public int insertCodingArticle_ref(Coding_refBean coding_refBean) {
		int insertCount = 0;
		PreparedStatement pstmt = null;
		
//		ref_num | post_num | nickname  |	subject   | content  | readcount	| file 	| date |  isSelected 
		String sql = "insert into coding_charge_ref values(null,?,?,?,?,?,?,now(),0,0)";
		try {
			String content = "";
			
			if(coding_refBean.getContent().contains("\r\n")) {
				content = coding_refBean.getContent().replace("\r\n", "<br>");
			}else {
				content = coding_refBean.getContent();
			}
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, coding_refBean.getPost_num());
			pstmt.setString(2, coding_refBean.getNickname());
			pstmt.setString(3, coding_refBean.getSubject());
			pstmt.setString(4, content);
			pstmt.setInt(5, coding_refBean.getReadcount() );
			pstmt.setString(6, coding_refBean.getFile());
//			pstmt.setInt(7, coding_refBean.getIsSelected()); //0 이면 무료, 1이면 유료

			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return insertCount;
	}

	public int modifyCodingArticle_ref(Coding_refBean ref) {
		int insertCount = 0;
		PreparedStatement pstmt = null;
		
//		ref_num | post_num | nickname  |	subject   | content  | readcount	| file 	| date |  isSelected 
		String sql = "UPDATE coding_charge_ref SET post_num=?, nickname=?, subject=?, content=?, file=?, isSelected=? WHERE ref_num=?";
		try {
			String content = "";
			
			if(ref.getContent().contains("\r\n")) {
				content = ref.getContent().replace("\r\n", "<br>");
			}else {
				content = ref.getContent();
			}
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ref.getPost_num());
			pstmt.setString(2, ref.getNickname());
			pstmt.setString(3, ref.getSubject());
			pstmt.setString(4,content);
//			pstmt.setInt(5, ref.getReadcount() );
			pstmt.setString(5, ref.getFile());
			pstmt.setInt(6, ref.getIsSelected()); //0 이면 무료, 1이면 유료
			pstmt.setInt(7, ref.getRef_num()); //0 이면 무료, 1이면 유료

			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return insertCount;
	}

	public Coding_refBean selectArticle_ref(int num) {
		Coding_refBean article_ref = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 글번호(board_num)에 해당하는 게시물 정보 조회
		try {
			String sql = "SELECT *, time(date) AS times FROM coding_charge_ref WHERE post_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			// 게시물이 존재할 경우 BoardBean 객체에 저장
//			if(rs.next()) { //while?
			while(rs.next()) {
				article_ref = new Coding_refBean();
				article_ref.setRef_num(rs.getInt("ref_num"));
				article_ref.setPost_num(rs.getInt("post_num"));
				article_ref.setNickname(rs.getString("nickname"));
				article_ref.setSubject(rs.getString("subject"));
				article_ref.setContent(rs.getString("content"));
				article_ref.setReadcount(rs.getInt("readcount"));
				article_ref.setFile(rs.getString("file"));
				article_ref.setDate(rs.getDate("date"));
				article_ref.setIsSelected(rs.getInt("isSelected"));
				article_ref.setTime(rs.getString("time"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return article_ref;
	}
	
	public Coding_refBean selectArticle_ref(int num, int ref_num) {
		Coding_refBean article_ref = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 글번호(board_num)에 해당하는 게시물 정보 조회
		try {
			String sql = "SELECT * FROM coding_charge_ref WHERE post_num=? AND ref_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, ref_num);
			rs = pstmt.executeQuery();
			
			// 게시물이 존재할 경우 BoardBean 객체에 저장
//			if(rs.next()) { //while?
			while(rs.next()) {
				article_ref = new Coding_refBean();
				article_ref.setRef_num(rs.getInt("ref_num"));
				article_ref.setPost_num(rs.getInt("post_num"));
				article_ref.setNickname(rs.getString("nickname"));
				article_ref.setSubject(rs.getString("subject"));
				article_ref.setContent(rs.getString("content"));
				article_ref.setReadcount(rs.getInt("readcount"));
				article_ref.setFile(rs.getString("file"));
				article_ref.setDate(rs.getDate("date"));
				article_ref.setIsSelected(rs.getInt("isSelected"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return article_ref;
	}
	
	public Coding_refBean selectReply(int ref_num) {
		Coding_refBean refBean = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 글번호(board_num)에 해당하는 게시물 정보 조회
		try {
			String sql = "SELECT *, time(date) AS time FROM coding_charge WHERE ref_num=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ref_num);
			rs = pstmt.executeQuery();
			
			// 게시물이 존재할 경우 BoardBean 객체에 저장
//			if(rs.next()) { //while?
			while(rs.next()) {
				refBean = new Coding_refBean();
				refBean.setPost_num(rs.getInt("post_num"));
				refBean.setRef_num(rs.getInt("ref_num"));
				refBean.setNickname(rs.getString("nickname"));
				refBean.setSubject(rs.getString("subject"));
				refBean.setContent(rs.getString("content"));
				refBean.setReadcount(rs.getInt("readcount"));
				refBean.setFile(rs.getString("file"));
				refBean.setDate(rs.getDate("date"));
				refBean.setIsSelected(rs.getInt("isSelected"));
				refBean.setCP(rs.getInt("CP"));
				refBean.setTime(rs.getString("time"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return refBean;
	}

	public int selectReplyListCount(int post_num) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT COUNT(*) FROM coding_charge_ref WHERE post_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, post_num);
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
	
	//(num, reply_page, reply_limit
	public ArrayList<Coding_refBean> selectArticleReplyList(int post_num, int reply_page, int reply_limit) {
		ArrayList<Coding_refBean> article_refList = new ArrayList<Coding_refBean>();
		int startRow = (reply_page - 1) * reply_limit;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		System.out.println("CodingDAO"+startRow);
		System.out.println("CodingDAO"+reply_limit);
		try {
			String sql = "SELECT *, time(date) AS time FROM coding_charge_ref WHERE post_num=? LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, post_num);
			 pstmt.setInt(2, startRow);
			 pstmt.setInt(3, reply_limit);
			 
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	Coding_refBean coding_refBean = new Coding_refBean();
            	coding_refBean.setRef_num(rs.getInt("ref_num"));
            	coding_refBean.setPost_num(rs.getInt("post_num"));
            	coding_refBean.setNickname(rs.getString("nickname"));
            	coding_refBean.setSubject(rs.getString("subject"));
            	coding_refBean.setContent(rs.getString("content"));
            	coding_refBean.setReadcount(rs.getInt("readcount"));
            	coding_refBean.setFile(rs.getString("file"));
            	coding_refBean.setDate(rs.getDate("date"));
            	coding_refBean.setIsSelected(rs.getInt("isSelected"));
            	coding_refBean.setTime(rs.getString("time"));
                
                article_refList.add(coding_refBean);
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return article_refList;
	}

	public ArrayList<Coding_refBean> selectArticleReplyList(int post_num) {
		ArrayList<Coding_refBean> article_refList = new ArrayList<Coding_refBean>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT *, time(date) AS time FROM coding_charge_ref WHERE post_num=?";
			pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, post_num);
			 
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	Coding_refBean coding_refBean = new Coding_refBean();
            	coding_refBean.setRef_num(rs.getInt("ref_num"));
            	coding_refBean.setPost_num(rs.getInt("post_num"));
            	coding_refBean.setNickname(rs.getString("nickname"));
            	coding_refBean.setSubject(rs.getString("subject"));
            	coding_refBean.setContent(rs.getString("content"));
            	coding_refBean.setReadcount(rs.getInt("readcount"));
            	coding_refBean.setFile(rs.getString("file"));
            	coding_refBean.setDate(rs.getDate("date"));
            	coding_refBean.setIsSelected(rs.getInt("isSelected"));
            	coding_refBean.setTime(rs.getString("time"));
            	coding_refBean.setCP(rs.getInt("CP"));
                
                article_refList.add(coding_refBean);
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return article_refList;
	}
	
	public int deleteReply(int post_num) {
		int deleteCount = 0 ;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM coding_charge_ref WHERE post_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, post_num);
			deleteCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return deleteCount;
	}

	public int deleteReply(int post_num, int reply_num) {
		int deleteCount = 0 ;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM coding_charge_ref WHERE post_num=? AND ref_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, post_num);
			pstmt.setInt(2, reply_num);
			deleteCount = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return deleteCount;
	}

	

	public String getNickname(int post_num, int ref_num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String nickname="";
		
		try {
			String sql = "SELECT nickname FROM coding_charge_ref WHERE post_num=? AND ref_num=?";
			
			pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, post_num);
			 pstmt.setInt(2, ref_num);
			 
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
            	nickname = rs.getString("nickname");
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return nickname;
	}

	
	
	public int deleteArticle(int num) {
		int deleteCount = 0 ;
		PreparedStatement pstmt = null;
		try {
//			String sql = "DELETE FROM coding_charge_ref WHERE post_num=?";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setInt(1, num);
//			deleteCount = pstmt.executeUpdate();
//			if(deleteCount>0) {
				String sql = "DELETE FROM coding_charge WHERE num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num);
				deleteCount = pstmt.executeUpdate();
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return deleteCount;
	}

	public int insertCmmnt(CmmntBean cmmntBean) {
		int insertCount = 0;
		PreparedStatement pstmt = null;
		
//		insert into coding_charge_comment values(null, 1, 'kim', 'kim-com', now(), 0);
		String sql = "INSERT INTO coding_charge_comment VALUES(null, ?, ?, ?, now(), 0)";
		try {
			String comment ="";
			if(cmmntBean.getComment().contains("\r\n")) {
				comment = cmmntBean.getComment().replace("\r\n", "<br>");
			}else {
				comment = cmmntBean.getComment();
			}
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cmmntBean.getPost_num());
			pstmt.setString(2, cmmntBean.getNickname());
			pstmt.setString(3, comment);

			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return insertCount;
	}

	public int selectCommentListCount(int post_num) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT COUNT(*) FROM coding_charge_comment WHERE post_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, post_num);
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
	
	
	public ArrayList<CmmntBean> selectCmmntList(int post_num, int cmmnt_page, int cmmnt_limit) {
		ArrayList<CmmntBean> cmmntfList = new ArrayList<CmmntBean>();
		int startRow = (cmmnt_page - 1) * cmmnt_limit;
	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT *, time(date) AS time FROM coding_charge_comment WHERE post_num=? LIMIT ?,?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, post_num);
			 pstmt.setInt(2, startRow);
	         pstmt.setInt(3, cmmnt_limit);
            
			 rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	CmmntBean cmmntBean = new CmmntBean();
            	cmmntBean.setComment_num(rs.getInt("comment_num"));
            	cmmntBean.setPost_num(rs.getInt("post_num"));
            	cmmntBean.setNickname(rs.getString("nickname"));
            	cmmntBean.setComment(rs.getString("comment"));
            	cmmntBean.setDate(rs.getDate("date"));
            	cmmntBean.setTime(rs.getString("time"));
//            	cmmntBean.setRecommender(rs.getString("recommender"));
            	cmmntBean.setHeart(rs.getInt("heart"));
            	cmmntfList.add(cmmntBean);
            }
//            time(now())
         
            
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return cmmntfList;
	}
	
	public CmmntBean selectCmmnt(int post_num, int comment_num) {
		CmmntBean cmmntBean = new CmmntBean();
	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT *, time(date) AS time FROM coding_charge_comment WHERE post_num=? AND comment_num=?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, post_num);
			 pstmt.setInt(2, comment_num);
            
			 rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	cmmntBean.setComment_num(rs.getInt("comment_num"));
            	cmmntBean.setPost_num(rs.getInt("post_num"));
            	cmmntBean.setNickname(rs.getString("nickname"));
            	cmmntBean.setComment(rs.getString("comment"));
            	cmmntBean.setDate(rs.getDate("date"));
            	cmmntBean.setTime(rs.getString("time"));
//            	cmmntBean.setRecommender(rs.getString("recommender"));
            	cmmntBean.setHeart(rs.getInt("heart"));
            }
//            time(now())
         
            
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return cmmntBean;
	}

	public ArrayList<Integer> checkCommentNumList(int post_num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Integer> numList = new ArrayList<Integer>();
		int num=0;
		
		try {
			String sql =  "select comment_num from coding_charge_comment where post_num=?";
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
	
	public int updateCmmnt(CmmntBean cmmntBean) {
		int updateCount = 0;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		
		try {
			String comment ="";
			if(cmmntBean.getComment().contains("\r\n")) {
				comment = cmmntBean.getComment().replace("\r\n", "<br>");
			}else {
				comment = cmmntBean.getComment();
			}
			String sql = "select count(recommender) AS hearts from charge_heart where comment_num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cmmntBean.getComment_num());
	            rs = pstmt.executeQuery();
            if(rs.next()) {
            	int hearts = rs.getInt("hearts");
				sql = "UPDATE coding_charge_comment SET comment=?, date=now(), heart=? WHERE post_num=? AND comment_num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, comment);
				pstmt.setInt(2, hearts);
				pstmt.setInt(3, cmmntBean.getPost_num());
				pstmt.setInt(4, cmmntBean.getComment_num());
				
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
			String sql = "DELETE FROM coding_charge_comment WHERE comment_num=?";
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
	
	
	
	//chargeheart
		public int insertChargeHeart(int cmmnt_num, String recommender, String nickname) {
			PreparedStatement pstmt = null;
			int insertCount = 0;
			
			try {
				String sql = "INSERT INTO charge_heart values(?,?,?)";
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
	
	public int updateChargeHeartCount(int cmmnt_num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int updateCount = 0;
		int hearts = 0;
		
		try {
			String sql = "select count(recommender) AS hearts from charge_heart where comment_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cmmnt_num);
            rs = pstmt.executeQuery();
            if(rs.next()) {
            	hearts = rs.getInt("hearts");
            	sql = "UPDATE coding_charge_comment SET heart=? WHERE comment_num=?";
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
	
	public int deleteChargeHeart(int cmmnt_num, String recommender) {
		PreparedStatement pstmt = null;
		int deleteCount = 0;
		
		try {
			String sql = "DELETE FROM charge_heart WHERE comment_num=? AND recommender=?";
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
	
	public int deleteChargeCmmntHeart(int comment_num) {
		PreparedStatement pstmt = null;
		int deleteCount = 0;
		
		try {
			String sql = "DELETE FROM charge_heart WHERE comment_num=?";
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
	

	public ArrayList<Integer> checkChargeRecommender(String recommender) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Integer> recList = new ArrayList<Integer>();
		int num=0;
		
		try {
			String sql = "SELECT ch.comment_num AS num FROM coding_charge_comment AS cc JOIN charge_heart as ch ON cc.comment_num=ch.comment_num AND ch.recommender=?"; 
//			String sql = "SELECT recommender FROM coding_charge_comment WHERE recommender= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, recommender);
//			pstmt.setInt(2, comment_num);
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
	
	public int selectChargeHeartCount(String nickname) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int hearts = 0;
		
		try {
			String sql = "select count(nickname) AS hearts from charge_heart where nickname=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
            rs = pstmt.executeQuery();
            if(rs.next()) {
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
	

	
	

	//채택
	public int getSelectedRef_num(int post_num) {
		int selectedRef_num=0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT ref_num FROM coding_charge_ref WHERE post_num=? AND isSelected=1";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, post_num);
            
			 rs = pstmt.executeQuery();
            
            if(rs.next()) {
            	selectedRef_num = rs.getInt("ref_num");
            }
//            time(now())
         
            
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}

		return selectedRef_num;
	}

	public int updateReplySelected(int post_num, int ref_num, int CP) {
		int updateCount =0;
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE coding_charge_ref SET isSelected=1, CP=? WHERE post_num=? and ref_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, CP);
			pstmt.setInt(2, post_num);
			pstmt.setInt(3, ref_num);
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}
	

	public int updateIsPublic(int pNum, int post_num) {
		int updateCount =0;
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE coding_charge SET isPublic=? WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pNum);
			pstmt.setInt(2, post_num);
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	public int minusCP(int post_num) {
		int updateCount =0;
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE coding_charge SET CP=0 WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, post_num);
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	public String getNickname(int cmmnt_num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String nickname="";
		
		try {
			// ORDER BY comment_num
			String sql = "SELECT nickname FROM coding_charge_comment WHERE comment_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cmmnt_num);
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

	

	

	
}
