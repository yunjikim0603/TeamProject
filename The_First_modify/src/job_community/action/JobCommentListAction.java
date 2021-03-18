package job_community.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import job_community.svc.JobBoardDeleteProService;
import job_community.svc.JobCommentListService;
import job_community.vo.ActionForward;
import job_community.vo.JobCommentBean;
import svc.AllService;

public class JobCommentListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		System.out.println("JobCommentListAction");

		int post_num = Integer.parseInt(request.getParameter("post_num"));
		
	    int cmmnt_page = 1;
		int cmmnt_limit = 3;
		
		if(request.getParameter("nowPage") != null) {
			cmmnt_page = Integer.parseInt(request.getParameter("nowPage")); // 정수로 변환하여 저장
		}
		
		JobCommentListService jobCommentListService = new JobCommentListService();
		ArrayList<JobCommentBean> cmmntList = jobCommentListService.getCommentList(post_num, cmmnt_page, cmmnt_limit);
		
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String json = "{\"replyList\":["; // replyList는 키값이 됨
		for (int i = 0; i < cmmntList.size(); i++) {
			String nickname = cmmntList.get(i).getNickname();
			String comment = cmmntList.get(i).getComment();
			Date date = cmmntList.get(i).getDate();
			String time = cmmntList.get(i).getTime();
			SimpleDateFormat df = new SimpleDateFormat("YY-MM-dd");
			int comment_num = cmmntList.get(i).getComment_num();
			
			json += "[{\"comment_num\":\"" + comment_num + "\"},"; //0
			if(date.compareTo(today)==0) {
				json += "{\"reply_date\":\"" + time + "\"},";		//1
			}else {
				json += "{\"reply_date\":\"" + df.format(date) + "\"},";		//1
			}
			json += "{\"nickname\":\"" + nickname + "\"},"; // 2
			json += "{\"comment\":\"" + comment + "\"}]"; // 3
			if (i != cmmntList.size() - 1) {
				json += ",";
			}
		}
		json += "]}";
		
		out.print(json);
		return forward;
	}
	
}
