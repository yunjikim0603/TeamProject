package job_community.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import job_community.svc.JobBoardDeleteProService;
import job_community.svc.JobCommentDeleteProService;
import job_community.vo.ActionForward;
import member.svc.MemberUpdateProService;

public class JobCommentDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		String nickname = request.getParameter("nickname");
		
		JobCommentDeleteProService jobCommentDeleteProService = new JobCommentDeleteProService();
		
			boolean isDeleteSuccess = jobCommentDeleteProService.deleteComment(comment_num);
					
			if(!isDeleteSuccess) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('댓글 삭제 실패!')");
				out.println("history.back()");
				out.println("</script>");
			} else {
				MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
				boolean isSuccess = memberUpdateProService.minusCommentLP(nickname);
				if(isSuccess) {
				forward = new ActionForward();
				forward.setPath("JobBoardDetail.job?num="+post_num);
				forward.setRedirect(true);
				}
			}
					
		return forward;
	}
	
}
