package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.svc.DupCheckService;
import member.vo.ActionForward;

public class idDupCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("idDupCheckAction!");
		
		request.setCharacterEncoding("utf-8");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("id");
		
		DupCheckService dupCheckService = new DupCheckService();
		int count = dupCheckService.idChk(id);
		
		out.print(count);
		
		return null;
	}

}
