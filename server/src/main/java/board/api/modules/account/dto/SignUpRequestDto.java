package board.api.modules.account.dto;

import board.api.modules.account.Account;
import board.api.modules.account.Role;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SignUpRequestDto {

    @NotBlank
    @Pattern(regexp = "([가-힣a-zA-Z0-9_]{3,15}$)")
    private String nickname;

    @Length(min = 8, max = 30)
    @NotBlank
    private String password;

    @Pattern(regexp = ".+@11stcorp.com$")
    @NotBlank
    private String email;

    private String authenticationToken;

    public SignUpRequestDto(String nickname, String password, String email) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }

    public Account toEntity() {
        return new Account(nickname, password, email, Role.USER);
    }
}
