package any_community.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import any_community.svc.CommentModifyService;
import any_community.vo.ActionForward;
import any_community.vo.AnyCommentBean;
import svc.AllService;

public class CommentModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CommentModifyProAction!");
		ActionForward forward = null;

		// 받아오는 파라미터
		// hidden comment_num , post_num
		// nickname comment
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		String nickname = request.getParameter("nickname");
		String comment = request.getParameter("comment");
		
		AnyCommentBean anyCommentBean = new AnyCommentBean();
		anyCommentBean.setComment_num(comment_num);
		anyCommentBean.setComment(comment);
		anyCommentBean.setNickname(nickname);
		anyCommentBean.setPost_num(post_num);
		
		CommentModifyService commentModifyService = new CommentModifyService();
		boolean isSuccess = commentModifyService.updateComment(anyCommentBean);
		
		AllService allService = new AllService();
		Date today = allService.getToday();
		
		request.setAttribute("today", today);
		
		if (isSuccess) {
			forward = new ActionForward();
			forward.setPath("CommunityDetail.any?num="+post_num);
			forward.setRedirect(true);
		}
		
		return forward;
	}

}
