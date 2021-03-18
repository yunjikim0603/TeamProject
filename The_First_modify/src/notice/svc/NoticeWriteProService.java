package notice.svc;

import static db.JdbcUtil.*;
import java.sql.Connection;

import notice.dao.NoticeDAO;
import notice.vo.NoticeBean;




public class NoticeWriteProService {

	public boolean registArticle(NoticeBean noticeBean) {
		System.out.println("NoticeWriteProService!");
		
		// 1. Connection 객체 가져오기
//		Connection con = JdbcUtil.getConnection(); // static 메서드 호출 시
		Connection con = getConnection(); // static import 를 사용했을 경우 클래스명 없이 static 메서드 호출
		
		// 2. DAO 객체 가져오기(싱글톤 패턴)
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		
		// 3. DAO 객체에 Connection 객체 전달하기
		noticeDAO.setConnection(con);
		
		// 4. DB 작업을 위한 DAO 객체의 메서드 호출
		// => 글쓰기 작업을 위해 NoticeDAO 객체의 insertArticle() 메서드 호출
		//    => 파라미터 : NoticeBean 객체, 리턴값 : int타입 글 등록 성공에 대한 결과값
		int insertCount = noticeDAO.insertArticle(noticeBean);
		
		boolean isWriteSuccess = false; // 글 등록 성공 여부를 리턴하기 위한 변수
		
		// insertCount 가 0보다 크면 성공, 아니면 실패
		if(insertCount > 0) {
			// 글쓰기 성공 시 commit 작업 수행 및 isWriteSuccess 변수값을 true 로 변경
			commit(con);
			
//			// 글쓰기 성공 후 참조글번호(board_re_ref) 업데이트를 위한 작업 수행
//			boardDAO.updateBoard_re_ref(boardBean);
//			commit(con);
			
			isWriteSuccess = true;
		} else {
			// 글쓰기 실패 시 rollback 작업 수행
			rollback(con);
		}
		
		// 5. Connection 객체 반환하기
		close(con);
		
		
		// 6. 글쓰기 성공 여부 리턴
		return isWriteSuccess;
	}

}














