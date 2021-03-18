package any_community.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import any_community.svc.CommunityWriteService;
import any_community.vo.ActionForward;
import any_community.vo.CommunityBean;
import member.svc.MemberUpdateProService;

public class CommunityWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CommunityWriteProAction!");
		ActionForward forward = null;

		String saveFolder = "/any_community/images";
		ServletContext context = request.getServletContext();
		String realFolder = context.getRealPath(saveFolder);
		
		int fileSize = 1024 * 1024 * 10;

		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8",
				new DefaultFileRenamePolicy());

		String realFilesystemName = multi.getFilesystemName((String) multi.getFileNames().nextElement());
		System.out.println("실제 파일명 : " + realFilesystemName);

		String nickname = multi.getParameter("any_nickname");
		CommunityBean cb = new CommunityBean(
				multi.getParameter("any_nickname"),
				multi.getParameter("any_subject"),
				multi.getParameter("any_content"),
				realFilesystemName);
		
		CommunityWriteService communityWriteService = new CommunityWriteService();
		boolean isWriteSuccess = communityWriteService.writeArticle(cb);
		
		if(!isWriteSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 작성 실패')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			MemberUpdateProService memberUpdateProService = new MemberUpdateProService();
			boolean isSuccess = memberUpdateProService.minusArticletLP(nickname);
			if(isSuccess) {
				forward = new ActionForward(); 
				forward.setPath("CommunityList.any");
				forward.setRedirect(true);
			}
		}
		
		return forward;
	}

}
