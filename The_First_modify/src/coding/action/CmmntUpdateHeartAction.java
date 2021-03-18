package coding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import coding.svc.CmmntHeartService;
import coding.svc.CmmntListService;
import coding_free.svc.CodingFreeCommentHeartService;
import member.svc.MemberUpdateProService;
import vo.ActionForward;

public class CmmntUpdateHeartAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CmmntUpdateHeartAction!");
		request.setCharacterEncoding("utf-8");
		ActionForward forward = null;
		
		
		int cmmnt_num = Integer.parseInt(request.getParameter("cmmnt_num"));
		System.out.println(cmmnt_num);
		String recommender = request.getParameter("recommender");
//		int cmmnt_num = 2;
		 
		CmmntListService cmmntListService = new CmmntListService();
		String nickname = cmmntListService.getNickname(cmmnt_num);
		
		CmmntHeartService cmmntHeartService = new CmmntHeartService();
		boolean isSuccess = cmmntHeartService.insertHeart(cmmnt_num, recommender, nickname);
		
		
		
		if(isSuccess) {
			System.out.println("insert 성공");
			boolean isSuccessUpdate = cmmntHeartService.updateHeartCount(cmmnt_num);
			
			CodingFreeCommentHeartService codingFreeCommentHeartService = new CodingFreeCommentHeartService();
			int free_heart = codingFreeCommentHeartService.selectFreeHeartCount(nickname);
			System.out.println(free_heart +"     free_heart");
			int charge_heart = cmmntHeartService.selectChargeHeartCount(nickname);
			System.out.println(charge_heart +"     charge_heart");
			int hearts = free_heart + charge_heart;
			System.out.println(hearts +"     hearts");
			
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
