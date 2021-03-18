package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.svc.DupCheckService;
import member.vo.ActionForward;

public class nicknameDupCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("nicknameDupCheckAction!");
		
		request.setCharacterEncoding("utf-8");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String nickname = request.getParameter("nickname");

		DupCheckService dupCheckService = new DupCheckService();
		int count = dupCheckService.nicknameChk(nickname);
		
		out.print(count);
		
		return null;
	}

}
