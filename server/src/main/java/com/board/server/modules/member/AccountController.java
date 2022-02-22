package com.board.server.modules.member;

import com.board.server.modules.member.dto.SignUpRequestDto;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/members/sign-up")
    public ResponseEntity signUp(@RequestBody @Valid SignUpRequestDto signupRequestDto)
            throws MessagingException {
        accountService.signUp(signupRequestDto);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler
    public String MessagingExceptionHandler(MessagingException exception, HttpServletRequest request) {
        log.info("{}의 {}요청 인증메일 전송 실패", getRemoteAddress(request), request.getRequestURI());
        log.error("Server Error: ", exception.getMessage());

        return "/error/5xx";
    }

    private String getRemoteAddress(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");

        if (remoteAddr == null) {
            return request.getHeader("Proxy-Client-IP");
        }
        if (remoteAddr == null) {
            return request.getHeader("WL-Proxy-Client-IP");
        }
        if (remoteAddr == null) {
            return request.getRemoteAddr();
        }
        return remoteAddr;
    }
}
