package academy_community.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import academy_community.svc.AcademyCommentModifyService;
import academy_community.svc.AcademyDetailService;
import academy_community.vo.AcademyBean;
import academy_community.vo.AcademyCommentBean;
import academy_community.vo.ActionForward;
import svc.AllService;

public class AcademyCommentModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		System.out.println("AcademyCommentModifyFormAction");
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		int modify_num = Integer.parseInt(request.getParameter("comment_num"));
		
//		int cmmnt_page = 1; // 현재 페이지 번호
//		int cmmnt_limit = 10; // 한 페이지 당 출력할 게시물 수

		AcademyCommentModifyService academyCommentModifyService = new AcademyCommentModifyService();
		AcademyCommentBean comment = academyCommentModifyService.getComment(post_num,modify_num);
		
		AcademyDetailService academyDetailService = new AcademyDetailService();
		AcademyBean article = academyDetailService.getArticle(post_num);
		
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		request.setAttribute("today", today);
		request.setAttribute("article", article);
		request.setAttribute("comment", comment);
		request.setAttribute("post_num", post_num);
		request.setAttribute("modify_num", modify_num);
		
		forward = new ActionForward();
		forward.setPath("/academy/academyCommentModifyForm.jsp");
		
		return forward;
	}

}
