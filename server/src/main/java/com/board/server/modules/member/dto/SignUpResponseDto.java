package com.board.server.modules.member.dto;

import com.board.server.modules.member.Account;
import com.board.server.modules.member.Role;
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
        this.joinedAt = account.getJoinedAt();
        this.role = account.getRole();
    }
}
