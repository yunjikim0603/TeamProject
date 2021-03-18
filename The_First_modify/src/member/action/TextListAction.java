package member.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import any_community.svc.CommunityListService;
import any_community.vo.CommunityBean;
import any_community.vo.PageInfo;
import member.svc.TextListService;
import member.vo.ActionForward;
import member.vo.TextBean;

public class TextListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("TextListAction!");
		ActionForward forward = null;
		
		String receiver = request.getParameter("receiver");
		
		int page = 1;
		int limit = 10;
		
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		TextListService textListService = new TextListService();
		int listCount = textListService.getListCount(receiver);
		
		ArrayList<TextBean> textList = textListService.getTextList(page, limit, receiver);
		
		int maxPage = (int)((double)listCount / limit + 0.95);
		int startPage = (((int)((double)page / 10 + 0.9)) - 1) * 10 + 1;
		int endPage = startPage + 10 - 1;

		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pageInfo = new PageInfo(page, maxPage, startPage, endPage, listCount);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("textList", textList);
		
		forward = new ActionForward();
		forward.setPath("/member/textList.jsp");
		
		return forward;
	}

}
