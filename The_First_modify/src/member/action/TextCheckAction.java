package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.svc.TextCheckService;
import member.vo.ActionForward;

public class TextCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("TextCheckAction!");
		
		String receiver = request.getParameter("receiver");
		
		TextCheckService textCheckService = new TextCheckService();
		int count = textCheckService.checkText(receiver);
		
		//
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(count);
		
		return null;
	}

}
