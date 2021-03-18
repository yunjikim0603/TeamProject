package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.vo.ActionForward;

public class MemberLogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		// 세션 객체 가져오기
		HttpSession session = request.getSession();
		
		// 세션 객체 비우기
		session.invalidate();
		
		forward = new ActionForward();
		forward.setPath("");
		forward.setRedirect(true);
		
		return forward;
	}

}
