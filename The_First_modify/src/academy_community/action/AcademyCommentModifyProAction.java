package academy_community.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import academy_community.svc.AcademyCommentModifyService;
import academy_community.vo.AcademyCommentBean;
import academy_community.vo.ActionForward;
import svc.AllService;

public class AcademyCommentModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CommentModifyProAction!");
		ActionForward forward = null;

		// 받아오는 파라미터
		// hidden comment_num , post_num
		// nickname comment
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		String nickname = request.getParameter("nickname");
		String comment = request.getParameter("comment");
		
		AcademyCommentBean academyCommentBean = new AcademyCommentBean();
		academyCommentBean.setComment_num(comment_num);
		academyCommentBean.setComment(comment);
		academyCommentBean.setNickname(nickname);
		academyCommentBean.setPost_num(post_num);
		
		AcademyCommentModifyService academyCommentModifyService = new AcademyCommentModifyService();
		boolean isSuccess = academyCommentModifyService.updateComment(academyCommentBean);
		
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		request.setAttribute("today", today);
		
		if (isSuccess) {
			forward = new ActionForward();
			forward.setPath("AcademyDetail.ac?num=" + post_num);
			forward.setRedirect(true);
		}
		
		return forward;
	}

}
