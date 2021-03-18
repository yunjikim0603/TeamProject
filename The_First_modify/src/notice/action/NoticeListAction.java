package notice.action;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.svc.NoticeListService;
import notice.vo.ActionForward;
import notice.vo.NoticeBean;
import notice.vo.PageInfo;
import svc.AllService;

public class NoticeListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("NoticeListAction!");
		
		ActionForward forward = null;
		
		int page = 1; // 현재 페이지 번호
		int limit = 10; // 한 페이지 당 출력할 게시물 수
		// page 파라미터가 존재할 경우 파라미터에 전달된 데이터를 현재 페이지 번호로 대체
		// => URL 주소 뒤에 XXX.bo?page=XXX 형태로 파라미터 전달됨
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page")); // 정수로 변환하여 저장
		}
		
		//data 시간
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		request.setAttribute("today", today);
		
		// NoticeListService 인스턴스 생성 후 게시물 목록 갯수 가져오기
		NoticeListService noticeListService = new NoticeListService();
		int listCount = noticeListService.getListCount();
		System.out.println("총 게시물 수 : " + listCount);
		
		// NoticeListService 객체의 getArticleList() 메서드를 호출하여 게시물 목록 가져오기
		// => 파라미터로 현재 페이지(page) 와 게시물 수(limit) 를 전달
		// => ArrayList<NoticeBean> 타입 객체 리턴
		ArrayList<NoticeBean> articleList = null;
		articleList = noticeListService.getArticleList(page, limit);
		
		// 페이지 계산
		// 1. 총 페이지 수 계산
		int maxPage = (int)((double)listCount / limit + 0.95);
		// 2. 시작 페이지 번호 계산
		int startPage = (((int)((double)page / 10 + 0.9)) - 1) * 10 + 1;
		// 3. 마지막 페이지 번호 계산
		int endPage = startPage + 10 - 1;
		
		// 마지막 페이지 번호가 총 페이지 수 보다 클 경우 총 페이지 수를 마지막 페이지 번호로 설정
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		// PageInfo 객체에 페이지 정보 저장
		PageInfo pageInfo = new PageInfo(page, maxPage, startPage, endPage, listCount);
		
		// request 객체의 setAttribute() 메서드를 호출하여 페이지 정보, 게시물 목록 저장
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("articleList", articleList);
		request.setAttribute("today", today);
		
		// ActionForward 객체를 생성하여 notice 폴더의 notice_list.jsp 페이지로 이동(Dispatch 방식)
		forward = new ActionForward();
		forward.setPath("/notice/notice_list.jsp");
		// forward.setRedirect(false) 는 생략 가능(기본값)
		
		return forward;
	}

}



















