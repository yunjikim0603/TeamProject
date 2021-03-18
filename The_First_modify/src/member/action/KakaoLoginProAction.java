package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.svc.KakaoLoginProService;
import member.vo.ActionForward;
import member.vo.MemberBean;

public class KakaoLoginProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("KakaoLoginProAction");
		ActionForward forward = null;
		
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		
		MemberBean member = new MemberBean(id, pass);
		KakaoLoginProService kakaoLoginProService = new KakaoLoginProService();
		int loginResult = kakaoLoginProService.isLoginMember(member);
		
		if(loginResult == 0) {
			System.out.println("아이디 없음!");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디 없음!')");
			out.println("history.back()"); 
			out.println("</script>");
		} else if(loginResult == -1) {
			System.out.println("패스워드 틀림!");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('패스워드 틀림!')");
			out.println("history.back()"); 
			out.println("</script>");
		} else {
			System.out.println("로그인 성공!");
			
			HttpSession session = request.getSession();
			session.setAttribute("sId", id);
			
			// index.jsp 로 이동
			forward = new ActionForward();
//			forward.setPath("index.jsp");
			forward.setPath("main.all"); // MVC_Board 프로젝트의 홈으로 이동 = index.jsp
			forward.setRedirect(true);
		}
		
		return forward;
	}
	

}
