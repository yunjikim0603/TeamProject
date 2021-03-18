package coding.action;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CodingDetailService;
import coding.vo.CodingBean;
import svc.AllService;
import vo.ActionForward;

public class CodingReplyWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		String nickname = request.getParameter("nickname");
		
	
		CodingBean article = null;
		CodingDetailService codingDetailService = new CodingDetailService();
		article = codingDetailService.getArticle(post_num);

		if(nickname.equals(article.getNickname())) {
			// 리턴받은 수정 결과(isModifySuccess)가 false 일 경우
			// => 자바스크립트를 사용하여 "글 수정 실패!" 출력 후 이전 페이지로 이동
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('작성자는 답글을 작성하실 수 없습니다.')");
			out.println("history.back()");
			out.println("</script>");
		} else {
	
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		// 원본 게시물과 페이지번호를 request 객체에 저장
		request.setAttribute("article", article);
		request.setAttribute("today", today);
		request.setAttribute("post_num", post_num);
		
		forward = new ActionForward();
		forward.setPath("/coding/codingReplyWriteForm.jsp");
		}
		return forward;
	}

}
