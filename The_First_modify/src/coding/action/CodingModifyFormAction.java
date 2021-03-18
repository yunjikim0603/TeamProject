package coding.action;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CodingDetailService;
import coding.svc.CodingReplyListService;
import coding.vo.CodingBean;
import coding.vo.Coding_refBean;
import svc.AllService;
import vo.ActionForward;

public class CodingModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CodingModifyFormAction");
		ActionForward forward = null;
		int post_num = Integer.parseInt(request.getParameter("num"));
		
		// 원본 게시물을 가져오기 위해 별도의 Service 클래스를 새로 정의하지 않고
		CodingBean article = null;
		CodingDetailService codingDetailService = new CodingDetailService();
		article = codingDetailService.getArticle(post_num);
//		System.out.println(article.getBoard_content());
		
		
		
		
		// 원본 게시물과 페이지번호를 request 객체에 저장
		request.setAttribute("post_num", post_num);
		request.setAttribute("article", article);

		
		forward = new ActionForward();
		forward.setPath("/coding/make_codingModifyForm.jsp"); // Dispatch 방식으로 이동
		
		return forward;
	}

}
