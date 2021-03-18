package coding.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import coding.svc.CodingDetailService;
import coding.svc.CodingModifyProService;
import coding.vo.CodingBean;
import vo.ActionForward;

public class CodingModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		int num = Integer.parseInt(request.getParameter("num"));

		String saveFolder = "/codingUpload"; //가상위치 (이클립스가 생성한 폴더)
		ServletContext context2 = request.getServletContext();
		String realFolder = context2.getRealPath(saveFolder); //가상위치에 대한 실제파일주소

		int fileSize = 1024*1024*10; //10Mbyte

		// MultipartRequest 객체생성, cos.jar API 필수
		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());

		//MultipartRequest 객체로부터 전달 된 파라미터들 가져오기
		String subject = multi.getParameter("subject");
		String content =multi.getParameter("content");
		//		int readcount = Integer.parseInt(multi.getParameter("readcount"));
		String file = multi.getOriginalFileName((String)multi.getFileNames().nextElement());
		//		int password = Integer.parseInt(multi.getParameter("password"));

		//		System.out.println("CP는"+CP);
		
		CodingDetailService codingDetailService = new CodingDetailService();
		CodingBean article = codingDetailService.getArticle(num);
		
		if(file==null) {
			file = article.getFile();
		}
		 	
		
		article = new CodingBean();
		article.setNum(num);
		article.setSubject(subject);
		article.setContent(content);
		article.setFile(file);
		//		codingBean.setReadcount(readcount);
		//		codingBean.setPassword(password);
		
		System.out.println(subject);
		System.out.println(content);
		System.out.println(file);
		
		CodingModifyProService codingModifyProService = new CodingModifyProService();
		boolean isModifySuccess = codingModifyProService.modifyArticle(article);

		if(!isModifySuccess) {
			// 리턴받은 수정 결과(isModifySuccess)가 false 일 경우
			// => 자바스크립트를 사용하여 "글 수정 실패!" 출력 후 이전 페이지로 이동
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 수정 실패!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			// 리턴받은 수정 결과(isModifySuccess)가 true 일 경우
			// => ActionForward 객체를 사용하여 수정된 게시물로 이동(Redirect 방식)
			//    (URL : BoardDetail.bo?board_num=x&page=y)
			forward = new ActionForward();
			//						+ "&page=" + page
			forward.setPath("CodingDetail.code?post_num=" + num );
			forward.setRedirect(true);
		}


		return forward;
	}

}
