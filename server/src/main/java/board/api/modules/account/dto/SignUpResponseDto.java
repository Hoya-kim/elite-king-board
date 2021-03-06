package board.api.modules.account.dto;

import board.api.modules.account.Account;
import board.api.modules.account.Role;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SignUpResponseDto {

    private String nickname;

    private String email;

    private LocalDateTime joinedAt;

    private Role role;

    public SignUpResponseDto(Account account) {
        this.nickname = account.getNickname();
        this.email = account.getEmail();
        this.joinedAt = account.getCreatedAt();
        this.role = account.getRole();
    }
}
