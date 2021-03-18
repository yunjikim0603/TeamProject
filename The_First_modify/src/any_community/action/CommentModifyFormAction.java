package any_community.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import any_community.svc.CommentModifyService;
import any_community.svc.CommunityDetailService;
import any_community.vo.ActionForward;
import any_community.vo.AnyCommentBean;
import any_community.vo.CommunityBean;
import svc.AllService;

public class CommentModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		System.out.println("CommentModifyFormAction");
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		int modify_num = Integer.parseInt(request.getParameter("comment_num"));
		int cmmnt_page = 1; // 현재 페이지 번호
		int cmmnt_limit = 10; // 한 페이지 당 출력할 게시물 수

		CommentModifyService commentModifyService = new CommentModifyService();
		AnyCommentBean comment = commentModifyService.getComment(post_num,modify_num);
		
		CommunityDetailService communityDetailService = new CommunityDetailService();
		CommunityBean article = communityDetailService.getArticle(post_num);
		
		//댓글페이지 계산
		// 1. 총 페이지 수 계산
//		int cmmnt_maxPage = (int)((double)cmmnt_page / cmmnt_limit + 0.95);
		// 2. 시작 페이지 번호 계산
//		int cmmnt_startPage = (((int)((double)cmmnt_page / 10 + 0.9)) - 1) * 10 + 1;
		// 3. 마지막 페이지 번호 계산
//		int cmmnt_endPage = cmmnt_startPage + 10 - 1;
		
		// 마지막 페이지 번호가 총 페이지 수 보다 클 경우 총 페이지 수를 마지막 페이지 번호로 설정
//		if(cmmnt_endPage > cmmnt_maxPage) {
//				cmmnt_endPage = cmmnt_maxPage;
//		}
					
		// 게시물 정보(BoardBean 객체), 페이지번호(page) 를 request 객체에 저장
//		request.setAttribute("article_refList", article_refList);
//		request.setAttribute("cmmnt_pageInfo", cmmnt_pageInfo);
		
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		request.setAttribute("today", today);
		request.setAttribute("article", article);
		request.setAttribute("comment", comment);
		request.setAttribute("post_num", post_num);
		request.setAttribute("modify_num", modify_num);
		
		forward = new ActionForward();
		forward.setPath("/any_community/commentModify.jsp");
		
		return forward;
	}

}
