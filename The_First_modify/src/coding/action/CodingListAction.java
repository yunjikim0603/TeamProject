package coding.action;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CodingListService;
import coding.svc.CodingReplyListService;
import coding.vo.CodingBean;
import coding.vo.PageInfo;
import svc.AllService;
import vo.ActionForward;

public class CodingListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CodingListAction");
		ActionForward forward = null;
		
		CodingListService codingListService = new CodingListService();
		int listCount = codingListService.getListCount();
//		System.out.println("총 게시물 수 : " + listCount);
		
		// codingListService 객체의 getArticleList() 메서드를 호출하여 게시물 목록 가져오기
		// => 파라미터로 현재 페이지(page) 와 게시물 수(limit) 를 전달  
		// => ArrayList<BoardBean> 타입 객체 리턴
		ArrayList<CodingBean> articleList = null;
		articleList = codingListService.getArticleList();

		CodingReplyListService codingReplyListService = new CodingReplyListService();
//		int reply_count = codingReplyListService.getReplyListCount(          );
		
		
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		// request 객체의 setAttribute() 메서드를 호출하여 페이지 정보, 게시물 목록 저장
		request.setAttribute("today", today);
		request.setAttribute("articleList", articleList);
//		request.setAttribute("reply_count", reply_count);
		
		forward = new ActionForward();
		forward.setPath("/coding/make_codingList.jsp");
//		forward.setPath("/coding/pre_codingList.jsp");
		
		return forward;
	}

}
