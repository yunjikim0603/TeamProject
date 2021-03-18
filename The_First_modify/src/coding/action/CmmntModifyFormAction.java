package coding.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CmmntListService;
import coding.svc.CodingDetailService;
import coding.svc.CodingReplyListService;
import coding.vo.CmmntBean;
import coding.vo.CodingBean;
import coding.vo.Coding_refBean;
import coding.vo.PageInfo;
import vo.ActionForward;

public class CmmntModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		System.out.println("CmmntModifyFormAction");
		//CmmntUpdateForm.code?post_num=<%=cmmntList.get(i).getPost_num()%>&comment_num=<%=cmmntList.get(i).getComment_num()%>
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		int modify_num = Integer.parseInt(request.getParameter("comment_num"));
//		int comment_page = Integer.parseInt(request.getParameter("comment_page"));
		int cmmnt_page = 1; // 현재 페이지 번호
		int cmmnt_limit = 10; // 한 페이지 당 출력할 게시물 수
		
		
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
		
		// 게시물 정보(BoardBean 객체), 페이지번호(page) 를 request 객체에 저장
//		request.setAttribute("article", article);
//		request.setAttribute("article_refList", article_refList);
		request.setAttribute("cmmnt_pageInfo", cmmnt_pageInfo);
		request.setAttribute("post_num", post_num);
//		request.setAttribute("comment_num", comment_num);
		request.setAttribute("cmmntList", cmmntList);
		request.setAttribute("modify_num", modify_num);
		
		forward = new ActionForward();
		forward.setPath("/coding/make_cmmntUpdateForm.jsp");
//		forward.setPath("/coding/cmmntUpdateForm.jsp");
//		forward.setPath("/coding/cmmntView.jsp");
		
		return forward;
	}

}
