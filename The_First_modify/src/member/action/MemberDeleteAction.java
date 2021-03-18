package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.svc.MemberDeleteService;
import member.vo.ActionForward;

public class MemberDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberDeleteAction!");
		ActionForward forward = null;
		boolean isDeleteSuccess = false;
		
		String id = request.getParameter("id");
		
		MemberDeleteService memberDeleteService = new MemberDeleteService();
		isDeleteSuccess = memberDeleteService.deleteMember(id);
		
		if (!isDeleteSuccess) { // 탈퇴 실패
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); 
			out.println("<script>"); 
			out.println("alert('회원 탈퇴 실패!');"); 
			out.println("history.back()"); 
			out.println("</script>");
		} else { // 탈퇴 성공
			System.out.println("회원 탈퇴 성공");

			HttpSession session = request.getSession();
			session.invalidate();
			
			// 왜 작동안하지 ?
			forward = new ActionForward();
			forward.setPath(""); 
			forward.setRedirect(true);
		}
		
		return forward;
	}

}
