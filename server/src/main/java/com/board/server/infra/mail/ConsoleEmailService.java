package com.board.server.infra.mail;

import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Slf4j
@Component
public class ConsoleEmailService implements EmailService {

    @Override
    public void send(EmailMessage emailMessage) throws MessagingException {
        log.info("To: {}, text: {}", emailMessage.getTo(), emailMessage.getText());
    }
}
