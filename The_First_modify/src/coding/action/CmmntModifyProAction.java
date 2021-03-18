package coding.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CmmntListService;
import coding.svc.CmmntModifyProService;
import coding.svc.CodingModifyProService;
import coding.vo.CmmntBean;
import coding.vo.CodingBean;
import svc.AllService;
import vo.ActionForward;

public class CmmntModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		
		CmmntBean cmmntBean = new CmmntBean();
		cmmntBean.setPost_num(post_num);
		cmmntBean.setComment_num(comment_num);
		cmmntBean.setNickname(request.getParameter("nickname"));
		cmmntBean.setComment(request.getParameter("comment"));
		
		CmmntModifyProService cmmntModifyProService = new CmmntModifyProService();
		cmmntModifyProService.modifyCmmnt(cmmntBean);
		
		CmmntListService cmmntListService = new CmmntListService();
		
		AllService allService = new AllService();
		Date today= allService.getToday();
		
		request.setAttribute("today", today);
		request.setAttribute("post_num", post_num);
		request.setAttribute("comment_num", comment_num);
		
		forward = new ActionForward();
		forward.setPath("CmmntList.code?post_num="+post_num);
//		forward.setPath("CodingDetail.code?num=" + num);
//		forward.setPath("CodingDetail.code?num=" + num + "&page=" + page);
		forward.setRedirect(true);
		
		return forward;
	}

}
