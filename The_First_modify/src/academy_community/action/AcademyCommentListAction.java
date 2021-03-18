package academy_community.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import academy_community.svc.AcademyCommentListService;
import academy_community.vo.AcademyCommentBean;
import academy_community.vo.ActionForward;
import svc.AllService;

public class AcademyCommentListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AcademyCommentListAction!");
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		
		int cmmnt_page = 1;
		int cmmnt_limit = 3;
		
		if(request.getParameter("nowPage") != null) {
			cmmnt_page = Integer.parseInt(request.getParameter("nowPage")); // 정수로 변환하여 저장
		}
		
		// date 시간
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		// BoardListService 인스턴스 생성 후 게시물 목록 갯수 가져오기
		AcademyCommentListService academyCommentListService = new AcademyCommentListService();
		ArrayList<AcademyCommentBean> list = academyCommentListService.getCommentList(post_num,cmmnt_page,cmmnt_limit);
		
		request.setAttribute("today", today);

		// 댓글 출력
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String json = "{\"replyList\":["; // replyList는 키값이 됨
		
		for (int i = 0; i < list.size(); i++) {
			String nickname = list.get(i).getNickname();
			String comment = list.get(i).getComment();
			Date date = list.get(i).getDate();
			String time = list.get(i).getTime();
			SimpleDateFormat df = new SimpleDateFormat("YY-MM-dd");
			int comment_num = list.get(i).getComment_num();
			
			json += "[{\"comment_num\":\"" + comment_num + "\"},"; //0
			
			if(date.compareTo(today)==0) {
				json += "{\"reply_date\":\"" + time + "\"},";		//1
			}else {
				json += "{\"reply_date\":\"" + df.format(date) + "\"},";		//1
			}
			
			json += "{\"nickname\":\"" + nickname + "\"},";// 2
			json += "{\"comment\":\"" + comment + "\"}]";   // 3
			
			if (i != list.size() - 1) {
				json += ",";
			}
		}
		json += "]}";
		
		out.print(json);
		
		return null;

	}

}
