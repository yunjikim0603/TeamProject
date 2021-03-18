package coding.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CmmntListService;
import coding.svc.CmmntHeartService;
import coding.vo.CmmntBean;
import svc.AllService;
import vo.ActionForward;

public class CmmntHeartListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CmmntUpdateHeartAction!");
		request.setCharacterEncoding("utf-8");
		ActionForward forward = null;

		int post_num = Integer.parseInt(request.getParameter("post_num"));
		String recommender = request.getParameter("recommender");
//		String nickname = request.getParameter("nickname");
		
		int cmmnt_page = 1;
		
		if(request.getParameter("cmmnt_page")!=null) {
			cmmnt_page = Integer.parseInt(request.getParameter("cmmnt_page"));
		}
	
		
		int cmmnt_limit =10;
		int exist = 0;
		
		CmmntListService cmmntListService = new CmmntListService();
		List<CmmntBean> commentList = cmmntListService.getCmmntList(post_num, cmmnt_page, cmmnt_limit);
		
		CmmntHeartService cmmntHeartService = new CmmntHeartService();
		ArrayList<Integer> numList = cmmntHeartService.checkRecommender(recommender);
		
//		if(comment_num == numList.get(j)) {
//			exist=1;
//			break;
//		}
		
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String list = "{\"commentList\":["; // replyList는 키값이 됨
		for (int i = 0; i < commentList.size(); i++) {
			String session_nick = recommender;
			int comment_num = commentList.get(i).getComment_num();
			String comment_nickname = commentList.get(i).getNickname();
			String comment = commentList.get(i).getComment();
			Date date = commentList.get(i).getDate();
			String time = commentList.get(i).getTime();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			int heart = commentList.get(i).getHeart();
			
			list += "[{\"comment_num\":\"" + comment_num + "\"},";		//0
			list += "{\"comment_nickname\":\"" + comment_nickname + "\"},"; //1
			list += "{\"comment\":\"" + comment + "\"},";		//2			
			if(date.compareTo(today)==0) {
				list += "{\"date\":\"" + time + "\"},";		//3
			}else {
				list += "{\"date\":\"" + df.format(date) + "\"},";		//3
			}
			list += "{\"post_num\":\"" + post_num + "\"},"; //4
			list += "{\"session_nick\":\"" + session_nick + "\"},"; //5
			list += "{\"heart\":\"" + heart + "\"},"; //6

			if(numList.contains(comment_num)) {
				list += "{\"exist\":\"" + comment_num + "\"}]";//7
			}else {
				list += "{\"exist\":\"" + exist + "\"}]";	//7
			}
			
			if (i != commentList.size() - 1) {
				list += ",";
			}
		}
		list += "]}";
		
		out.print(list);
		
		request.setAttribute("post_num", post_num);
		
		return forward;
	}

}
