package job_community.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import job_community.svc.JobBoardDeleteProService;
import job_community.svc.JobCommentWriteProService;
import job_community.vo.ActionForward;
import job_community.vo.JobCommentBean;
import member.svc.MemberUpdateProService;

public class JobCommentWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("JobCommentWriteProJobCommentWriteProJobCommentWriteProJobCommentWritePro!");
		ActionForward forward = null;
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		String nickname = request.getParameter("nickname");
		String comment = request.getParameter("comment");
		
		System.out.println(post_num);
		System.out.println(nickname);
		
		JobCommentBean jobCommentBean = new JobCommentBean();
		jobCommentBean.setPost_num(post_num);
		jobCommentBean.setNickname(nickname);
		jobCommentBean.setComment(comment);
		
		
		JobCommentWriteProService jobCommentWriteProService = new JobCommentWriteProService();
		boolean isWriteSuccess = jobCommentWriteProService.writeComment(jobCommentBean);
					
			if(!isWriteSuccess) { // 글쓰기 실패
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter(); 
				out.println("<script>"); 
				out.println("alert('댓글 등록 실패')"); 
				out.println("history.back()"); 
				out.println("</script>"); 
			} else { // 글쓰기 성공
				System.out.println("댓글 작성 완료");
				
				MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
				boolean isSuccess = memberUpdateProService.updateCommentLP(nickname);
				
				if(isSuccess) {
					forward = new ActionForward();
					forward.setPath("JobBoardDetail.job?num=" + post_num);
					forward.setRedirect(true);
				}
			}	
		return forward;
	}
	
}
