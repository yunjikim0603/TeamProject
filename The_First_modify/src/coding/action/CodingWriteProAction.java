package coding.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import coding.svc.CodingDetailService;
import coding.svc.CodingReplyDetailService;
import coding.svc.CodingWriteProService;
import coding.vo.CodingBean;
import coding.vo.Coding_refBean;
import member.svc.MemberDetailService;
import member.svc.MemberUpdateProService;
import member.vo.MemberBean;
import vo.ActionForward;

public class CodingWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String saveFolder = "/codingUpload"; //가상위치 (이클립스가 생성한 폴더)
		ServletContext context2 = request.getServletContext();
		String realFolder = context2.getRealPath(saveFolder); //가상위치에 대한 실제파일주소
		
		int fileSize = 1024*1024*10; //10Mbyte
		
		// MultipartRequest 객체생성, cos.jar API 필수
		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		
		System.out.println(multi.getParameter("CP"));
		//MultipartRequest 객체로부터 전달 된 파라미터들 가져오기
		String nickname = multi.getParameter("nickname");
		String subject = multi.getParameter("subject");
		String content =multi.getParameter("content");
//		int readcount = Integer.parseInt(multi.getParameter("readcount"));
		String file = multi.getOriginalFileName((String)multi.getFileNames().nextElement());
		int article_CP = Integer.parseInt(multi.getParameter("CP"));
//		int password = Integer.parseInt(multi.getParameter("password"));
		
		CodingBean codingBean = new CodingBean();
		codingBean.setNickname(nickname);
		codingBean.setSubject(subject);
		codingBean.setContent(content);
		codingBean.setFile(file);
//		codingBean.setReadcount(readcount);
		codingBean.setCP(article_CP);
//		codingBean.setPassword(password);
		
		MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
		int userCP = memberUpdateProService.selectCP(nickname);
		
		if(userCP < article_CP) {
			// 리턴받은 수정 결과(isModifySuccess)가 false 일 경우
			// => 자바스크립트를 사용하여 "글 수정 실패!" 출력 후 이전 페이지로 이동
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('CP가 부족합니다. CP를 충전하시거나 남은 CP에 맞게 설정해주세요.')");
			out.println("history.back()");
			out.println("</script>");
		} else {	
			CodingWriteProService codingWriteProService = new CodingWriteProService();
			boolean isSuccess = codingWriteProService.insertArticle(codingBean);
		
			if(isSuccess) {
				isSuccess = memberUpdateProService.updateArticleCP(nickname , article_CP);
				isSuccess = memberUpdateProService.updateArticleLP(nickname); //글쓰기는 20점
				 
				if(isSuccess) {
					forward = new ActionForward();
					forward.setPath("CodingList.code");
					forward.setRedirect(true);
				}
			}
		}
		
		return forward;
	}

}
