package board.api.modules.account;

import board.api.exception.InvalidSignUpRequestException;
import board.api.modules.account.dto.SignUpRequestDto;
import board.api.modules.account.dto.SignUpResponseDto;
import board.api.modules.account.mapper.AccountMapper;
import board.api.modules.account.validator.SignUpValidator;
import board.api.utils.RequestUtils;
import java.net.URI;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private static final int FIRST_ERROR = 0;

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final SignUpValidator signUpValidator;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequestDto signupRequestDto,
        Errors errors)
        throws MessagingException {
        signUpValidator.validate(signupRequestDto, errors);
        if (errors.hasErrors()) {
            throw new InvalidSignUpRequestException(getFirstErrorDefaultMessage(errors));
        }

        accountService.signUp(signupRequestDto);

        return ResponseEntity.ok().build();
    }

    private String getFirstErrorDefaultMessage(Errors errors) {
        return errors.getAllErrors().get(FIRST_ERROR).getDefaultMessage();
    }

    @GetMapping("/authentication-mail")
    public ResponseEntity<SignUpResponseDto> checkAuthenticationToken(String email, String token) {
        Account newAccount = accountService.completeSignUp(email, token);

        return ResponseEntity.created(URI.create("/members/" + newAccount.getId()))
            .body(accountMapper.toResponseDto(newAccount));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public String invalidSignUpRequestExceptionHandler(InvalidSignUpRequestException exception,
        HttpServletRequest request) {
        log.error("{}의 회원 가입 요청 실패: {}", RequestUtils.getRemoteAddress(request), exception.getMessage());

        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public String MessagingExceptionHandler(MessagingException exception,
        HttpServletRequest request) {
        log.info("{}의 {}요청 인증메일 전송 실패", RequestUtils.getRemoteAddress(request), request.getRequestURI());
        log.error("Server Error: ", exception.getMessage());

        return "/error/5xx";
    }
}
