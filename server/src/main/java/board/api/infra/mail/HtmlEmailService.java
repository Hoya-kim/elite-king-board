package board.api.infra.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Profile("dev")
@RequiredArgsConstructor
@Slf4j
@Component
public class HtmlEmailService implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void send(EmailMessage emailMessage) throws MessagingException {
        log.info("{}로 인증 메일 전송 시도", emailMessage.getTo());
        MimeMessage mimeMessage = createMimeMessage(emailMessage);
        javaMailSender.send(mimeMessage);
    }

    private MimeMessage createMimeMessage(EmailMessage emailMessage) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        setMimeMessage(mimeMessage, emailMessage);

        return mimeMessage;
    }

    private void setMimeMessage(MimeMessage mimeMessage, EmailMessage emailMessage)
            throws MessagingException {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        mimeMessageHelper.setTo(emailMessage.getTo());
        mimeMessageHelper.setSubject(emailMessage.getSubject());
        mimeMessageHelper.setText(emailMessage.getText(), true);
    }
}
