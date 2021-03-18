package job_community.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import job_community.svc.JobBoardWriteProService;
import job_community.vo.ActionForward;
import job_community.vo.JobBoardBean;
import member.svc.MemberUpdateProService;

public class JobBoardWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("JobBoardWriteProAction!");
		ActionForward forward = null;
		
		String saveFolder = "/job_community/images";
		ServletContext context = request.getServletContext();
		String realFolder = context.getRealPath(saveFolder);
		
		int fileSize = 1024 * 1024 * 10;

		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8",
				new DefaultFileRenamePolicy());

		String realFilesystemName = multi.getFilesystemName((String) multi.getFileNames().nextElement());
		System.out.println("실제 파일명 : " + realFilesystemName);

		String nickname = multi.getParameter("nickname");
//		JobBoardBean jobBoardBean = new JobBoardBean();
//		jobBoardBean.setNickname(multi.getParameter("nickname"));
//		jobBoardBean.setSubject(multi.getParameter("subject"));
//		jobBoardBean.setContent(multi.getParameter("content"));
		
		JobBoardBean jobBoardBean = new JobBoardBean(
				multi.getParameter("nickname"), multi.getParameter("subject"), 
				multi.getParameter("content"), realFilesystemName);
		
		JobBoardWriteProService jobBoardWriteProService = new JobBoardWriteProService();
		boolean isWriteSuccess = jobBoardWriteProService.insertArticle(jobBoardBean);
		
		if(!isWriteSuccess) { 
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); 
			out.println("<script>");
			out.println("alert('게시물 등록 실패!')"); 
			out.println("history.back()"); 
			out.println("</script>"); 
		} else { 
			MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
			boolean isSuccess = memberUpdateProService.updateArticleLP(nickname);
			if(isSuccess) {
				forward = new ActionForward(); 
				forward.setPath("JobBoardList.job");
				forward.setRedirect(true); 
			}
		}
		
		return forward;
	}

}
