package coding_free.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coding_free.svc.CodingFreeCommentHeartService;
import coding_free.svc.CodingFreeCommentListService;
import coding_free.vo.CodingFreeCommentBean;
import svc.AllService;
import coding_free.vo.ActionForward;

public class CmmntListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ActionForward forward = null;
		
		System.out.println("CmmntListAction");
		
		String recommender = request.getParameter("recommender");
		int post_num = Integer.parseInt(request.getParameter("post_num"));
//		String page = request.getParameter("nowPage"); 
		
		int cmmnt_page = 1;
		int cmmnt_limit = 3;
		
		if(request.getParameter("nowPage") != null) {
			cmmnt_page = Integer.parseInt(request.getParameter("nowPage")); // 정수로 변환하여 저장
		}
		
		System.out.println(cmmnt_page);
		
		CodingFreeCommentListService codingFreeCommentListService = new CodingFreeCommentListService();
		List<CodingFreeCommentBean> cmmntList = codingFreeCommentListService.getCommentList(post_num, cmmnt_page, cmmnt_limit);
		
		// 리턴받은 BoardBean 객체가 null 이 아닐 경우 BoardDetailService 클래스의 plusReadcount() 메서드 호출
//		if(article_ref != null) {
////			codingDetailService.updateReadcount(post_num);
//		}
		CodingFreeCommentHeartService codingFreeCommentHeartService = new CodingFreeCommentHeartService();
		ArrayList<Integer> numList = codingFreeCommentHeartService.checkRecommender(recommender);
		
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String json = "{\"cmmntList\":["; // replyList는 키값이 됨
		
		for (int i = 0; i < cmmntList.size(); i++) {
			String nickname = cmmntList.get(i).getNickname();
			String comment = cmmntList.get(i).getComment();
//			post_num = cmmntList.get(i).getPost_num();
			Date date = cmmntList.get(i).getDate();
			String time = cmmntList.get(i).getTime();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//			int readcount = replyList.get(i).getReadcount();
			int cmmnt_num = cmmntList.get(i).getComment_num();
			int heart = cmmntList.get(i).getHeart();
			String session_nick = recommender;
			
			json += "[{\"cmmnt_num\":\"" + cmmnt_num + "\"},"; //0
			if(date.compareTo(today)==0) {
				json += "{\"cmmnt_date\":\"" + time + "\"},";		//1
			}else {
				json += "{\"cmmnt_date\":\"" + df.format(date) + "\"},";		//1
			}
			json += "{\"nickname\":\"" + nickname + "\"},";		//2
			json += "{\"session_nick\":\"" + session_nick + "\"},"; //6
			json += "{\"comment\":\"" + comment + "\"},"; //4
//			json += "{\"subject\":\"" + subject + "\"},"; //
//			json += "{\"file\":\"" + file + "\"},";		//
//			json += "{\"content\":\"" + content + "\"},";		//
			json += "{\"heart\":\"" + heart + "\"},";		//5
			if(numList.contains(cmmnt_num)) {
				json += "{\"exist\":\"" +cmmnt_num + "\"}]";
			}else {

				json += "{\"exist\":\"" +0 + "\"}]"; 
			}
			
			if (i != cmmntList.size() - 1) {
				json += ",";
			}
		}
		json += "]}";
		
		
		// 누적된 json 형식의 문자열을 호출한 페이지(뷰페이지)로 보내줌
		out.print(json);

		return forward;
	}

}
