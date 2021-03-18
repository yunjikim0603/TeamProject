package job_community.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import job_community.svc.JobBoardDeleteProService;
import job_community.svc.JobCommentModifyProService;
import job_community.svc.JobCommentWriteProService;
import job_community.vo.ActionForward;
import job_community.vo.JobCommentBean;

public class JobCommentModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("JobCommentWriteProAction!");
		ActionForward forward = null;
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		String comment = request.getParameter("comment");
		int comment_num =  Integer.parseInt(request.getParameter("comment_num"));
		
		System.out.println(comment);
		
		JobCommentBean jobCommentBean = new JobCommentBean();
		jobCommentBean.setComment_num(comment_num);
		jobCommentBean.setComment(comment);
		
		
		JobCommentModifyProService jobCommentModifyProService = new JobCommentModifyProService();
		boolean isWriteSuccess = jobCommentModifyProService.updateCmmntJob(jobCommentBean);
					
			if(!isWriteSuccess) { // 글쓰기 실패
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter(); 
				out.println("<script>"); 
				out.println("alert('댓글 수정 실패')"); 
				out.println("history.back()"); 
				out.println("</script>"); 
			} else { // 글쓰기 성공
				System.out.println("댓글 작성 완료");
				forward = new ActionForward();
				forward.setPath("JobBoardDetail.job?num=" + post_num);
				forward.setRedirect(true);
			}	
		return forward;
	}
	
}
