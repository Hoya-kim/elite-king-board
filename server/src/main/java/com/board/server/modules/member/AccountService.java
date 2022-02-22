package com.board.server.modules.member;

import com.board.server.config.AppProperties;
import com.board.server.infra.mail.EmailMessage;
import com.board.server.infra.mail.EmailService;
import com.board.server.modules.member.dto.SignUpRequestDto;
import java.util.UUID;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AccountService {

    private static final String AUTHENTICATION_LINK_FORMAT = "%s/members/authentication-mail/?email=%s&token=%s";
    private static final String ACCOUNT_CACHE_NAME = "accountTemp";
    private static final String AUTHENTICATION_EMAIL_VIEW = "mail/authentication";
    private static final String AUTHENTICATION_EMAIL_SUBJECT = "Elite King Board 인증 메일 입니다.";

    private final PasswordEncoder passwordEncoder;
    private final CacheManager cacheManager;
    private final EmailService emailService;
    private final ITemplateEngine templateEngine;
    private final AppProperties appProperties;

    public void signUp(SignUpRequestDto requestDto) throws MessagingException {
        EmailMessage emailMessage = sendAuthenticationEmail(requestDto); //TODO 이름 변경.
        emailService.send(emailMessage);

        saveRequestToCache(requestDto);
    }

    private void saveRequestToCache(SignUpRequestDto requestDto) {
        requestDto.setPassword(encode(requestDto.getPassword()));
        cacheManager.getCache(ACCOUNT_CACHE_NAME).put(requestDto.getEmail(), requestDto);
    }

    private EmailMessage sendAuthenticationEmail(SignUpRequestDto requestDto) throws MessagingException {
        requestDto.setAuthenticationToken(UUID.randomUUID().toString());

        Context context = createContext(requestDto);
        String view = templateEngine.process(AUTHENTICATION_EMAIL_VIEW, context);

        EmailMessage message = EmailMessage.builder()
                .to(requestDto.getEmail())
                .subject(AUTHENTICATION_EMAIL_SUBJECT)
                .text(view)
                .build();

        return message;
    }

    private Context createContext(SignUpRequestDto requestDto) {
        Context context = new Context();
        context.setVariable("nickname", requestDto.getNickname());
        context.setVariable("link",
                String.format(AUTHENTICATION_LINK_FORMAT, appProperties.getHost(), requestDto.getEmail(),
                        requestDto.getAuthenticationToken()));

        return context;
    }

    private String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
