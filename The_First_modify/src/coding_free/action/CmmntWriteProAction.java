package coding_free.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coding_free.svc.CmmntFreeDeleteProService;
import coding_free.svc.CmmntFreeWriteProService;
import coding_free.svc.CodingFreeCommentListService;
import coding_free.svc.CodingFreeDeleteProService;
import coding_free.vo.ActionForward;
import coding_free.vo.CodingFreeCommentBean;
import coding_free.vo.PageInfo;
import member.svc.MemberDetailService;
import member.svc.MemberUpdateProService;
import svc.AllService;

public class CmmntWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		CodingFreeCommentBean codingFreeCommentBean = new CodingFreeCommentBean();
		
		
		String page = request.getParameter("page"); 
//		int cmmnt_page = Integer.parseInt(request.getParameter("cmmnt_page"));
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		int cmmnt_page = 1; // 현재 페이지 번호
		int cmmnt_limit = 10; // 
		
		String nickname = request.getParameter("nickname");
		
		codingFreeCommentBean.setPost_num(Integer.parseInt(request.getParameter("post_num")));
		codingFreeCommentBean.setNickname(request.getParameter("nickname"));
		codingFreeCommentBean.setComment(request.getParameter("comment"));
//		cmmntBean.setDate(request.getParameter("date"));
		
		CmmntFreeWriteProService cmmntFreeWriteProService = new CmmntFreeWriteProService();
		boolean isWriteSuccess = cmmntFreeWriteProService.writeComment(codingFreeCommentBean);
		
		if(isWriteSuccess) {
			MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
			memberUpdateProService.updateCommentLP(nickname);
		}
		

		AllService allService = new AllService();
		Date today= allService.getToday();
		
		
		request.setAttribute("today", today);		
		if(isWriteSuccess) {
			MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
			boolean isSuccess = memberUpdateProService.updateCommentLP(nickname);
			if(isSuccess) {
				forward = new ActionForward();
				forward.setPath("CodingFreeDetail.cf?post_num="+post_num);
				forward.setRedirect(true);
			}
		}
		return forward;
	}


}
