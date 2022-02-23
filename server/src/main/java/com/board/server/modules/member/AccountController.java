package com.board.server.modules.member;

import com.board.server.exception.InvalidSignUpRequestException;
import com.board.server.modules.member.dto.SignUpRequestDto;
import com.board.server.modules.member.dto.SignUpResponseDto;
import com.board.server.modules.member.mapper.AccountMapper;
import com.board.server.modules.member.validator.SignUpValidator;
import java.net.URI;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountController {

    private static final int FIRST_ERROR = 0;

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final SignUpValidator signUpValidator;

    @InitBinder("signUpRequestDto")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator);
    }

    @PostMapping("/members/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequestDto signupRequestDto, Errors errors)
            throws MessagingException {
        if (errors.hasErrors()) {
            throw new InvalidSignUpRequestException(getFirstErrorDefaultMessage(errors));
        }
        accountService.signUp(signupRequestDto);

        return ResponseEntity.ok().build();
    }

    private String getFirstErrorDefaultMessage(Errors errors) {
        return errors.getAllErrors().get(FIRST_ERROR).getDefaultMessage();
    }

    @GetMapping("/members/authentication-mail")
    public ResponseEntity<SignUpResponseDto> checkAuthenticationToken(String email, String token) {
        Account newAccount = accountService.completeSignUp(email, token);

        return ResponseEntity.created(URI.create("/members/" + newAccount.getId()))
                .body(accountMapper.toResponseDto(newAccount));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public String InvalidSignUpRequestExceptionHandler(InvalidSignUpRequestException exception,
            HttpServletRequest request) {
        log.error("{}의 회원 가입 요청 실패: {}", getRemoteAddress(request), exception.getMessage());

        return exception.getMessage();
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
