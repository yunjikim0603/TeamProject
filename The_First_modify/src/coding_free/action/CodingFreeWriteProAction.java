package coding_free.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import coding_free.svc.CodingFreeWriteService;
import coding_free.vo.ActionForward;
import coding_free.vo.CodingFreeBean;
import member.svc.MemberUpdateProService;

public class CodingFreeWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CodingFreeWriteProAction!");
		ActionForward forward = null;

		String saveFolder = "/coding_free/images";
		ServletContext context = request.getServletContext();
		String realFolder = context.getRealPath(saveFolder);

		int fileSize = 1024 * 1024 * 10;

		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8",
				new DefaultFileRenamePolicy());

		String realFilesystemName = multi.getFilesystemName((String) multi.getFileNames().nextElement());
		System.out.println("실제 파일명 : " + realFilesystemName);

		String nickname = multi.getParameter("nickname");
		CodingFreeBean cb = new CodingFreeBean(
				multi.getParameter("nickname"),
				multi.getParameter("subject"),
				multi.getParameter("content"),
				realFilesystemName);
		
		CodingFreeWriteService codingFreeWriteService = new CodingFreeWriteService();
		boolean isWriteSuccess = codingFreeWriteService.writeArticle(cb);
		
		if(!isWriteSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 작성 실패')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
			boolean isSuccess = memberUpdateProService.updateArticleLP(nickname);
			if(isSuccess) {
				forward = new ActionForward(); 
				forward.setPath("CodingFreeList.cf");
				forward.setRedirect(true);
			}
		}
		
		return forward;
	}

}
