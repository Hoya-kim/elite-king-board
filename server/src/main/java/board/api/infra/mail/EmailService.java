package board.api.infra.mail;

import javax.mail.MessagingException;

public interface EmailService {

    void send(EmailMessage emailMessage) throws MessagingException;
}
