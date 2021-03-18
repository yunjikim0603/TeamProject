package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.dao.MemberDAO;
import member.svc.MemberDetailService;
import member.svc.MemberLoginService;
import member.vo.ActionForward;
import member.vo.MemberBean;

public class MemberLoginProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("LoginProAction!");
		ActionForward forward = null;
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		MemberBean member = new MemberBean(id, password);
		
		MemberLoginService memberLoginService = new MemberLoginService();
		int loginResult = memberLoginService.isLoginMember(member);
		boolean emailCheckResult = memberLoginService.isEmailChecked(id);
		String nickname = memberLoginService.getNickname(id);
		
		MemberDetailService memberDetailService = new MemberDetailService();
		MemberBean memberBean = memberDetailService.getMember(nickname);
		int level = memberBean.getLevel();
		
		if(loginResult == 0) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>"); 
			out.println("alert('아이디 없음')"); 
			out.println("history.back()"); 
			out.println("</script>"); 
		} else if(loginResult == -1) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); 
			out.println("<script>"); 
			out.println("alert('패스워드 틀림')"); 
			out.println("history.back()"); 
			out.println("</script>"); 
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("sId", id);
			
			if (emailCheckResult) {
				session.setAttribute("nickname", nickname);
				session.setAttribute("level", level);
				forward = new ActionForward();
				forward.setPath(""); 
				forward.setRedirect(true);
			} else {
				forward = new ActionForward();
				forward.setPath("emailSendConfirm.jsp"); 
				forward.setRedirect(true);
			}
		}
		
		return forward;
	}

}





