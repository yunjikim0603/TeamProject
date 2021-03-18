package coding.action;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CodingDetailService;
import coding.svc.CodingReplyDetailService;
import coding.svc.CodingReplyListService;
import coding.vo.CodingBean;
import coding.vo.Coding_refBean;
import svc.AllService;
import vo.ActionForward;

public class CodingReplyModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CodingModifyFormAction");
		ActionForward forward = null;
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		int ref_num = Integer.parseInt(request.getParameter("ref_num"));
		
		
		// 원본 게시물을 가져오기 위해 별도의 Service 클래스를 새로 정의하지 않고
		Coding_refBean ref = null;
		CodingReplyDetailService codingReplyDetailService = new CodingReplyDetailService();
		ref = codingReplyDetailService.getArticle_ref(post_num, ref_num);
		
		int isSelected = ref.getIsSelected();
		System.out.println(isSelected);
		
		CodingDetailService codingDetailService = new CodingDetailService();
		CodingBean article = codingDetailService.getArticle(post_num);
		
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		
		
		// 원본 게시물과 페이지번호를 request 객체에 저장
		request.setAttribute("article", article);
		request.setAttribute("post_num", post_num);
		request.setAttribute("ref_num", ref_num);
		request.setAttribute("ref", ref);
		request.setAttribute("isSelected", isSelected);
		request.setAttribute("today", today);
		

		
		forward = new ActionForward();
		forward.setPath("/coding/codingReplyModifyForm.jsp"); // Dispatch 방식으로 이동
		
		return forward;
	}

}
