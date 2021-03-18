package coding.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import action.Action;
import coding.svc.CodingReplyDeleteService;
import member.svc.MemberUpdateProService;
import vo.ActionForward;

public class CodingReplyDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		int reply_num = Integer.parseInt(request.getParameter("ref_num"));
		String nickname = request.getParameter("nickname");
		
		
		CodingReplyDeleteService codingReplyDeleteService = new CodingReplyDeleteService();
		boolean isDeleteSuccess = codingReplyDeleteService.deleteReply(post_num, reply_num);

			if(!isDeleteSuccess) {
				// 리턴받은 수정 결과(isModifySuccess)가 false 일 경우
				// => 자바스크립트를 사용하여 "글 수정 실패!" 출력 후 이전 페이지로 이동
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('글 삭제 실패!')");
				out.println("history.back()");
				out.println("</script>");			
			}else {
				MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
				boolean isSuccess = memberUpdateProService.minusArticletLP(nickname);
				if(isSuccess){
					forward = new ActionForward();
					forward.setPath("CodingDetail.code?post_num="+post_num);
					forward.setRedirect(true);
				}
			} 

		
		return forward;
	}

}
