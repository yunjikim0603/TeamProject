package academy_community.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import academy_community.svc.AcademyCommentDeleteProService;
import academy_community.svc.AcademyCommentListService;
import academy_community.svc.AcademyDeleteProService;
import academy_community.svc.AcademyDetailService;
import academy_community.svc.AcademyModifyProService;
import academy_community.vo.AcademyBean;
import academy_community.vo.ActionForward;
import any_community.svc.CommentDeleteProService;
import member.svc.MemberUpdateProService;

public class AcademyDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AcademyDeleteProAction");
		ActionForward forward = null;
		
		int num = Integer.parseInt(request.getParameter("post_num"));
		String nowPage = request.getParameter("page");

		// test
		if (nowPage == null) {
			nowPage = "1";
		}
		
		boolean isCommentDeleteSuccess =false;
		
		// 1)post_num에 대한 댓글 카운팅 
		// 2) 댓글이 존재할경우 CommentDeleteProService에서 post_num으로 댓글 전부삭제
		// 3) 댓글 전부 삭제가 완료될겅유 communityDeleteProService.deleteArticle 실행
		AcademyCommentListService academyCommentListService = new AcademyCommentListService();
		int commentListCount = academyCommentListService.getCommentListCount(num);
			
		if (commentListCount > 0) {
			AcademyCommentDeleteProService academyCommentDeleteProService = new AcademyCommentDeleteProService();
			isCommentDeleteSuccess = academyCommentDeleteProService.deleteAllComment(num);
		}
		AcademyDetailService academyDetailService = new AcademyDetailService();
		AcademyBean academyBean = academyDetailService.getArticle(num);
		String nickname = academyBean.getNickname();
		 
		if(isCommentDeleteSuccess) {
			AcademyDeleteProService academyDeleteProService = new AcademyDeleteProService();
			boolean isArticleDeleteSuccess = academyDeleteProService.removeArticle(num);
		
				if (!isArticleDeleteSuccess) {
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
						forward.setPath("AcademyList.ac?page=" + nowPage);
						forward.setRedirect(true);
					}
				}
		}

		return forward;
	}
}
