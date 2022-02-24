package board.api;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import board.api.modules.account.Account;
import board.api.modules.account.AccountService;
import board.api.modules.account.Role;
import board.api.modules.account.dto.SignUpRequestDto;
import board.api.modules.account.dto.SignUpResponseDto;
import board.api.modules.account.mapper.AccountMapper;
import board.api.modules.account.validator.SignUpValidator;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@MockBean(JpaMetamodelMappingContext.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AccountMapper accountMapper;
    @MockBean
    private AccountService accountService;
    @MockBean
    private SignUpValidator signUpValidator;

    private String requestContent;

    @BeforeEach
    void sut() {
        requestContent = "{\"nickname\": \"kimtaejun\",\"password\": \"12341234\",\"email\": \"taejun0509@11stcorp.com\"}";
    }

    @DisplayName("회원 가입을 요청을 받는다. - 정상")
    @Test
    void GivenRequest_WhenSignUp_ThenOk() throws Exception {
        // Given
        doNothing().when(accountService).signUp(any(SignUpRequestDto.class));

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
        Account account = new Account("kimtaejun", "12341234", "taejun0509@11stcorp.com",
            Role.USER);
        when(accountService.completeSignUp(any(String.class), any(String.class))).thenReturn(
            account);
        when(accountMapper.toResponseDto(any(Account.class))).thenReturn(
            new SignUpResponseDto(account));

        // When & Then
        mvc.perform(get("/accounts/authentication-mail")
                .param("email", "taejun0509@11stcorp.com")
                .param("token", UUID.randomUUID().toString()))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(jsonPath("$.nickname", equalTo("kimtaejun")))
            .andExpect(jsonPath("$.email", equalTo("taejun0509@11stcorp.com")));
    }
}
