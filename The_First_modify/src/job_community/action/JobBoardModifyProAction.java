package job_community.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import job_community.svc.JobBoardDetailService;
import job_community.svc.JobBoardModifyProService;
import job_community.vo.ActionForward;
import job_community.vo.JobBoardBean;

public class JobBoardModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardModifyProAction");
		
		ActionForward forward = null;
		int num = Integer.parseInt(request.getParameter("num"));
		
		String saveFolder = "/job_community/images";
		ServletContext context = request.getServletContext();
		String realFolder = context.getRealPath(saveFolder);

		int fileSize = 1024 * 1024 * 10;

		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8",
				new DefaultFileRenamePolicy());

		String realFilesystemName = multi.getFilesystemName((String) multi.getFileNames().nextElement());
		System.out.println("실제 파일명 : " + realFilesystemName);
		
		JobBoardDetailService jobBoardDetailService = new JobBoardDetailService();
		JobBoardBean jobBoardBean = jobBoardDetailService.getArticle(num);
	
		
		if(realFilesystemName==null) {
			realFilesystemName = jobBoardBean.getFile();
		}
		
		JobBoardBean article = new JobBoardBean();
		article.setNum(num);
		article.setSubject(multi.getParameter("subject"));
		article.setContent(multi.getParameter("content"));
		article.setFile(realFilesystemName);
		
		JobBoardModifyProService boardModifyProService = new JobBoardModifyProService();
		boolean isModifySuccess = boardModifyProService.modifyArticle(article);
			
			if(!isModifySuccess) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('글 수정 실패!')");
				out.println("history.back()");
				out.println("</script>");
			} else {
				request.setAttribute("num", num);
				
				forward = new ActionForward();
				forward.setPath("JobBoardDetail.job?num=" + num);
				forward.setRedirect(true);
			}
		
		return forward;
	}
	
}
