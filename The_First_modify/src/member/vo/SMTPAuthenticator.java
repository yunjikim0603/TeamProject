package member.vo;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {

	// 구글기준 (라이브러리  mail.jar )
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("subin.lee1226","kingsubin"); // 보낼 메일 구글 계정의 id,pass입력
	}
}
