package academy_community.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import academy_community.svc.AcademyWriteProService;
import academy_community.vo.AcademyBean;
import academy_community.vo.ActionForward;
import member.svc.MemberUpdateProService;

public class AcademyWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 글쓰기 요청을 받아 작성된 게시물 정보를 가져와서 DB 추가를 위한 준비 작업 및 이동 작업 수행
//		System.out.println("BoardWriteProAction!");
		ActionForward forward = null;
		AcademyBean boardBean = null;
		
		String nickname = request.getParameter("nickname");
		
		// MultipartRequest 객체로부터 전달된 파라미터들 가져오기 => BoardBean 객체에 저장
		// => 이름, 패스워드, 글제목, 글내용, 파일명
		boardBean = new AcademyBean();
		// 주의사항! request.getParameter() 메서드 대신 multi.getParameter() 메서드 사용 필수!
		boardBean.setNickname(nickname);
		boardBean.setSubject(request.getParameter("subject"));
		boardBean.setContent(request.getParameter("content"));
		boardBean.setAddress(request.getParameter("address"));
		boardBean.setAcademy_name(request.getParameter("academy_name"));
		
		// 실제 DB 작업을 진행하기 위한 Service 클래스 객체 생성
		AcademyWriteProService boardWriteProService = new AcademyWriteProService();
		MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
		
		// 게시물 등록 작업을 위한 registArticle() 메서드 호출
		// => 파라미터 : 게시물 정보(BoardBean 객체), 리턴값 : 게시물 등록 성공 여부(boolean)
		boolean isWriteSuccess = boardWriteProService.registArticle(boardBean);
		
		System.out.println("isWriteSuccess = " + isWriteSuccess);
		
	
		// 글쓰기 성공 여부에 따른 후속 처리(이동 작업)
		// => 실패 시 : 자바스크립트를 사용하여 실패 메세지 출력 후, 이전 페이지로 이동
		// => 성공 시 : ActionForward 객체를 생성하여 글목록(BoardList.ac) 페이지 요청(Redirect 방식)
		if(!isWriteSuccess) { // 글쓰기 실패
			// HTML 문서 형식으로 출력하기 위해 response 객체의 setContentType() 메서드 호출 => HTML 타입 지정
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); // HTML 태그 출력을 위한 Writer 객체 가져오기
			// out 객체의 println() 메서드를 호출하여 HTML 태그 작성
			out.println("<script>"); // 자바스크립트 실행을 위한 <script> 시작 태그
			out.println("alert('게시물 등록 실패!')"); // alert dialog 출력
			out.println("history.back()"); // 또는 out.println("history.go(-1)");  // 이전 페이지로 돌아가기
			out.println("</script>"); // 자바스크립트 종료 위한 <script> 끝 태그
		} else { // 글쓰기 성공
			isWriteSuccess = memberUpdateProService.updateArticleLP(nickname);
			forward = new ActionForward(); // ActionForward 객체 생성
			forward.setPath("AcademyList.ac"); // 서블릿 주소 지정
			forward.setRedirect(true); // Redirect 방식(true) 지정
		}
		
		// ActionForward 객체 리턴
		return forward; 
	}

}