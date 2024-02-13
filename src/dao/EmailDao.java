package dao;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailDao {
	public boolean sendAuthenticationEmail(String toEmail, String title, String content) {
		// 회원가입 시 이메일 인증 절차를 위한 이메일 전송 메서드.
		// 원래 MemberDao에 있어야 하지만 import되는 같은 이름의 다른 클래스가 중복되어서 다른 Dao클래스로 분리
		try {
			String id = "javaemailtest@naver.com";  // 네이버 이메일 아이디
			String pw = "!javatest123";        		// 네이버 비밀번호
			
			// 메일 관련 정보
			String host = "smtp.naver.com";
			int port = 465;
			final String username = id;
			final String password = pw;
			
			// 메일 내용
			Properties props = System.getProperties();
			
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.trust", host);
			
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				String un = username;
				String pw = password;
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(un, pw);
				}
			});
			session.setDebug(true); // for debug
			
			// 메일 전송
			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(username));
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			mimeMessage.setSubject(title);
			mimeMessage.setText(content);
			Transport.send(mimeMessage);
			return true;
		} catch(Exception e) {
			// 중간에 예외 발생 시(이메일 비정상적으로 전송 실패할 시)
			e.printStackTrace();
			return false;
		}
	}
}
