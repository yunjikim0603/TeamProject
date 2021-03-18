package coding_free.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coding_free.svc.CodingFreeDetailService;
import coding_free.vo.ActionForward;
import coding_free.vo.CodingFreeBean;

public class CodingFreeModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CodingFreeModifyFormAction");

		int num = Integer.parseInt(request.getParameter("num"));
		
		CodingFreeBean article = null;
		CodingFreeDetailService codingFreeDetailService = new CodingFreeDetailService();
		article = codingFreeDetailService.getArticle(num);
		
		request.setAttribute("article", article);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/coding_free/codingFreeModify.jsp");
		
		return forward;
	}

}
