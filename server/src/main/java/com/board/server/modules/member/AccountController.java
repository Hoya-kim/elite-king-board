package com.board.server.modules.member;

import com.board.server.modules.member.dto.SignUpRequestDto;
import com.board.server.modules.member.dto.SignUpResponseDto;
import com.board.server.modules.member.mapper.AccountMapper;
import java.net.URI;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping("/members/sign-up")
    public ResponseEntity signUp(@RequestBody @Valid SignUpRequestDto signupRequestDto)
            throws MessagingException {
        accountService.signUp(signupRequestDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/members/authentication-mail")
    public ResponseEntity<SignUpResponseDto> checkAuthenticationToken(String email, String token) {
        Account newAccount = accountService.completeSignUp(email, token);

        return ResponseEntity.created(URI.create("/members/" + newAccount.getId()))
                .body(accountMapper.toResponseDto(newAccount));
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public String MessagingExceptionHandler(MessagingException exception, HttpServletRequest request) {
        log.info("{}의 {}요청 인증메일 전송 실패", getRemoteAddress(request), request.getRequestURI());
        log.error("Server Error: ", exception.getMessage());

        return "/error/5xx";
    }

    private String getRemoteAddress(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");

        if (remoteAddr == null) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        }
        if (remoteAddr == null) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        if (remoteAddr == null) {
            remoteAddr = request.getRemoteAddr();
        }
        return remoteAddr;
    }
}
