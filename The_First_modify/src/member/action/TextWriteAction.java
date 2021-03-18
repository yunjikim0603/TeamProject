package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import any_community.svc.CommunityWriteService;
import member.svc.TextWriteService;
import member.vo.ActionForward;
import member.vo.TextBean;

public class TextWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("TextWriteAction!");
		request.setCharacterEncoding("UTF-8");		
		ActionForward forward = null;
		
		String sender = request.getParameter("sender");
		String receiver = request.getParameter("receiver");
		String subject = request.getParameter("subject");
		String contents = request.getParameter("contents");
		
		TextBean tb = new TextBean();
		tb.setSender(sender);
		tb.setReceiver(receiver);
		tb.setSubject(subject);
		tb.setContents(contents);
		
		TextWriteService textWriteService = new TextWriteService();
		boolean isWriteSuccess = textWriteService.writeText(tb);
		
		if(!isWriteSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('쪽지 전송 실패')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			forward = new ActionForward(); 
			forward.setPath("TextList.te?receiver=" + receiver);
		}
		
		return forward;
	}

}
