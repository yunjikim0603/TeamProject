package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.tribes.MembershipService;

import member.svc.KakaoJoinProService;
import member.svc.KakaoLoginProService;
import member.vo.ActionForward;
import member.vo.MemberBean;

public class KakaoJoinProAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("kakoJoinProAction!");
		ActionForward forward = null;
		String id = request.getParameter("id");
		String password = request.getParameter("id") + "pass";
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		MemberBean member = new MemberBean(id, password, nickname, email);
		System.out.println("id : " + id + ", password : " + password + ", nickname : " + nickname + ", email : " + email);
		
		KakaoJoinProService kakaoJoinProService = new KakaoJoinProService();
		MemberBean mb = KakaoJoinProService.getMember(id);
		
		System.out.println(id);
		
		HttpSession session = request.getSession(); 
		if(mb!=null) {
			//아이디가있음
			System.out.println("아이디있음");
			session.setAttribute("sId", id);
			session.setAttribute("pass", password); //세션값 생성이 그냥 아에 로그인
			session.setAttribute("nickname", nickname);
//			session.setAttribute("nickname", nickname);
			forward = new ActionForward();
			forward.setPath("main.all");//main 가상주소
			forward.setRedirect(true);
		}else {
			System.out.println("아이디없음");
			kakaoJoinProService.joinMember(member);
			session.setAttribute("sId", id);
			session.setAttribute("pass", password); //세션값 생성이 그냥 아에 로그인
			session.setAttribute("nickname", nickname);
			forward = new ActionForward();
			forward.setPath("main.all");
			forward.setRedirect(true);
		}
		

//		boolean isJoinSuccess = kakaoJoinProService.joinMember(member);
//		
//		if (!isJoinSuccess) { // 가입 실패
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out = response.getWriter(); 
//			out.println("<script>"); 
//			out.println("alert('회원 가입 실패!');"); 
//			out.println("history.back()"); 
//			out.println("</script>");
//		} else { // 가입 성공
//			System.out.println("카카오 회원가입 성공!!");
//			session.setAttribute("sId", id);
//			session.setAttribute("pass", password); //세션값 생성이 그냥 아에 로그인
//			session.setAttribute("nickname", nickname);
//			
//			forward = new ActionForward();
//			forward.setPath("KakaoLoginPro.kakao");
//			forward.setRedirect(true);
//			//바로 다음액션으로 넘기는 방법이랑 기존에 값잇는거 비교해서 넘기는 방법 물어보기
//		}
//		
		return forward;
	}
	
	

}
