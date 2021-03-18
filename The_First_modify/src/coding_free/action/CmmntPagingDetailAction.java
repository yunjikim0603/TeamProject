package coding_free.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coding.svc.CodingReplyListService;
import coding_free.svc.CodingFreeCommentListService;
import coding_free.vo.ActionForward;


public class CmmntPagingDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
ActionForward forward = null;
		
		System.out.println("CodingReplyDetailAction");
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
//		 cmmnt_page = Integer.parseInt(request.getParameter("nowPage"));
		
//		System.out.println("nowPage    " + request.getParameter("nowPage"));
		 int cmmnt_page = 1;
		int cmmnt_limit = 3;
		
		if(request.getParameter("nowPage") != null) {
			cmmnt_page = Integer.parseInt(request.getParameter("nowPage")); // 정수로 변환하여 저장
//			System.out.println("cmmnt_page: " + cmmnt_page);
		}
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		CodingFreeCommentListService codingFreeCommentListService = new CodingFreeCommentListService();

		int cmmnt_count = codingFreeCommentListService.getCommentListCount(post_num);
		//답글 페이지
		int cmmnt_maxPage = (int)((double)cmmnt_count / cmmnt_limit + 0.95);
		// 2. 시작 페이지 번호 계산
		int cmmnt_startPage = (((int)((double)cmmnt_page / 10 + 0.9)) - 1) *10 + 1;
		// 3. 마지막 페이지 번호 계산
		int cmmnt_endPage = cmmnt_startPage + 10 - 1;
		
		// 마지막 페이지 번호가 총 페이지 수 보다 클 경우 총 페이지 수를 마지막 페이지 번호로 설정
		if(cmmnt_endPage > cmmnt_maxPage) {
			cmmnt_endPage = cmmnt_maxPage;
		}
		// PageInfo 객체에 페이지 정보 저장
//		PageInfo cmmntPageInfo = new PageInfo(cmmnt_page, cmmnt_maxPage, cmmnt_startPage, cmmnt_endPage, cmmnt_count);
//		System.out.println(reply_page+", "+reply_maxPage+", "+reply_startPage+", "+reply_endPage+", "+reply_count+", ");
		
		
		String page = "{\"pageInfo\":["; // cmmntList는 키값이 됨
		
			page += "{\"cmmnt_page\":\"" + cmmnt_page + "\""; //0
			page += "},";
			page += "{\"cmmnt_maxPage\":\"" + cmmnt_maxPage + "\"";		//1
			page += "},";
			page += "{\"cmmnt_startPage\":\"" + cmmnt_startPage+ "\"";		//1
			page += "},";
			page += "{\"cmmnt_endPage\":\"" + cmmnt_endPage + "\"";		//2
			page += "},";
			page += "{\"cmmnt_count\":\"" + cmmnt_count + "\"";			//3
			page += "}";
			
		page += "]}";
		
		
		// 누적된 json 형식의 문자열을 호출한 페이지(뷰페이지)로 보내줌

		out.print(page);
		
		return forward;
	}


}
