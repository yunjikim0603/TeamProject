package job_community.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import job_community.svc.JobBoardDetailService;
import job_community.svc.JobBoardListService;
import job_community.vo.ActionForward;
import job_community.vo.JobBoardBean;
import svc.AllService;

public class JobBoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDetailAction");
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		JobBoardDetailService boardDetailService = new JobBoardDetailService();
		JobBoardBean article = boardDetailService.getArticle(num);
		
		if(article != null) {
			boardDetailService.plusReadcount(num);
		}
		
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		// 게시물 정보(BoardBean 객체), 페이지번호(page) 를 request 객체에 저장
		request.setAttribute("article", article);
		request.setAttribute("today", today);
		
		
		// ActionForward 객체를 생성하여 board 폴더 내의 qna_board_view.jsp 로 이동 설정
		ActionForward forward = new ActionForward();
		forward.setPath("/job_community/job_board_view.jsp");
		
		return forward;
	}
	
}
