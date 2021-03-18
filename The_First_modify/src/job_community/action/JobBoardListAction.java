package job_community.action;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import job_community.svc.JobBoardListService;
import job_community.vo.ActionForward;
import job_community.vo.JobBoardBean;
import job_community.vo.PageInfo;
import svc.AllService;

public class JobBoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("JobBoardListAction");
		ActionForward forward = null;
		
		
		JobBoardListService boardListService = new JobBoardListService();
		int listCount = boardListService.getListCount();
		
		ArrayList<JobBoardBean> jobList = boardListService.getJobList();
		
		ArrayList<JobBoardBean> articleList = boardListService.getArticleList();
		
		
		AllService allService = new AllService();
		Date today = allService.getToday();
		// request 객체의 setAttribute() 메서드를 호출하여 페이지 정보, 게시물 목록 저장
		request.setAttribute("today", today);
		request.setAttribute("articleList", articleList);
		request.setAttribute("jobList", jobList);
		
		// ActionForward 객체를 생성하여 board 폴더의 qna_board_list.jsp 페이지로 이동(Dispatch 방식)
		forward = new ActionForward();
		forward.setPath("/job_community/job_board_list.jsp");
		// forward.setRedirect(false) 는 생략 가능(기본값)
		
		return forward;
	}

}
