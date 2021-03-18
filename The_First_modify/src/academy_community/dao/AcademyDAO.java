package academy_community.dao;

import static academy_community.db.JdbcUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import academy_community.vo.AcademyBean;

public class AcademyDAO {
	// ----------------------------------------------------
	// 싱글톤 패턴을 활용한 BoardDAO 인스턴스 생성 및 리턴
	private AcademyDAO() {}
	
	private static AcademyDAO instance = new AcademyDAO();

	public static AcademyDAO getInstance() {
		return instance;
	}
	// ----------------------------------------------------
	
	Connection con;

	// 외부로부터 Connection 객체를 전달받아 저장할 setConnection 메서드
	public void setConnection(Connection con) {
		this.con = con;
	}

// ============ 글 쓰기 ============= 
		public int insertArticle(AcademyBean boardBean) {
//			System.out.println("BoardDAO - insertArticle()");
			int insertCount = 0; // executeUpdate() 메서드를 통해 글쓰기 작업 수행 결과를 저장할 변수
			
			PreparedStatement pstmt = null;
			
			try {
				// INSERT 구문을 사용하여 전달된 항목들 및 기타 데이터를 board 테이블에 추가
				// 글번호(num) : 자동 증가 옵션이 설정되어 있으므로 null 값 전달(쿼리에서 직접 전달)
				// 작성자(board_name), 패스워드(board_pass), 제목(board_subject), 본문(board_content), 파일명(board_file)
				// 참조글 번호(board_re_ref) : 자동증가로 인해 글 번호 식별이 불가능하므로 임시번호 -1 지정
				// 들여쓰기 레벨(board_re_lev) : 현재 글이 원본 글이므로 0 사용
				// 답글 순서 번호(board_re_seq) : 현재 글이 원본 글이므로 0 사용
				// 조회수(board_readcount) : 새 글이므로 0 사용
				// 작성일(board_date) : 현재 DB 의 날짜 및 시각 정보 전달(now() 함수를 사용하여 쿼리에서 직접 전달)
				String content = "";
				
				if(boardBean.getContent().contains("\r\n")) {
					content = boardBean.getContent().replace("\r\n", "<br>");
				}else {
					content = boardBean.getContent();
				}
				
				String sql = "INSERT INTO academy_community VALUES (?,?,?,?,?,?,?,now());";
				
				// Connection 객체로부터 PreparedStatement 객체 가져와서 쿼리 전달
				pstmt = con.prepareStatement(sql);
				// ? 파라미터값 채우기
				pstmt.setInt(1, 0); //num
				pstmt.setString(2, boardBean.getNickname());
				pstmt.setString(3, boardBean.getSubject());
				pstmt.setString(4, content);
				pstmt.setInt(5, 0); // readcount
				pstmt.setString(6, boardBean.getAddress());
				pstmt.setString(7, boardBean.getAcademy_name());
				// 쿼리 실행
				insertCount = pstmt.executeUpdate();
				
			} catch (SQLException e) {
//				e.printStackTrace();
				System.out.println("insertArticle() 에러 : " + e.getMessage());
			} finally {
				close(pstmt);
			}
			
			return insertCount;
		}

// ============ 글 목록 조회 ============= 
		
