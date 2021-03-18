package coding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CmmntDeleteProService;
import coding.svc.CodingDeleteProService;
import member.svc.MemberUpdateProService;
import vo.ActionForward;

public class CmmntDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		System.out.println("CmmntDeleteProAction");
		
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		String page = request.getParameter("page");
		String nickname = request.getParameter("nickname");
		
		
		CmmntDeleteProService cmmntDeleteProService = new CmmntDeleteProService();
		boolean isDelete = cmmntDeleteProService.deleteCmmntHeart(comment_num);
		 
		if(isDelete) {
			isDelete = cmmntDeleteProService.deleteCmmnt(comment_num);
			if(isDelete) {
				MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
				boolean isSuccess = memberUpdateProService.minusCommentLP(nickname);
				if(isSuccess) {
				
				forward = new ActionForward();
		//			request.setAttribute("post_num", post_num);
		//			request.setAttribute("page", page);
	//				forward.setPath("CodingDetail.code");
	//				forward.setPath("/coding/make_cmmntView.jsp");
					forward.setPath("CmmntList.code?post_num="+post_num);
	//				forward.setRedirect(true);
				}
			}
		}
		return forward;
	}

}
