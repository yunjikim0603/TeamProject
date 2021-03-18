package coding.action;

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
import coding.svc.CodingReplySelectedService;
import coding.vo.CmmntBean;
import coding.vo.CodingBean;
import coding.vo.Coding_refBean;
import coding.vo.PageInfo;
import jdk.nashorn.internal.scripts.JS;
import svc.AllService;
import vo.ActionForward;

public class CodingDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		System.out.println("CodingDetailAction~~~~~~~~~~~~~~~~~");
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));

		
//		if(request.getParameter("reply_page") != null) {
//			reply_page = Integer.parseInt(request.getParameter("reply_page")); // 정수로 변환하여 저장
//		}

		CodingDetailService codingDetailService = new CodingDetailService();
		CodingBean article = codingDetailService.getArticle(post_num);

		CodingReplyListService codingReplyListService = new CodingReplyListService();
		int reply_count = codingReplyListService.getReplyListCount(post_num);

		CmmntListService cmmntListService = new CmmntListService();
		ArrayList<CmmntBean>cmmntList = cmmntListService.getCmmntList(post_num, 1, 2);
		int comment_count = cmmntListService.getCommentListCount(post_num);
		
		// 리턴받은 BoardBean 객체가 null 이 아닐 경우 BoardDetailService 클래스의 plusReadcount() 메서드 호출
		if(article != null) {
			codingDetailService.updateReadcount(post_num);
		}		
		
		AllService allService = new AllService();
		Date today = allService.getToday();	
		
		CodingReplySelectedService codingReplySelectedService = new CodingReplySelectedService();
		int selectedRef_num = codingReplySelectedService.getSelectedRef_num(post_num);
		
//		int selected = article.getIsPublic();
	
		
		request.setAttribute("today", today);
		request.setAttribute("article", article);
		request.setAttribute("reply_count", reply_count);
		request.setAttribute("comment_count", comment_count);
		request.setAttribute("cmmntList", cmmntList);
		request.setAttribute("post_num", post_num);
;
		//else if(selected==0)
		if(selectedRef_num>0) {
			System.out.println("if문");
//			String selectedReply = request.getParameter("isSelected");
			request.setAttribute("isSelected", selectedRef_num);
			forward = new ActionForward();
			forward.setPath("/coding/make_codingReplySelected.jsp");
		}else {
			System.out.println("else문");
			forward = new ActionForward();
	//		forward.setPath("/coding/codingView.jsp");
	//		forward.setPath("/coding/mdf_codingView.jsp");
			forward.setPath("/coding/makeView.jsp");
		}
		return forward;
	}

}
