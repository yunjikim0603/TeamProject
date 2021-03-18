package notice.action;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.svc.NoticeCommentModifyService;
import notice.svc.NoticeDetailService;
import notice.vo.ActionForward;
import notice.vo.NoticeBean;
import notice.vo.NoticeCommentBean;
import svc.AllService;

public class NoticeCommentModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		System.out.println("NoticeCommentModifyProAction!");
		
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		int post_num = Integer.parseInt(request.getParameter("post_num"));
//		String page = request.getParameter("page");
		
		NoticeCommentBean noticeCommentBean = new NoticeCommentBean();
		noticeCommentBean.setComment(request.getParameter("comment"));
		noticeCommentBean.setNickname(request.getParameter("nickname"));
		noticeCommentBean.setComment_num(comment_num);
		
		
		NoticeCommentModifyService noticeCommentModifyService = new NoticeCommentModifyService();
		boolean isSuccess = noticeCommentModifyService.updateComment(noticeCommentBean);
		
		if(isSuccess) {
			AllService allService = new AllService();
			Date today = allService.getToday();
			
			request.setAttribute("today", today);
			request.setAttribute("post_num", post_num);
			
			forward = new ActionForward();
	//		forward.setPath("/coding_free/codingFreeView.jsp");
			forward.setPath("NoticeDetail.no?post_num="+post_num);
		}
		
		return forward;
	}

}
