package job_community.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import job_community.svc.JobBoardDeleteProService;
import job_community.svc.JobBoardDetailService;
import job_community.svc.JobCommentDeleteProService;
import job_community.svc.JobCommentListService;
import job_community.vo.ActionForward;
import job_community.vo.JobBoardBean;
import member.svc.MemberUpdateProService;

public class JobBoardDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int num = Integer.parseInt(request.getParameter("num"));
				
		JobBoardDeleteProService boardDeleteProService = new JobBoardDeleteProService();
		
		JobBoardDetailService jobBoardDetailService = new JobBoardDetailService();
		JobBoardBean jobBoardBean = jobBoardDetailService.getArticle(num);
		String nickname = jobBoardBean.getNickname();
		
		JobCommentDeleteProService jobCommentDeleteProService = new JobCommentDeleteProService();
		boolean isDeleteSuccess  = jobCommentDeleteProService.deleteCommentAll(num);
		
		if(isDeleteSuccess) {
			isDeleteSuccess = boardDeleteProService.removeArticle(num);
				if(!isDeleteSuccess) {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('글 삭제 실패!')");
					out.println("history.back()");
					out.println("</script>");
				} else {
					MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
					boolean isSuccess = memberUpdateProService.minusArticletLP(nickname);
					if(isSuccess) {
						forward = new ActionForward();
						forward.setPath("JobBoardList.job");
						forward.setRedirect(true);
					}
				}
		}
			
		return forward;
	}
	
}
