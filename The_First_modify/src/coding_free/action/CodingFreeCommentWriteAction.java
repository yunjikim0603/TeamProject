package coding_free.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coding_free.svc.CodingFreeCommentWriteService;
import coding_free.vo.CodingFreeCommentBean;
import member.svc.MemberUpdateProService;
import coding_free.vo.ActionForward;

public class CodingFreeCommentWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CodingFreeCommentWriteAction!");
		ActionForward forward = null;
		
		//파라미터 가져오기
		String nowPage = request.getParameter("nowPage");
		int post_num = Integer.parseInt(request.getParameter("comment_board"));
		String nickname = request.getParameter("comment_nickname");
		String comment = request.getParameter("comment_content");
		
		CodingFreeCommentBean codingFreeCommentBean = new CodingFreeCommentBean(post_num, nickname, comment);
		
		CodingFreeCommentWriteService codingFreeCommentWriteService = new CodingFreeCommentWriteService();
		boolean isWriteSuccess = codingFreeCommentWriteService.writeComment(codingFreeCommentBean);
		
		if(!isWriteSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>"); 
			out.println("alert('댓글 작성 실패')"); 
			out.println("history.back()"); 
			out.println("</script>"); 
		} else {
			MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
			boolean isSuccess = memberUpdateProService.updateCommentLP(nickname);
			if(isSuccess) {
				forward = new ActionForward();
				forward.setPath("CodingFreeCommunityDetail.cfC?num=" + post_num + "&page=" + nowPage); 
				forward.setRedirect(true);
			}
		}
		
		return forward;
	}

}
