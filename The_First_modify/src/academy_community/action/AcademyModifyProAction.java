package academy_community.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import academy_community.svc.AcademyModifyProService;
import academy_community.vo.AcademyBean;
import academy_community.vo.ActionForward;

public class AcademyModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("BoardModifyProAction");
		
		ActionForward forward = null;
		
		// 파라미터로 전달된 글번호(board_num), 페이지번호(page) 가져와서 변수에 저장
		int num = Integer.parseInt(request.getParameter("num"));
		String page = request.getParameter("page");
		
		
		// 게시물 수정을 위한 본인 확인 작업 => BoardModifyProService 클래스의 isArticleWriter() 메서드 호출
		// => 파라미터 : 글번호(board_num) & 패스워드(board_pass), 리턴타입 : boolean(isRightUser)
		AcademyModifyProService boardModifyProService = new AcademyModifyProService();
//		boolean isRightUser = boardModifyProService.isArticleWriter(num, request.getParameter("board_pass"));
			
		// 본인 확인 완료 시 BoardBean 객체에 수정된 게시물 내용 저장 후
		// BoardModifyProService 클래스의 modifyArticle() 메서드를 호출
		// => 파라미터 : BoardBean   리턴타입 : boolean(isModifySuccess)
			AcademyBean article = new AcademyBean();
			article.setNum(num);
			article.setNickname(request.getParameter("nickname"));
			article.setSubject(request.getParameter("subject"));
			article.setContent(request.getParameter("content"));
			article.setAddress(request.getParameter("address"));
			article.setAcademy_name(request.getParameter("academy_name"));
					
			boolean isModifySuccess = boardModifyProService.modifyArticle(article);
					
			if(!isModifySuccess) {
				// 리턴받은 수정 결과(isModifySuccess)가 false 일 경우
				// => 자바스크립트를 사용하여 "글 수정 실패!" 출력 후 이전 페이지로 이동
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('수정 실패')");
				out.println("history.back()");
				out.println("</script>");
			} else {
				// 리턴받은 수정 결과(isModifySuccess)가 true 일 경우
				// => ActionForward 객체를 사용하여 수정된 게시물로 이동(Redirect 방식)
				//    (URL : BoardDetail.ac?board_num=x&page=y)
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('수정 완료')");
				out.println("</script>");
				forward = new ActionForward();
				forward.setPath("AcademyDetail.ac?num=" + num + "&page=" + page);
				forward.setRedirect(true);
			}
		
		return forward;
	}

}