package job_community.action;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import job_community.svc.JobBoardDetailService;
import job_community.svc.JobCommentListService;
import job_community.vo.ActionForward;
import job_community.vo.JobBoardBean;
import job_community.vo.JobCommentBean;
import svc.AllService;

public class JobCommentModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		
		JobBoardDetailService boardDetailService = new JobBoardDetailService();
		JobBoardBean article = boardDetailService.getArticle(post_num);
		
		JobCommentListService jobCommentListService = new JobCommentListService();		
		JobCommentBean jobCommentBean = jobCommentListService.getComment(post_num, comment_num);
	
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		if(jobCommentBean != null) {
				String comment = jobCommentBean.getComment();
				request.setAttribute("comment", comment);
				request.setAttribute("article", article);
				request.setAttribute("today", today);
				request.setAttribute("modify_num", comment_num);
				
				forward = new ActionForward();
				forward.setPath("/job_community/job_comment_modify.jsp");
			}
					
		return forward;
	}
	
}
