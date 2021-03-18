package coding_free.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coding_free.svc.CmmntFreeDeleteProService;
import coding_free.svc.CodingFreeDeleteProService;
import coding_free.vo.ActionForward;
import member.svc.MemberUpdateProService;

public class CmmntDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CodingFreeDeleteProAction");
		ActionForward forward = null;
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		String nickname = request.getParameter("nickname");
//		String page = request.getParameter("page");
		
		
		CmmntFreeDeleteProService cmmntDeleteProService = new CmmntFreeDeleteProService();
		boolean isDeleteSuccess = cmmntDeleteProService.deleteCmmntHeart(comment_num);
		
		if (isDeleteSuccess) {
			isDeleteSuccess = cmmntDeleteProService.deleteCmmnt(comment_num);
			if (!isDeleteSuccess) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('글 삭제 실패!')");
				out.println("history.back()");
				out.println("</script>");
			}else {
				MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
				boolean isSuccess = memberUpdateProService.minusCommentLP(nickname);
				if(isSuccess) {
	//				request.setAttribute("post_num", post_num);
					forward = new ActionForward();
		//			forward.setPath("CodingFreeDetail.cf");
					forward.setPath("CodingFreeDetail.cf?post_num="+post_num);
		//			forward.setPath("CodingFreeList.cf?page=" + nowPage);
					forward.setRedirect(true);
				}
			}
		}
		
		return forward;
	}

}
