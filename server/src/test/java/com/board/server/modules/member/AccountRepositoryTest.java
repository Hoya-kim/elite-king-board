package com.board.server.modules.member;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @DisplayName("계정 정보를 저장 한다.")
    @Test
    void Given_When_Then() {
        // Given
        Account account = new Account("kim", "12341234", "test@11stcorp.com", Role.USER);

        // When
        Account newAccount = accountRepository.save(account);

        // Then
        assertThat(newAccount).isEqualTo(account);
    }
}
