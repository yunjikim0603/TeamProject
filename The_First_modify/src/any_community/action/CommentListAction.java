package any_community.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import any_community.svc.CommentListService;
import any_community.vo.ActionForward;
import any_community.vo.AnyCommentBean;
import svc.AllService;

public class CommentListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CommentListAction!");

		int post_num = Integer.parseInt(request.getParameter("post_num"));
		
	    int cmmnt_page = 1;
		int cmmnt_limit = 3;
		
		if(request.getParameter("nowPage") != null) {
			cmmnt_page = Integer.parseInt(request.getParameter("nowPage")); // 정수로 변환하여 저장
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		CommentListService commentListService = new CommentListService();
		ArrayList<AnyCommentBean> list = commentListService.getCommentList(post_num,cmmnt_page,cmmnt_limit);
		
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		// 구해온 댓글 목록을 반복문을 이용하여 json 형식으로 문자열 변수(json)에 누적 저장
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
			
			json += "{\"nickname\":\"" + nickname + "\"},"; // 2
			json += "{\"comment\":\"" + comment + "\"}]"; // 3
			
			if (i != list.size() - 1) {
				json += ",";
			}
		}
		json += "]}";
		
		out.print(json);

		return null;
	}

}
