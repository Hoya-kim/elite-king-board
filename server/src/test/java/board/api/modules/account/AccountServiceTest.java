package board.api.modules.account;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

import board.api.infra.mail.EmailMessage;
import board.api.infra.mail.EmailService;
import board.api.modules.account.dto.SignUpRequestDto;
import javax.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private CacheManager cacheManager;
    @MockBean
    private EmailService emailService;
    @Autowired
    private AccountService accountService;

    private SignUpRequestDto signUpRequestDto;

    @BeforeEach
    void before() {
        signUpRequestDto = new SignUpRequestDto("kim", "12341234", "test@11stcorp.com");
    }

    @DisplayName("회원 가입 요청이 들어오면 캐시에 임시로 저장한다.")
    @Test
    void GivenRequest_WhenSignUp_ThenSaveRequestToCache() throws MessagingException {
        // When
        accountService.signUp(signUpRequestDto);

        // Then
        assertThat(
            cacheManager.getCache("accountTemp").get(signUpRequestDto.getEmail()).get()).isEqualTo(
            signUpRequestDto);
    }

    @DisplayName("회원 가입 요청이 들어오면 인증 메일을 전송한다.")
    @Test
    void GivenRequest_WhenSignUp_ThenSendAuthenticationEmail() throws MessagingException {
        // When
        accountService.signUp(signUpRequestDto);

        // Then
        then(emailService).should().send(any(EmailMessage.class));
    }

    @DisplayName("회원 가입 인증 요청이 들어온다 - 정상")
    @Test
    void GivenRequest_WhenAuthenticationRequest_ThenSaveRequestToRepository()
        throws MessagingException {
        // Given
        accountService.signUp(signUpRequestDto);

        // When
        accountService.completeSignUp(signUpRequestDto.getEmail(),
            signUpRequestDto.getAuthenticationToken());

        // Then
        Account account = accountRepository.findAll().get(0);
        assertThat(account.getEmail()).isEqualTo(signUpRequestDto.getEmail());
        assertThat(account.getNickname()).isEqualTo(signUpRequestDto.getNickname());
    }

    @DisplayName("회원 가입 인증 요청이 들어온다 - 캐시에 존재하지 않음.")
    @Test
    void GivenRequestSignUp_WhenNotFoundFromCache_ThenThrowsException() {
        // When & Then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> accountService.completeSignUp(signUpRequestDto.getEmail(),
                signUpRequestDto.getAuthenticationToken()));
    }
}
