package board.api.modules.account;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import board.api.modules.account.dto.SignUpRequestDto;
import board.api.modules.account.mapper.AccountMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountMapper accountMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private String requestContent;


    @BeforeEach
    void sut() {
        requestContent = "{\"nickname\": \"kimtaejun\",\"password\": \"12341234\",\"email\": \"taejun0509@11stcorp.com\"}";
        accountRepository.deleteAll();
    }

    @DisplayName("회원 가입을 요청을 받는다. - 임시 저장소에 이미 존재하는 이메일")
    @Test
    void GivenRequest_WhenExistsEmailInCache_ThenThrowException() throws Exception {
        // Given
        cacheManager.getCache("accountTemp").put("taejun0509@11stcorp.com", "any");

        // When & Then
        mvc.perform(post("/accounts/sign-up")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("회원 가입을 요청을 받는다. - Repository 이미 존재하는 이메일")
    @Test
    void GivenRequest_WhenExistsEmail_ThenThrowException() throws Exception {
        // Given
        accountRepository.save(
                new Account("kim", "12341234", "taejun0509@11stcorp.com", Role.USER));

        // When & Then
        mvc.perform(post("/accounts/sign-up")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("회원 가입을 요청을 받는다. - 닉네임에 유효하지 않은 특수문자가 존재")
    @Test
    void GivenInvalidName_WhenSignUp_ThenThrowException() throws Exception {
        String request = "{\"nickname\": \"kimtaejun!\",\"password\": \"12341234\",\"email\": \"taejun0509@11stcorp.com\"}";

        // When & Then
        mvc.perform(post("/accounts/sign-up")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("회원 가입을 요청을 받는다. - 너무 짧은 이름")
    @Test
    void GivenShortName_WhenSignUp_ThenThrowException() throws Exception {
        String request = "{\"nickname\": \"HI\",\"password\": \"12341234\",\"email\": \"taejun0509@11stcorp.com\"}";

        // When & Then
        mvc.perform(post("/accounts/sign-up")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("회원 가입을 요청을 받는다. - 너무 긴 이름")
    @Test
    void GivenlongName_WhenSignUp_ThenThrowException() throws Exception {
        String request = "{\"nickname\": \"KingGodGeneralUno!\",\"password\": \"12341234\",\"email\": \"taejun0509@11stcorp.com\"}";

        // When & Then
        mvc.perform(post("/accounts/sign-up")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("회원 가입을 요청을 받는다. - 11번가 이메일이 아님")
    @Test
    void GivenInvalidEmail_WhenSignUp_ThenThrowException() throws Exception {
        String request = "{\"nickname\": \"kimtaejun\",\"password\": \"12341234\",\"email\": \"taejun0509@naver.com\"}";

        // When & Then
        mvc.perform(post("/accounts/sign-up")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("회원 가입을 요청을 받는다. - 너무 짧은 비밀번호")
    @Test
    void GivenShortPassword_WhenSignUp_ThenThrowException() throws Exception {
        String request = "{\"nickname\": \"kimtaejun\",\"password\": \"1234\",\"email\": \"taejun0509@11stcorp.com\"}";

        // When & Then
        mvc.perform(post("/accounts/sign-up")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("회원 가입을 요청을 받는다. - 너무 긴 비밀번호")
    @Test
    void GivenLongPassword_WhenSignUp_ThenThrowException() throws Exception {
        String request = "{\"nickname\": \"kimtaejun\",\"password\": \"1234567890123456789012345678901\",\"email\": \"taejun0509@11stcorp.com\"}";

        // When & Then
        mvc.perform(post("/accounts/sign-up")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("로그인을 시도하여 JWT 토큰을 발급 받는다.")
    @Test
    void givenAccount_whenLogin_thenJwtToken() throws Exception {
        // Given
        Map<String, String> userInfo = new HashMap<>() {
            {
                put("email", "taejun0509@11stcorp.com");
                put("password", "12341234");
            }
        };
        String request = objectMapper.writeValueAsString(userInfo);
        accountRepository.save(
                new Account("kimtaejun", passwordEncoder.encode("12341234"), "taejun0509@11stcorp.com", Role.USER));

        // When & Then
        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.AUTHORIZATION));
    }

    @DisplayName("회원 가입을 요청을 받는다. - 정상")
    @Test
    void GivenRequest_WhenSignUp_ThenOk() throws Exception {
        // When & Then
        mvc.perform(post("/accounts/sign-up")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("인증 요청을 받는다. - 정상")
    @Test
    void GivenSignUpAccountRequest_WhenAuthenticationRequest_ThenSaveAccount() throws Exception {
        // Given
        String token = UUID.randomUUID().toString();
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto("kimtaejun", "12341234", "taejun0509@11stcorp.com");
        signUpRequestDto.setAuthenticationToken(token);
        cacheManager.getCache("accountTemp").put("taejun0509@11stcorp.com", signUpRequestDto);

        // When & Then
        mvc.perform(get("/accounts/authentication-mail")
                        .param("email", "taejun0509@11stcorp.com")
                        .param("token", token))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.nickname", equalTo("kimtaejun")))
                .andExpect(jsonPath("$.email", equalTo("taejun0509@11stcorp.com")));
    }
}
