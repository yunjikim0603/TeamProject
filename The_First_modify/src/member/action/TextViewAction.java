package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import any_community.svc.CommunityDetailService;
import any_community.vo.CommunityBean;
import member.svc.TextViewService;
import member.vo.ActionForward;
import member.vo.TextBean;

public class TextViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("TextViewAction!");
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		String nowPage = request.getParameter("page");
		
		TextViewService textViewService = new TextViewService();
		TextBean text = textViewService.getText(idx);
		
		if (text != null) {
			textViewService.updateChecked(idx);
		}
		
		request.setAttribute("text", text);
		request.setAttribute("page", nowPage);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/member/textView.jsp");
		return forward;
	}

}
