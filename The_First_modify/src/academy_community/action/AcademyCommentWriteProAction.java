package academy_community.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import academy_community.svc.AcademyCommentWriteProService;
import academy_community.vo.AcademyCommentBean;
import academy_community.vo.ActionForward;
import member.svc.MemberUpdateProService;

public class AcademyCommentWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AcademyCommentWriteProAction!");
		ActionForward forward = null;

		// 
		int num = Integer.parseInt(request.getParameter("post_num"));
		String nickname = request.getParameter("nickname");
		String comment = request.getParameter("comment");
		
		//
		String nowPage ="1";
		
		AcademyCommentBean commentBean = new AcademyCommentBean();
		commentBean.setPost_num(num);
		commentBean.setNickname(nickname);
		commentBean.setComment(comment);
		
		AcademyCommentWriteProService commentWriteProService = new AcademyCommentWriteProService();
		boolean isCommentWrite = commentWriteProService.registComment(commentBean);
		
		if(!isCommentWrite) { // 글쓰기 실패
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); 
			out.println("<script>"); 
			out.println("alert('댓글 등록 실패')"); 
			out.println("history.back()"); 
			out.println("</script>"); 
		} else { // 글쓰기 성공
			MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
			boolean isSuccess = memberUpdateProService.updateCommentLP(nickname);
			if(isSuccess) {
				System.out.println("댓글 작성 완료");
				forward = new ActionForward();
				forward.setPath("AcademyDetail.ac?num=" + num + "&page=" + nowPage);
				forward.setRedirect(true);
			}
		}
		
		return forward; 
	}

}
