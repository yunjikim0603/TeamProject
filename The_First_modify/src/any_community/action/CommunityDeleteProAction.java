package any_community.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import any_community.svc.CommentDeleteProService;
import any_community.svc.CommentListService;
import any_community.svc.CommunityDeleteProService;
import any_community.svc.CommunityDetailService;
import any_community.vo.ActionForward;
import any_community.vo.CommunityBean;
import member.svc.MemberUpdateProService;

public class CommunityDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CommunityDeleteProAction");
		ActionForward forward = null;
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		// 1)post_num에 대한 댓글 카운팅 
		// 2) 댓글이 존재할경우 CommentDeleteProService에서 post_num으로 댓글 전부삭제
		// 3) 댓글 전부 삭제가 완료될겅유 communityDeleteProService.deleteArticle 실행
		CommentListService commentListService = new CommentListService();
		int commentListCount = commentListService.getCommentListCount(num);
		
		if (commentListCount > 0) {
			CommentDeleteProService CommentDeleteProService = new CommentDeleteProService();
			boolean isCommentDeleteSuccess = CommentDeleteProService.deleteAllComment(num);
		}
		
		// 게시글에 대한 작성자 닉네임 가져오기 
		CommunityDetailService communityDetailService = new CommunityDetailService();
		CommunityBean communityBean = communityDetailService.getArticle(num);
		String nickname = communityBean.getNickname();
		
		// 글삭제
		CommunityDeleteProService communityDeleteProService = new CommunityDeleteProService();
		boolean isArticleDeleteSuccess = communityDeleteProService.deleteArticle(num);
		
		if (!isArticleDeleteSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 삭제 실패!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			// 게시글 삭제한 유저에대해 LP 감소
			MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
			boolean isSuccess = memberUpdateProService.minusArticletLP(nickname);
			
			if(isSuccess) {
				forward = new ActionForward();
				forward.setPath("CommunityList.any");
				forward.setRedirect(true);
			}
		}
		
		return forward;
	}

}