		public int selectListCount() {
			// 총 게시물 수 조회하여 리턴
//			System.out.println("BoardDAO - selectListCount()");
			int listCount = 0;
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				String sql = "SELECT COUNT(*) FROM academy_community";
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
// 글목록 페이징 처리
		public ArrayList<AcademyBean> selectArticleList() {
			// 게시물 목록 조회 후 리턴
			ArrayList<AcademyBean> articleList = new ArrayList<AcademyBean>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			/* 
			 * 전체 게시물 중 원하는 페이지의 게시물 첫번째 row 번호 설정
			 * - 원본 글 번호(board_re_ref) 기준으로 내림차순 정렬
			 * - 글 순서번호(board_re_seq) 기준으로 오름차순 정렬
			 * - 조회할 게시물 갯수 : 첫번째 게시물 위치 ~ limit 수 만큼
			 *   첫번째 게시물 위치 = (현재페이지 - 1) * 10
			 * 
			 * ex) 현재 페이지(page) 가 1 페이지 일 경우 : 게시물 조회 결과의 0번 행부터 10개 가져오기
			 */
			
			try {
				// 조회 결과(ResultSet) 객체가 존재할 경우
				// 반복문을 사용하여 1개 게시물 정보(패스워드 제외한 나머지)를 BoardBean 객체에 저장하고
				// BoardBean 객체를 ArrayList<BoardBean> 객체에 저장 반복
				String sql = "SELECT *, time(date) AS time FROM academy_community";
				pstmt = con.prepareStatement(sql);
	            rs = pstmt.executeQuery();
	            
	            // ResultSet 객체 내의 모든 레코드를 각각 레코드별로 BoardBean 에 담아서 ArrayList 객체에 저장
	            // => 패스워드 제외
	            while(rs.next()) {
	                AcademyBean boardBean = new AcademyBean();
	                boardBean.setNum(rs.getInt("num"));
	                boardBean.setNickname(rs.getString("nickname"));
	                boardBean.setSubject(rs.getString("subject"));
	                boardBean.setContent(rs.getString("content"));
	                boardBean.setReadcount(rs.getInt("readcount"));
	                boardBean.setAddress(rs.getString("address"));
	                boardBean.setDate(rs.getDate("date"));	
	                boardBean.setAcademy_name(rs.getString("academy_name"));
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
		public AcademyBean selectArticle(int num) {
			// 게시물 상세 내용 조회 후 BoardBean 객체(article)에 저장
			// SELECT 문을 사용하여 게시물 조회 후 정보를 BoardBean 객체에 저장(패스워드 제외)
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			AcademyBean article = null;
			
			// 글번호(board_num)에 해당하는 게시물 정보 조회
			try {
				String sql = "SELECT *, time(date) AS time FROM academy_community WHERE num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num);
				rs = pstmt.executeQuery();
				
				// 게시물이 존재할 경우 BoardBean 객체에 저장
				if(rs.next()) {
					article = new AcademyBean();
					article.setNum(rs.getInt("num"));
					article.setNickname(rs.getString("nickname"));
					article.setSubject(rs.getString("subject"));
					article.setContent(rs.getString("content"));
					article.setReadcount(rs.getInt("readcount"));
			        article.setAddress(rs.getString("address"));
			        article.setDate(rs.getDate("date"));	
			        article.setAcademy_name(rs.getString("academy_name"));
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
// 글 조회수 업데이트
		public int updateReadcount(int num) {
			// 게시물 조회 수 1 증가 후 결과(updateCount) 리턴
			// UPDATE 문을 사용하여 게시물 조회수(readcount) 를 1 증가시킴
			PreparedStatement pstmt = null;
			
			int updateCount = 0;
			
			// board_num 에 해당하는 board_readcount 값을 1 증가
			try {
				String sql = "UPDATE academy_community SET readcount=readcount+1 WHERE num=?";
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
// 글 내용 수정
		public int updateArticle(AcademyBean article) {
			int updateCount = 0;
			PreparedStatement pstmt = null;
			
			try {
				String content = "";
				
				if(article.getContent().contains("\r\n")) {
					content = article.getContent().replace("\r\n", "<br>");
				}else {
					content = article.getContent();
				}
				
				String sql = "UPDATE academy_community SET subject=?, content=?, address=?, academy_name=? WHERE num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, article.getSubject());
				pstmt.setString(2, content);
				pstmt.setString(3, article.getAddress());
				pstmt.setString(4, article.getAcademy_name());
				pstmt.setInt(5, article.getNum());
				
				updateCount = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(pstmt);
			}
			
			return updateCount;
		}

// ========== 글 삭제 ===========
// => 삭제를 위한 패스워드 확인은 글 수정의 isArticleWriter() 메서드 함께 사용
		public int deleteArticle(int num) {
			// 글 번호(board_num) 에 해당하는 게시물 삭제
			int deleteCount = 0;
			
			PreparedStatement pstmt = null;
			
			try {
				String sql = "DELETE FROM academy_community WHERE num=?";
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