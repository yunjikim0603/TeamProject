package academy_community.action;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import academy_community.svc.AcademyListService;
import academy_community.vo.AcademyBean;
import academy_community.vo.ActionForward;
import academy_community.vo.PageInfo;
import svc.AllService;

public class AcademyListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("BoardListAction!");
		
		ActionForward forward = null;
		
		
		// date 시간
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		// BoardListService 인스턴스 생성 후 게시물 목록 갯수 가져오기
		AcademyListService boardListService = new AcademyListService();
		
		ArrayList<AcademyBean> articleList = null;
		articleList = boardListService.getArticleList();
		
		request.setAttribute("articleList", articleList);
		request.setAttribute("today", today);

		// ActionForward 객체를 생성하여 board 폴더의 qna_board_list.jsp 페이지로 이동(Dispatch 방식)
		forward = new ActionForward();
		forward.setPath("/academy/academy_list.jsp");
		// forward.setRedirect(false) 는 생략 가능(기본값)

		return forward;

	}

}
