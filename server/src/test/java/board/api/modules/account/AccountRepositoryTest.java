package board.api.modules.account;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    void before() {
        account = new Account("kim", "12341234", "test@11stcorp.com", Role.USER);
    }

    @DisplayName("계정 정보를 저장 한다.")
    @Test
    void GivenAccount_WhenSaveAccount_ThenSave() {
        // When
        Account newAccount = accountRepository.save(account);

        // Then
        assertThat(newAccount).isEqualTo(account);
    }

    @DisplayName("Repository에 해당 Email을 가진 계정이 존재하는지 반환 - 존재")
    @Test
    void GivenEmail_WhenQueryIfExists_ThenTrue() {
        // Given
        Account newAccount = accountRepository.save(account);

        // When & Then
        assertThat(accountRepository.existsByEmail(account.getEmail())).isTrue();
    }

    @DisplayName("Repository에 해당 Email을 가진 계정이 존재하는지 반환 - 존재")
    @Test
    void GivenEmail_WhenQueryIfExists_ThenFalse() {
        // When & Then
        assertThat(accountRepository.existsByEmail(account.getEmail())).isFalse();
    }
}
