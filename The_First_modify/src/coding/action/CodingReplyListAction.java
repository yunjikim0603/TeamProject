package coding.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import any_community.svc.CommentListService;
import coding.svc.CmmntListService;
import coding.svc.CodingDetailService;
import coding.svc.CodingListService;
import coding.svc.CodingReplyListService;
import coding.vo.CmmntBean;
import coding.vo.CodingBean;
import coding.vo.Coding_refBean;
import coding.vo.PageInfo;
import jdk.nashorn.internal.runtime.JSONFunctions;
import netscape.javascript.JSObject;
import svc.AllService;
import vo.ActionForward;

public class CodingReplyListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
//		System.out.println("CodingReplyListAction");
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
//		String page = request.getParameter("page"); 
		
		int reply_page = 1;
		int reply_limit = 2;
		
		if(request.getParameter("reply_page") != null) {
			reply_page = Integer.parseInt(request.getParameter("reply_page")); // 정수로 변환하여 저장
		}
		
//		System.out.println(reply_page);
		
		CodingReplyListService codingReplyListService = new CodingReplyListService();
		int reply_count = codingReplyListService.getReplyListCount(post_num);
		List<Coding_refBean> replyList = codingReplyListService.getReplyList(post_num, reply_page, reply_limit);
		
		
		// 페이지 계산
		// 1. 총 페이지 수 계산
		int reply_maxPage = (int)((double)reply_count / reply_limit + 0.95);
		// 2. 시작 페이지 번호 계산
		int reply_startPage = (((int)((double)reply_page / 10 + 0.9)) - 1) * 10 + 1;
		// 3. 마지막 페이지 번호 계산
		int reply_endPage = reply_startPage + 10 - 1;
		
		// 마지막 페이지 번호가 총 페이지 수 보다 클 경우 총 페이지 수를 마지막 페이지 번호로 설정
		if(reply_endPage > reply_maxPage) {
			reply_endPage = reply_maxPage;
		}
		System.out.println(reply_page+", "+reply_maxPage+", "+reply_startPage+", "+reply_endPage+", "+reply_count+", ");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String pageInfo = "{\"pageInfoList\":"; // replyList는 키값이 됨

			pageInfo += "[{\"reply_page\":\"" + reply_page + "\"},";
			pageInfo += "{\"reply_maxPage\":\"" + reply_maxPage + "\"},";
			pageInfo += "{\"reply_startPage\":\"" + reply_startPage + "\"},";
			pageInfo += "{\"reply_endPage\":\"" + reply_endPage + "\"},";
			pageInfo += "{\"reply_count\":\"" + reply_count + "\"}]";

		pageInfo += "}";
		
		// 누적된 json 형식의 문자열을 호출한 페이지(뷰페이지)로 보내줌
		out.print(pageInfo);

		return forward;
	}

}
