package coding.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import coding.svc.CodingListService;
import coding.svc.CodingReplyListService;
import coding.svc.CodingReplyProService;
import coding.svc.CodingWriteProService;
import coding.vo.CodingBean;
import coding.vo.Coding_refBean;
import coding.vo.PageInfo;
import vo.ActionForward;

public class CodingReplyWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		System.out.println("CodingReplyWirtePro");
		String saveFolder = "/codingUpload"; //가상위치 (이클립스가 생성한 폴더)
		ServletContext context2 = request.getServletContext();
		String realFolder = context2.getRealPath(saveFolder); //가상위치에 대한 실제파일주소
		
		int fileSize = 1024*1024*10; //10Mbyte
		
		// MultipartRequest 객체생성, cos.jar API 필수
		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		
		//MultipartRequest 객체로부터 전달 된 파라미터들 가져오기
		int post_num = Integer.parseInt(multi.getParameter("post_num"));
//		String id = request.getParameter("id");
		String nickname = multi.getParameter("nickname");
		String subject = multi.getParameter("subject");
		String content =multi.getParameter("content");
//		int readcount = Integer.parseInt(multi.getParameter("readcount"));
		String file = multi.getOriginalFileName((String)multi.getFileNames().nextElement());
//		int isSelected = Integer.parseInt(multi.getParameter("isSelected"));
//		int password = Integer.parseInt(multi.getParameter("password"));
		
		
		Coding_refBean coding_refBean = new Coding_refBean();
		coding_refBean.setPost_num(post_num);
		coding_refBean.setNickname(nickname);
		coding_refBean.setSubject(subject);
		coding_refBean.setContent(content);
		coding_refBean.setFile(file);
//		codingBean.setReadcount(readcount);
//		coding_refBean.setIsSelected(isSelected);
//		codingBean.setPassword(password);
		
		CodingReplyProService codingReplyProService = new CodingReplyProService();
		boolean isSuccess = codingReplyProService.insertArticle_ref(coding_refBean);
		
		
		if(isSuccess) {
			request.setAttribute("post_num", post_num);

			forward = new ActionForward();
			forward.setPath("CodingDetail.code?post_num="+post_num);
			forward.setRedirect(true);
		}
		
		return forward;
	}

}
