package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.svc.MemberLoginService;
import member.vo.ActionForward;
import member.vo.MemberBean;

public class NewLoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("NewLoginAction!");
		ActionForward forward = new ActionForward();
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		MemberBean member = new MemberBean(id, password);
		
		MemberLoginService memberLoginService = new MemberLoginService();
		int loginResult = memberLoginService.isLoginMember(member);
		boolean emailCheckResult = memberLoginService.isEmailChecked(id);
		String nickname = memberLoginService.getNickname(id);

		if (loginResult == 0) {
			out.println("<script>"); 
			out.println("alert('아이디 없음')"); 
			out.println("history.back()"); 
			out.println("</script>"); 
			out.close();
		} else if (loginResult == -1) {
			out.println("<script>"); 
			out.println("alert('패스워드 틀림')"); 
			out.println("history.back()"); 
			out.println("</script>"); 
			out.close();
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("sId", id);
			
			if (emailCheckResult) {
				session.setAttribute("nickname", nickname);
				out.print("<script>");
				out.print("window.close();");
				out.print("</script>");
				out.close();
			} else {
				forward.setPath("emailSendConfirm.jsp"); 
				forward.setRedirect(true);
			}
		}
		
		return forward;
	}

}
