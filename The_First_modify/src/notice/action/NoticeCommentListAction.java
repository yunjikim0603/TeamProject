package notice.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.svc.NoticeCommentListService;
import notice.vo.ActionForward;
import notice.vo.NoticeCommentBean;
import svc.AllService;

public class NoticeCommentListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("NoticeCommentListAction");
		
		ActionForward forward = null;
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		
		// date 시간
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		 	int cmmnt_page = 1;
			int cmmnt_limit = 3;
			
			if(request.getParameter("nowPage") != null) {
				cmmnt_page = Integer.parseInt(request.getParameter("nowPage")); // 정수로 변환하여 저장
				System.out.println("cmmnt_page: " + cmmnt_page);
			}
			
			
			NoticeCommentListService noticeCommentListService = new NoticeCommentListService();
			List<NoticeCommentBean> commentList = noticeCommentListService.getCommentList(post_num, cmmnt_page, cmmnt_limit);

			int cmmnt_count = noticeCommentListService.getCommentListCount(post_num);
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

		
//		NoticeListService noticeListService = new NoticeListService();
		

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String json = "{\"cList\":["; // replyList는 키값이 됨
		
		for (int i = 0; i < commentList.size(); i++) {
			String nickname = commentList.get(i).getNickname();
			String comment = commentList.get(i).getComment();
			Date date = commentList.get(i).getDate();
			String time = commentList.get(i).getTime();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			int cmmnt_num = commentList.get(i).getComment_num();
			
			json += "[{\"cmmnt_num\":\"" + cmmnt_num + "\"},"; //0
			if(date.compareTo(today)==0) {
				json += "{\"cmmnt_date\":\"" + time + "\"},";		//1
			}else {
				json += "{\"cmmnt_date\":\"" + df.format(date) + "\"},";		//1
			}
			json += "{\"nickname\":\"" + nickname + "\"},";//2
			json += "{\"comment\":\"" +comment + "\"}]";   //3
			
			if (i != commentList.size() - 1) {
				json += ",";
			}
		}
		json += "]}";
		
		// 누적된 json 형식의 문자열을 호출한 페이지(뷰페이지)로 보내줌
		out.print(json);
		return forward;

	}

}
