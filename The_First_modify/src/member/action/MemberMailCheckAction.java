package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.svc.MemberMailService;
import member.vo.ActionForward;
import member.vo.SHA256;

public class MemberMailCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberMailCheckAction!");
		HttpSession session = request.getSession();
		String id = null;
		
		String code = request.getParameter("code");
		
		if(session.getAttribute("sId") != null) {
			id = (String) session.getAttribute("sId");
		}
		
		if(id == null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>"); 
			out.println("alert('로그인을 해주세요.')"); 
			out.println("location.href = 'LoginForm.me'"); 
			out.println("</script>");
		}
		
		MemberMailService memberMailService = new MemberMailService();
		String email = memberMailService.getMemberEmail(id);
		
		new SHA256();
		boolean rightCode = (SHA256.getSHA256(email).equals(code)) ? true : false;
		
		if(rightCode == true) {
			memberMailService.setUserEmailChecked(id);
			
			session.invalidate();
			System.out.println("MailCheckAction 성공 !");
			
			//
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); 
			out.println("<script>"); 
			out.println("alert('인증에 성공했습니다. 로그인해주세요.');"); 
			out.println("location.href = ''"); 
			out.println("</script>"); 
		} else {
			System.out.println("MailCheckAction 실패 !");
			
			//
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); 
			out.println("<script>"); 
			out.println("alert('인증에 실패. 유효하지 않은 코드입니다.');"); 
			out.println("location.href = ''"); 
			out.println("</script>"); 
		}
		ActionForward forward = new ActionForward();
		forward.setPath("");
		forward.setRedirect(true);
		
		return forward;
	}
}
