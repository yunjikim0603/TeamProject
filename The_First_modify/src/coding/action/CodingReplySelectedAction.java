package coding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CodingDetailService;
import coding.svc.CodingReplySelectedService;
import member.svc.MemberUpdateProService;
import vo.ActionForward;

public class CodingReplySelectedAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		System.out.println("CodingSelectedAction");
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		int ref_num = Integer.parseInt(request.getParameter("ref_num"));
		int CP = Integer.parseInt(request.getParameter("CP"));
		int pNum = Integer.parseInt(request.getParameter("pNum"));

		CodingReplySelectedService codingReplySelectedService = new CodingReplySelectedService();
		boolean isSuccess = codingReplySelectedService.replySelected(post_num,ref_num,CP);
		
		if(isSuccess) {
			isSuccess = codingReplySelectedService.updateIsPublic(pNum,post_num);	
			if(isSuccess) {
				isSuccess= codingReplySelectedService.codingMinusCP(post_num);
			}
		}
		
		
		String nickname = codingReplySelectedService.getNickname(post_num, ref_num);
			
		if(isSuccess) {
			MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
			isSuccess = memberUpdateProService.updateMemberCP(nickname, CP);
			
			if(isSuccess) {
				request.setAttribute("isSelected", ref_num);
				request.setAttribute("post_num", post_num);
				forward = new ActionForward();
//				forward.setPath("");
//				forward.setPath("CodingReplySelectedFin.code?num="+post_num+"&page"+page);
//				forward.setPath("/coding/codingReplySelected.jsp?num="+post_num+"&page"+page);
				forward.setPath("CodingDetail.code?post_num="+post_num+"&isSelected="+ref_num);
//				forward.setPath("CodingDetail.code");
				forward.setRedirect(true);
			}

		}
		
		return forward;
	}

}
