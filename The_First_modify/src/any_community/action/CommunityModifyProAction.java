package any_community.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import any_community.svc.CommunityDetailService;
import any_community.svc.CommunityModifyService;
import any_community.vo.ActionForward;
import any_community.vo.CommunityBean;
import coding_free.svc.CodingFreeDetailService;
import coding_free.vo.CodingFreeBean;

public class CommunityModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CommunityModifyProAction");

		ActionForward forward = null;
		
		String saveFolder = "/any_community/images";
		ServletContext context = request.getServletContext();
		String realFolder = context.getRealPath(saveFolder);

		int fileSize = 1024 * 1024 * 10;
		
		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8",
				new DefaultFileRenamePolicy());

		String realFilesystemName = multi.getFilesystemName((String) multi.getFileNames().nextElement());
		System.out.println("실제 파일명 : " + realFilesystemName);
		
		int num = Integer.parseInt(multi.getParameter("post_num"));
		String page = request.getParameter("page");
		String subject = multi.getParameter("subject");
		String content = multi.getParameter("content");
		
		CommunityDetailService communityDetailService = new CommunityDetailService();
		CommunityBean communityBean = communityDetailService.getArticle(num);
		
		if(realFilesystemName==null) {
			realFilesystemName = communityBean.getFile();
		}
		
		CommunityModifyService communityModifyService = new CommunityModifyService();
		
		CommunityBean article = new CommunityBean();
		article.setNum(num);
		article.setSubject(subject);
		article.setContent(content);
		article.setFile(realFilesystemName);
			
		boolean isModifySuccess = communityModifyService.modifyArticle(article);
	
		if(!isModifySuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 수정 실패!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			forward = new ActionForward();
			forward.setPath("CommunityDetail.any?num=" + num + "&page=" + page);
			forward.setRedirect(true);
		}
		
		return forward;
	}

}
