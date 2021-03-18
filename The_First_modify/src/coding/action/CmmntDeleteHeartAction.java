package coding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CmmntHeartService;
import coding.svc.CmmntListService;
import coding_free.svc.CodingFreeCommentHeartService;
import coding_free.svc.CodingFreeCommentListService;
import member.svc.MemberUpdateProService;
import vo.ActionForward;

public class CmmntDeleteHeartAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CmmntDELETEHeartAction!");
		request.setCharacterEncoding("utf-8");
		ActionForward forward = null;
		
		int cmmnt_num = Integer.parseInt(request.getParameter("comment_num"));
		String recommender = request.getParameter("recommender");
//		int cmmnt_num = 2;
		 
		CmmntHeartService cmmntHeartService = new CmmntHeartService();
		boolean isSuccess = cmmntHeartService.deleteHeart(cmmnt_num,recommender);
		
		CmmntListService cmmntListService = new CmmntListService();
		String nickname = cmmntListService.getNickname(cmmnt_num);
		 
		
		if(isSuccess) {
			System.out.println("delete 성공");
			boolean isSuccessUpdate = cmmntHeartService.updateHeartCount(cmmnt_num);
			
			CodingFreeCommentHeartService codingFreeCommentHeartService = new CodingFreeCommentHeartService();
			int free_heart = codingFreeCommentHeartService.selectFreeHeartCount(nickname);
			int charge_heart = cmmntHeartService.selectChargeHeartCount(nickname);
			int hearts = free_heart + charge_heart;
			if(isSuccessUpdate) {
				MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
				isSuccess = memberUpdateProService.updateMemberHeart(nickname,hearts);
				if(isSuccess) {
					System.out.println("update 성공");
				}
			}
		}
		
		
		
		return forward;
	}

}
