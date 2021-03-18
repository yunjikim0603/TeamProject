package shop.action;

import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.svc.MemberMailService;
import member.vo.SMTPAuthenticator;
import shop.vo.ActionForward;

public class ShopGifticonSendAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ShopGifticonSendAction");
		HttpSession session = request.getSession();
		ActionForward forward = null;
		
		String id = null;
		String barcode = request.getParameter("barcode");
		
		
		if(session.getAttribute("sId") != null) {
			id = (String) session.getAttribute("sId");
		}
		
		MemberMailService memberMailService = new MemberMailService();
		boolean emailChecked = memberMailService.getUserEmailChecked(id);
		String email = memberMailService.getMemberEmail(id);

		if(emailChecked == true) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); 
			out.println("<script>"); 
			out.println("alert('이미 인증 된 회원입니다.')"); 
			out.println("location.href = ''"); 
			out.println("</script>"); 
		}
		
		// 사용자에게 보낼 메시지를 기입합니다.
		String host = "http://localhost:8080/The_First_modify/";
		String from = "itwillbs_191015@gmail.com";
		String to = email;
		String subject = "기프티콘이 도착했어요!";
		String content = "하트를 클릭하여 기프티콘을 받으세요! <a href='" + host + "ShopGifticonGet.shop?barcode="+barcode+"'> ♡ </a>";

		System.out.println(barcode);
		// SMTP에 접속하기 위한 정보를 기입합니다.
		Properties p = new Properties();
		p.put("mail.smtp.user", from);
		p.put("mail.smtp.host", "smtp.googlemail.com");
		p.put("mail.smtp.port", "465");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		
		try{
		    Authenticator auth = new SMTPAuthenticator();
		    Session ses = Session.getInstance(p, auth);
		    ses.setDebug(true);
		    MimeMessage msg = new MimeMessage(ses); 
		    msg.setSubject(subject);
		    Address fromAddr = new InternetAddress(from);
		    msg.setFrom(fromAddr);
		    Address toAddr = new InternetAddress(to);
		    msg.addRecipient(Message.RecipientType.TO, toAddr);
		    msg.setContent(content, "text/html;charset=UTF-8");
		    Transport.send(msg);
		} catch(Exception e){
		    e.printStackTrace();
		    
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); 
			out.println("<script>"); 
			out.println("alert('이메일 전송 실패!');"); 
			out.println("history.back()"); 
			out.println("</script>"); 
		}
//		session.invalidate();
		forward = new ActionForward(); 
		forward.setPath("ShopMain.shop"); //성공시 메인으로 이동
		forward.setRedirect(true);
		
		return forward;
	}
}
