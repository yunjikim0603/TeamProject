package any_community.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import any_community.svc.CommentWriteService;
import any_community.vo.AnyCommentBean;
import member.svc.MemberUpdateProService;
import any_community.vo.ActionForward;

public class CommentWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CommentWriteAction!");
		ActionForward forward = null;
	
		//파라미터 가져오기
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		String nickname = request.getParameter("nickname");
		String comment = request.getParameter("comment");
		
		// test
		String nowPage = "1";
		
		AnyCommentBean anyCommentBean = new AnyCommentBean(post_num, nickname, comment);
		
		CommentWriteService commentWriteService = new CommentWriteService();
		boolean isWriteSuccess = commentWriteService.writeComment(anyCommentBean);
	
		if(!isWriteSuccess) { // 글쓰기 실패
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); 
			out.println("<script>"); 
			out.println("alert('댓글 등록 실패')"); 
			out.println("history.back()"); 
			out.println("</script>"); 
		} else { // 글쓰기 성공
			System.out.println("댓글 작성 완료");
			MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
			boolean isSuccess = memberUpdateProService.updateCommentLP(nickname);
			
			if(isSuccess) {
				forward = new ActionForward();
				forward.setPath("CommunityDetail.any?num=" + post_num + "&page=" + nowPage);
				forward.setRedirect(true);
			}
		}
		
		return forward;
	}

}
