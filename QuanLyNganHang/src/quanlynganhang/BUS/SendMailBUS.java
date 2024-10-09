package quanlynganhang.BUS;

import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import quanlynganhang.BUS.validation.InputValidation;

public class SendMailBUS {
    private final String EMAIL_NAME = "notification.testproject31203@gmail.com";
    private final String EMAIL_PASSWORD = "tazc tlss vdjw mftm";
    private Properties props;
    private Authenticator auth;
    private Session session;
    
    public SendMailBUS() {
        props = new Properties();
        initProps();
    }
    
    private void initProps() {
        if (props != null && props.isEmpty()) {
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
        }
        
        if (auth == null) {
            auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_NAME, EMAIL_PASSWORD);
                }
            };
        }
    }
    
    public boolean guiMaXacNhan(String emailTo, int codeAuth, String requestType) {
        session = Session.getInstance(props, auth);
        
        MimeMessage msg = new MimeMessage(session);
        
        try {
            String htmlContent = "<p>Chào bạn</p>" 
        + "<p>Bạn đang " + requestType + ", Mã xác nhận là <span style='color: rgb(78, 164, 220); font-weight: bold;'>" + codeAuth + "</span>.</p>"
        + "<p>Vui lòng hoàn thành xác nhận trước khi hai hòn dái của bạn nổ tung.</p>" 
        + "<p><span style='color: rgb(255, 0, 0)'>Tin nhắn này từ đồ án mà chúng tôi đang thực hành, không ảnh hưởng đến thực tế</span>. Thân trọng</p>"
        + "<p style='font-size: 13px; font-weight: bold; color: rgb(119, 119, 119)'>Đây là thư từ hệ thống, vui lòng không trả lời thư.</p>";
            
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setFrom(new InternetAddress(EMAIL_NAME));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo, false));
            msg.setSubject(codeAuth + " là mã xác nhận tài khoản của bạn");
            msg.setReplyTo(null);
            msg.setContent(htmlContent, "text/html; charset=UTF-8");
            
            Transport.send(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int taoMaXacNhan() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}
