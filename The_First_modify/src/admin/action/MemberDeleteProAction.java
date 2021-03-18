package admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.svc.MemberDeleteProService;
import admin.vo.ActionForward;



public class MemberDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberDeleteProAction");
		
		ActionForward forward = null;
		
		String id = request.getParameter("id");
		
		MemberDeleteProService memberdeleteProService = new MemberDeleteProService();
		
		boolean isDeleteSuccess = memberdeleteProService.removeMember(id);
		
		if(!isDeleteSuccess) {
			// 리턴받은 수정 결과(isModifySuccess)가 false 일 경우
			// => 자바스크립트를 사용하여 "글 수정 실패!" 출력 후 이전 페이지로 이동
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원 강퇴 실패')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			forward = new ActionForward();
			forward.setPath("MemberList.ad");
			forward.setRedirect(true);
		}
		return forward;
	}

}
