package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.svc.TextDeleteService;
import member.vo.ActionForward;

public class TextDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("TextViewAction!");
		ActionForward forward = null;

		int idx = Integer.parseInt(request.getParameter("idx"));
		String nowPage = request.getParameter("page");
		String receiver = request.getParameter("receiver");
		
		TextDeleteService textDeleteService = new TextDeleteService();
		boolean isDeleteSuccess = textDeleteService.deleteText(idx);

		if (!isDeleteSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 삭제 실패!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			forward = new ActionForward();
			forward.setPath("TextList.te?page="+nowPage+"&receiver="+receiver);
			forward.setRedirect(true);
		}
		
		return forward;
	}

}
