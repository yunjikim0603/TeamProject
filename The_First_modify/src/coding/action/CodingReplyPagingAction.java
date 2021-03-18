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

public class CodingReplyPagingAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		System.out.println("CodingReplyDetailAction");
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
//		 reply_page = Integer.parseInt(request.getParameter("nowPage"));
		
//		System.out.println("nowPage    " + request.getParameter("nowPage"));
		 int reply_page = 1;
		int reply_limit = 1;
		
		if(request.getParameter("nowPage") != null) {
			reply_page = Integer.parseInt(request.getParameter("nowPage")); // 정수로 변환하여 저장
//			System.out.println("reply_page: " + reply_page);
		}
		
		
		
		CodingReplyListService codingReplyListService = new CodingReplyListService();
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		

		int reply_count = codingReplyListService.getReplyListCount(post_num);
		//답글 페이지
		int reply_maxPage = (int)((double)reply_count / reply_limit + 0.95);
		// 2. 시작 페이지 번호 계산
		int reply_startPage = (((int)((double)reply_page / 10 + 0.9)) - 1) *10 + 1;
		// 3. 마지막 페이지 번호 계산
		int reply_endPage = reply_startPage + 10 - 1;
		
		// 마지막 페이지 번호가 총 페이지 수 보다 클 경우 총 페이지 수를 마지막 페이지 번호로 설정
		if(reply_endPage > reply_maxPage) {
			reply_endPage = reply_maxPage;
		}
		// PageInfo 객체에 페이지 정보 저장
		PageInfo replyPageInfo = new PageInfo(reply_page, reply_maxPage, reply_startPage, reply_endPage, reply_count);
//		System.out.println(reply_page+", "+reply_maxPage+", "+reply_startPage+", "+reply_endPage+", "+reply_count+", ");
		
		
		String page = "{\"pageInfo\":["; // replyList는 키값이 됨
//		String page = "";
//		for (int i = 0; i <5; i++) {
			
//			page += reply_page +",";
//			page += reply_maxPage +",";
//			page += reply_startPage +",";
//			page += reply_endPage +",";
//			page += reply_count;
		
			page += "{\"reply_page\":\"" + reply_page + "\""; //0
			page += "},";
			page += "{\"reply_maxPage\":\"" + reply_maxPage + "\"";		//1
			page += "},";
			page += "{\"reply_startPage\":\"" + reply_startPage+ "\"";		//1
			page += "},";
			page += "{\"reply_endPage\":\"" + reply_endPage + "\"";		//2
			page += "},";
			page += "{\"reply_count\":\"" + reply_count + "\"";			//3
			page += "}";
//			if (i != 4) {
//				page += ",";
//			}
//		}
		page += "]}";
		
		
		// 누적된 json 형식의 문자열을 호출한 페이지(뷰페이지)로 보내줌

		out.print(page);
		
		return forward;
	}

}
