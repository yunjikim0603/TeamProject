package coding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CmmntDeleteProService;
import coding.svc.CodingDeleteProService;
import coding.svc.CodingDetailService;
import coding.svc.CodingReplyDeleteService;
import coding.vo.CodingBean;
import vo.ActionForward;

public class CodingDeleteFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CodingDeleteFormAction");
		ActionForward forward = null;
		int post_num = Integer.parseInt(request.getParameter("post_num"));
//		String page = request.getParameter("page");
		
		CodingReplyDeleteService codingReplyDeleteService = new CodingReplyDeleteService();
		boolean isDelete = codingReplyDeleteService.deleteReply(post_num);
		
		if(isDelete) {
			CodingDeleteProService codingDeleteProService = new CodingDeleteProService();
			isDelete = codingDeleteProService.removeArticle(post_num);
			if(isDelete) {
				forward = new ActionForward();
				request.setAttribute("post_num", post_num);
//				request.setAttribute("page", page);
				
				forward = new ActionForward();
				forward.setPath("CodingList.code"); // Dispatch 방식으로 이동
				
			}
		}

		
		return forward;
	}

}
