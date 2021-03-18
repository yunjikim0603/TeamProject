package coding_free.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coding.svc.CmmntListService;
import coding_free.svc.CmmntFreeDeleteProService;
import coding_free.svc.CodingFreeCommentListService;
import coding_free.svc.CodingFreeDeleteProService;
import coding_free.svc.CodingFreeDetailService;
import coding_free.vo.ActionForward;
import coding_free.vo.CodingFreeBean;
import member.svc.MemberUpdateProService;

public class CodingFreeDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CodingFreeDeleteProAction");
		ActionForward forward = null;

		int num = Integer.parseInt(request.getParameter("num"));


		CodingFreeCommentListService codingFreeCommentListService = new CodingFreeCommentListService();
		ArrayList<Integer> numList =  codingFreeCommentListService.checkCommentNumList(num);
		
		CodingFreeDetailService codingFreeDetailService = new CodingFreeDetailService();
		CodingFreeBean codingFreeBean = codingFreeDetailService.getArticle(num);
		String nickname = codingFreeBean.getNickname();
		
		
		boolean isCommentDeleteSuccess = false;
		
		if(numList!=null) {
			for(int i = 0; i<numList.size(); i++){
				int comment_num  = numList.get(i);
				CmmntFreeDeleteProService cmmntDeleteProService = new CmmntFreeDeleteProService();
				isCommentDeleteSuccess = cmmntDeleteProService.deleteCmmntHeart(comment_num);
				if (isCommentDeleteSuccess) {
					isCommentDeleteSuccess = cmmntDeleteProService.deleteCmmnt(comment_num);
				}
			}	
		}
		if(numList.size()==0){
			isCommentDeleteSuccess = true;
		}
		
		System.out.println(isCommentDeleteSuccess);
		if(isCommentDeleteSuccess) {
			CodingFreeDeleteProService codingFreeDeleteProService = new CodingFreeDeleteProService();
			boolean isDeleteSuccess = codingFreeDeleteProService.deleteArticle(num);

			if (!isDeleteSuccess) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('글 삭제 실패!')");
				out.println("history.back()");
				out.println("</script>");
			} else {
				MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
				boolean isSuccess = memberUpdateProService.minusArticletLP(nickname);
				if(isSuccess) {
					forward = new ActionForward();
					forward.setPath("CodingFreeList.cf");
					forward.setRedirect(true);
				}
			}
		}

		return forward;
	}

}
