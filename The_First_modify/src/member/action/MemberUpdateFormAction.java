package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.svc.MemberDetailService;
import member.vo.ActionForward;
import member.vo.MemberBean;

public class MemberUpdateFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberUpdateFormAction");
		ActionForward forward = null;
		
		String nickname = request.getParameter("nickname");
		
		MemberDetailService memberDetailService = new MemberDetailService();
		MemberBean mb = memberDetailService.getMember(nickname);
		
		request.setAttribute("mb", mb);
		
		forward = new ActionForward();
		forward.setPath("/member/updateForm.jsp"); 
		
		return forward;
	}

}


