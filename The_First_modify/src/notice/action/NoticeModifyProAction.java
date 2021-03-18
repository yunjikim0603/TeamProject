package notice.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import coding.svc.CodingDetailService;
import coding.vo.CodingBean;
import notice.svc.NoticeDetailService;
import notice.svc.NoticeModifyProService;
import notice.vo.ActionForward;
import notice.vo.NoticeBean;

public class NoticeModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("NoticeModifyProAction");
		ActionForward forward = null;
		
		
		// 파라미터로 전달된 글번호(num), 페이지번호(page) 가져와서 변수에 저장
		int num = Integer.parseInt(request.getParameter("num"));
//		String page = request.getParameter("page");
		
		String saveFolder = "/notice/noticeUpload"; 
		ServletContext context2 = request.getServletContext();
		String realFolder = context2.getRealPath(saveFolder);

		int fileSize = 1024*1024*10; //10Mbyte
		
		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());

		//MultipartRequest 객체로부터 전달 된 파라미터들 가져오기
		String subject = multi.getParameter("subject");
		String content =multi.getParameter("content");
		String file = multi.getOriginalFileName((String)multi.getFileNames().nextElement());
		
		NoticeDetailService noticeDetailService = new NoticeDetailService();
		NoticeBean noticeBean = noticeDetailService.getArticle(num);
		
		if(file==null) {
			file=noticeBean.getFile();

			
		}
		
			NoticeBean article = new NoticeBean();
//			article.setNum(num);
//			article.setSubject(request.getParameter("subject"));
//			article.setContent(request.getParameter("content"));
			article.setNum(num);
			article.setSubject(subject);
			article.setContent(content);
			article.setFile(file);
			

			NoticeModifyProService noticeModifyProService = new NoticeModifyProService();
			boolean isModifySuccess = noticeModifyProService.modifyArticle(article);
			
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
				//    (URL : NoticeDetail.no?num=x&page=y)
				forward = new ActionForward();
				forward.setPath("NoticeDetail.no?post_num=" + num); /* + "&page=" + page */
				forward.setRedirect(true);
			}
			
		
		return forward;
	}

}



















