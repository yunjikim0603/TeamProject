package admin.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.svc.MemberListService;
import admin.vo.ActionForward;
import member.vo.MemberBean;



public class MemberListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("MemberListAction!");
		
		ActionForward forward = null;
		
		MemberListService memberListService = new MemberListService();
		
		ArrayList<MemberBean> memberList = null;
		memberList = memberListService.getListMember();
		
		request.setAttribute("memberList", memberList);
		
		forward = new ActionForward();
		forward.setPath("/admin/member_list.jsp");
		
		return forward;
	}

}
