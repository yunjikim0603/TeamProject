package any_community.action;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import any_community.svc.CommunityListService;
import any_community.vo.ActionForward;
import any_community.vo.CommunityBean;
import any_community.vo.PageInfo;
import svc.AllService;

public class CommunityListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CommunityListAction!");
		ActionForward forward = null;
		
		int page = 1;
		int limit = 10;
		
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		CommunityListService communityListService = new CommunityListService();
		
		ArrayList<CommunityBean> articleList = communityListService.getArticleList(page, limit);
		
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		request.setAttribute("today", today);
		request.setAttribute("articleList", articleList);
		
		forward = new ActionForward();
		forward.setPath("/any_community/communityList.jsp");
		
		return forward;
	}

}
