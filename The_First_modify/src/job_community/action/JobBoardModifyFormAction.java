package job_community.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import job_community.svc.JobBoardDetailService;
import job_community.vo.ActionForward;
import job_community.vo.JobBoardBean;

public class JobBoardModifyFormAction implements Action{
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("JobBoardModifyFormAction");

		int num = Integer.parseInt(request.getParameter("num"));
		
		JobBoardBean article = null;
		JobBoardDetailService boardDetailService = new JobBoardDetailService();
		article = boardDetailService.getArticle(num);
		
		request.setAttribute("article", article);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/job_community/job_board_update.jsp"); 
		return forward;
	}

}
