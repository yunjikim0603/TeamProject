package coding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CodingDetailService;
import coding.vo.CodingBean;
import member.svc.MemberDetailService;
import member.vo.MemberBean;
import vo.ActionForward;

public class CodingWriteFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CodingWriteFormAction");
		ActionForward forward = null;
		
		String nickname = request.getParameter("nickname");
		MemberDetailService memberDetailService = new MemberDetailService();
		MemberBean memberBean = memberDetailService.getMember(nickname);
		
		int CP = memberBean.getCp();
		System.out.println(CP);
		
		request.setAttribute("CP", CP);
		
		forward = new ActionForward();
		forward.setPath("/coding/make_codingWriteForm.jsp"); // Dispatch 방식으로 이동
		
		
		return forward;
	}

}
