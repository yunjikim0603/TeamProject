package coding.action;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CmmntListService;
import coding.svc.CmmntWriteProService;
import coding.vo.CmmntBean;
import coding.vo.PageInfo;
import svc.AllService;
import vo.ActionForward;

public class CmmntWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null; 
		CmmntBean cmmntBean = new CmmntBean();
		
		String page = request.getParameter("page"); 
//		int cmmnt_page = Integer.parseInt(request.getParameter("cmmnt_page"));
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		int cmmnt_page = 1; // 현재 페이지 번호
		int cmmnt_limit = 10; // 
		
		System.out.println("CmmntWriteProAction");
		System.out.println(request.getParameter("nickname"));
		
		cmmntBean.setPost_num(Integer.parseInt(request.getParameter("post_num")));
		cmmntBean.setNickname(request.getParameter("nickname"));
		cmmntBean.setComment(request.getParameter("comment"));
//		cmmntBean.setDate(request.getParameter("date"));
		
		CmmntWriteProService cmmntWriteProService = new CmmntWriteProService();
		boolean isWriteSuccess = cmmntWriteProService.writeCmmnt(cmmntBean);
		
		CmmntListService cmmntListService = new CmmntListService();
		int cmmnt_count = cmmntListService.getCommentListCount(post_num);
		ArrayList<CmmntBean> cmmntList = cmmntListService.getCmmntList(post_num,cmmnt_page,cmmnt_limit);
		
		//댓글페이지 계산
		// 1. 총 페이지 수 계산
		int cmmnt_maxPage = (int)((double)cmmnt_page / cmmnt_limit + 0.95);
		// 2. 시작 페이지 번호 계산
		int cmmnt_startPage = (((int)((double)cmmnt_page / 10 + 0.9)) - 1) * 10 + 1;
		// 3. 마지막 페이지 번호 계산
		int cmmnt_endPage = cmmnt_startPage + 10 - 1;
		
		// 마지막 페이지 번호가 총 페이지 수 보다 클 경우 총 페이지 수를 마지막 페이지 번호로 설정
		if(cmmnt_endPage > cmmnt_maxPage) {
				cmmnt_endPage = cmmnt_maxPage;
		}
				
		// PageInfo 객체에 페이지 정보 저장
		PageInfo cmmnt_pageInfo = new PageInfo(cmmnt_page, cmmnt_maxPage, cmmnt_startPage, cmmnt_endPage, cmmnt_count);

		AllService allService = new AllService();
		Date today= allService.getToday();
		
		
		request.setAttribute("today", today);		
		request.setAttribute("cmmnt_pageInfo", cmmnt_pageInfo);
//		request.setAttribute("cmmnt_page", cmmnt_page);
		request.setAttribute("post_num", post_num);
//		request.setAttribute("page", page);
		request.setAttribute("cmmntList", cmmntList);
		
		
		forward = new ActionForward();
//		forward.setPath("/coding/cmmntView_ajax.jsp");
//		forward.setPath("/coding/cmmntView.jsp");
		forward.setPath("/coding/make_cmmntView.jsp");
		
		return forward;
	}

}
