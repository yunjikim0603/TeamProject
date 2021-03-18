package academy_community.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import academy_community.svc.AcademyCommentListService;
import academy_community.svc.AcademyDetailService;
import academy_community.vo.AcademyBean;
import academy_community.vo.AcademyCommentBean;
import academy_community.vo.ActionForward;
import svc.AllService;

public class AcademyDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("BoardDetailAction");
		
		// URL 을 사용하여 전달받은 파라미터(board_num, page) 가져오기
		// => board_num 은 int형 변수, page 는 String형 변수에 저장
		int num = Integer.parseInt(request.getParameter("num")); // 실제 사용을 위해 형변환 필요
		String page = request.getParameter("page"); // 단순 전달용이므로 별도의 형변환 불필요
		
		if (page == null) {
			page ="1";
		}
		
		// date 시간
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		// BoardDetailService 클래스의 getArticle() 메서드를 호출하여 게시물 정보 가져오기
		// => 파라미터로 글 번호(board_num) 전달, 리턴타입 BoardBean
		AcademyDetailService boardDetailService = new AcademyDetailService();
		AcademyBean article = boardDetailService.getArticle(num);
		
		AcademyCommentListService academyCommentService = new AcademyCommentListService();
		int cmmntListCount = academyCommentService.getCommentListCount(num);
		
		// 리턴받은 BoardBean 객체가 null 이 아닐 경우 BoardDetailService 클래스의 plusReadcount() 메서드 호출
			if(article != null) {
				boardDetailService.plusReadcount(num);
			}
				
		// 게시물 정보(BoardBean 객체), 페이지번호(page) 를 request 객체에 저장
		request.setAttribute("article", article);
//		request.setAttribute("page", page);
		request.setAttribute("today", today);
		request.setAttribute("post_num", num);
				
		// ActionForward 객체를 생성하여 board 폴더 내의 qna_board_view.jsp 로 이동 설정
		ActionForward forward = new ActionForward();
		forward.setPath("/academy/academy_view.jsp");
		
		return forward;
	}

}