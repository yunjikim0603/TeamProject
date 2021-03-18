package notice.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import notice.dao.NoticeDAO;
import notice.vo.NoticeBean;


public class NoticeListService {

	public int getListCount() {
		System.out.println("NoticeListService - getListCount()");
		int listCount = 0; // 총 게시물 수 저장할 변수
		
		// 1. Connection 객체 가져오기
		Connection con = getConnection();
		
		// 2. DAO 객체 가져오기(싱글톤 패턴)
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		
		// 3. DAO 객체에 Connection 객체 전달하기
		noticeDAO.setConnection(con);
		
		// 4. DB 작업을 위한 DAO 객체의 메서드 호출
		// => selectListCount() 메서드를 호출하여 게시물 수 리턴받기
		listCount = noticeDAO.selectListCount();
		
		// 5. Connection 객체 반환하기
		close(con);
		
		return listCount;
	}

	public ArrayList<NoticeBean> getArticleList(int page, int limit) {
		System.out.println("NoticeListService - getArticleList()");
		ArrayList<NoticeBean> articleList = null;
		
		// 1. Connection 객체 가져오기
		Connection con = getConnection();
		
		// 2. DAO 객체 가져오기(싱글톤 패턴)
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		
		// 3. DAO 객체에 Connection 객체 전달하기
		noticeDAO.setConnection(con);
		
		// 4. DB 작업을 위한 DAO 객체의 메서드 호출
		// => NoticeDAO 객체의 selectArticleList() 메서드를 호출하여 게시물 목록 가져오기
		// => 파라미터 : page, limit, 리턴타입 : ArrayList<NoticeBean>
		articleList = noticeDAO.selectArticleList(page, limit);
		
		// 5. Connection 객체 반환하기
		close(con);
		
		return articleList;
	}

}
















