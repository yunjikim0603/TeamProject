package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.svc.MemberUpdateProService;
import member.svc.TextUpdateService;
import member.vo.ActionForward;
import member.vo.MemberBean;

public class MemberUpdateProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberUpdateProAction");
		ActionForward forward = null;
		boolean isTextUpdate = false;

		String id = request.getParameter("id");
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		MemberBean mb = new MemberBean(id, password, nickname, email);
		
		MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
		boolean isMemberUpdateSuccess = memberUpdateProService.updateMember(mb);
		
		if(!isMemberUpdateSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원정보 수정 실패')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			
			TextUpdateService textUpdateService = new TextUpdateService();
			int textCount = textUpdateService.getTextCount(nickname);
			
			if (textCount > 0) {
				isTextUpdate = textUpdateService.updateReceiver(nickname);
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("nickname", nickname);
			forward = new ActionForward();
			forward.setPath("");
			forward.setRedirect(true);
			
		}
		
		return forward;
	}

}


