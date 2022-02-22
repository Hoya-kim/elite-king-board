package com.board.server.modules.member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.board.server.modules.member.dto.SignUpRequestDto;
import com.board.server.modules.member.mapper.AccountMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    AccountMapper accountMapper;
    @MockBean
    private AccountService accountService;

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
        mvc.perform(post("/members/sign-up")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
