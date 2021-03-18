package any_community.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import any_community.svc.CommentDeleteProService;
import any_community.vo.ActionForward;
import member.svc.MemberUpdateProService;

public class CommentDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CommentDeleteProAction");
		ActionForward forward = null;
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		String nickname = request.getParameter("nickname");
		
		CommentDeleteProService commentDeleteProService = new CommentDeleteProService();
		boolean isDeleteSuccess = commentDeleteProService.deleteComment(comment_num);
		
		if (!isDeleteSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 삭제 실패!')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
			boolean isSuccess = memberUpdateProService.minusCommentLP(nickname);
			
			if(isSuccess) {
				System.out.println("댓글 삭제 성공");
				forward = new ActionForward();
				forward.setPath("CommunityDetail.any?num="+post_num);
				forward.setRedirect(true);
			}
		}
		
		return forward;
	}

}
