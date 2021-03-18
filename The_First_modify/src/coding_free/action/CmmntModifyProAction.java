package coding_free.action;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coding_free.svc.CodingFreeCommentListService;
import coding_free.vo.CodingFreeCommentBean;
import svc.AllService;
import coding_free.svc.CodingFreeDetailService;
import coding_free.svc.CodingFreeModifyService;
import coding_free.vo.ActionForward;
import coding_free.vo.CodingFreeBean;

public class CmmntModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CmmntModifyProAction!");
		
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
//		String page = request.getParameter("page");
		
		CodingFreeCommentBean codingFreeCommentBean = new CodingFreeCommentBean();
		codingFreeCommentBean.setComment(request.getParameter("comment"));
		codingFreeCommentBean.setNickname(request.getParameter("nickname"));
		codingFreeCommentBean.setComment_num(comment_num);
		
		
		CodingFreeModifyService codingFreeModifyService = new CodingFreeModifyService();
		boolean isSuccess = codingFreeModifyService.updateCmmntFree(codingFreeCommentBean);
		
//		if (comentList.size() > 0) {
//			request.setAttribute("commentList", comentList);
//		}
//		
//		if (article != null) {
//			CodingFreeDetailService.plusReadcount(post_num);
//		}
		
		AllService allService = new AllService();
		Date today = allService.getToday();
		
//		request.setAttribute("article", article);
//		request.setAttribute("page", page);
		request.setAttribute("today", today);
//		request.setAttribute("post_num", post_num);
		
		ActionForward forward = new ActionForward();
//		forward.setPath("/coding_free/codingFreeView.jsp");
		forward.setPath("CodingFreeDetail.cf");
		
		return forward;
	}

}
