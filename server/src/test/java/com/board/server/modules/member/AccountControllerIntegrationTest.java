package com.board.server.modules.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
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

    private String requestContent;

    @BeforeEach
    void sut() {
        requestContent = "{\"nickname\": \"kimtaejun\",\"password\": \"12341234\",\"email\": \"taejun0509@11stcorp.com\"}";
    }

    @DisplayName("회원 가입을 요청을 받는다. - 임시 저장소에ㅐ 이미 존재하는 이메일")
    @Test
    void GivenRequest_WhenExistsEmailInCache_ThenThrowException() throws Exception {
        // Given
        cacheManager.getCache("accountTemp").put("taejun0509@11stcorp.com", "any");

        // When & Then
        mvc.perform(post("/members/sign-up")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("회원 가입을 요청을 받는다. - Repository 이미 존재하는 이메일")
    @Test
    void GivenRequest_WhenExistsEmail_ThenThrowException() throws Exception {
        // Given
        accountRepository.save(new Account("kim", "12341234", "taejun0509@11stcorp.com", Role.USER));

        // When & Then
        mvc.perform(post("/members/sign-up")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
