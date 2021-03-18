package job_community.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import academy_community.svc.AcademyCommentListService;
import job_community.svc.JobBoardDeleteProService;
import job_community.svc.JobCommentListService;
import job_community.vo.ActionForward;

public class JobCommentPagingAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		System.out.println("JobCommentPagingAction");
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		
	    int cmmnt_page = 1;
		int cmmnt_limit = 3;
		
		if(request.getParameter("reply_page") != null) {
			cmmnt_page = Integer.parseInt(request.getParameter("reply_page")); // 정수로 변환하여 저장
		}
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		JobCommentListService jobCommentListService = new JobCommentListService();

		int cmmnt_count = jobCommentListService.getCommentListCount(post_num);
		//답글 페이지
		int cmmnt_maxPage = (int)((double)cmmnt_count / cmmnt_limit + 0.95);
		// 2. 시작 페이지 번호 계산
		int cmmnt_startPage = (((int)((double)cmmnt_page / 10 + 0.9)) - 1) *10 + 1;
		// 3. 마지막 페이지 번호 계산
		int cmmnt_endPage = cmmnt_startPage + 10 - 1;
		
		// 마지막 페이지 번호가 총 페이지 수 보다 클 경우 총 페이지 수를 마지막 페이지 번호로 설정
		if(cmmnt_endPage > cmmnt_maxPage) {
			cmmnt_endPage = cmmnt_maxPage;
		}
		
		String page = "{\"pageInfo\":["; // cmmntList는 키값이 됨
			page += "{\"cmmnt_page\":\"" + cmmnt_page + "\""; //0
			page += "},";
			page += "{\"cmmnt_maxPage\":\"" + cmmnt_maxPage + "\"";		//1
			page += "},";
			page += "{\"cmmnt_startPage\":\"" + cmmnt_startPage+ "\"";		//1
			page += "},";
			page += "{\"cmmnt_endPage\":\"" + cmmnt_endPage + "\"";		//2
			page += "},";
			page += "{\"cmmnt_count\":\"" + cmmnt_count + "\"";			//3
			page += "}";
		page += "]}";
		
		out.print(page);
		
		return null;
	}
	
}
