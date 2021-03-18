package notice.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.svc.NoticeDetailService;
import notice.vo.ActionForward;
import notice.vo.NoticeBean;
import svc.AllService;

public class NoticeDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("NoticeDetailAction");
		
		// URL 을 사용하여 전달받은 파라미터(num, page) 가져오기
		// => num 은 int형 변수, page 는 String형 변수에 저장
		int post_num = Integer.parseInt(request.getParameter("post_num")); // 실제 사용을 위해 형변환 필요
//		String page = request.getParameter("page"); // 단순 전달용이므로 별도의 형변환 불필요
		
		
		// NoticeDetailService 클래스의 getArticle() 메서드를 호출하여 게시물 정보 가져오기
		// => 파라미터로 글 번호(num) 전달, 리턴타입 NoticeBean
		NoticeDetailService noticeDetailService = new NoticeDetailService();
		NoticeBean article = noticeDetailService.getArticle(post_num);
		
		// 리턴받은 NoticeBean 객체가 null 이 아닐 경우 NoticeDetailService 클래스의 plusReadcount() 메서드 호출
		if(article != null) {
			noticeDetailService.plusReadcount(post_num);
		}
		
		//data 시간
				AllService allService = new AllService();
				Date today = allService.getToday();
		
		// 게시물 정보(NoticeBean 객체), 페이지번호(page) 를 request 객체에 저장
		request.setAttribute("article", article);
//		request.setAttribute("page", page);
		request.setAttribute("today", today);
		request.setAttribute("post_num", post_num);
		
		// ActionForward 객체를 생성하여 notice 폴더 내의 notice_view.jsp 로 이동 설정
		ActionForward forward = new ActionForward();
		forward.setPath("/notice/notice_view.jsp");
		
		return forward;
	}

}















