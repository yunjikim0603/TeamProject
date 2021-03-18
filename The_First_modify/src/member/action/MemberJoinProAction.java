package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.svc.MemberJoinProService;
import member.vo.ActionForward;
import member.vo.MemberBean;

public class MemberJoinProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberJoinProAction!");
		ActionForward forward = null;

		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		MemberBean member = new MemberBean(id, password, nickname, email);

		MemberJoinProService memberJoinProService = new MemberJoinProService();
		boolean isJoinSuccess = memberJoinProService.joinMember(member);

		if (!isJoinSuccess) { // 가입 실패
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); 
			out.println("<script>"); 
			out.println("alert('회원 가입 실패!');"); 
			out.println("history.back()"); 
			out.println("</script>");
		} else { // 가입 성공
			HttpSession session = request.getSession(); // 가입 성공하자마자 세션 부여
			session.setAttribute("sId", id);
			
			forward = new ActionForward();
			forward.setPath("MemberMailSendAction.me"); 
		}

		return forward;
	}

}
