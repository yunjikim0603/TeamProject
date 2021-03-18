package coding_free.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coding.svc.CmmntHeartService;
import coding_free.svc.CodingFreeCommentHeartService;
import coding_free.svc.CodingFreeCommentListService;
import coding_free.vo.ActionForward;
import member.svc.MemberUpdateProService;

public class CmmntFreeUpdateHeartAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		System.out.println("CmmntFreeUpdateHeartAction");


		int cmmnt_num = Integer.parseInt(request.getParameter("cmmnt_num"));
		String recommender = request.getParameter("recommender");
		
		CodingFreeCommentListService codingFreeCommentListService = new CodingFreeCommentListService();
		String nickname = codingFreeCommentListService.getNickname(cmmnt_num);
		
		CodingFreeCommentHeartService codingFreeCommentHeartService = new CodingFreeCommentHeartService();
		boolean isSuccess = codingFreeCommentHeartService.insertHeart(cmmnt_num, recommender,nickname);
		
		if(isSuccess) {
			isSuccess = codingFreeCommentHeartService.updateHeartCount(cmmnt_num);
			int free_heart = codingFreeCommentHeartService.selectFreeHeartCount(nickname);
			CmmntHeartService cmmntHeartService = new CmmntHeartService();
			int charge_heart = cmmntHeartService.selectChargeHeartCount(nickname);
			int hearts = free_heart + charge_heart;
			MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
				
				isSuccess = memberUpdateProService.updateMemberHeart(nickname,hearts);
				if(isSuccess) {
					System.out.println("-~~~~~~~~~~~~~~~update 성공");
				}			
		}
		
		return forward;
	}

}
