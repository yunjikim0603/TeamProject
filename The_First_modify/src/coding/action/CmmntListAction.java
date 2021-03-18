package coding.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CmmntListService;
import coding.svc.CodingListService;
import coding.vo.CmmntBean;
import coding.vo.CodingBean;
import coding.vo.PageInfo;
import svc.AllService;
import vo.ActionForward;

public class CmmntListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CmmntListAction");
		ActionForward forward = null;
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		String page = request.getParameter("page"); 

		int cmmnt_page = 1; // 현재 페이지 번호
		int cmmnt_limit = 10; // 한 페이지 당 출력할 게시물 수
	
		
		if(request.getParameter("cmmnt_page") != null) {
			cmmnt_page = Integer.parseInt(request.getParameter("cmmnt_page")); // 정수로 변환하여 저장
		}
		
		CmmntListService cmmntListService = new CmmntListService();
		int cmmnt_count = cmmntListService.getCommentListCount(post_num);
		ArrayList<CmmntBean> cmmntList = cmmntListService.getCmmntList(post_num,cmmnt_page,cmmnt_limit);
		
		System.out.println(cmmnt_page);
		
		// 페이지 계산
		// 1. 총 페이지 수 계산
		int cmmnt_maxPage = (int)((double)cmmnt_count / cmmnt_limit + 0.95);
		// 2. 시작 페이지 번호 계산
		int cmmnt_startPage = (((int)((double)cmmnt_page / 10 + 0.9)) - 1) * 10 + 1;
		// 3. 마지막 페이지 번호 계산
		int cmmnt_endPage = cmmnt_startPage + 10 - 1;
		
		// 마지막 페이지 번호가 총 페이지 수 보다 클 경우 총 페이지 수를 마지막 페이지 번호로 설정
		if(cmmnt_endPage > cmmnt_maxPage) {
			cmmnt_endPage = cmmnt_maxPage;
		}
		
		// PageInfo 객체에 페이지 정보 저장
//		PageInfo pageInfo = new PageInfo(page, maxPage, startPage, endPage, listCount);
		PageInfo cmmnt_pageInfo = new PageInfo(cmmnt_page, cmmnt_maxPage, cmmnt_startPage, cmmnt_endPage, cmmnt_count);
		
		//date 시간
		AllService allService = new AllService();
		Date today= allService.getToday();
		
		System.out.println(post_num+"asdasdasdadsadasdasddasd");
		// request 객체의 setAttribute() 메서드를 호출하여 페이지 정보, 게시물 목록 저장
		request.setAttribute("today", today);
		request.setAttribute("cmmntList", cmmntList);
		request.setAttribute("cmmnt_pageInfo", cmmnt_pageInfo);
		request.setAttribute("post_num", post_num);
		request.setAttribute("page", page);
		
		forward = new ActionForward();
//		forward.setPath("/coding/cmmntView_ajax.jsp");
//		forward.setPath("/coding/cmmntView.jsp");
		forward.setPath("/coding/make_cmmntView.jsp");
		return forward;
	}

}
