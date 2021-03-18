package coding_free.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import coding_free.svc.CodingFreeDetailService;
import coding_free.svc.CodingFreeModifyService;
import coding_free.vo.ActionForward;
import coding_free.vo.CodingFreeBean;

public class CodingFreeModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CodingFreeModifyProAction");
		int num = Integer.parseInt(request.getParameter("num"));
		
		ActionForward forward = null;

		String saveFolder = "/coding_free/images";
		ServletContext context = request.getServletContext();
		String realFolder = context.getRealPath(saveFolder);

		int fileSize = 1024 * 1024 * 10;

		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8",
				new DefaultFileRenamePolicy());

		String realFilesystemName = multi.getFilesystemName((String) multi.getFileNames().nextElement());
		System.out.println("실제 파일명 : " + realFilesystemName);
		
		CodingFreeDetailService codingFreeDetailService = new CodingFreeDetailService();
		CodingFreeBean codingFreeBean = codingFreeDetailService.getArticle(num);
		
		if(realFilesystemName==null) {
			realFilesystemName = codingFreeBean.getFile();
		}
		
		CodingFreeBean cb = new CodingFreeBean(
				multi.getParameter("nickname"),
				multi.getParameter("subject"),
				multi.getParameter("content"),
				realFilesystemName);
		cb.setNum(num);
		
		CodingFreeModifyService codingFreeModifyService = new CodingFreeModifyService();
		boolean isModifySuccess = codingFreeModifyService.modifyArticle(cb);
	
		if(!isModifySuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 수정 실패!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			request.setAttribute("post_num", num);
			
			forward = new ActionForward();
			forward.setPath("CodingFreeDetail.cf?post_num=" + num);
			forward.setRedirect(true);
		}
		
		return forward;
	}

}
